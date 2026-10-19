package org.mage.test.cards.single.ydsk;

import mage.constants.PhaseStep;
import mage.constants.Zone;

import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class ChitteringIlluminatorTest extends CardTestPlayerBase {

    @Test
    public void testCastsItselfFromLibraryThenTopCreature() {
        removeAllCardsFromLibrary(playerA);
        skipInitShuffling();
        addCard(Zone.LIBRARY, playerA, "Grizzly Bears");
        addCard(Zone.LIBRARY, playerA, "Chittering Illuminator");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Chittering Illuminator");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears");
        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Chittering Illuminator", 1);
        assertPermanentCount(playerA, "Grizzly Bears", 1);
    }

    @Test
    public void testDoesNotAllowNoncreaturesOrBuriedCopy() {
        removeAllCardsFromLibrary(playerA);
        skipInitShuffling();
        addCard(Zone.LIBRARY, playerA, "Chittering Illuminator");
        addCard(Zone.LIBRARY, playerA, "Divination");
        addCard(Zone.BATTLEFIELD, playerA, "Chittering Illuminator");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        checkPlayableAbility("noncreature", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Divination", false);
        checkPlayableAbility("buried", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Chittering Illuminator", false);
        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLibraryCount(playerA, "Divination", 1);
    }

}
