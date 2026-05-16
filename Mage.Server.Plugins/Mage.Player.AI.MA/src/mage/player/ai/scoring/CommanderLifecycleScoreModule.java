package mage.player.ai.scoring;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.cards.Card;
import mage.constants.CommanderCardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.player.ai.AiStrategyScore;
import mage.players.Player;
import mage.target.Target;
import mage.util.CardUtil;
import mage.watchers.common.CommanderPlaysCountWatcher;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public final class CommanderLifecycleScoreModule implements AiScoreModule {

    private static final String ENABLED_PROPERTY = "xmage.ai.strategy.commanderLifecycle.enabled";
    private static final String APPLY_PROPERTY = "xmage.ai.strategy.commanderLifecycle.apply";
    private static final String MAX_MODIFIER_PROPERTY = "xmage.ai.strategy.commanderLifecycle.maxScoreModifier";

    private final boolean globalApplyModifiers;

    public CommanderLifecycleScoreModule(AiScoreModuleConfig config) {
        this.globalApplyModifiers = config != null && config.isApplyModifiers();
    }

    @Override
    public String getName() {
        return "commander-lifecycle";
    }

    @Override
    public AiStrategyScore evaluate(AiScoringContext context) {
        if (context == null) {
            return AiStrategyScore.none(0);
        }
        if (!Boolean.parseBoolean(System.getProperty(ENABLED_PROPERTY, "true"))) {
            return AiStrategyScore.none(context.getBaseScore());
        }
        Game decisionGame = context.getDecisionGame();
        UUID playerId = context.getPlayerId();
        Ability action = context.getAction();
        if (decisionGame == null || playerId == null || action == null) {
            return AiStrategyScore.none(context.getBaseScore());
        }
        Player player = decisionGame.getPlayer(playerId);
        if (player == null || commanderIds(decisionGame, player).isEmpty()) {
            return AiStrategyScore.none(context.getBaseScore());
        }

        List<AiStrategyScore.Contribution> contributions = new ArrayList<>();
        addCommanderCastContribution(contributions, decisionGame, player, action);
        addCommanderProtectionContribution(contributions, decisionGame, player, action);
        addCommanderLossContribution(contributions, decisionGame, context.getFinalGame(), player);

        int rawModifier = contributions.stream().mapToInt(AiStrategyScore.Contribution::getValue).sum();
        if (rawModifier == 0 && contributions.isEmpty()) {
            return AiStrategyScore.none(context.getBaseScore());
        }
        int maxModifier = Math.max(0, Integer.getInteger(MAX_MODIFIER_PROPERTY, 60));
        rawModifier = clamp(rawModifier, -maxModifier, maxModifier);
        int appliedModifier = globalApplyModifiers && Boolean.getBoolean(APPLY_PROPERTY) ? rawModifier : 0;
        return AiStrategyScore.of(context.getBaseScore(), rawModifier, appliedModifier, contributions);
    }

    private static void addCommanderCastContribution(List<AiStrategyScore.Contribution> contributions,
                                                     Game game,
                                                     Player player,
                                                     Ability action) {
        if (!isCommanderAction(game, player, action) || action.getZone() != Zone.COMMAND) {
            return;
        }
        int taxMana = getCommanderTaxMana(game, action);
        int handSize = player.getHand().size();
        int battlefieldCommanders = countBattlefieldCommanders(game, player);
        if (taxMana <= 2 && battlefieldCommanders == 0) {
            contributions.add(new AiStrategyScore.Contribution(
                    "commander-lifecycle:deploy",
                    18,
                    "commander available from command zone; tax +" + taxMana
            ));
            return;
        }
        if (taxMana <= 4) {
            return;
        }

        int penalty = taxMana * (handSize >= 3 ? 9 : 6);
        contributions.add(new AiStrategyScore.Contribution(
                "commander-lifecycle:tax",
                -penalty,
                "expensive commander recast; tax +" + taxMana + ", hand " + handSize
        ));
    }

    private static void addCommanderProtectionContribution(List<AiStrategyScore.Contribution> contributions,
                                                           Game game,
                                                           Player player,
                                                           Ability action) {
        Permanent commander = findTargetedFriendlyCommander(game, player, action);
        if (commander == null || FfaScoringUtil.isHostileAction(action) || !hasProtectiveOutcome(action)) {
            return;
        }
        int taxMana = getCommanderTaxMana(game, commander);
        int value = 24 + Math.min(36, taxMana * 6);
        contributions.add(new AiStrategyScore.Contribution(
                "commander-lifecycle:protect",
                value,
                "protects " + commander.getName() + "; current tax +" + taxMana
        ));
    }

    private static void addCommanderLossContribution(List<AiStrategyScore.Contribution> contributions,
                                                     Game decisionGame,
                                                     Game finalGame,
                                                     Player player) {
        if (finalGame == null) {
            return;
        }
        Set<UUID> commanderIds = commanderIds(decisionGame, player);
        for (Permanent before : decisionGame.getBattlefield().getAllActivePermanents(player.getId())) {
            if (!isCommanderPermanent(decisionGame, player, commanderIds, before)) {
                continue;
            }
            Permanent after = finalGame.getPermanent(before.getId());
            if (after != null && after.isControlledBy(player.getId())) {
                continue;
            }
            int taxMana = getCommanderTaxMana(decisionGame, before);
            contributions.add(new AiStrategyScore.Contribution(
                    "commander-lifecycle:lost",
                    -(40 + Math.min(50, taxMana * 8)),
                    "commander leaves battlefield: " + before.getName() + "; current tax +" + taxMana
            ));
        }
    }

    private static Permanent findTargetedFriendlyCommander(Game game, Player player, Ability action) {
        Set<UUID> commanderIds = commanderIds(game, player);
        for (Target target : action.getAllSelectedTargets()) {
            for (UUID targetId : target.getTargets()) {
                Permanent permanent = game.getPermanent(targetId);
                if (isCommanderPermanent(game, player, commanderIds, permanent)) {
                    return permanent;
                }
            }
        }
        return null;
    }

    private static boolean hasProtectiveOutcome(Ability action) {
        for (Effect effect : action.getAllEffects()) {
            Outcome outcome = effect.getOutcome();
            if (outcome == Outcome.Protect
                    || outcome == Outcome.Regenerate
                    || outcome == Outcome.PreventDamage
                    || outcome == Outcome.AddAbility
                    || outcome == Outcome.BoostCreature
                    || outcome == Outcome.Benefit) {
                return true;
            }
        }
        return false;
    }

    private static boolean isCommanderAction(Game game, Player player, Ability action) {
        try {
            return commanderIds(game, player).contains(CardUtil.getMainCardId(game, action.getSourceId()));
        } catch (RuntimeException e) {
            return false;
        }
    }

    private static boolean isCommanderPermanent(Game game, Player player, Set<UUID> commanderIds, Permanent permanent) {
        if (permanent == null || !permanent.isControlledBy(player.getId())) {
            return false;
        }
        try {
            return game.isCommanderObject(player, permanent)
                    || commanderIds.contains(CardUtil.getMainCardId(game, permanent.getId()));
        } catch (RuntimeException e) {
            return commanderIds.contains(permanent.getId());
        }
    }

    private static int countBattlefieldCommanders(Game game, Player player) {
        int count = 0;
        Set<UUID> commanderIds = commanderIds(game, player);
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(player.getId())) {
            if (isCommanderPermanent(game, player, commanderIds, permanent)) {
                count++;
            }
        }
        return count;
    }

    private static int getCommanderTaxMana(Game game, MageObject object) {
        return object == null ? 0 : getCommanderTaxMana(game, object.getId());
    }

    private static int getCommanderTaxMana(Game game, Ability action) {
        return action == null ? 0 : getCommanderTaxMana(game, action.getSourceId());
    }

    private static int getCommanderTaxMana(Game game, UUID objectId) {
        try {
            CommanderPlaysCountWatcher watcher = game.getState().getWatcher(CommanderPlaysCountWatcher.class);
            if (watcher == null || objectId == null) {
                return 0;
            }
            UUID mainCardId = CardUtil.getMainCardId(game, objectId);
            return 2 * watcher.getPlaysCount(mainCardId);
        } catch (RuntimeException e) {
            return 0;
        }
    }

    private static Set<UUID> commanderIds(Game game, Player player) {
        try {
            return game.getCommandersIds(player, CommanderCardType.COMMANDER_OR_OATHBREAKER, true);
        } catch (RuntimeException e) {
            return java.util.Collections.emptySet();
        }
    }

    private static int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(max, value));
    }
}
