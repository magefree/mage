package org.mage.test.cards.abilities.keywords;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 702.49. Ninjutsu
 702.49a. Ninjutsu is an activated ability that functions only while the card with ninjutsu is in a player's hand.
    "Ninjutsu [cost]" means "[Cost], Reveal this card from your hand, Return an unblocked attacking creature you control
    to its owner's hand: Put this card onto the battlefield from your hand tapped and attacking."
 702.49b. The card with ninjutsu remains revealed from the time the ability is announced until the ability leaves the stack.
 702.49c. A ninjutsu ability may be activated only while a creature on the battlefield is unblocked (see rule 509.1h).
    The creature with ninjutsu is put onto the battlefield unblocked. It will be attacking the same player, planeswalker,
    or battle as the creature that was returned to its owner's hand.
 702.49d. Commander ninjutsu is a variant of the ninjutsu ability that also functions while the card with commander
    ninjutsu is in the command zone. "Commander ninjutsu [cost]" means "[Cost], Reveal this card from your hand or
    from the command zone, Return an unblocked attacking creature you control to its owner's hand: Put this card onto
    the battlefield tapped and attacking."
 */
public class NinjutsuTest extends CardTestPlayerBase {

    private static final String drake = "Seacoast Drake"; // 1/3 flying
    private static final String crab = "Jwari Scuttler"; // 2/3
    private static final String shinobi = "Moonblade Shinobi"; // 3/2, Ninjutsu 2U, on combat damage to player create 1/1 Illusion

    @Test
    public void testMultipleUsage() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 6);
        addCard(Zone.BATTLEFIELD, playerA, drake);
        addCard(Zone.BATTLEFIELD, playerA, crab);
        addCard(Zone.HAND, playerA, shinobi);

        attack(1, playerA, drake, playerB);
        attack(1, playerA, crab, playerB);

        activateAbility(1, PhaseStep.DECLARE_BLOCKERS, playerA, "Ninjutsu");
        setChoice(playerA, drake);
        activateAbility(1, PhaseStep.DECLARE_BLOCKERS, playerA, "Ninjutsu"); // while above ability on stack
        setChoice(playerA, crab);
        // result, both drake and crab in hand, shinobi on battlefield

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertLife(playerB, 17);
        assertPowerToughness(playerA, shinobi, 3, 2);
        assertPermanentCount(playerA, "Illusion Token", 1);
        assertHandCount(playerA, drake, 1);
        assertHandCount(playerA, crab, 1);
        assertTappedCount("Island", true, 6); // activated twice
    }

}
