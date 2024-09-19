package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.HasteAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterOwnedCard;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.SandWarriorToken;
import mage.game.permanent.token.Token;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.target.targetpointer.FixedTargets;

import java.util.UUID;

/**
 * @author Sidorovich77
 */
public final class DesertWarfare extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent();

    static {
        filter.add(SubType.DESERT.getPredicate());
    }

    public DesertWarfare(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{G}");

        // Whenever you sacrifice a Desert and whenever a Desert card is put into your graveyard from your hand or library, put that card onto the battlefield under your control at the beginning of your next end step.
        //Based on Seraph, Yuma, Proud Protector
        Effect effect = new CreateDelayedTriggeredAbilityEffect(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(new DesertWarfareReturnEffect(), TargetController.YOU));
        effect.setText("put that card onto the battlefield under your control at the beginning of your next end step");

        this.addAbility(new DesertWarfareTriggeredAbility(Zone.BATTLEFIELD, effect, false, TargetController.YOU, SetTargetPointer.CARD));

        //At the beginning of combat on your turn, if you control five or more Deserts, create that many 1/1 red, green, and white Sand Warrior creature tokens. They gain haste.
        //Based on Palani's Hatcher
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfCombatTriggeredAbility(new DesertWarfareCreateTokensEffect(), TargetController.YOU, false),
                new PermanentsOnTheBattlefieldCondition(filter, ComparisonType.OR_GREATER, 5, true),
                "At the beginning of combat on your turn, if you control five or more Deserts, " +
                        "create that many 1/1 red, green, and white Sand Warrior creature tokens. They gain haste.")
                .addHint(DesertsYouControlCount.getHint()));
    }

    private DesertWarfare(final DesertWarfare card) {
        super(card);
    }

    @Override
    public DesertWarfare copy() {
        return new DesertWarfare(this);
    }

}

//Based on PutCardIntoGraveFromAnywhereAllTriggeredAbility
class DesertWarfareTriggeredAbility extends TriggeredAbilityImpl {

    private static FilterOwnedCard filterCard = new FilterOwnedCard();

    static {
        filterCard.add(SubType.DESERT.getPredicate());
    }

    private final SetTargetPointer setTargetPointer;

    public DesertWarfareTriggeredAbility(Zone zone, Effect effect, boolean optional, TargetController targetController, SetTargetPointer setTargetPointer) {
        super(zone, effect, optional);
        FilterOwnedCard filter = filterCard.copy();
        this.setTargetPointer = setTargetPointer;
        filter.add(targetController.getOwnerPredicate());
        setTriggerPhrase("Whenever you sacrifice a Desert and whenever a Desert card is put into your graveyard from your hand or library, ");
    }

    protected DesertWarfareTriggeredAbility(final DesertWarfareTriggeredAbility ability) {
        super(ability);
        filterCard = filterCard.copy();
        this.setTargetPointer = ability.setTargetPointer;
    }

    @Override
    public DesertWarfareTriggeredAbility copy() {
        return new DesertWarfareTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE
                || event.getType() == GameEvent.EventType.SACRIFICED_PERMANENT;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Card card = game.getCard(event.getTargetId());
        if (card == null) {
            return false;
        }
        switch (event.getType()) {
            case SACRIFICED_PERMANENT:
                Permanent permanent = game.getPermanentOrLKIBattlefield(event.getTargetId());
                if (permanent == null || !permanent.hasSubtype(SubType.DESERT, game)) {
                    return false;
                }
                break;
            case ZONE_CHANGE:
                ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
                if (zEvent.getToZone() != Zone.GRAVEYARD
                        || (zEvent.getFromZone() != Zone.LIBRARY && zEvent.getFromZone() != Zone.HAND)
                        || !filterCard.match(card, getControllerId(), this, game)) {
                    return false;
                }
                break;
            default:
                return false;
        }
        this.getEffects().setTargetPointer(new FixedTarget(event.getTargetId(), game));
        return true;
    }
}

class DesertWarfareReturnEffect extends OneShotEffect {

    DesertWarfareReturnEffect() {
        super(Outcome.Benefit);
        this.staticText = "put that card onto the battlefield under your control";
    }

    private DesertWarfareReturnEffect(final DesertWarfareReturnEffect effect) {
        super(effect);
    }

    @Override
    public DesertWarfareReturnEffect copy() {
        return new DesertWarfareReturnEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Card card = game.getCard(getTargetPointer().getFirst(game, source));
        if (controller != null && card != null && game.getState().getZone(card.getId()) == Zone.GRAVEYARD) {
            controller.moveCards(card, Zone.BATTLEFIELD, source, game, false, false, false, null);
            return true;
        }
        return false;
    }
}

class DesertWarfareCreateTokensEffect extends OneShotEffect {

    DesertWarfareCreateTokensEffect() {
        super(Outcome.Benefit);
        this.staticText = "At the beginning of combat on your turn, if you control five or more Deserts, create that many 1/1 red, green, and white Sand Warrior creature tokens. They gain haste.";
    }

    private DesertWarfareCreateTokensEffect(final DesertWarfareCreateTokensEffect effect) {
        super(effect);
    }

    @Override
    public DesertWarfareCreateTokensEffect copy() {
        return new DesertWarfareCreateTokensEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (source.getControllerId() == null) {
            return false;
        }
        int deserts = DesertsYouControlCount.instance.calculate(game, source, this);
        Token token = new SandWarriorToken();
        token.putOntoBattlefield(deserts, game, source);
        game.addEffect(new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.Custom)
                .setTargetPointer(new FixedTargets(token, game)), source);
        return true;
    }
}

enum DesertsYouControlCount implements DynamicValue {
    instance;

    private static final Hint hint = new ValueHint("Deserts you control", instance);

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return new PermanentsOnBattlefieldCount(new FilterControlledPermanent(SubType.DESERT)).calculate(game, sourceAbility, null);
    }

    @Override
    public DynamicValue copy() {
        return instance;
    }

    @Override
    public String getMessage() {
        return "";
    }

    public static Hint getHint() {
        return hint;
    }
}