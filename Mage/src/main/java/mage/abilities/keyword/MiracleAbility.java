package mage.abilities.keyword;

import mage.ApprovingObject;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.VariableCost;
import mage.abilities.costs.VariableCostType;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.Card;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SpellAbilityCastMode;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;
import mage.watchers.common.CardsDrawnThisTurnWatcher;

import java.util.List;
import java.util.UUID;

/**
 * 702.94. Miracle
 * <p>
 * 702.94a Miracle is a static ability linked to a triggered ability (see rule
 * 603.11). "Miracle [cost]" means "You may reveal this card from your hand as
 * you draw it if it's the first card you've drawn this turn. When you reveal
 * this card this way, you may cast it by paying [cost] rather than its mana
 * cost."
 * <p>
 * 702.94b If a player chooses to reveal a card using its miracle ability, they
 * play with that card revealed until that card leaves their hand, that ability
 * resolves, or that ability otherwise leaves the stack.
 * <p>
 * Implementation logic and support:
 * - source and gainable miracle;
 * - miracle with static cost and reduced cost;
 * - cost reduction in X part;
 * - draw event implemented as replacement effect instead part of the draw process 
 *   (due game engine limit - no ApplyEffects after move to hand on draw)
 *
 * @author noxx, LevelX2, JayDi85
 */
public class MiracleAbility extends SimpleStaticAbility {

    private static final String staticRule = " <i>(You may cast this card for its miracle cost when you draw it if it's the first card you drew this turn.)</i>";
    private final String ruleText;

    public MiracleAbility(String miracleCosts) {
        super(Zone.LIBRARY, new MiracleDrawCardReplacementEffect(new MiracleStaticCostCastEffect(miracleCosts), makeRuleText(miracleCosts)));
        this.ruleText = makeRuleText(miracleCosts);
    }

    private MiracleAbility(final MiracleAbility ability) {
        super(ability);
        this.ruleText = ability.ruleText;
    }

    @Override
    public MiracleAbility copy() {
        return new MiracleAbility(this);
    }

    @Override
    public String getRule() {
        return ruleText;
    }

    static String makeRuleText(String miracleCosts) {
        return "Miracle " + miracleCosts + staticRule;
    }

    public static String getReducedMiracleCostText(Card card, int reducedByCost) {
        String reducedText = CardUtil.reduceCost(card.getManaCost(), reducedByCost).getText();
        if (reducedText.isEmpty() && !card.getManaCost().isEmpty()) {
            return "{0}";
        }
        return reducedText;
    }

    public static Ability makeExternalDrawAbility(FilterCard filter, String staticCost, String text) {
        return makeExternalDrawAbility(filter, new MiracleStaticCostCastEffect(staticCost), text);
    }

    public static Ability makeExternalDrawAbility(FilterCard filter, int reducedByCost, String text) {
        return makeExternalDrawAbility(filter, new MiracleReducedCostCastEffect(reducedByCost), text);
    }

    private static Ability makeExternalDrawAbility(FilterCard filter, MiracleBaseCastEffect castEffect, String text) {
        Ability drawAbility = new SimpleStaticAbility(Zone.BATTLEFIELD, new ExternalMiracleDrawCardReplacementEffect(filter, castEffect, text));
        drawAbility.setRuleVisible(false);
        return drawAbility;
    }

    static boolean isBeforeFirstCardDrawnThisTurn(UUID playerId, Game game) {
        if (game.getPhase() == null || playerId == null) {
            return false;
        }
        CardsDrawnThisTurnWatcher watcher = game.getState().getWatcher(CardsDrawnThisTurnWatcher.class);
        return watcher != null && watcher.getCardsDrawnThisTurn(playerId) == 0;
    }

    static Card getCardAboutToBeDrawn(Player player, Game game) {
        if (player == null) {
            return null;
        }
        return player.isDrawsFromBottom()
                ? player.getLibrary().getFromBottom(game)
                : player.getLibrary().getFromTop(game);
    }

    static void handleDrawCardForMiracle(Player player, Card card, MiracleBaseCastEffect castEffect, Ability source, Game game) {
        if (player == null || card == null || castEffect == null) {
            return;
        }
        String choiceKey = "MiracleRevealChoice_" + game.getTurnNum() + "_" + player.getId() + "_" + card.getId() + "_" + card.getZoneChangeCounter(game);
        Object existingChoice = game.getState().getValue(choiceKey);
        boolean revealed;
        if (existingChoice instanceof Boolean) {
            revealed = (Boolean) existingChoice;
        } else {
            Cards cards = new CardsImpl(card);
            player.lookAtCards("Miracle", cards, game);
            revealed = player.chooseUse(Outcome.Benefit, "Reveal " + card.getLogName() + " to be able to use Miracle?", source, game);
            game.getState().setValue(choiceKey, revealed);
            if (revealed) {
                player.revealCards("Miracle", cards, game);
            }
        }
        if (revealed) {
            String ruleText = makeRuleText(castEffect.getMiracleCostText(card));
            MiracleCastDelayedTriggeredAbility delayed = new MiracleCastDelayedTriggeredAbility(card.getId(), castEffect.copy(), ruleText);
            delayed.setSourceId(card.getId());
            delayed.setControllerId(player.getId());
            game.addDelayedTriggeredAbility(delayed, null);
        }
    }
}

class MiracleDrawCardReplacementEffect extends ReplacementEffectImpl {

    private final MiracleBaseCastEffect castEffect;

    MiracleDrawCardReplacementEffect(MiracleBaseCastEffect castEffect, String miracleRuleText) {
        super(Duration.EndOfGame, Outcome.Benefit);
        this.castEffect = castEffect;
        this.staticText = miracleRuleText;
    }

    private MiracleDrawCardReplacementEffect(final MiracleDrawCardReplacementEffect effect) {
        super(effect);
        this.castEffect = effect.castEffect.copy();
    }

    @Override
    public MiracleDrawCardReplacementEffect copy() {
        return new MiracleDrawCardReplacementEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DRAW_CARD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (!MiracleAbility.isBeforeFirstCardDrawnThisTurn(event.getPlayerId(), game)) {
            return false;
        }
        Card sourceCard = game.getCard(source.getSourceId());
        if (sourceCard == null || !event.getPlayerId().equals(sourceCard.getOwnerId())) {
            return false;
        }
        Player player = game.getPlayer(event.getPlayerId());
        Card cardToDraw = MiracleAbility.getCardAboutToBeDrawn(player, game);
        return cardToDraw != null && cardToDraw.getId().equals(sourceCard.getId());
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player player = game.getPlayer(event.getPlayerId());
        Card cardToDraw = MiracleAbility.getCardAboutToBeDrawn(player, game);
        if (cardToDraw != null && cardToDraw.getId().equals(source.getSourceId())) {
            MiracleAbility.handleDrawCardForMiracle(player, cardToDraw, castEffect, source, game);
        }
        // Return false so the normal drawCards pipeline runs (drawFromBottom, moveToZone, DrewCardEvent, etc.)
        return false;
    }
}

class ExternalMiracleDrawCardReplacementEffect extends ReplacementEffectImpl {

    private final FilterCard filter;
    private final MiracleBaseCastEffect castEffect;

    ExternalMiracleDrawCardReplacementEffect(FilterCard filter, MiracleBaseCastEffect castEffect, String text) {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        this.filter = filter;
        this.castEffect = castEffect;
        this.staticText = text;
    }

    private ExternalMiracleDrawCardReplacementEffect(final ExternalMiracleDrawCardReplacementEffect effect) {
        super(effect);
        this.filter = effect.filter.copy();
        this.castEffect = effect.castEffect.copy();
    }

    @Override
    public ExternalMiracleDrawCardReplacementEffect copy() {
        return new ExternalMiracleDrawCardReplacementEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DRAW_CARD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (!source.isControlledBy(event.getPlayerId())) {
            return false;
        }
        if (!MiracleAbility.isBeforeFirstCardDrawnThisTurn(event.getPlayerId(), game)) {
            return false;
        }
        Player player = game.getPlayer(event.getPlayerId());
        Card cardToDraw = MiracleAbility.getCardAboutToBeDrawn(player, game);
        return cardToDraw != null && filter.match(cardToDraw, source.getControllerId(), source, game);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player player = game.getPlayer(event.getPlayerId());
        Card cardToDraw = MiracleAbility.getCardAboutToBeDrawn(player, game);
        if (cardToDraw != null && filter.match(cardToDraw, source.getControllerId(), source, game)) {
            MiracleAbility.handleDrawCardForMiracle(player, cardToDraw, castEffect, source, game);
        }
        return false;
    }
}

class MiracleCastDelayedTriggeredAbility extends DelayedTriggeredAbility {

    private final UUID cardId;
    private final String ruleText;

    MiracleCastDelayedTriggeredAbility(UUID cardId, MiracleBaseCastEffect castEffect, String ruleText) {
        super(castEffect, Duration.EndOfStep, true, true);
        this.cardId = cardId;
        this.ruleText = ruleText;
    }

    private MiracleCastDelayedTriggeredAbility(final MiracleCastDelayedTriggeredAbility ability) {
        super(ability);
        this.cardId = ability.cardId;
        this.ruleText = ability.ruleText;
    }

    @Override
    public MiracleCastDelayedTriggeredAbility copy() {
        return new MiracleCastDelayedTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DREW_CARD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!event.getTargetId().equals(this.cardId) || !event.getPlayerId().equals(this.getControllerId())) {
            return false;
        }
        Card card = game.getCard(event.getTargetId());
        if (card == null) {
            return false;
        }
        getEffects().setTargetPointer(new FixedTarget(card, game));
        return true;
    }

    @Override
    public String getRule() {
        return ruleText;
    }
}

abstract class MiracleBaseCastEffect extends OneShotEffect {

    protected MiracleBaseCastEffect() {
        super(Outcome.Benefit);
        this.staticText = "cast this card for its miracle cost";
    }

    protected MiracleBaseCastEffect(final MiracleBaseCastEffect effect) {
        super(effect);
    }

    @Override
    public abstract MiracleBaseCastEffect copy();

    abstract String getMiracleCostText(Card card);

    protected abstract boolean castWithMiracleCost(Player controller, SpellAbility abilityToCast, Ability source, Game game);

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        // use target pointer here, so it's the same card that triggered the event (not gone back to library e.g.)
        Card card = game.getCard(getTargetPointer().getFirst(game, source));
        if (controller == null || card == null) {
            return false;
        }

        List<Card> castableComponents = CardUtil.getCastableComponents(card, null, source, controller, game, null, false);
        if (castableComponents.isEmpty()) {
            return false;
        }
        try {
            castableComponents.forEach(partCard -> game.getState().setValue("PlayFromNotOwnHandZone" + partCard.getId(), Boolean.TRUE));
            SpellAbility chosen = controller.chooseAbilityForCast(card, game, true);
            if (chosen == null) {
                return false;
            }
            // same as Madness
            SpellAbility abilityToCast = chosen.copy();
            abilityToCast.setSpellAbilityCastMode(SpellAbilityCastMode.MIRACLE);
            return castWithMiracleCost(controller, abilityToCast, source, game);
        } finally {
            castableComponents.forEach(partCard -> game.getState().setValue("PlayFromNotOwnHandZone" + partCard.getId(), null));
        }
    }
}

class MiracleStaticCostCastEffect extends MiracleBaseCastEffect {

    private final ManaCosts<ManaCost> staticCost;

    MiracleStaticCostCastEffect(String staticCost) {
        this.staticCost = new ManaCostsImpl<>(staticCost);
    }

    private MiracleStaticCostCastEffect(final MiracleStaticCostCastEffect effect) {
        super(effect);
        this.staticCost = effect.staticCost.copy();
    }

    @Override
    public MiracleStaticCostCastEffect copy() {
        return new MiracleStaticCostCastEffect(this);
    }

    @Override
    String getMiracleCostText(Card card) {
        return staticCost.getText();
    }

    @Override
    protected boolean castWithMiracleCost(Player controller, SpellAbility abilityToCast, Ability source, Game game) {
        // same as Madness - it's not spell ability, so use controller.cast
        ManaCosts<ManaCost> costRef = abilityToCast.getManaCostsToPay();
        // Keep additional variable mana costs (e.g. "As an additional cost to cast this spell, waterbend {X}")
        costRef.removeIf(cost -> !(cost instanceof VariableCost && ((VariableCost) cost).getCostType() == VariableCostType.ADDITIONAL));
        costRef.add(staticCost.copy());
        controller.cast(abilityToCast, game, false, new ApprovingObject(source, game));
        return true;
    }
}

/**
 * Casts a revealed Miracle card with its mana cost reduced by {N} (e.g. Aminatou, Veil Piercer:
 * "Its miracle cost is equal to its mana cost reduced by {4}").
 * Any remaining generic reduction carries into unpaid {X} via {@link CardUtil#reduceCost} so it applies
 * when X is announced at CR 601.2b without spilling into additional costs or taxes (such as Kicker or Thalia).
 * <p>
 * Rules references:
 * <ul>
 *   <li>Matt Tabak (WotC) on Aminatou, Veil Piercer — https://bsky.app/profile/wotcmatt.bsky.social/post/3lap5zmwppe2z
 *       <br>https://bsky.app/profile/wotcmatt.bsky.social/post/3mstpszffek2m:
 *       "Aminatou imposes an alternative cost, so it can be used in conjunction with additional costs, like kicker.
 *       However, you start with the mana cost reduced by 4, then add in additional costs."
 *       <br>https://bsky.app/profile/wotcmatt.bsky.social/post/3msvooqnpxs24:
 *       "Correct. The 'reduced by 4' is all part of figuring out the alternative cost (in rule 601.2b, if you're following along)."</li>
 *   <li>Jess Dunks (@Dunkatog, WotC Rules Manager) on the same "mana cost reduced by {N}" template (Dream Devourer):
 *       "If you foretell Stonecoil Serpent with Dream Devourer, you can cast it with an X of 2 for zero mana or an X of 4 for 2 mana."</li>
 * </ul>
 */
class MiracleReducedCostCastEffect extends MiracleBaseCastEffect {

    private final int reducedByCost;

    MiracleReducedCostCastEffect(int reducedByCost) {
        this.reducedByCost = reducedByCost;
    }

    private MiracleReducedCostCastEffect(final MiracleReducedCostCastEffect effect) {
        super(effect);
        this.reducedByCost = effect.reducedByCost;
    }

    @Override
    public MiracleReducedCostCastEffect copy() {
        return new MiracleReducedCostCastEffect(this);
    }

    @Override
    String getMiracleCostText(Card card) {
        return MiracleAbility.getReducedMiracleCostText(card, reducedByCost);
    }

    @Override
    protected boolean castWithMiracleCost(Player controller, SpellAbility abilityToCast, Ability source, Game game) {
        ManaCosts<ManaCost> costRef = abilityToCast.getManaCostsToPay();
        if (costRef.isEmpty()) {
            // Card has no mana cost -> mana cost reduced by {N} is still unpayable (CR 118.6a)
            return false;
        }
        // CardUtil.reduceCost reduces fixed generic mana and carries any remaining reduction into non-additional {X}
        // (VariableCostType != ADDITIONAL);
        // Additional costs and taxes are untouched per the WotC rulings cited above.
        ManaCosts<ManaCost> reduced = CardUtil.reduceCost(costRef, reducedByCost);
        if (reduced.isEmpty()) {
            reduced.add(new GenericManaCost(0));
        }
        costRef.clear();
        costRef.addAll(reduced);
        controller.cast(abilityToCast, game, false, new ApprovingObject(source, game));
        return true;
    }
}
