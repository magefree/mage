
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
    private final boolean landMustExists;

    public TapLandForManaAllTriggeredAbility(Effect effect, boolean optional, boolean setTargetPointer, boolean landMustExists) {
        super(Zone.BATTLEFIELD, effect, optional);
        this.setTargetPointer = setTargetPointer;
        this.landMustExists = landMustExists;
    }

    public TapLandForManaAllTriggeredAbility(final TapLandForManaAllTriggeredAbility ability) {
        super(ability);
        this.setTargetPointer = ability.setTargetPointer;
        this.landMustExists = ability.landMustExists;
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TAPPED_FOR_MANA;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (game.inCheckPlayableState()) { // Ignored - see GameEvent.TAPPED_FOR_MANA
            return false;
        }

        Permanent permanent;
        if (landMustExists) {
            permanent = game.getPermanent(event.getSourceId());
        } else {
            permanent = game.getPermanentOrLKIBattlefield(event.getSourceId());
        }

        if (permanent != null && permanent.isLand(game)) {
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
    public String getTriggerPhrase() {
        return "Whenever a land is tapped for mana, ";
    }
}
