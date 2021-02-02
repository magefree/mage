
package mage.cards.g;

import java.util.UUID;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.events.ZoneChangeEvent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author jeffwadsworth
 */
public final class GracefulReprieve extends CardImpl {

    public GracefulReprieve(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");

        // When target creature dies this turn, return that card to the battlefield under its owner's control.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new GracefulReprieveEffect());

    }

    private GracefulReprieve(final GracefulReprieve card) {
        super(card);
    }

    @Override
    public GracefulReprieve copy() {
        return new GracefulReprieve(this);
    }
}

class GracefulReprieveEffect extends OneShotEffect {

    public GracefulReprieveEffect() {
        super(Outcome.Benefit);
        this.staticText = "When target creature dies this turn, return that card to the battlefield under its owner's control";
    }

    public GracefulReprieveEffect(final GracefulReprieveEffect effect) {
        super(effect);
    }

    @Override
    public GracefulReprieveEffect copy() {
        return new GracefulReprieveEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        DelayedTriggeredAbility delayedAbility = new GracefulReprieveDelayedTriggeredAbility(new MageObjectReference(targetPointer.getFirst(game, source), game));
        game.addDelayedTriggeredAbility(delayedAbility, source);
        return true;
    }
}

class GracefulReprieveDelayedTriggeredAbility extends DelayedTriggeredAbility {

    private MageObjectReference target;

    public GracefulReprieveDelayedTriggeredAbility(MageObjectReference target) {
        super(new GracefulReprieveDelayedEffect(), Duration.EndOfTurn);
        this.target = target;
    }

    public GracefulReprieveDelayedTriggeredAbility(GracefulReprieveDelayedTriggeredAbility ability) {
        super(ability);
        this.target = ability.target;
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (target.refersTo(((ZoneChangeEvent) event).getTarget(), game)) {
            ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
            if (zEvent.isDiesEvent()) {
                getEffects().setTargetPointer(new FixedTarget(target.getSourceId()));
                return true;
            }
        }
        return false;
    }

    @Override
    public GracefulReprieveDelayedTriggeredAbility copy() {
        return new GracefulReprieveDelayedTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "When target creature dies this turn, return that card to the battlefield under its owner's control.";
    }
}

class GracefulReprieveDelayedEffect extends OneShotEffect {

    public GracefulReprieveDelayedEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "return that card to the battlefield under its owner's control";
    }

    public GracefulReprieveDelayedEffect(final GracefulReprieveDelayedEffect effect) {
        super(effect);
    }

    @Override
    public GracefulReprieveDelayedEffect copy() {
        return new GracefulReprieveDelayedEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Card card = game.getCard(getTargetPointer().getFirst(game, source));
            if (card != null && game.getState().getZone(card.getId()) == Zone.GRAVEYARD) {
                controller.moveCards(card, Zone.BATTLEFIELD, source, game, false, false, true, null);
            }
            return true;
        }
        return false;
    }
}
