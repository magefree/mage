
package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author Quercitron
 */
public class TapLandForManaAllTriggeredAbility extends TriggeredAbilityImpl {

    private final boolean setTargetPointer;

    public TapLandForManaAllTriggeredAbility(Effect effect, boolean optional, boolean setTargetPointer) {
        super(Zone.BATTLEFIELD, effect, optional);
        this.setTargetPointer = setTargetPointer;
    }

    public TapLandForManaAllTriggeredAbility(final TapLandForManaAllTriggeredAbility ability) {
        super(ability);
        this.setTargetPointer = ability.setTargetPointer;
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TAPPED_FOR_MANA;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanentOrLKIBattlefield(event.getSourceId());
        if (permanent != null && permanent.isLand()) {
            if (setTargetPointer) {
                getEffects().get(0).setTargetPointer(new FixedTarget(permanent, game));
            }
            return true;
        }
        return false;
    }

    @Override
    public TapLandForManaAllTriggeredAbility copy() {
        return new TapLandForManaAllTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return new StringBuilder("Whenever a land is tapped for mana, ").append(super.getRule()).toString();
    }
}
