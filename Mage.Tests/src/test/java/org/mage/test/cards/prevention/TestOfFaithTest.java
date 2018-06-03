

package org.mage.test.cards.prevention;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author LevelX2
 */
public class TestOfFaithTest extends CardTestPlayerBase {

    @Test
    public void testOneAttackerOneBlockerUsingFaith() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Soulmender");
        addCard(Zone.HAND, playerA, "Test of Faith");

        addCard(Zone.BATTLEFIELD, playerB, "Blur Sliver");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA, "Test of Faith", "Soulmender");

        attack(2, playerB, "Blur Sliver");
        block(2, playerA, "Soulmender", "Blur Sliver");

        setStopAt(2, PhaseStep.END_COMBAT);
        execute();

        assertPermanentCount(playerA, "Soulmender", 1);
        assertPowerToughness(playerA, "Soulmender", 3, 3);

        assertPermanentCount(playerB, "Blur Sliver", 1);
    }

    @Test
    public void testOneAttackerTwoBlockerOneUsingFaith() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Soulmender");
        addCard(Zone.BATTLEFIELD, playerA, "Elvish Mystic");

        addCard(Zone.HAND, playerA, "Test of Faith");

        addCard(Zone.BATTLEFIELD, playerB, "Kalonian Tusker");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA, "Test of Faith", "Soulmender");

        attack(2, playerB, "Kalonian Tusker");
        block(2, playerA, "Elvish Mystic", "Kalonian Tusker");
        block(2, playerA, "Soulmender", "Kalonian Tusker");


        setStopAt(2, PhaseStep.END_COMBAT);
        execute();

        assertPermanentCount(playerA, "Soulmender", 1);
        assertPowerToughness(playerA, "Soulmender", 2, 2); // one damage was prevented so Soulmender got +1/+1
        assertPermanentCount(playerA, "Elvish Mystic", 0);

        assertPermanentCount(playerB, "Kalonian Tusker", 1); // only 2 damage to Kalonian Tusker so he still lives
    }

    @Test
    public void testOneAttackerTwoBlockerTwoUsingFaith() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Soulmender");
        addCard(Zone.BATTLEFIELD, playerA, "Elvish Mystic");

        addCard(Zone.HAND, playerA, "Test of Faith", 2);

        addCard(Zone.BATTLEFIELD, playerB, "Kalonian Tusker");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA, "Test of Faith", "Soulmender");
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA, "Test of Faith", "Elvish Mystic");

        attack(2, playerB, "Kalonian Tusker");
        block(2, playerA, "Elvish Mystic", "Kalonian Tusker");
        block(2, playerA, "Soulmender", "Kalonian Tusker");


        setStopAt(2, PhaseStep.END_COMBAT);
        execute();

        assertPermanentCount(playerA, "Soulmender", 1);
        assertPowerToughness(playerA, "Soulmender", 2, 2); // one damage was prevented so Soulmender got +1/+1
        assertPermanentCount(playerA, "Elvish Mystic", 1);
        assertPowerToughness(playerA, "Elvish Mystic", 3, 3); // two damage were prevented so Elvish Mystic got +2/+2

        assertPermanentCount(playerB, "Kalonian Tusker", 1); // only 2 damage to Kalonian Tusker so he still lives
    }
}
