package mage.player.ai.scoring;

import mage.MageObject;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.cards.Card;
import mage.constants.CommanderCardType;
import mage.constants.Zone;
import mage.filter.FilterMana;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.player.ai.AiStrategyScore;
import mage.players.Player;
import mage.util.CardUtil;
import mage.watchers.common.CommanderPlaysCountWatcher;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Values mana development by the colored spells it actually unlocks, not only
 * by raw mana-source count. This is especially important in Commander where
 * command-zone access and multicolor utility lands can matter more than one
 * extra generic source.
 */
public final class ColorAccessScoreModule implements AiScoreModule {

    private static final String ENABLED_PROPERTY = "xmage.ai.strategy.colorAccess.enabled";
    private static final String APPLY_PROPERTY = "xmage.ai.strategy.colorAccess.apply";
    private static final String MAX_MODIFIER_PROPERTY = "xmage.ai.strategy.colorAccess.maxScoreModifier";

    private final boolean globalApplyModifiers;

    public ColorAccessScoreModule(AiScoreModuleConfig config) {
        this.globalApplyModifiers = config != null && config.isApplyModifiers();
    }

    @Override
    public String getName() {
        return "color-access";
    }

    @Override
    public AiStrategyScore evaluate(AiScoringContext context) {
        if (context == null) {
            return AiStrategyScore.none(0);
        }
        if (!AiScoreSupport.isEnabled(ENABLED_PROPERTY)) {
            return AiStrategyScore.none(context.getBaseScore());
        }
        Game before = context.getDecisionGame();
        Game after = context.getFinalGame();
        UUID playerId = context.getPlayerId();
        Ability action = context.getAction();
        if (before == null || after == null || playerId == null || action == null || !AiScoreSupport.isMainPhase(before)) {
            return AiStrategyScore.none(context.getBaseScore());
        }
        Player player = before.getPlayer(playerId);
        if (player == null) {
            return AiStrategyScore.none(context.getBaseScore());
        }

        ColorState beforeState = ColorState.from(before, player);
        ColorState afterState = ColorState.from(after, player);
        if (!afterState.improvesOn(beforeState) && !AiScoreSupport.developsOwnMana(before, action, playerId)) {
            return AiStrategyScore.none(context.getBaseScore());
        }

        List<AiStrategyScore.Contribution> contributions = new ArrayList<>();
        addHandUnlockContribution(contributions, before, player, beforeState, afterState);
        addCommanderUnlockContribution(contributions, before, player, beforeState, afterState);
        addRelevantColorContribution(contributions, before, player, beforeState, afterState);

        int rawModifier = contributions.stream().mapToInt(AiStrategyScore.Contribution::getValue).sum();
        rawModifier = AiScoreSupport.clamp(rawModifier, AiScoreSupport.moduleCap(MAX_MODIFIER_PROPERTY, 45));
        if (rawModifier == 0 && contributions.isEmpty()) {
            return AiStrategyScore.none(context.getBaseScore());
        }
        return AiStrategyScore.of(
                context.getBaseScore(),
                rawModifier,
                AiScoreSupport.apply(rawModifier, globalApplyModifiers, APPLY_PROPERTY),
                contributions
        );
    }

    private static void addHandUnlockContribution(List<AiStrategyScore.Contribution> contributions,
                                                  Game game,
                                                  Player player,
                                                  ColorState before,
                                                  ColorState after) {
        List<Card> unlocked = new ArrayList<>();
        int colorBlockedBefore = 0;
        int colorBlockedAfter = 0;
        for (Card card : player.getHand().getCards(game)) {
            if (!shouldConsiderSpell(card, game)) {
                continue;
            }
            CastNeed need = CastNeed.of(card);
            if (need.requiredColors.isEmpty()) {
                continue;
            }
            boolean hadColors = before.covers(need.requiredColors);
            boolean hasColors = after.covers(need.requiredColors);
            if (!hadColors) {
                colorBlockedBefore++;
            }
            if (!hasColors) {
                colorBlockedAfter++;
            }
            if (!before.canCast(need) && after.canCast(need)) {
                unlocked.add(card);
            }
        }
        if (!unlocked.isEmpty()) {
            int value = 10 + Math.min(30, unlocked.size() * 8 + bestManaValue(unlocked));
            contributions.add(new AiStrategyScore.Contribution(
                    "color-access:hand-unlocked",
                    value,
                    "unlocks " + unlocked.size() + " hand card(s): " + summarizeNames(unlocked, 3)
                            + "; colors " + before.describeColors() + " -> " + after.describeColors()
            ));
            return;
        }
        int improvedBlocked = Math.max(0, colorBlockedBefore - colorBlockedAfter);
        if (improvedBlocked > 0) {
            contributions.add(new AiStrategyScore.Contribution(
                    "color-access:fixing-progress",
                    Math.min(18, 6 + improvedBlocked * 4),
                    "reduces color-blocked hand cards " + colorBlockedBefore + " -> " + colorBlockedAfter
            ));
        }
    }

    private static void addCommanderUnlockContribution(List<AiStrategyScore.Contribution> contributions,
                                                       Game game,
                                                       Player player,
                                                       ColorState before,
                                                       ColorState after) {
        for (Card commander : commanderCards(game, player)) {
            CastNeed need = CastNeed.of(commander, commanderTaxMana(game, commander.getId()));
            if (need.requiredColors.isEmpty()) {
                continue;
            }
            boolean beforeCast = before.canCast(need);
            boolean afterCast = after.canCast(need);
            if (!beforeCast && afterCast) {
                contributions.add(new AiStrategyScore.Contribution(
                        "color-access:commander-unlocked",
                        30,
                        "unlocks commander cast: " + commander.getName()
                                + "; colors " + before.describeColors() + " -> " + after.describeColors()
                ));
                return;
            }
            int gainedCommanderColors = after.countCovered(need.requiredColors) - before.countCovered(need.requiredColors);
            if (gainedCommanderColors > 0) {
                contributions.add(new AiStrategyScore.Contribution(
                        "color-access:commander-colors",
                        Math.min(18, 8 + gainedCommanderColors * 5),
                        "adds commander color access for " + commander.getName()
                                + "; colors " + before.describeColors() + " -> " + after.describeColors()
                ));
                return;
            }
        }
    }

    private static void addRelevantColorContribution(List<AiStrategyScore.Contribution> contributions,
                                                     Game game,
                                                     Player player,
                                                     ColorState before,
                                                     ColorState after) {
        Set<ManaColor> gained = after.colors.stream()
                .filter(color -> !before.colors.contains(color))
                .collect(Collectors.toCollection(() -> EnumSet.noneOf(ManaColor.class)));
        if (gained.isEmpty()) {
            return;
        }
        Set<ManaColor> wanted = wantedColors(game, player);
        gained.retainAll(wanted);
        if (gained.isEmpty()) {
            return;
        }
        contributions.add(new AiStrategyScore.Contribution(
                "color-access:new-relevant-color",
                Math.min(15, gained.size() * 6),
                "adds relevant color(s) " + describeColors(gained)
        ));
    }

    private static boolean shouldConsiderSpell(Card card, Game game) {
        return card != null && !card.isLand(game) && card.getManaValue() > 0;
    }

    private static int bestManaValue(Collection<Card> cards) {
        int best = 0;
        for (Card card : cards) {
            best = Math.max(best, card.getManaValue());
        }
        return Math.min(8, best);
    }

    private static String summarizeNames(List<Card> cards, int limit) {
        List<String> names = cards.stream()
                .limit(limit)
                .map(Card::getName)
                .collect(Collectors.toList());
        if (cards.size() > limit) {
            names.add("+" + (cards.size() - limit) + " more");
        }
        return String.join(", ", names);
    }

    private static Set<ManaColor> wantedColors(Game game, Player player) {
        Set<ManaColor> colors = EnumSet.noneOf(ManaColor.class);
        for (Card card : player.getHand().getCards(game)) {
            if (shouldConsiderSpell(card, game)) {
                colors.addAll(CastNeed.of(card).requiredColors);
            }
        }
        for (Card commander : commanderCards(game, player)) {
            colors.addAll(CastNeed.of(commander).requiredColors);
        }
        return colors;
    }

    private static Collection<Card> commanderCards(Game game, Player player) {
        try {
            return game.getCommanderCardsFromAnyZones(player, CommanderCardType.COMMANDER_OR_OATHBREAKER, Zone.ALL);
        } catch (RuntimeException e) {
            return java.util.Collections.emptyList();
        }
    }

    private static int commanderTaxMana(Game game, UUID objectId) {
        try {
            CommanderPlaysCountWatcher watcher = game.getState().getWatcher(CommanderPlaysCountWatcher.class);
            if (watcher == null || objectId == null) {
                return 0;
            }
            return watcher.getPlaysCount(CardUtil.getMainCardId(game, objectId)) * 2;
        } catch (RuntimeException e) {
            return 0;
        }
    }

    private static Set<ManaColor> commanderIdentityColors(Game game, Player player) {
        Set<ManaColor> colors = EnumSet.noneOf(ManaColor.class);
        for (Card commander : commanderCards(game, player)) {
            colors.addAll(fromFilterMana(commander.getColorIdentity()));
        }
        return colors;
    }

    private static Set<ManaColor> fromFilterMana(FilterMana filterMana) {
        Set<ManaColor> colors = EnumSet.noneOf(ManaColor.class);
        if (filterMana == null) {
            return colors;
        }
        if (filterMana.isWhite()) {
            colors.add(ManaColor.WHITE);
        }
        if (filterMana.isBlue()) {
            colors.add(ManaColor.BLUE);
        }
        if (filterMana.isBlack()) {
            colors.add(ManaColor.BLACK);
        }
        if (filterMana.isRed()) {
            colors.add(ManaColor.RED);
        }
        if (filterMana.isGreen()) {
            colors.add(ManaColor.GREEN);
        }
        return colors;
    }

    private static Set<ManaColor> fromObjectColor(ObjectColor objectColor) {
        Set<ManaColor> colors = EnumSet.noneOf(ManaColor.class);
        if (objectColor == null) {
            return colors;
        }
        if (objectColor.isWhite()) {
            colors.add(ManaColor.WHITE);
        }
        if (objectColor.isBlue()) {
            colors.add(ManaColor.BLUE);
        }
        if (objectColor.isBlack()) {
            colors.add(ManaColor.BLACK);
        }
        if (objectColor.isRed()) {
            colors.add(ManaColor.RED);
        }
        if (objectColor.isGreen()) {
            colors.add(ManaColor.GREEN);
        }
        return colors;
    }

    private static Set<ManaColor> colorsFromRules(String rules, Set<ManaColor> commanderIdentity) {
        Set<ManaColor> colors = EnumSet.noneOf(ManaColor.class);
        String text = rules == null ? "" : rules.toLowerCase(Locale.ROOT);
        if (!text.contains("add")) {
            return colors;
        }
        if (text.contains("any color in your commander's color identity")) {
            colors.addAll(commanderIdentity);
            return colors;
        }
        if (text.contains("one mana of any color") || text.contains("one mana in any combination of colors")) {
            colors.addAll(commanderIdentity.isEmpty() ? EnumSet.allOf(ManaColor.class) : commanderIdentity);
        }
        for (ManaColor color : ManaColor.values()) {
            if (text.contains("{" + color.symbol.toLowerCase(Locale.ROOT) + "}")) {
                colors.add(color);
            }
        }
        return colors;
    }

    static String describeProducedColorsForTest(String rules, String commanderIdentitySymbols) {
        return describeColors(colorsFromRules(rules, colorsFromSymbols(commanderIdentitySymbols)));
    }

    private static Set<ManaColor> colorsFromSymbols(String symbols) {
        Set<ManaColor> colors = EnumSet.noneOf(ManaColor.class);
        if (symbols == null) {
            return colors;
        }
        String normalized = symbols.toUpperCase(Locale.ROOT);
        for (ManaColor color : ManaColor.values()) {
            if (normalized.contains(color.symbol)) {
                colors.add(color);
            }
        }
        return colors;
    }

    private static String describeColors(Set<ManaColor> colors) {
        if (colors == null || colors.isEmpty()) {
            return "none";
        }
        return colors.stream().map(color -> color.symbol).collect(Collectors.joining(""));
    }

    private enum ManaColor {
        WHITE("W"),
        BLUE("U"),
        BLACK("B"),
        RED("R"),
        GREEN("G");

        private final String symbol;

        ManaColor(String symbol) {
            this.symbol = symbol;
        }
    }

    private static final class CastNeed {
        private final Set<ManaColor> requiredColors;
        private final int manaValue;

        private CastNeed(Set<ManaColor> requiredColors, int manaValue) {
            this.requiredColors = requiredColors;
            this.manaValue = manaValue;
        }

        private static CastNeed of(Card card) {
            return of(card, 0);
        }

        private static CastNeed of(Card card, int taxMana) {
            Set<ManaColor> colors = EnumSet.noneOf(ManaColor.class);
            try {
                String manaCost = card.getManaCost().getText();
                if (manaCost != null) {
                    for (ManaColor color : ManaColor.values()) {
                        if (manaCost.contains("{" + color.symbol + "}")) {
                            colors.add(color);
                        }
                    }
                }
            } catch (RuntimeException ignored) {
            }
            return new CastNeed(colors, Math.max(0, card == null ? 0 : card.getManaValue()) + Math.max(0, taxMana));
        }
    }

    private static final class ColorState {
        private final Set<ManaColor> colors;
        private final Map<ManaColor, Integer> sourcesByColor;
        private final int manaSources;

        private ColorState(Set<ManaColor> colors, Map<ManaColor, Integer> sourcesByColor, int manaSources) {
            this.colors = colors;
            this.sourcesByColor = sourcesByColor;
            this.manaSources = manaSources;
        }

        private static ColorState from(Game game, Player player) {
            Set<ManaColor> commanderIdentity = commanderIdentityColors(game, player);
            Set<ManaColor> colors = EnumSet.noneOf(ManaColor.class);
            Map<ManaColor, Integer> sourcesByColor = new EnumMap<>(ManaColor.class);
            int manaSources = AiScoreSupport.countManaSources(game, player.getId());
            for (Permanent permanent : game.getBattlefield().getAllActivePermanents(player.getId())) {
                if (permanent == null) {
                    continue;
                }
                Set<ManaColor> produced = producedColors(permanent, game, commanderIdentity);
                colors.addAll(produced);
                for (ManaColor color : produced) {
                    sourcesByColor.put(color, sourcesByColor.getOrDefault(color, 0) + 1);
                }
            }
            return new ColorState(colors, sourcesByColor, manaSources);
        }

        private static Set<ManaColor> producedColors(MageObject object, Game game, Set<ManaColor> commanderIdentity) {
            Set<ManaColor> colors = EnumSet.noneOf(ManaColor.class);
            boolean land = false;
            try {
                land = object.isLand(game);
            } catch (RuntimeException ignored) {
            }
            if (land) {
                try {
                    colors.addAll(fromObjectColor(object.getFrameColor(game)));
                } catch (RuntimeException ignored) {
                }
            }
            colors.addAll(colorsFromRules(AiScoreSupport.rules(object), commanderIdentity));
            return colors;
        }

        private boolean improvesOn(ColorState before) {
            return manaSources > before.manaSources
                    || colors.stream().anyMatch(color -> !before.colors.contains(color))
                    || sourcesByColor.entrySet().stream()
                    .anyMatch(entry -> entry.getValue() > before.sourcesByColor.getOrDefault(entry.getKey(), 0));
        }

        private boolean covers(Set<ManaColor> requiredColors) {
            return colors.containsAll(requiredColors);
        }

        private boolean canCast(CastNeed need) {
            return covers(need.requiredColors) && manaSources >= need.manaValue;
        }

        private int countCovered(Set<ManaColor> requiredColors) {
            int count = 0;
            for (ManaColor color : requiredColors) {
                if (colors.contains(color)) {
                    count++;
                }
            }
            return count;
        }

        private String describeColors() {
            return ColorAccessScoreModule.describeColors(colors);
        }
    }
}
