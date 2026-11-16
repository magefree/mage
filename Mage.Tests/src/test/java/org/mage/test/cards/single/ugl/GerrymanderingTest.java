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
 * <p>
 * 731.3
 * If a merged permanent leaves the battlefield, one permanent leaves the battlefield and each of the individual
 * components are put into the appropriate zone.
 * <p>
 * 731.3c
 * If an effect can find the new object that a merged permanent becomes as it leaves the battlefield, it finds all of
 * those objects.
 *
 * @author DarioBarrabinoDev
 */
public class GerrymanderingTest extends CardTestPlayerBase {

    @Test
    public void test_ModalDoubleFaced_BackFace() {
        // Exile all lands. Give each player a number of those cards chosen at random equal to the number of those
        // cards the player controlled. Each player returns those cards to the battlefield under their control.
        addCard(Zone.HAND, playerA, "Gerrymandering"); // {2}{G}
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Boulderloft Pathway"); // modal double-faced, back face land

        // only playerA controls lands, so all of them return to playerA
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Gerrymandering");

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
        addCard(Zone.HAND, playerA, "Gerrymandering"); // {2}{G}
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Azcanta, the Sunken Ruin"); // transformed Search for Azcanta, land

        // only playerA controls lands, so all of them return to playerA
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Gerrymandering");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertExileCount(playerA, 0);
        assertPermanentCount(playerA, "Azcanta, the Sunken Ruin", 0);
        assertPermanentCount(playerA, "Search for Azcanta", 1); // returns with front face up
        assertPermanentCount(playerA, "Forest", 3);
    }

    @Test
    public void test_Mutated_AllCardsAreGivenOut() {
        // Exile all lands. Give each player a number of those cards chosen at random equal to the number of those
        // cards the player controlled. Each player returns those cards to the battlefield under their control.
        addCard(Zone.HAND, playerA, "Gerrymandering"); // {2}{G}
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Dryad Arbor"); // land creature, so it can be mutated onto
        addCard(Zone.HAND, playerA, "Sea-Dasher Octopus"); // flash, mutate {1}{U}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sea-Dasher Octopus using Mutate", "Dryad Arbor");
        setChoice(playerA, true); // mutate under, so Dryad Arbor stays on top and the permanent is still a land
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // only playerA controls lands, so all of them return to playerA
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Gerrymandering");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertExileCount(playerA, 0); // no component is left behind in exile
        assertPermanentCount(playerA, "Dryad Arbor", 1);
        assertPermanentCount(playerA, "Sea-Dasher Octopus", 1); // the component under the land also returns
        assertPermanentCount(playerA, "Forest", 3);
        assertPermanentCount(playerA, "Island", 2);
    }
}
