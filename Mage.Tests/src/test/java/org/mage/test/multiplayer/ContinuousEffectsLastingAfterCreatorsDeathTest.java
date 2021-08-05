package org.mage.test.multiplayer;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestMultiPlayerBase;

/**
 *
 * @author LevelX2
 */
public class ContinuousEffectsLastingAfterCreatorsDeathTest extends CardTestMultiPlayerBase {

    // Player order: A -> D -> C -> B
    @Test
    public void testDontUntapNormal() {
        // Trample
        // Whenever you tap a land for mana, add one mana of any type that land produced.
        // Whenever an opponent taps a land for mana, that land doesn't untap during its controller's next untap step.
        addCard(Zone.BATTLEFIELD, playerA, "Vorinclex, Voice of Hunger"); // Creature {6}{G}{G} 7/6

        addCard(Zone.BATTLEFIELD, playerD, "Plains", 2);
        addCard(Zone.HAND, playerD, "Silvercoat Lion");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerD, "Silvercoat Lion");

        setStopAt(6, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Vorinclex, Voice of Hunger", 1);
        assertPermanentCount(playerD, "Silvercoat Lion", 1);

        Assert.assertTrue("Active player is player D", currentGame.getActivePlayerId().equals(playerD.getId()));
        assertTappedCount("Plains", true, 2);
    }

    @Test
    public void testDontUntap() {
        /**
         * https://github.com/magefree/mage/issues/6997 Some continuous effects
         * should stay in play even after the player that set them leaves the
         * game. Example:
         *
         * Player A: Casts Vorinclex, Voice of Hunger Player B: Taps all lands
         * and do stuff (lands shouldn't untap during his next untap step)
         * Player C: Kills Player A Player B: Lands untapped normally, though
         * they shouldn't
         *
         * This happened playing commander against 3 AIs. One of the AIs played
         * Vorinclex, I tapped all my lands during my turn to do stuff. Next AI
         * killed the one that had Vorinclex. When the game got to my turn, my
         * lands untapped normally.
         */

        // Trample
        // Whenever you tap a land for mana, add one mana of any type that land produced.
        // Whenever an opponent taps a land for mana, that land doesn't untap during its controller's next untap step.
        addCard(Zone.BATTLEFIELD, playerA, "Vorinclex, Voice of Hunger"); // Creature {6}{G}{G} 7/6

        addCard(Zone.BATTLEFIELD, playerD, "Plains", 2);
        addCard(Zone.HAND, playerD, "Silvercoat Lion");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerD, "Silvercoat Lion");

        concede(2, PhaseStep.POSTCOMBAT_MAIN, playerA);

        setStopAt(5, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Vorinclex, Voice of Hunger", 0);
        assertPermanentCount(playerD, "Silvercoat Lion", 1);

        Assert.assertTrue("Active player is player D", currentGame.getActivePlayerId().equals(playerD.getId()));
        assertTappedCount("Plains", true, 2);
    }

}
