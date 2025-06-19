package mage.abilities.common;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbility;
import mage.abilities.condition.common.RoomLeftHalfUnlockedCondition;
import mage.abilities.condition.common.RoomRightHalfUnlockedCondition;
import mage.abilities.costs.mana.ManaCosts;
import mage.cards.Card;
import mage.cards.SplitCard;
import mage.constants.Zone;

/**
 * Room ability system - manages locked/unlocked abilities using existing
 * triggered abilities and adds activated abilities for unlocking.
 * based (very loosely) on Saga
 */
public class RoomAbility extends SimpleStaticAbility {

    public RoomAbility(Card card, Ability leftTriggerAbility, Ability rightTriggerAbility) {
        super(Zone.ALL, null);
        this.setRuleVisible(true);
        this.setRuleAtTheTop(true);

        // Ensure the card is a SplitCard (or specifically RoomCard) to access halves
        if (!(card instanceof SplitCard)) {
            throw new IllegalArgumentException("Non split card with room ability " + card.getCardType());
        }
        SplitCard roomCard = (SplitCard) card;

        // Room abilities for triggered effects
        if (leftTriggerAbility != null && leftTriggerAbility instanceof TriggeredAbility) {
            TriggeredAbility conditionalLeft = (TriggeredAbility) leftTriggerAbility.copy();
            conditionalLeft.withInterveningIf(RoomLeftHalfUnlockedCondition.instance);
            card.addAbility(conditionalLeft);
        }

        if (rightTriggerAbility != null && rightTriggerAbility instanceof TriggeredAbility) {
            TriggeredAbility conditionalRight = (TriggeredAbility) rightTriggerAbility.copy();
            conditionalRight.withInterveningIf(RoomRightHalfUnlockedCondition.instance);
            card.addAbility(conditionalRight);
        }

        // Add Activated Abilities to unlock halves
        ManaCosts leftHalfManaCost = null;
        if (roomCard.getLeftHalfCard() != null && roomCard.getLeftHalfCard().getSpellAbility() != null) {
            leftHalfManaCost = roomCard.getLeftHalfCard().getSpellAbility().getManaCosts();
        }

        ManaCosts rightHalfManaCost = null;
        if (roomCard.getRightHalfCard() != null && roomCard.getRightHalfCard().getSpellAbility() != null) {
            rightHalfManaCost = roomCard.getRightHalfCard().getSpellAbility().getManaCosts();
        }

        if (leftHalfManaCost != null) {
            RoomUnlockAbility leftUnlockAbility = new RoomUnlockAbility(leftHalfManaCost, true);
            card.addAbility(leftUnlockAbility);
        }

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
        return "<i>(You may cast either half. That door unlocks on the battlefield. " +
                "As a sorcery, you may pay the mana cost of a locked door to unlock it.)</i>";
    }

    @Override
    public RoomAbility copy() {
        return new RoomAbility(this);
    }
}