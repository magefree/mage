package org.mage.test.cards.single.frc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.game.permanent.Permanent;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestCommander3PlayersFFA;

/**
 * @author Susucr
 */
public class DackFaydenHelpingHandTest extends CardTestCommander3PlayersFFA {

    private static final String dack = "Dack Fayden, Helping Hand";

    /**
     * Dack Fayden, Helping Hand
     * {4}{W}{W}
     * Legendary Creature — Human Advisor
     * 4/6
     * When Dack Fayden enters, reveal cards from the top of your library until you reveal X creature cards,
     * where X is the number of opponents you have. Put those creature cards onto the battlefield, then shuffle.
     * They're goaded for the rest of the game. For each of those permanents, choose a different opponent.
     * Each opponent gains control of the permanent for which they were chosen.
     */
    @Test
    public void testTwoCreaturesGivenToTwoOpponents() {
        skipInitShuffling();
        addCard(Zone.HAND, playerA, dack);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 6);

        removeAllCardsFromLibrary(playerA);
        addCard(Zone.LIBRARY, playerA, "Forest");
        addCard(Zone.LIBRARY, playerA, "Hill Giant"); // 2nd creature revealed
        addCard(Zone.LIBRARY, playerA, "Mountain");
        addCard(Zone.LIBRARY, playerA, "Grizzly Bears"); // 1st creature revealed
        addCard(Zone.LIBRARY, playerA, "Plains", 2); // 1 drawn on T1 draw step, 1 revealed

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, dack);
        setChoice(playerA, "PlayerB"); // choice for Grizzly Bears
        setChoice(playerA, "PlayerC"); // choice for Hill Giant

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, dack, 1);
        assertPermanentCount(playerB, "Grizzly Bears", 1);
        assertPermanentCount(playerC, "Hill Giant", 1);
        assertPermanentCount(playerA, "Grizzly Bears", 0);
        assertPermanentCount(playerA, "Hill Giant", 0);
    }

    @Test
    public void testFewerCreaturesThanOpponents() {
        skipInitShuffling();
        addCard(Zone.HAND, playerA, dack);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 6);

        removeAllCardsFromLibrary(playerA);
        addCard(Zone.LIBRARY, playerA, "Forest");
        addCard(Zone.LIBRARY, playerA, "Mountain");
        addCard(Zone.LIBRARY, playerA, "Grizzly Bears"); // only 1 creature
        addCard(Zone.LIBRARY, playerA, "Plains", 2); // 1 drawn on T1 draw step, 1 revealed

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, dack);
        setChoice(playerA, "PlayerB"); // choice for Grizzly Bears

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, dack, 1);
        assertPermanentCount(playerB, "Grizzly Bears", 1);
        assertPermanentCount(playerC, "Grizzly Bears", 0);
        assertPermanentCount(playerA, "Grizzly Bears", 0);
    }

    @Test
    public void testGoadedMustAttackOtherPlayer() {
        skipInitShuffling();
        addCard(Zone.HAND, playerA, dack);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 6);

        removeAllCardsFromLibrary(playerA);
        addCard(Zone.LIBRARY, playerA, "Forest");
        addCard(Zone.LIBRARY, playerA, "Hill Giant"); // 2nd creature revealed (to PlayerC)
        addCard(Zone.LIBRARY, playerA, "Mountain");
        addCard(Zone.LIBRARY, playerA, "Grizzly Bears"); // 1st creature revealed (to PlayerB)
        addCard(Zone.LIBRARY, playerA, "Plains", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, dack);
        setChoice(playerA, "PlayerB"); // Grizzly Bears to PlayerB
        setChoice(playerA, "PlayerC"); // Hill Giant to PlayerC

        // Player order: PlayerA (T1) -> PlayerC (T2) -> PlayerB (T3)
        // On turn 2 (Player C's turn), Player C controls Hill Giant. Because it is goaded by Player A,
        // it must attack Player B (the only other opponent).
        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.DECLARE_BLOCKERS);
        execute();

        assertPermanentCount(playerB, "Grizzly Bears", 1);
        assertPermanentCount(playerC, "Hill Giant", 1);

        Permanent hillGiant = getPermanent("Hill Giant");
        Assert.assertTrue("Hill Giant must be goaded by Player A", hillGiant.getGoadingPlayers().contains(playerA.getId()));
        Assert.assertTrue("Hill Giant must be attacking", hillGiant.isAttacking());
        Assert.assertEquals("Hill Giant must attack Player B", playerB.getId(), currentGame.getCombat().getDefenderId(hillGiant.getId()));

        Permanent grizzlyBears = getPermanent("Grizzly Bears");
        Assert.assertTrue("Grizzly Bears must be goaded by Player A", grizzlyBears.getGoadingPlayers().contains(playerA.getId()));
    }
}
