package org.mage.test.cards.single.hbg;

import mage.constants.PhaseStep;
import mage.constants.Zone;

import org.junit.Test;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestPlayerBase;

import java.util.ArrayList;

public class CalimDjinnEmperorTest extends CardTestPlayerBase {

    @Test
    public void testDiscardConjuresSeventhBeforeBreathDraw() {
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.LIBRARY, playerA, "Island", 10);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.HAND, playerA, "Calim, Djinn Emperor");
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "<i>Calim's Breath</i>");
        addTarget(playerA, "Grizzly Bears");
        runCode("sixth after drawing", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, (info, player, game) -> {
            org.junit.Assert.assertEquals("Calim, Djinn Emperor", new ArrayList<>(player.getLibrary().getTopCards(game, 6)).get(5).getName());
        });
        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertTapped("Grizzly Bears", true);
        assertHandCount(playerA, "Island", 1);
        assertLibraryCount(playerA, "Calim, Djinn Emperor", 1);
        assertGraveyardCount(playerA, "Calim, Djinn Emperor", 1);
    }

    @Test
    public void testTwoOtherCopiesReturnDiscardedCalimTapped() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.HAND, playerA, "Calim, Djinn Emperor");
        addCard(Zone.GRAVEYARD, playerA, "Calim, Djinn Emperor", 2);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "<i>Calim's Breath</i>");
        addTarget(playerA, TestPlayer.TARGET_SKIP);
        setChoice(playerA, true);
        setChoice(playerA, "Calim, Djinn Emperor");
        setChoice(playerA, "Calim, Djinn Emperor");
        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Calim, Djinn Emperor", 1);
        assertTapped("Calim, Djinn Emperor", true);
        assertExileCount(playerA, "Calim, Djinn Emperor", 2);
    }

    @Test
    public void testDiscardFromAnotherSpellAlsoConjures() {
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.LIBRARY, playerA, "Island", 10);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.HAND, playerA, "Calim, Djinn Emperor");
        addCard(Zone.HAND, playerA, "Faithless Looting");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Faithless Looting");
        setChoice(playerA, "Calim, Djinn Emperor");
        setChoice(playerA, "Island");
        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLibraryCount(playerA, "Calim, Djinn Emperor", 1);
        assertGraveyardCount(playerA, "Calim, Djinn Emperor", 1);
    }

}
