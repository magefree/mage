

package mage.abilities.common;

import mage.abilities.effects.common.ManaEffect;
import mage.abilities.mana.TriggeredManaAbility;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public class TapLandForManaAllTriggeredManaAbility extends TriggeredManaAbility {

    private final boolean setTargetPointer;
    
    public TapLandForManaAllTriggeredManaAbility(ManaEffect manaEffect, boolean optional, boolean setTargetPointer) {
        super(Zone.BATTLEFIELD, manaEffect, optional);
        this.setTargetPointer = setTargetPointer;
    }

    public TapLandForManaAllTriggeredManaAbility(final TapLandForManaAllTriggeredManaAbility ability) {
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
                getEffects().get(0).setTargetPointer(new FixedTarget(permanent.getId()));
            }
            return true;
        }
        return false;
    }

    @Override
    public TapLandForManaAllTriggeredManaAbility copy() {
        return new TapLandForManaAllTriggeredManaAbility(this);
    }

    @Override
    public String getRule() {
        return new StringBuilder("Whenever a player taps a land for mana, ").append(super.getRule()).toString();
    }
}
