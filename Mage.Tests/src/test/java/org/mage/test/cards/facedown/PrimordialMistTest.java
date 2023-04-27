package org.mage.test.cards.facedown;

import mage.constants.EmptyNames;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class PrimordialMistTest extends CardTestPlayerBase {

    /**
     * I have Brine Elemental played face down as a morph, an artifact which has
     * been manifested and Kadena which has been turned face by Ixidron. I can't
     * seem to activate Primordial Mist's second ability for any of these kinds
     * of face down creatures:
     */
    @Test
    public void test_ExileAndCastMorphFaceDownCard() {
        setStrictChooseMode(true);

        // At the beginning of your end step, you may manifest the top card of your library.
        // Exile a face-down permanent you control face-up: You may play that card this turn        
        addCard(Zone.BATTLEFIELD, playerA, "Primordial Mist");
        // Morph {5}{U}{U}
        // When Brine Elemental is turned face up, each opponent skips their next untap step.        
        addCard(Zone.HAND, playerA, "Brine Elemental"); // Creature {5}{U}{U} (5/4)
        addCard(Zone.BATTLEFIELD, playerA, "Island", 9);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Brine Elemental");
        setChoice(playerA, true); // cast it face down as 2/2 creature
        
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Exile a face-down permanent you control");
        setChoice(playerA, EmptyNames.FACE_DOWN_CREATURE.toString());

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Brine Elemental");
        setChoice(playerA, false); // cast it face down as 2/2 creature
        
        setChoice(playerA, true);
                
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertExileCount(playerA, 0);
        
        assertPowerToughness(playerA, "Brine Elemental", 5, 4);

    }
}
