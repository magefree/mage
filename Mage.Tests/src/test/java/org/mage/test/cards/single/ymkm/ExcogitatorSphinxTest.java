package org.mage.test.cards.single.ymkm;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class ExcogitatorSphinxTest extends CardTestPlayerBase {

    @Test
    public void testMultipleCreaturesInvestigateOnlyOnce() {
        addCard(Zone.BATTLEFIELD, playerA, "Excogitator Sphinx");
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears");
        attack(1, playerA, "Excogitator Sphinx");
        attack(1, playerA, "Grizzly Bears");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Clue Token", 1);
        assertLife(playerB, 14);
    }

    @Test
    public void testSacrificeClueSeeksInstant() {
        checkSeek("Shock");
    }

    @Test
    public void testSacrificeClueSeeksSorcery() {
        checkSeek("Divination");
    }

    private void checkSeek(String spell) {
        removeAllCardsFromLibrary(playerA);
        skipInitShuffling();
        addCard(Zone.LIBRARY, playerA, "Island");
        addCard(Zone.LIBRARY, playerA, "Grizzly Bears");
        addCard(Zone.LIBRARY, playerA, spell);
        addCard(Zone.BATTLEFIELD, playerA, "Excogitator Sphinx");
        addCard(Zone.BATTLEFIELD, playerA, "Island");
        addCard(Zone.BATTLEFIELD, playerA, "Plains");
        addCard(Zone.HAND, playerA, "Thraben Inspector");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Thraben Inspector", true);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}, Sacrifice a Clue");
        setChoice(playerA, "Clue Token");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Clue Token", 0);
        assertHandCount(playerA, spell, 1);
        assertLibraryCount(playerA, 2);
        assertTapped("Island", true);
    }

    @Test
    public void testNoEligibleSpellStillSacrificesClue() {
        removeAllCardsFromLibrary(playerA);
        skipInitShuffling();
        addCard(Zone.LIBRARY, playerA, "Island");
        addCard(Zone.LIBRARY, playerA, "Grizzly Bears");
        addCard(Zone.BATTLEFIELD, playerA, "Excogitator Sphinx");
        addCard(Zone.BATTLEFIELD, playerA, "Island");
        addCard(Zone.BATTLEFIELD, playerA, "Plains");
        addCard(Zone.HAND, playerA, "Thraben Inspector");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Thraben Inspector", true);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}, Sacrifice a Clue");
        setChoice(playerA, "Clue Token");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Clue Token", 0);
        assertHandCount(playerA, 0);
        assertLibraryCount(playerA, 2);
    }

    @Test
    public void testCannotSacrificeOpponentsClue() {
        addCard(Zone.BATTLEFIELD, playerA, "Excogitator Sphinx");
        addCard(Zone.BATTLEFIELD, playerA, "Island");
        addCard(Zone.BATTLEFIELD, playerB, "Plains");
        addCard(Zone.HAND, playerB, "Thraben Inspector");
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Thraben Inspector", true);
        checkPlayableAbility("no own Clue", 3, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}, Sacrifice a Clue", false);

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();
    }
}
