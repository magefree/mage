// src/main/java/mage/abilities/common/RoomUnlockAbility.java
package mage.abilities.common;

import mage.abilities.SpecialAction; // Changed import
import mage.abilities.condition.common.RoomLeftHalfLockedCondition;
import mage.abilities.condition.common.RoomRightHalfLockedCondition;
import mage.abilities.costs.mana.ManaCosts;
import mage.constants.TimingRule;
import mage.constants.Zone;
import mage.cards.Card;


/**
 * Special action for Room cards to unlock a locked half by paying its mana
 * cost.
 * This ability is only active if the corresponding half is currently locked.
 */
public class RoomUnlockAbility extends SpecialAction {

    private final boolean isLeftHalf;

    public RoomUnlockAbility(ManaCosts costs, boolean isLeftHalf) {
        // Call the SpecialAction constructor with the zone and mana costs
        // SpecialAction's constructor already sets usesStack = false
        super(Zone.BATTLEFIELD, null); // No AlternateManaPaymentAbility needed here, costs are handled by super(effect, costs)
        this.addCost(costs); // Add the mana costs to this ability

        this.isLeftHalf = isLeftHalf;
        this.timing = TimingRule.SORCERY; // Unlock only as a sorcery, ensures main phase and empty stack

        // only if the half is *locked*
        if (isLeftHalf) {
            this.setCondition(RoomLeftHalfLockedCondition.instance);
        } else {
            this.setCondition(RoomRightHalfLockedCondition.instance);
        }

        // Add the effect to unlock the half
        this.addEffect(new RoomUnlockHalfEffect(isLeftHalf));
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
        // Since it's a special action, the cost text is usually just stated
        sb.append(getManaCostsToPay().getText()).append(": "); // Changed getManaCostsToPay() to getCosts() if addCost is used
        sb.append("Unlock the ");
        sb.append(isLeftHalf ? "left" : "right").append(" half.");
        sb.append(" <i>(Activate only as a sorcery, and only if the ");
        sb.append(isLeftHalf ? "left" : "right").append(" half is locked.)</i>");
        return sb.toString();
    }
}