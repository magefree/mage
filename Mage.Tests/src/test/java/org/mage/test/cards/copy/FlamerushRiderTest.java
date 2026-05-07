package org.mage.test.cards.copy;

import mage.constants.EmptyNames;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author notgreat
 */
public class FlamerushRiderTest extends CardTestPlayerBase {

    @Test
    public void CopyMorphCreature() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 8);
        addCard(Zone.BATTLEFIELD, playerA, "Fervor");
        addCard(Zone.HAND, playerA, "Canyon Lurkers");
        addCard(Zone.HAND, playerA, "Flamerush Rider");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Canyon Lurkers using Morph", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Flamerush Rider");
        setChoice(playerA, TestPlayer.CHOICE_NORMAL_COST);
        attack(1, playerA, "Flamerush Rider");
        attack(1, playerA, EmptyNames.FACE_DOWN_CREATURE.getTestCommand());
        addTarget(playerA, EmptyNames.FACE_DOWN_CREATURE.getTestCommand());

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, 11); // Morph + Flamerush Rider + 9 starting
        assertPermanentCount(playerA, EmptyNames.FACE_DOWN_CREATURE.getTestCommand(), 1);
        assertPermanentCount(playerA, "Flamerush Rider", 1);
        assertLife(playerB, 13); // 20 - 3 - 2 - 2
    }
}
