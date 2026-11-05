package org.mage.test.cards.single.mmq;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * 400.7j
 * If an effect causes an object to move to a public zone, other parts of that effect can find that object. If the cost
 * of a spell or ability causes an object to move to a public zone, that spell or ability’s effects can find that object.
 * <p>
 * 712.8a
 * While a double-faced card is outside the game or in a zone other than the battlefield or stack, it has only the
 * characteristics of its front face.
 * <p>
 * 712.14
 * A double-faced card put onto the battlefield from a zone other than the stack enters the battlefield with its front
 * face up by default.
 *
 * @author DarioBarrabinoDev
 */
public class ThievesAuctionTest extends CardTestPlayerBase {

    private static final String THIEVES_AUCTION = "Thieves' Auction";

    @Test
    public void test_ModalDoubleFaced_FrontFace() {
        // Exile all nontoken permanents. Starting with you, each player chooses one of the exiled cards and puts it
        // onto the battlefield tapped under their control. Repeat this process until all cards exiled this way have
        // been chosen.
        addCard(Zone.HAND, playerA, THIEVES_AUCTION); // {4}{R}{R}{R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 7);
        //
        addCard(Zone.BATTLEFIELD, playerB, "Tergrid, God of Fright"); // modal double-faced, front 4/5

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, THIEVES_AUCTION);
        setChoice(playerA, "Tergrid, God of Fright"); // first pick
        setChoice(playerB, "Mountain", 4); // other picks
        setChoice(playerA, "Mountain", 3);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertExileCount(playerB, 0);
        assertPermanentCount(playerA, "Tergrid, God of Fright", 1);
        assertTapped("Tergrid, God of Fright", true);
        assertPermanentCount(playerA, "Mountain", 3);
        assertPermanentCount(playerB, "Mountain", 4);
    }

    @Test
    public void test_ModalDoubleFaced_BackFace() {
        // Exile all nontoken permanents. Starting with you, each player chooses one of the exiled cards and puts it
        // onto the battlefield tapped under their control. Repeat this process until all cards exiled this way have
        // been chosen.
        addCard(Zone.HAND, playerA, THIEVES_AUCTION); // {4}{R}{R}{R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 7);
        //
        addCard(Zone.BATTLEFIELD, playerB, "Tergrid's Lantern"); // modal double-faced, back face artifact

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, THIEVES_AUCTION);
        setChoice(playerA, "Tergrid, God of Fright"); // first pick, front face in exile
        setChoice(playerB, "Mountain", 4); // other picks
        setChoice(playerA, "Mountain", 3);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertExileCount(playerB, 0);
        assertPermanentCount(playerA, "Tergrid's Lantern", 0);
        assertPermanentCount(playerA, "Tergrid, God of Fright", 1); // returns with front face up
        assertTapped("Tergrid, God of Fright", true);
        assertPermanentCount(playerA, "Mountain", 3);
        assertPermanentCount(playerB, "Mountain", 4);
    }

    @Test
    public void test_TransformingDoubleFaced_BackFace() {
        // Exile all nontoken permanents. Starting with you, each player chooses one of the exiled cards and puts it
        // onto the battlefield tapped under their control. Repeat this process until all cards exiled this way have
        // been chosen.
        addCard(Zone.HAND, playerA, THIEVES_AUCTION); // {4}{R}{R}{R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 7);
        //
        addCard(Zone.BATTLEFIELD, playerB, "Insectile Aberration"); // transformed Delver of Secrets, 3/2 flying

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, THIEVES_AUCTION);
        setChoice(playerA, "Delver of Secrets"); // first pick, front face in exile
        setChoice(playerB, "Mountain", 4); // other picks
        setChoice(playerA, "Mountain", 3);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertExileCount(playerB, 0);
        assertPermanentCount(playerA, "Insectile Aberration", 0);
        assertPermanentCount(playerA, "Delver of Secrets", 1); // returns with front face up
        assertTapped("Delver of Secrets", true);
        assertPermanentCount(playerA, "Mountain", 3);
        assertPermanentCount(playerB, "Mountain", 4);
    }
}
