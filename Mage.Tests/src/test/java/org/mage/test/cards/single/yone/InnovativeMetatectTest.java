package org.mage.test.cards.single.yone;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class InnovativeMetatectTest extends CardTestPlayerBase {

    @Test
    public void testMultipleArtifactCreaturesSeekOnlyOnce() {
        removeAllCardsFromLibrary(playerA);
        skipInitShuffling();
        addCard(Zone.LIBRARY, playerA, "Forest");
        addCard(Zone.LIBRARY, playerA, "Divination");
        addCard(Zone.LIBRARY, playerA, "Shock");
        addCard(Zone.BATTLEFIELD, playerA, "Innovative Metatect");
        addCard(Zone.BATTLEFIELD, playerA, "Memnite", 2);
        attack(1, playerA, "Memnite");
        attack(1, playerA, "Memnite");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertHandCount(playerA, "Shock", 1);
        assertHandCount(playerA, 1);
        assertLibraryCount(playerA, "Divination", 1);
        assertLibraryCount(playerA, "Forest", 1);
    }

    @Test
    public void testNonartifactCreatureDoesNotTrigger() {
        removeAllCardsFromLibrary(playerA);
        skipInitShuffling();
        addCard(Zone.LIBRARY, playerA, "Shock");
        addCard(Zone.BATTLEFIELD, playerA, "Innovative Metatect");
        attack(1, playerA, "Innovative Metatect");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertHandCount(playerA, 0);
        assertLibraryCount(playerA, "Shock", 1);
    }

    @Test
    public void testNoEligibleCard() {
        removeAllCardsFromLibrary(playerA);
        skipInitShuffling();
        addCard(Zone.LIBRARY, playerA, "Forest");
        addCard(Zone.LIBRARY, playerA, "Divination");
        addCard(Zone.BATTLEFIELD, playerA, "Innovative Metatect");
        addCard(Zone.BATTLEFIELD, playerA, "Memnite");
        attack(1, playerA, "Memnite");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertHandCount(playerA, 0);
        assertLibraryCount(playerA, 2);
    }
}
