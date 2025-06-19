package mage.abilities.common;

import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.effects.common.RoomManaValueEffect;
import mage.abilities.effects.common.RoomRulesTextEffect;
import mage.cards.Card;
import mage.cards.SplitCard;
import mage.constants.Zone;

/**
 * Room ability system - manages locked/unlocked abilities using existing
 * triggered abilities and adds activated abilities for unlocking.
 * based (very loosely) on Saga
 */
public class RoomAbility extends SimpleStaticAbility {
    public RoomAbility(Card card, Ability leftAbility, Ability rightAbility) {
        super(Zone.ALL, null);
        this.setRuleVisible(true);
        this.setRuleAtTheTop(true);

        // Ensure the card is a SplitCard (or specifically RoomCard) to access halves
        if (!(card instanceof SplitCard)) {
            throw new IllegalArgumentException("Non split card with room ability " + card.getCardType());
        }

        SplitCard roomCard = (SplitCard) card;
        RoomRulesTextEffect rulesTextEffect = new RoomRulesTextEffect();

        if (leftAbility != null) {
            card.addAbility(leftAbility);
            rulesTextEffect.registerLeftHalfAbility(leftAbility.getId());
            ManaCosts leftHalfManaCost = null;
            if (roomCard.getLeftHalfCard() != null && roomCard.getLeftHalfCard().getSpellAbility() != null) {
                leftHalfManaCost = roomCard.getLeftHalfCard().getSpellAbility().getManaCosts();
            }
            RoomUnlockAbility leftUnlockAbility = new RoomUnlockAbility(leftHalfManaCost, true);
            card.addAbility(leftUnlockAbility.setRuleAtTheTop(true));
        }

        if (rightAbility != null) {
            card.addAbility(rightAbility);
            rulesTextEffect.registerRightHalfAbility(rightAbility.getId());
            ManaCosts rightHalfManaCost = null;
            if (roomCard.getRightHalfCard() != null && roomCard.getRightHalfCard().getSpellAbility() != null) {
                rightHalfManaCost = roomCard.getRightHalfCard().getSpellAbility().getManaCosts();
            }
            RoomUnlockAbility rightUnlockAbility = new RoomUnlockAbility(rightHalfManaCost, false);
            card.addAbility(rightUnlockAbility.setRuleAtTheTop(true));
        }

        this.addEffect(rulesTextEffect);
        this.addEffect(new RoomManaValueEffect());
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