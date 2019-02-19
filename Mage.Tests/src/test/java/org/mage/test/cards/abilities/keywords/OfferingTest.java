package org.mage.test.cards.abilities.keywords;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class OfferingTest extends CardTestPlayerBase {

    private static final String nezumiPatron = "Patron of the Nezumi";


    @Test
    public void testOfferRatDecreaseCC() {

        String kurosTaken = "Kuro's Taken";

        addCard(Zone.HAND, playerA, nezumiPatron, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 8);
        addCard(Zone.BATTLEFIELD, playerA, kurosTaken);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, nezumiPatron);
        setChoice(playerA, "Yes");
        addTarget(playerA, kurosTaken);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerA, kurosTaken, 1);
        assertPermanentCount(playerA, nezumiPatron, 1);
        assertTappedCount("Swamp", true, 5); // {5}{B}{B} - {1}{B} = {4}{B} = 5 swamps tapped
    }

    @Test
    public void testDontOfferRatNotDecreaseCC() {

        String kurosTaken = "Kuro's Taken";

        addCard(Zone.HAND, playerA, nezumiPatron, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 8);
        addCard(Zone.BATTLEFIELD, playerA, kurosTaken);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, nezumiPatron);
        setChoice(playerA, "No");
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, kurosTaken, 1);
        assertPermanentCount(playerA, nezumiPatron, 1);
        assertTappedCount("Swamp", true, 7); // {5}{B}{B} - {1}{B} = {4}{B} = 7 swamps tapped
    }
}
