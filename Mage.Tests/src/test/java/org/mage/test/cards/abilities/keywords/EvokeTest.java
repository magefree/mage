

package org.mage.test.cards.abilities.keywords;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */

public class EvokeTest extends CardTestPlayerBase {

   /*
    * Shriekmaw         {4}{B}
    * Creature — Elemental
    * 3/2
    * Fear (This creature can't be blocked except by artifact creatures and/or black creatures.)
    * When Shriekmaw enters the battlefield, destroy target nonartifact, nonblack creature.
    * Evoke {1}{B}  (You may cast this spell for its evoke cost. If you do, it's sacrificed when it enters the battlefield.)

    Exhume  {1}{B}
    Sorcery
    Each player puts a creature card from their graveyard onto the battlefield.

    */

    @Test
    public void testCreatureComesIntoPlay() {
        // Check that Lion goes to graveyard from evoke ability
        // Check that evoke does not trigger again to sacrifice Shriekmaw if it's exhumed

        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);
        addCard(Zone.HAND, playerA, "Shriekmaw");
        addCard(Zone.HAND, playerA, "Exhume");

        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Shriekmaw");
        setChoice(playerA, true);
        // addTarget(playerA, "Silvercoat Lion"); Autochosen, only target
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Exhume");
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertGraveyardCount(playerA, "Exhume", 1);
        assertGraveyardCount(playerB, "Silvercoat Lion", 1); // went again to graveyard from shriekmaw's triggered ability

        assertPermanentCount(playerA, "Shriekmaw", 1);
    }


}