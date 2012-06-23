package org.mage.test.cards.single.avr;

import mage.Constants;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author noxx
 */
public class CavernOfSoulsTest extends CardTestPlayerBase {

    /**
     * Tests simple cast
     */
    @Test
    public void testCastDrake() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Mountain", 3);
        addCard(Constants.Zone.HAND, playerA, "Cavern of Souls");
        addCard(Constants.Zone.HAND, playerA, "Azure Drake");

        setChoice(playerA, "Drake");
        setChoice(playerA, "Blue");

        playLand(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Cavern of Souls");
        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Azure Drake");

        setStopAt(1, Constants.PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Azure Drake", 1);
    }

    /**
     * Tests "Cavern of Souls" with "Human" creature type chosen.
     * Then tests casting Azure Drake (should fail) and Elite Vanguard (should be ok as it has "Human" subtype)
     */
    @Test
    public void testNoCastBecauseOfCreatureType() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Constants.Zone.HAND, playerA, "Cavern of Souls");
        addCard(Constants.Zone.HAND, playerA, "Abuna Acolyte");
        addCard(Constants.Zone.HAND, playerA, "Elite Vanguard");

        setChoice(playerA, "Human");
        setChoice(playerA, "White");
        setChoice(playerA, "White");

        playLand(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Cavern of Souls"); // choose Human
        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Abuna Acolyte");  // not Human but Cat Cleric
        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Elite Vanguard"); // Human

        setStopAt(1, Constants.PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Abuna Acolyte", 0);
        assertPermanentCount(playerA, "Elite Vanguard", 1);
    }

    /**
     * Tests card can be countered for usual cast
     */
    @Test
    public void testDrakeCountered() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Mountain", 3);
        addCard(Constants.Zone.HAND, playerA, "Island");
        addCard(Constants.Zone.HAND, playerA, "Azure Drake");

        addCard(Constants.Zone.HAND, playerB, "Remove Soul");
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Island", 2);

        playLand(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Island");
        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Azure Drake");
        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerB, "Remove Soul");

        setStopAt(1, Constants.PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Azure Drake", 0);
        assertGraveyardCount(playerA, "Azure Drake", 1);
    }

    /**
     * Tests card can be countered for cast with Cavern of Souls
     */
    @Test
    public void testDrakeCantBeCountered() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Mountain", 3);
        addCard(Constants.Zone.HAND, playerA, "Cavern of Souls");
        addCard(Constants.Zone.HAND, playerA, "Azure Drake");

        addCard(Constants.Zone.HAND, playerB, "Remove Soul");
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Island", 2);

        setChoice(playerA, "Drake");
        setChoice(playerA, "Blue");

        playLand(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Cavern of Souls");
        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Azure Drake");
        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerB, "Remove Soul");

        setStopAt(1, Constants.PhaseStep.BEGIN_COMBAT);
        execute();

        // check wasn't countered
        assertGraveyardCount(playerA, "Azure Drake", 0);
        assertPermanentCount(playerA, "Azure Drake", 1);
    }

}
