
package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentToken;
import mage.target.targetpointer.FixedTarget;

public class OpponentSacrificesNonTokenPermanentTriggeredAbility extends TriggeredAbilityImpl {

    public OpponentSacrificesNonTokenPermanentTriggeredAbility(Effect effect) {
        super(Zone.BATTLEFIELD, effect, false);
        setTriggerPhrase("Whenever an opponent sacrifices a nontoken permanent, ");
    }

    public OpponentSacrificesNonTokenPermanentTriggeredAbility(final OpponentSacrificesNonTokenPermanentTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SACRIFICED_PERMANENT;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (game.getPlayer(getControllerId()).hasOpponent(event.getPlayerId(), game)) {
            Permanent permanent = (Permanent) game.getLastKnownInformation(event.getTargetId(), Zone.BATTLEFIELD);
            if (permanent != null && !(permanent instanceof PermanentToken)) {
                getEffects().setTargetPointer(new FixedTarget(event.getTargetId(), game.getState().getZoneChangeCounter(event.getTargetId())));
                return true;
            }
        }
        return false;
    }

    @Override
    public OpponentSacrificesNonTokenPermanentTriggeredAbility copy() {
        return new OpponentSacrificesNonTokenPermanentTriggeredAbility(this);
    }

}
