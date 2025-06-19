// src/main/java/mage/abilities/common/RoomAbility.java
package mage.abilities.common;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbility;
import mage.abilities.condition.common.RoomLeftHalfUnlockedCondition;
import mage.abilities.condition.common.RoomRightHalfUnlockedCondition;
import mage.abilities.costs.mana.ManaCosts; // Import ManaCosts
import mage.cards.Card;
import mage.cards.SplitCard; // Assuming RoomCard is a SplitCard or similar
import mage.constants.Zone;

/**
 * Room ability system - manages locked/unlocked abilities using existing
 * triggered abilities and adds activated abilities for unlocking.
 * Similar to how SagaAbility works but for Room mechanics
 */
public class RoomAbility extends SimpleStaticAbility {

    public RoomAbility(Card card, Ability leftTriggerAbility, Ability rightTriggerAbility) {
        super(Zone.ALL, null);
        this.setRuleVisible(true);
        this.setRuleAtTheTop(true);

        // Ensure the card is a SplitCard (or specifically RoomCard) to access halves
        if (!(card instanceof SplitCard)) { // Adjust this type check if RoomCard is not SplitCard
            // Handle error or log warning: RoomAbility requires a SplitCard like structure
            System.err.println("RoomAbility created with a card that is not a SplitCard type: " + card.getName());
            return; // Or throw an exception
        }
        SplitCard roomCard = (SplitCard) card;

        // Room abilities for triggered effects (existing logic)
        // Left half ability - only works when left half is unlocked
        if (leftTriggerAbility != null && leftTriggerAbility instanceof TriggeredAbility) {
            TriggeredAbility conditionalLeft = (TriggeredAbility) leftTriggerAbility.copy();
            conditionalLeft.withInterveningIf(RoomLeftHalfUnlockedCondition.instance);
            card.addAbility(conditionalLeft);
        }

        // Right half ability - only works when right half is unlocked
        if (rightTriggerAbility != null && rightTriggerAbility instanceof TriggeredAbility) {
            TriggeredAbility conditionalRight = (TriggeredAbility) rightTriggerAbility.copy();
            conditionalRight.withInterveningIf(RoomRightHalfUnlockedCondition.instance);
            card.addAbility(conditionalRight);
        }

        // NEW: Add Activated Abilities to unlock halves
        // Retrieve mana costs from the card halves
        ManaCosts leftHalfManaCost = null;
        if (roomCard.getLeftHalfCard() != null && roomCard.getLeftHalfCard().getSpellAbility() != null) {
            leftHalfManaCost = roomCard.getLeftHalfCard().getSpellAbility().getManaCosts();
        }

        ManaCosts rightHalfManaCost = null;
        if (roomCard.getRightHalfCard() != null && roomCard.getRightHalfCard().getSpellAbility() != null) {
            rightHalfManaCost = roomCard.getRightHalfCard().getSpellAbility().getManaCosts();
        }
        
        // Add left half unlock ability
        if (leftHalfManaCost != null) {
            RoomUnlockAbility leftUnlockAbility = new RoomUnlockAbility(leftHalfManaCost, true);
            card.addAbility(leftUnlockAbility);
        }

        // Add right half unlock ability
        if (rightHalfManaCost != null) {
            RoomUnlockAbility rightUnlockAbility = new RoomUnlockAbility(rightHalfManaCost, false);
            card.addAbility(rightUnlockAbility);
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