package org.mage.test.cards.single;

import mage.cards.d.DelayedBlastFireball;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;


/**
 * Tests for {@link DelayedBlastFireball}
 * TODO
 * Bug test: Copy
 *  https://github.com/magefree/mage/issues/9180
 *
 * @author the-red-lily
 */
public class DelayedBlastFireballTest extends CardTestPlayerBase {

    // Delayed Blast Fireball deals 2 damage to each opponent and each creature they control.
    // If this spell was cast from exile, it deals 5 damage to each opponent and each creature they control instead.
    // Foretell {4}{R}{R} (During your turn, you may pay {2} and exile this card from your hand face down.
    //      Cast it on a later turn for its foretell cost.)
    @Test
    public void testForetoldDelayedBlastFireball() {
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.LIBRARY, playerA, "Mountain");
        addCard(Zone.HAND, playerA, "Delayed Blast Fireball", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2 + 6); //TODO

        // Foretell (pay {2} and exile this card from your hand face down. Cast it on a later turn for its foretell cost.)
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Foretell");

        // Opponent's turn
        showAvailableAbilities("abilities", 2, PhaseStep.PRECOMBAT_MAIN, playerA);
        // Foretell {4}{R}{R} Cast it on a later turn for its foretell cost.
        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerA, "Foretell");
        //activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerA, "Delayed Blast Fireball");

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertResult(playerA, GameResult.DRAW);
        assertLife(playerB, 20 - 5);
        //todo check creatures
    }

    // Test for bug https://github.com/magefree/mage/issues/9180
    @Test
    public void testCopySpell() {
        removeAllCardsFromLibrary(playerA);
//            addCard(Zone.LIBRARY, playerA, "Plains", 6);
        addCard(Zone.HAND, playerA, "Delayed Blast Fireball", 1);
        addCard(Zone.HAND, playerA, "Fork", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2 + 6); //TODO

        //Cast normally
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Delayed Blast Fireball");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Fork", "Delayed Blast Fireball", "Cast Delayed Blast Fireball"); //Copy Delayed Blast Fireball
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN); //Wait for Fork to copy and for copy to resolve

//                        checkStackSize("Approach of the Second Sun on stack", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 1);
//            checkLife("Copy of Approach of the Second Sun gains life", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 27);
//            checkLibraryCount("Copy of Approach of the Second Sun does not put a card in library",
//                    1, PhaseStep.PRECOMBAT_MAIN, playerA, "Approach of the Second Sun", 0);
//            waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
//            checkLife("Approach of the Second Sun gains life", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 34);
//            //checkLife("Approach of the Second Sun gains life - Begin Combat", 1, PhaseStep.BEGIN_COMBAT, playerA, 34); //For some reason this fails??
//            checkLibraryCount("Approach of the Second Sun goes to library",
//                    1, PhaseStep.PRECOMBAT_MAIN, playerA, "Approach of the Second Sun", 1);
//
//            //Second copy doesn't win the game
//            castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Approach of the Second Sun");
//            castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Fork", "Approach of the Second Sun", "Cast Approach of the Second Sun"); //Copy Approach of the Second Sun
//            waitStackResolved(1, PhaseStep.POSTCOMBAT_MAIN, 2); //Wait for Fork to copy and for copy to resolve
//            checkLife("Copy of 2nd Approach of the Second Sun gains life", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, 41);
//            checkLibraryCount("Copy of 2nd Approach of the Second Sun does not put a card in library",
//                    1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Approach of the Second Sun", 1); //Already have 1 in library
//            //Does not work, checks after game end do not execute
////        checkLibraryCount("2nd cast of Approach of the Second Sun does not go to library",
////                1, PhaseStep.END_TURN, playerA, "Approach of the Second Sun", 1);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertResult(playerA, GameResult.DRAW);
        assertLife(playerB, 20 - 2 - 2);
    }

    //TODO test Demilich
}

