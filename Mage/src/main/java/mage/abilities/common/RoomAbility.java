package mage.abilities.common;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbility;
import mage.abilities.condition.common.RoomLeftHalfUnlockedCondition;
import mage.abilities.condition.common.RoomRightHalfUnlockedCondition;
import mage.cards.Card;
import mage.constants.Zone;

/**
 * Room ability system - manages locked/unlocked abilities using existing
 * triggered abilities
 * Similar to how SagaAbility works but for Room mechanics
 */
public class RoomAbility extends SimpleStaticAbility {

    public RoomAbility(Card card, Ability leftAbility, Ability rightAbility) {
        super(Zone.ALL, null);
        this.setRuleVisible(true);
        this.setRuleAtTheTop(true);

        // Room abilities
        // Left half ability - only works when left half is unlocked
        if (leftAbility != null && leftAbility instanceof TriggeredAbility) {
            TriggeredAbility conditionalLeft = (TriggeredAbility) leftAbility.copy();
            conditionalLeft.withInterveningIf(RoomLeftHalfUnlockedCondition.instance);
            card.addAbility(conditionalLeft);
        }

        // Right half ability - only works when right half is unlocked
        if (rightAbility != null && rightAbility instanceof TriggeredAbility) {
            TriggeredAbility conditionalRight = (TriggeredAbility) rightAbility.copy();
            conditionalRight.withInterveningIf(RoomRightHalfUnlockedCondition.instance);
            card.addAbility(conditionalRight);
        }
    }

    protected RoomAbility(final RoomAbility ability) {
        super(ability);
    }

    @Override
    public String getRule() {
        // This is the reminder text that appears on all room cards
        return "<i>(You may cast either half. That door unlocks on the battlefield. " +
                "As a sorcery, you may pay the mana cost of a locked door to unlock it.)</i>";
    }

    @Override
    public RoomAbility copy() {
        return new RoomAbility(this);
    }
}