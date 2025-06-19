// src/main/java/mage/abilities/common/RoomUnlockAbility.java
package mage.abilities.common;

import mage.abilities.ActivatedAbilityImpl;
import mage.abilities.condition.common.RoomLeftHalfLockedCondition;
import mage.abilities.condition.common.RoomRightHalfLockedCondition;
import mage.abilities.costs.mana.ManaCosts;
import mage.constants.TimingRule;
import mage.constants.Zone;

/**
 * Activated ability for Room cards to unlock a locked half by paying its mana
 * cost.
 * This ability is only active if the corresponding half is currently locked.
 */
public class RoomUnlockAbility extends ActivatedAbilityImpl {

    private final boolean isLeftHalf;

    public RoomUnlockAbility(ManaCosts costs, boolean isLeftHalf) {
        super(Zone.BATTLEFIELD, new RoomUnlockHalfEffect(isLeftHalf), costs);
        this.isLeftHalf = isLeftHalf;
        this.timing = TimingRule.SORCERY; // Unlock only as a sorcery
        this.usesStack = false;

        // only if the half is *locked*
        if (isLeftHalf) {
            this.setCondition(RoomLeftHalfLockedCondition.instance);
        } else {
            this.setCondition(RoomRightHalfLockedCondition.instance);
        }
    }

    protected RoomUnlockAbility(final RoomUnlockAbility ability) {
        super(ability);
        this.isLeftHalf = ability.isLeftHalf;
    }

    @Override
    public RoomUnlockAbility copy() {
        return new RoomUnlockAbility(this);
    }

    @Override
    public String getRule() {
        // Construct the rule text for display in the game
        StringBuilder sb = new StringBuilder();
        sb.append(getManaCostsToPay().getText()).append(": ");
        sb.append("Unlock the ");
        sb.append(isLeftHalf ? "left" : "right").append(" half.");
        sb.append(" <i>(Activate only as a sorcery, and only if the ");
        sb.append(isLeftHalf ? "left" : "right").append(" half is locked.)</i>");
        return sb.toString();
    }
}