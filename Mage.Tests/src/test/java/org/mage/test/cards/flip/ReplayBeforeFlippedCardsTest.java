
package org.mage.test.cards.flip;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class ReplayBeforeFlippedCardsTest extends CardTestPlayerBase {

    @Test
    public void testHanweirMilitiaCaptain() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);

        // At the beginning of your upkeep, if you control four or more creatures, transform Hanweir Militia Captain.
        // transformed side:
        // Westvale Cult Leader's power and toughness are each equal to the number of creatures you control.
        // At the beginning of your end step, put a 1/1 white and black Human Cleric creature token onto the battlefield.
        addCard(Zone.HAND, playerA, "Hanweir Militia Captain", 1); // {1}{W} Creature
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Hanweir Militia Captain");

        setStopAt(3, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Hanweir Militia Captain", 0);
        assertPermanentCount(playerA, "Westvale Cult Leader", 1);
        assertPowerToughness(playerA, "Westvale Cult Leader", 5, 5);

    }

    /**
     * Return a flipped Hanweir Militia Captain to its owners hand and when
     * replayed it still has * / * power and toughness.
     */
    @Test
    public void testHanweirMilitiaCaptainReturned() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);

        // At the beginning of your upkeep, if you control four or more creatures, transform Hanweir Militia Captain.
        // transformed side:
        // Westvale Cult Leader's power and toughness are each equal to the number of creatures you control.
        // At the beginning of your end step, put a 1/1 white and black Human Cleric creature token onto the battlefield.
        addCard(Zone.HAND, playerA, "Hanweir Militia Captain", 1); // {1}{W} Creature
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 4);

        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);
        // Return target creature to its owner's hand.
        // Madness
        addCard(Zone.HAND, playerB, "Just the Wind", 1); // Instant {1}{U}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Hanweir Militia Captain");

        castSpell(3, PhaseStep.DRAW, playerB, "Just the Wind", "Westvale Cult Leader");
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Hanweir Militia Captain");
        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerB, "Just the Wind", 1);
        assertPermanentCount(playerA, "Hanweir Militia Captain", 1);
        assertPowerToughness(playerA, "Hanweir Militia Captain", 2, 2);

    }
}
