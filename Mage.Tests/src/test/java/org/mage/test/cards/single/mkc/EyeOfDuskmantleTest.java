package org.mage.test.cards.single.mkc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author brahle
 */
public class EyeOfDuskmantleTest extends CardTestPlayerBase {

    @Test
    public void testPlayLandAndCastSpellSurveilledThisTurn() {
        setStrictChooseMode(true);
        skipInitShuffling();
        addCard(Zone.BATTLEFIELD, playerA, "Eye of Duskmantle");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.HAND, playerA, "Consider", 2);

        // Library setup: top cards are Swamp, then Island, then Grizzly Bears, then Island
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.LIBRARY, playerA, "Island");
        addCard(Zone.LIBRARY, playerA, "Grizzly Bears"); // MV 2
        addCard(Zone.LIBRARY, playerA, "Island");
        addCard(Zone.LIBRARY, playerA, "Swamp");

        // Cast Consider 1: looks at Swamp, put it into graveyard (surveil), draw Island
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Consider");
        addTarget(playerA, "Swamp"); // Put Swamp into graveyard
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // Play Swamp from graveyard as land
        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Swamp");

        // Cast Consider 2: looks at Grizzly Bears, put it into graveyard (surveil), draw Island
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Consider");
        addTarget(playerA, "Grizzly Bears"); // Put Grizzly Bears into graveyard
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // Cast Grizzly Bears from graveyard paying 2 life (no mana needed)
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Grizzly Bears");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Swamp", 1);
        assertPermanentCount(playerA, "Grizzly Bears", 1);
        assertGraveyardCount(playerA, "Grizzly Bears", 0);
        // Started at 20 life, paid 2 life for Grizzly Bears (MV 2)
        assertLife(playerA, 18);
    }

    @Test
    public void testCardInGraveyardNotSurveilledCannotBeCast() {
        setStrictChooseMode(true);
        addCard(Zone.BATTLEFIELD, playerA, "Eye of Duskmantle");
        addCard(Zone.GRAVEYARD, playerA, "Hill Giant"); // In graveyard without being surveilled

        checkPlayableAbility("Cannot cast card not surveilled", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Hill Giant", false);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Hill Giant", 0);
        assertGraveyardCount(playerA, "Hill Giant", 1);
    }

    @Test
    public void testCardSurveilledToTopOfLibraryThenMilledCannotBeCast() {
        setStrictChooseMode(true);
        skipInitShuffling();
        addCard(Zone.BATTLEFIELD, playerA, "Eye of Duskmantle");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.HAND, playerA, "Consider");
        addCard(Zone.BATTLEFIELD, playerA, "Putrid Imp");

        // Library setup: Grizzly Bears on top
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.LIBRARY, playerA, "Forest", 4);
        addCard(Zone.LIBRARY, playerA, "Grizzly Bears");

        // Consider: leave on top (TARGET_SKIP), draw Grizzly Bears
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Consider");
        addTarget(playerA, TestPlayer.TARGET_SKIP); // Do not put into graveyard via surveil
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // Discard Grizzly Bears via Putrid Imp
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Discard a card:");
        setChoice(playerA, "Grizzly Bears");

        // Grizzly Bears was looked at during surveil, but put in graveyard via discard, so NOT surveilled into graveyard
        checkPlayableAbility("Cannot cast card that was left on top and then discarded", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Cast Grizzly Bears", false);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Grizzly Bears", 0);
        assertGraveyardCount(playerA, "Grizzly Bears", 1);
    }

    @Test
    public void testSurveilledCardCannotBeCastOnLaterTurn() {
        setStrictChooseMode(true);
        skipInitShuffling();
        addCard(Zone.BATTLEFIELD, playerA, "Eye of Duskmantle");
        addCard(Zone.BATTLEFIELD, playerA, "Island");
        addCard(Zone.HAND, playerA, "Consider");

        removeAllCardsFromLibrary(playerA);
        addCard(Zone.LIBRARY, playerA, "Island", 3);
        addCard(Zone.LIBRARY, playerA, "Grizzly Bears");

        // Turn 1: surveil Grizzly Bears into graveyard
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Consider");
        addTarget(playerA, "Grizzly Bears");

        // On Turn 3 (player A's next turn), Grizzly Bears cannot be cast
        checkPlayableAbility("Cannot cast surveilled card on later turn", 3, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Grizzly Bears", false);

        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Grizzly Bears", 0);
        assertGraveyardCount(playerA, "Grizzly Bears", 1);
    }
}
