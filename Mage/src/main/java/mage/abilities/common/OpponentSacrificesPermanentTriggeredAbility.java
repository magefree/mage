
package mage.abilities.common;

import mage.MageObject;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

public class OpponentSacrificesPermanentTriggeredAbility extends TriggeredAbilityImpl {

    public OpponentSacrificesPermanentTriggeredAbility(Effect effect) {
        super(Zone.BATTLEFIELD, effect, false);
        setTriggerPhrase("Whenever an opponent sacrifices a nontoken permanent, ");
    }

    public OpponentSacrificesPermanentTriggeredAbility(final OpponentSacrificesPermanentTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SACRIFICED_PERMANENT;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
    if (game.getPlayer(this.getControllerId()).hasOpponent(event.getPlayerId(), game)) {
            MageObject object = game.getLastKnownInformation(event.getTargetId(), Zone.BATTLEFIELD);
            if (object instanceof Permanent) {
                for (Effect effect : getEffects()) {
                    effect.setTargetPointer(new FixedTarget(event.getTargetId(), game));
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public OpponentSacrificesPermanentTriggeredAbility copy() {
        return new OpponentSacrificesPermanentTriggeredAbility(this);
    }

}