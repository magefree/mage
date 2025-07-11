package mage.abilities.keyword;

import mage.MageIdentifier;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.SpecialAction;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.condition.common.SuspendedCondition;
import mage.abilities.costs.VariableCostType;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.costs.mana.VariableManaCost;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainSuspendEffect;
import mage.abilities.effects.common.counter.RemoveCounterSourceEffect;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.Card;
import mage.cards.CardsImpl;
import mage.cards.ModalDoubleFacedCard;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

import java.util.Set;
import java.util.UUID;

/**
 * 702.62. Suspend
 * <p>
 * 702.62a. Suspend is a keyword that represents three abilities.
 * The first is a static ability that functions while the card with suspend is in a player's hand.
 * The second and third are triggered abilities that function in the exile zone.
 * "Suspend N--[cost]" means "If you could begin to cast this card by putting it onto the stack from your hand,
 * you may pay [cost] and exile it with N time counters on it. This action doesn't use the stack,"
 * and "At the beginning of your upkeep, if this card is suspended, remove a time counter from it,"
 * and "When the last time counter is removed from this card, if it's exiled,
 * you may play it without paying its mana cost if able. If you don't, it remains exiled.
 * If you cast a creature spell this way, it gains haste until you lose control of the spell or the permanent it becomes."
 * <p>
 * 702.62b. A card is "suspended" if it's in the exile zone, has suspend, and has a time counter on it.
 * <p>
 * 702.62c. While determining if you could begin to cast a card with suspend,
 * take into consideration any effects that would prohibit that card from being cast.
 * <p>
 * 702.62d. Casting a spell as an effect of its suspend ability follows the rules
 * for paying alternative costs in rules 601.2b and 601.2f-h.
 * <p>
 * The phrase "if you could play this card from your hand" checks only for
 * timing restrictions and permissions. This includes both what's inherent in
 * the card's type (for example, if the card with suspend is a creature, it must
 * be your main phase and the stack must be empty) and what's imposed by other
 * abilities, such as flash or Meddling Mage's ability. Whether you could
 * actually follow all steps in playing the card is irrelevant. If the card is
 * impossible to play due to a lack of legal targets or an unpayable mana cost,
 * for example, it may still be removed from the game with suspend.
 * <p>
 * Removing a card from the game with its suspend ability is not playing that
 * card. This action doesn't use the stack and can't be responded to.
 * <p>
 * If a spell with suspend has targets, the targets are chosen when the spell is
 * played, not when it's removed from the game.
 * <p>
 * If the first triggered ability of suspend is countered, no time counter is
 * removed. The ability will trigger again during its owner's next upkeep.
 * <p>
 * When the last time counter is removed from a suspended card, the second
 * triggered ability of suspend will trigger. It doesn't matter why the time
 * counter was removed or whose effect removed it. (The _Time Spiral_ reminder
 * text is misleading on this point.)
 * <p>
 * If the second triggered ability of suspend is countered, the card can't be
 * played. It remains in the removed-from-the-game zone without any time
 * counters on it for the rest of the game, and it's no longer considered
 * suspended.
 * <p>
 * If the second triggered ability of suspend resolves, the card's owner must
 * play the spell if possible, even if that player doesn't want to. Normal
 * timing considerations for the spell are ignored (for example, if the
 * suspended card is a creature and this ability resolves during your upkeep,
 * you're able to play the card), but other play restrictions are not ignored.
 * <p>
 * If the second triggered ability of suspend resolves and the suspended card
 * can't be played due to a lack of legal targets or a play restriction, for
 * example, it remains in the removed-from-the-game zone without any time
 * counters on it for the rest of the game, and it's no longer considered
 * suspended.
 * <p>
 * As the second triggered ability of suspend resolves, if playing the suspended
 * card involves an additional cost, the card's owner must pay that cost if
 * able. If they can't, the card remains removed from the game. If the
 * additional cost includes mana, the situation is more complex. If the player
 * has enough mana in their mana pool to pay the cost, that player must do so.
 * If the player can't possibly pay the cost, the card remains removed from the
 * game. However, if the player has the means to produce enough mana to pay the
 * cost, then they have a choice: The player may play the spell, produce mana,
 * and pay the cost. Or the player may choose to play no mana abilities, thus
 * making the card impossible to play because the additional mana can't be paid.
 * <p>
 * A creature played via suspend comes into play with haste. It still has haste
 * after the first turn it's in play as long as the same player controls it. As
 * soon as another player takes control of it, it loses haste.
 *
 * @author LevelX2
 */
public class SuspendAbility extends SpecialAction {

    private final String ruleText;

    /**
     * Gives the card the SuspendAbility
     *
     * @param suspend - amount of time counters, if Integer.MAX_VALUE is set
     *                there will be {X} costs and X counters added with X can't be 0 limit
     * @param cost    - null is used for temporary gained suspend ability
     * @param card    - card that has the suspend ability
     */
    public SuspendAbility(int suspend, ManaCost cost, Card card) {
        this(suspend, cost, card, false);
    }

    public SuspendAbility(int suspend, ManaCost cost, Card card, boolean hideReminderText) {
        super(Zone.HAND);
        this.addCost(cost);
        this.addEffect(new SuspendExileEffect(suspend));
        this.usesStack = false;
        if (suspend == Integer.MAX_VALUE) {
            // example: Suspend X-{X}{W}{W}. X can't be 0.
            // TODO: replace by costAdjuster for shared logic
            VariableManaCost xCosts = new VariableManaCost(VariableCostType.ALTERNATIVE);
            xCosts.setMinX(1);
            this.addCost(xCosts);
            cost = new ManaCostsImpl<>("{X}" + cost.getText());
        }
        if (cost != null) {
            ruleText = "Suspend " + (suspend == Integer.MAX_VALUE ? "X" : suspend) + "&mdash;"
                    + cost.getText() + (suspend == Integer.MAX_VALUE ? ". X can't be 0." : "")
                    + (hideReminderText ? "" : makeReminderText(suspend, cost.getText(), card.isCreature()));
            if (card.getManaCost().isEmpty()) {
                setRuleAtTheTop(true);
            }
            addSubAbility(new SuspendUpkeepAbility());
            addSubAbility(new SuspendPlayCardAbility());
        } else {
            ruleText = "Suspend";
        }
    }

    private String makeReminderText(int suspend, String costText, boolean isCreature) {
        String counterText;
        switch (suspend) {
            case 1:
                counterText = "a time counter";
                break;
            case Integer.MAX_VALUE:
                counterText = "X time counters";
                break;
            default:
                counterText = CardUtil.numberToText(suspend) + " time counters";
        }
        return " <i>(Rather than cast this card from your hand, you may pay " + costText
                + " and exile it with " + counterText + " on it. "
                + "At the beginning of your upkeep, remove a time counter. "
                + "When the last is removed, you may cast it without paying its mana cost."
                + (isCreature ? " It has haste." : "") + ")</i>";
    }

    /**
     * Adds suspend to a card that does not have it regularly e.g. Epochrasite
     * or added by Jhoira of the Ghitu
     */
    public static void addSuspendTemporaryToCard(Card card, Ability source, Game game) {
        if (card instanceof ModalDoubleFacedCard) {
            // Need to ensure the suspend ability gets put on the left side card
            // since counters get added to this card.
            card = ((ModalDoubleFacedCard) card).getLeftHalfCard();
        }
        SuspendAbility ability = new SuspendAbility(0, null, card, false);
        ability.setSourceId(card.getId());
        ability.setControllerId(card.getOwnerId());
        game.getState().addOtherAbility(card, ability);

        SuspendUpkeepAbility ability1 = new SuspendUpkeepAbility();
        ability1.setSourceId(card.getId());
        ability1.setControllerId(card.getOwnerId());
        game.getState().addOtherAbility(card, ability1);

        SuspendPlayCardAbility ability2 = new SuspendPlayCardAbility();
        ability2.setSourceId(card.getId());
        ability2.setControllerId(card.getOwnerId());
        game.getState().addOtherAbility(card, ability2);
    }

    public static UUID getSuspendExileId(UUID controllerId, Game game) {
        UUID exileId = (UUID) game.getState().getValue("SuspendExileId" + controllerId.toString());
        if (exileId == null) {
            exileId = UUID.randomUUID();
            game.getState().setValue("SuspendExileId" + controllerId, exileId);
        }
        return exileId;
    }

    private SuspendAbility(final SuspendAbility ability) {
        super(ability);
        this.ruleText = ability.ruleText;
    }

    @Override
    public ActivationStatus canActivate(UUID playerId, Game game) {
        // suspend can only be activated from a hand
        if (game.getState().getZone(getSourceId()) != Zone.HAND) {
            return ActivationStatus.getFalse();
        }
        // suspend uses card's timing restriction
        Card card = game.getCard(getSourceId());
        if (card == null) {
            return ActivationStatus.getFalse();
        }
        Set<MageIdentifier> allowedToBeCastNow = card.getSpellAbility().spellCanBeActivatedNow(playerId, game);
        if (!allowedToBeCastNow.contains(MageIdentifier.Default) && !allowedToBeCastNow.contains(card.getSpellAbility().getIdentifier())) {
            return ActivationStatus.getFalse();
        }
        return super.canActivate(playerId, game);
    }

    public static boolean addTimeCountersAndSuspend(Card card, int amount, Ability source, Game game) {
        if (card == null || card.isCopy()) {
            return false;
        }
        if (!Zone.EXILED.match(game.getState().getZone(card.getId()))) {
            return false;
        }
        Player owner = game.getPlayer(card.getOwnerId());
        if (owner == null) {
            return false;
        }
        game.getExile().moveToAnotherZone(
                card.getMainCard(), game,
                game.getExile().createZone(
                        SuspendAbility.getSuspendExileId(owner.getId(), game),
                        "Suspended cards of " + owner.getName()
                )
        );
        if (amount > 0) {
            card.addCounters(CounterType.TIME.createInstance(amount), owner.getId(), source, game);
        }
        if (!card.getAbilities(game).containsClass(SuspendAbility.class)) {
            game.addEffect(new GainSuspendEffect(new MageObjectReference(card, game)), source);
        }
        game.informPlayers(owner.getLogName() + " suspends " + amount + " - " + card.getName());
        return true;
    }

    @Override
    public String getRule() {
        return ruleText;
    }

    @Override
    public SuspendAbility copy() {
        return new SuspendAbility(this);
    }

}

class SuspendExileEffect extends OneShotEffect {

    private int suspend;

    SuspendExileEffect(int suspend) {
        super(Outcome.PutCardInPlay);
        this.staticText = "Suspend " + (suspend == Integer.MAX_VALUE ? "X" : suspend);
        this.suspend = suspend;
    }

    private SuspendExileEffect(final SuspendExileEffect effect) {
        super(effect);
        this.suspend = effect.suspend;
    }

    @Override
    public SuspendExileEffect copy() {
        return new SuspendExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(source.getSourceId());
        Player controller = game.getPlayer(source.getControllerId());
        if (card == null || controller == null) {
            return false;
        }
        UUID exileId = SuspendAbility.getSuspendExileId(controller.getId(), game);
        if (controller.moveCardToExileWithInfo(card, exileId, "Suspended cards of "
                + controller.getName(), source, game, Zone.HAND, true)) {
            if (suspend == Integer.MAX_VALUE) {
                suspend = CardUtil.getSourceCostsTag(game, source, "X", 0);
            }
            card.addCounters(CounterType.TIME.createInstance(suspend), source.getControllerId(), source, game);
            game.informPlayers(controller.getLogName()
                    + " suspends (" + suspend + ") " + card.getLogName());
        }
        return true;
    }
}

class SuspendPlayCardAbility extends TriggeredAbilityImpl {

    SuspendPlayCardAbility() {
        super(Zone.EXILED, new SuspendPlayCardEffect());
        setRuleVisible(false);
    }

    private SuspendPlayCardAbility(final SuspendPlayCardAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.COUNTER_REMOVED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getTargetId().equals(this.getSourceId())) {
            Card card = game.getCard(this.getSourceId());
            return card != null
                    && game.getState().getZone(card.getId()) == Zone.EXILED
                    && card.getCounters(game).getCount(CounterType.TIME) == 0;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "When the last time counter is removed from {this}, if it's exiled, ";
    }

    @Override
    public SuspendPlayCardAbility copy() {
        return new SuspendPlayCardAbility(this);
    }
}

class SuspendPlayCardEffect extends OneShotEffect {

    SuspendPlayCardEffect() {
        super(Outcome.PlayForFree);
        staticText = "you may play it without paying its mana cost if able. If you don't, it remains exiled";
    }

    protected SuspendPlayCardEffect(final SuspendPlayCardEffect effect) {
        super(effect);
    }

    @Override
    public SuspendPlayCardEffect copy() {
        return new SuspendPlayCardEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Card card = game.getCard(source.getSourceId());
        if (player == null || card == null) {
            return false;
        }
        // ensure we're getting the main card when passing to CardUtil to check all parts of card
        // MDFC points to left half card
        card = card.getMainCard();
        // cast/play the card for free
        if (!CardUtil.castSpellWithAttributesForFree(player, source, game, new CardsImpl(card),
                StaticFilters.FILTER_CARD, null, true)) {
            return true;
        }
        // creatures cast from suspend gain haste
        if ((card.isCreature(game))) {
            ContinuousEffect effect = new GainHasteEffect();
            effect.setTargetPointer(new FixedTarget(card.getId(), card.getZoneChangeCounter(game) + 1));
            game.addEffect(effect, source);
        }
        return true;
    }
}

class GainHasteEffect extends ContinuousEffectImpl {

    private UUID suspendController;

    GainHasteEffect() {
        super(Duration.Custom, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        staticText = "If you cast a creature spell this way, it gains haste until you lose control of the spell or the permanent it becomes.";
    }

    private GainHasteEffect(final GainHasteEffect effect) {
        super(effect);
        this.suspendController = effect.suspendController;
    }

    @Override
    public GainHasteEffect copy() {
        return new GainHasteEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (suspendController == null) {
            suspendController = source.getControllerId();
        }
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent != null) {
            if (suspendController.equals(source.getControllerId())) {
                permanent.addAbility(HasteAbility.getInstance(), source.getSourceId(), game);
            } else {
                this.discard();
            }
            return true;
        }
        if (game.getState().getZoneChangeCounter(((FixedTarget) getTargetPointer()).getTarget())
                >= ((FixedTarget) getTargetPointer()).getZoneChangeCounter()) {
            this.discard();
        }
        return false;
    }

}

class SuspendUpkeepAbility extends BeginningOfUpkeepTriggeredAbility {

    SuspendUpkeepAbility() {
        super(Zone.EXILED, TargetController.YOU, new RemoveCounterSourceEffect(CounterType.TIME.createInstance()).setText("remove a time counter from it"), false);
        this.withInterveningIf(SuspendedCondition.instance);
        this.setRuleVisible(false);
    }

    private SuspendUpkeepAbility(final SuspendUpkeepAbility effect) {
        super(effect);
    }

    @Override
    public SuspendUpkeepAbility copy() {
        return new SuspendUpkeepAbility(this);
    }
}
