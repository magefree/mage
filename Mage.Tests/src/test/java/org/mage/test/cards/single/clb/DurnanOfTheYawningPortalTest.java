package org.mage.test.cards.single.clb;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author brahle
 */
public class DurnanOfTheYawningPortalTest extends CardTestPlayerBase {

    @Test
    public void testAttackExilesCreatureAndGrantsUndaunted() {
        skipInitShuffling();
        addCard(Zone.BATTLEFIELD, playerA, "Durnan of the Yawning Portal");
        // Hill Giant costs {3}{R}; with 1 opponent, Undaunted reduces its cost by {1} to {2}{R} (3 Mountains)
        addCard(Zone.LIBRARY, playerA, "Hill Giant");
        addCard(Zone.LIBRARY, playerA, "Forest", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);

        attack(1, playerA, "Durnan of the Yawning Portal", playerB);
        setChoice(playerA, "Hill Giant");
        setChoice(playerA, "Forest", 2);

        // Cast exiled Hill Giant in postcombat main using only 3 Mountains thanks to Undaunted
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Hill Giant");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Hill Giant", 1);
        assertExileCount(playerA, "Hill Giant", 0);
    }

    @Test
    public void cannotPlayLandFace() {
        skipInitShuffling();
        addCard(Zone.BATTLEFIELD, playerA, "Durnan of the Yawning Portal");
        addCard(Zone.LIBRARY, playerA, "Kazandu Mammoth");
        addCard(Zone.LIBRARY, playerA, "Forest", 3);

        attack(1, playerA, "Durnan of the Yawning Portal", playerB);
        setChoice(playerA, "Kazandu Mammoth");
        setChoice(playerA, "Forest", 2);

        checkPlayableAbility("Durnan only allows casting", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Play Kazandu Valley", false);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void adventureGetsUndaunted() {
        skipInitShuffling();
        addCard(Zone.BATTLEFIELD, playerA, "Durnan of the Yawning Portal");
        addCard(Zone.LIBRARY, playerA, "Beanstalk Giant");
        addCard(Zone.LIBRARY, playerA, "Forest", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);

        attack(1, playerA, "Durnan of the Yawning Portal", playerB);
        setChoice(playerA, "Beanstalk Giant");
        setChoice(playerA, "Forest", 2);

        // Fertile Footsteps is normally {2}{G}; with 1 opponent, Undaunted reduces it to {1}{G} (castable with 2 Forests)
        checkPlayableAbility("Adventure should cost 1G", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Cast Fertile Footsteps", true);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }
}
