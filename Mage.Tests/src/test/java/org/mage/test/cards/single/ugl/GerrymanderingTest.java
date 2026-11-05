package org.mage.test.cards.single.ugl;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
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
public class GerrymanderingTest extends CardTestPlayerBase {

    private static final String GERRYMANDERING = "Gerrymandering";

    @Test
    public void test_ModalDoubleFaced_BackFace() {
        // Exile all lands. Give each player a number of those cards chosen at random equal to the number of those
        // cards the player controlled. Each player returns those cards to the battlefield under their control.
        addCard(Zone.HAND, playerA, GERRYMANDERING); // {2}{G}
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Boulderloft Pathway"); // modal double-faced, back face land

        // only playerA controls lands, so all of them return to playerA
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, GERRYMANDERING);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertExileCount(playerA, 0);
        assertPermanentCount(playerA, "Boulderloft Pathway", 0);
        assertPermanentCount(playerA, "Branchloft Pathway", 1); // returns with front face up
        assertPermanentCount(playerA, "Forest", 3);
    }

    @Test
    public void test_TransformingDoubleFaced_BackFace() {
        // Exile all lands. Give each player a number of those cards chosen at random equal to the number of those
        // cards the player controlled. Each player returns those cards to the battlefield under their control.
        addCard(Zone.HAND, playerA, GERRYMANDERING); // {2}{G}
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Azcanta, the Sunken Ruin"); // transformed Search for Azcanta, land

        // only playerA controls lands, so all of them return to playerA
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, GERRYMANDERING);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertExileCount(playerA, 0);
        assertPermanentCount(playerA, "Azcanta, the Sunken Ruin", 0);
        assertPermanentCount(playerA, "Search for Azcanta", 1); // returns with front face up
        assertPermanentCount(playerA, "Forest", 3);
    }
}
