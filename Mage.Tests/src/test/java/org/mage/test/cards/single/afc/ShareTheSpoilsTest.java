package org.mage.test.cards.single.afc;

import mage.cards.s.ShareTheSpoils;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestCommander4Players;


public class ShareTheSpoilsTest extends CardTestCommander4Players {

    private static final String shareTheSpoils = "Share the Spoils";

    /**
     * When Share the Spoils enters the battlefield every player exiles one card.
     */
    @Test
    public void enterTheBattleField() {
        addCard(Zone.HAND, playerA, shareTheSpoils);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, shareTheSpoils);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertAllCommandsUsed();
        // TODO: figure out how to make this work now that i'm using the UUID
//        assertExileZoneCount("Share the Spoils " + ShareTheSpoils.getExileNumber(), 4);
        assertExileCount(playerA, 1);
        assertExileCount(playerB, 1);
        assertExileCount(playerC, 1);
        assertExileCount(playerD, 1);
    }

    /**
     * When an opponent loses, their exiled cards are removed from the game and all other players exile a new card.
     */
    @Test
    public void nonOwnerLoses() {
        setLife(playerD, 1);
        addCard(Zone.BATTLEFIELD, playerA, shareTheSpoils);
        addCard(Zone.BATTLEFIELD, playerA, "Banehound", 1);

        attack(1, playerA, "Banehound", playerD);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertAllCommandsUsed();
        // 3 from 1 activation when playerD concedes
//        assertExileZoneCount("Share the Spoils " + ShareTheSpoils.getExileNumber(), 3);
        assertExileCount(playerA, 1);
        assertExileCount(playerB, 1);
        assertExileCount(playerC, 1);
        assertExileCount(playerD, 0);
    }

    /**
     * When an opponent loses, their exiled cards are removed from the game and all other players exile a new card.
     */
    @Test
    public void nonOwnerConcedes() {
        addCard(Zone.BATTLEFIELD, playerA, shareTheSpoils);
        concede(1, PhaseStep.PRECOMBAT_MAIN, playerD);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertAllCommandsUsed();
        // 3 from 1 activation when playerD concedes
//        assertExileZoneCount("Share the Spoils " + ShareTheSpoils.getExileNumber(), 3);
        assertExileCount(playerA, 1);
        assertExileCount(playerB, 1);
        assertExileCount(playerC, 1);
        assertExileCount(playerD, 0);
    }

    /**
     * When owner loses, no new cards should be exiled and owner's exiled cards are removed from the game.
     */
    @Test
    public void ownerConcedes() {
        addCard(Zone.HAND, playerA, shareTheSpoils);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, shareTheSpoils);
        concede(1, PhaseStep.POSTCOMBAT_MAIN, playerA);

        setStopAt(2, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertAllCommandsUsed();
        // 4 initial exiled cards - 1 card for playedA losing and removing their card
//        assertExileZoneCount("Share the Spoils " + ShareTheSpoils.getExileNumber(), 3);
        assertExileCount(playerA, 0);
        assertExileCount(playerB, 1);
        assertExileCount(playerC, 1);
        assertExileCount(playerD, 1);
    }

    @Test
    public void ownerLoses() {
        setLife(playerA, 1);
        addCard(Zone.HAND, playerA, shareTheSpoils);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.BATTLEFIELD, playerD, "Banehound", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, shareTheSpoils);
        attack(2, playerD, "Banehound", playerA);

        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertAllCommandsUsed();
        // 4 initial exiled cards - 1 card for playedA losing and removing their card
//        assertExileZoneCount("Share the Spoils " + ShareTheSpoils.getExileNumber(), 3);
        assertExileCount(playerA, 0);
        assertExileCount(playerB, 1);
        assertExileCount(playerC, 1);
        assertExileCount(playerD, 1);
    }

    // TODO: How to cast spells from exile using commands
    //       Looks PsychicIntrusionTest.java
    // Limit to only cast on your turn

    // Limit to one card per turn

    // Exile card when you cast
}
