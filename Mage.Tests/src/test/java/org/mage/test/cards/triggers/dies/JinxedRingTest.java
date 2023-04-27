
package org.mage.test.cards.triggers.dies;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class JinxedRingTest extends CardTestPlayerBase {

    @Test
    public void testShatterstorm() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        // Destroy all artifacts. They can't be regenerated.
        addCard(Zone.HAND, playerA, "Shatterstorm", 1); // Sorcery - {2}{R}{R}
        // Whenever a nontoken permanent is put into your graveyard from the battlefield, Jinxed Ring deals 1 damage to you.
        // Sacrifice a creature: Target opponent gains control of Jinxed Ring.
        addCard(Zone.BATTLEFIELD, playerA, "Jinxed Ring", 1);

        // Bonded Construct can't attack alone.
        addCard(Zone.BATTLEFIELD, playerA, "Bonded Construct", 2); // Artifact Creature - Construct 2/1

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Shatterstorm");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Shatterstorm", 1);
        assertGraveyardCount(playerA, "Jinxed Ring", 1);
        assertGraveyardCount(playerA, "Bonded Construct", 2);
        assertLife(playerA, 17);
        assertLife(playerB, 20);

    }

    /**
     * Card works fine on destroying individual things, but after I gave it to
     * my opponent, when I cast Shatterstorm there were no triggers. Maybe a
     * problem because Jinxed Ring goes to my graveyard and other permanents go
     * to their owner's graveyards, while Jinxed Ring looks at permanent's
     * entering "your graveyard."
     */
    @Test
    public void testShatterstormControlledByOpponent() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 6);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        // Exchange control of target artifact or creature and another target permanent that shares one of those types with it.
        addCard(Zone.HAND, playerA, "Legerdemain", 1); // Sorcery - {2}{U}{U}
        // Destroy all artifacts. They can't be regenerated.
        addCard(Zone.HAND, playerA, "Shatterstorm", 1); // Sorcery - {2}{R}{R}
        // Whenever a nontoken permanent is put into your graveyard from the battlefield, Jinxed Ring deals 1 damage to you.
        // Sacrifice a creature: Target opponent gains control of Jinxed Ring.
        addCard(Zone.BATTLEFIELD, playerA, "Jinxed Ring", 1);

        // Bonded Construct can't attack alone.
        addCard(Zone.BATTLEFIELD, playerB, "Bonded Construct", 2); // Artifact Creature - Construct 2/1

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Legerdemain", "Jinxed Ring^Bonded Construct");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Shatterstorm");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Legerdemain", 1);
        assertGraveyardCount(playerA, "Shatterstorm", 1);
        assertGraveyardCount(playerA, "Jinxed Ring", 1);
        assertGraveyardCount(playerB, "Bonded Construct", 2);
        assertLife(playerA, 20);
        assertLife(playerB, 18);
    }
}
