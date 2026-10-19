package org.mage.test.cards.single.ybro;

import mage.constants.PhaseStep;
import mage.constants.Zone;

import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class JarsylDarkAgeScionTest extends CardTestPlayerBase {

    @Test
    public void testCopyStartsAtTheCopiedIntensity() {
        addCard(Zone.BATTLEFIELD, playerB, "Jarsyl, Dark Age Scion");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        addCard(Zone.HAND, playerA, "Clone");
        addCard(Zone.GRAVEYARD, playerA, "Llanowar Elves");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Clone");
        setChoice(playerA, true);
        setChoice(playerA, "Jarsyl, Dark Age Scion");
        setChoice(playerA, true);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Llanowar Elves", 1);
    }

    @Test
    public void testIntensityAdvancesAndSurvivesBounce() {
        addCard(Zone.BATTLEFIELD, playerA, "Jarsyl, Dark Age Scion");
        addCard(Zone.GRAVEYARD, playerA, "Llanowar Elves");
        addCard(Zone.GRAVEYARD, playerA, "Grizzly Bears");
        addCard(Zone.BATTLEFIELD, playerA, "Taiga", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Island");
        addCard(Zone.HAND, playerA, "Unsummon");
        setChoice(playerA, true);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Unsummon", "Jarsyl, Dark Age Scion");
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Jarsyl, Dark Age Scion");
        setChoice(playerA, true);
        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Llanowar Elves", 1);
        assertPermanentCount(playerA, "Grizzly Bears", 1);
    }

    @Test
    public void testDeclineDoesNotIntensify() {
        addCard(Zone.BATTLEFIELD, playerA, "Jarsyl, Dark Age Scion");
        addCard(Zone.GRAVEYARD, playerA, "Llanowar Elves");
        addCard(Zone.GRAVEYARD, playerA, "Grizzly Bears");
        setChoice(playerA, false);
        setChoice(playerA, true);
        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Llanowar Elves", 1);
        assertGraveyardCount(playerA, "Grizzly Bears", 1);
    }

}
