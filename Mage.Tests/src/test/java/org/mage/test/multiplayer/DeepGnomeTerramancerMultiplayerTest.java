package org.mage.test.multiplayer;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestMultiPlayerBaseWithRangeAll;

/**
 * @author JayDi85
 */
public class DeepGnomeTerramancerMultiplayerTest extends CardTestMultiPlayerBaseWithRangeAll {

    @Test
    public void test_FieldOfRuin_LandsOfAllOpponentsEnter_OneTrigger() {
        // Player order: A -> D -> C -> B
        removeAllCardsFromLibrary(playerA);
        removeAllCardsFromLibrary(playerB);
        removeAllCardsFromLibrary(playerC);
        removeAllCardsFromLibrary(playerD);

        // Whenever one or more lands enter the battlefield under an opponent's control without being played,
        // you may search your library for a Plains card, put it onto the battlefield tapped, then shuffle.
        // Do this only once each turn.
        addCard(Zone.BATTLEFIELD, playerA, "Deep Gnome Terramancer");
        addCard(Zone.LIBRARY, playerA, "Plains", 3);
        //
        // {2}, {T}, Sacrifice Field of Ruin: Destroy target nonbasic land an opponent controls. Each player searches
        // their library for a basic land card, puts it onto the battlefield, then shuffles.
        addCard(Zone.BATTLEFIELD, playerB, "Field of Ruin");
        addCard(Zone.BATTLEFIELD, playerB, "Wastes", 2);
        addCard(Zone.LIBRARY, playerB, "Forest", 1);
        addCard(Zone.LIBRARY, playerC, "Forest", 1);
        addCard(Zone.LIBRARY, playerD, "Forest", 1);
        addCard(Zone.BATTLEFIELD, playerC, "Evolving Wilds", 1); // nonbasic land

        // lands of all players enter at once (3 of them under A's opponents) - one trigger only
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerB, "{2}, {T}, Sacrifice", "Evolving Wilds");
        addTarget(playerA, "Plains"); // search by Field of Ruin
        addTarget(playerB, "Forest");
        addTarget(playerC, "Forest");
        addTarget(playerD, "Forest");
        setChoice(playerA, true); // yes to trigger from Gnome
        addTarget(playerA, "Plains"); // search by trigger

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerC, "Evolving Wilds", 1);
        assertPermanentCount(playerB, "Forest", 1);
        assertPermanentCount(playerC, "Forest", 1);
        assertPermanentCount(playerD, "Forest", 1);
        assertPermanentCount(playerA, "Plains", 1 + 1);
    }
}
