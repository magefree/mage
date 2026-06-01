package mage.cards.d;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnToBattlefieldUnderYourControlTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.SandWarriorToken;
import mage.game.permanent.token.Token;
import mage.target.targetpointer.FixedTarget;
import mage.target.targetpointer.FixedTargets;

/**
 * @author balazskristof
 */
public final class DesertWarfare extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent(
            SubType.DESERT, "you control five or more deserts"
    );

    public DesertWarfare(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{G}");

        // Whenever you sacrifice a Desert and whenever a Desert card is put into your graveyard from your hand or library, put that card onto the battlefield under your control at the beginning of your next end step.
        this.addAbility(new DesertWarfareTriggeredAbility());

        // At the beginning of combat on your turn, if you control five or more Deserts, create that many 1/1 red, green, and white Sand Warrior creature tokens. They gain haste.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfCombatTriggeredAbility(new DesertWarfareTokenEffect()),
                new PermanentsOnTheBattlefieldCondition(filter, ComparisonType.OR_GREATER, 5),
                "At the beginning of combat on your turn, if you control five or more Deserts, "
                        + "create that many 1/1 red, green, and white Sand Warrior creature tokens. They gain haste."
        ).addHint(new ValueHint("Deserts you control", new PermanentsOnBattlefieldCount(filter))));
    }

    private DesertWarfare(final DesertWarfare card) {
        super(card);
    }

    @Override
    public DesertWarfare copy() {
        return new DesertWarfare(this);
    }
}

class DesertWarfareTriggeredAbility extends TriggeredAbilityImpl {

    DesertWarfareTriggeredAbility() {
        super(Zone.BATTLEFIELD, null, false);
        setTriggerPhrase("Whenever you sacrifice a Desert and whenever a Desert card is put into your graveyard from your hand or library, "
                + "put that card onto the battlefield under your control at the beginning of your next end step.");
    }

    private DesertWarfareTriggeredAbility(final DesertWarfareTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public DesertWarfareTriggeredAbility copy() {
        return new DesertWarfareTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SACRIFICED_PERMANENT
                || event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event instanceof ZoneChangeEvent) {
            ZoneChangeEvent zce = (ZoneChangeEvent) event;
            Card card = game.getCard(event.getTargetId());
            if (card == null
                    || !card.isOwnedBy(getControllerId())
                    || !card.hasSubtype(SubType.DESERT, game)
                    || zce.getToZone() != Zone.GRAVEYARD
                    || (zce.getFromZone() != Zone.HAND
                    && zce.getFromZone() != Zone.LIBRARY)) {
                return false;
            }
        } else {
            Permanent permanent = game.getPermanentOrLKIBattlefield(event.getTargetId());
            if (permanent == null
                    || !permanent.isOwnedBy(getControllerId())
                    || !permanent.hasSubtype(SubType.DESERT, game)) {
                return false;
            }
        }
        Effect effect = new ReturnToBattlefieldUnderYourControlTargetEffect();
        effect.setTargetPointer(new FixedTarget(event.getTargetId(), game));
        game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(effect, TargetController.YOU), this);
        return true;
    }
}

class DesertWarfareTokenEffect extends OneShotEffect {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent(SubType.DESERT);

    DesertWarfareTokenEffect() {
        super(Outcome.Benefit);
        staticText = "create that many 1/1 red, green, and white Sand Warrior creature tokens. "
                + " They gain haste.";
    }

    private DesertWarfareTokenEffect(final DesertWarfareTokenEffect effect) {
        super(effect);
    }

    @Override
    public DesertWarfareTokenEffect copy() {
        return new DesertWarfareTokenEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Token token = new SandWarriorToken();
        int count = game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source, game).size();
        token.putOntoBattlefield(count, game, source, source.getControllerId());
        game.addEffect(new GainAbilityTargetEffect(
                HasteAbility.getInstance(), Duration.WhileOnBattlefield
        ).setTargetPointer(new FixedTargets(token, game)), source);
        return true;
    }
}
