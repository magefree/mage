
package org.mage.test.cards.copy;

import mage.constants.PhaseStep;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.permanent.Permanent;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * Tests for Ultron, Artificial Malevolence:
 * "Whenever another nontoken artifact you control enters, you may pay {2}.
 * If you do, create a token that's a copy of it. If the token isn't a creature,
 * it becomes a 2/2 Robot Villain creature in addition to its other types."
 */
public class UltronArtificialMalevolenceTest extends CardTestPlayerBase {

    @Test
    public void testCopyNontokenArtifact() {
        // Whenever another nontoken artifact you control enters, you may pay {2}. If you do, create a token that's a copy of it.
        addCard(Zone.BATTLEFIELD, playerA, "Ultron, Artificial Malevolence"); // {3} 2/4 artifact creature
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);

        // {1} artifact equipment without other abilities
        addCard(Zone.HAND, playerA, "Commander's Plate");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Commander's Plate");
        setChoice(playerA, true); // pay {2}

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Commander's Plate", 2);
        // the token copy is a 2/2 Robot Villain creature in addition to its other types
        assertPowerToughness(playerA, "Commander's Plate", 2, 2);
        int robotVillainArtifacts = 0;
        for (Permanent permanent : currentGame.getBattlefield().getAllActivePermanents(playerA.getId())) {
            if (permanent.getName().equals("Commander's Plate")
                    && permanent.isCreature(currentGame)
                    && permanent.hasSubtype(SubType.ROBOT, currentGame)
                    && permanent.hasSubtype(SubType.VILLAIN, currentGame)
                    && permanent.isArtifact(currentGame)) {
                robotVillainArtifacts++;
            }
        }
        Assert.assertEquals("Token copy should be a 2/2 Robot Villain artifact creature", 1, robotVillainArtifacts);
    }

    @Test
    public void testCopyArtifactCreature() {
        addCard(Zone.BATTLEFIELD, playerA, "Ultron, Artificial Malevolence");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);

        // {3} 2/3 artifact creature Robot Villain
        addCard(Zone.HAND, playerA, "Ultron Drone");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Ultron Drone");
        setChoice(playerA, true); // pay {2}

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        // already a creature: plain token copy, keeps its 2/3 p/t
        assertPermanentCount(playerA, "Ultron Drone", 2);
        assertPowerToughness(playerA, "Ultron Drone", 2, 3);
    }

    @Test
    public void testDontPayNoToken() {
        addCard(Zone.BATTLEFIELD, playerA, "Ultron, Artificial Malevolence");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);

        addCard(Zone.HAND, playerA, "Commander's Plate");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Commander's Plate");
        setChoice(playerA, false); // don't pay {2}

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Commander's Plate", 1);
    }

    @Test
    public void testOpponentArtifactDoesNotTrigger() {
        addCard(Zone.BATTLEFIELD, playerA, "Ultron, Artificial Malevolence");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);

        addCard(Zone.BATTLEFIELD, playerB, "Mountain");
        addCard(Zone.HAND, playerB, "Commander's Plate"); // {1}

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Commander's Plate");

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerB, "Commander's Plate", 1);
        assertPermanentCount(playerA, "Commander's Plate", 0);
    }

    @Test
    public void testCopyFromLastKnownInformation() {
        addCard(Zone.BATTLEFIELD, playerA, "Ultron, Artificial Malevolence");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        addCard(Zone.HAND, playerA, "Commander's Plate"); // {1}

        addCard(Zone.BATTLEFIELD, playerB, "Forest", 2);
        addCard(Zone.HAND, playerB, "Naturalize"); // {1}{G} destroy target artifact or enchantment

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Commander's Plate");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, 1); // plate enters, Ultron trigger still on stack

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Naturalize", "Commander's Plate");
        setChoice(playerA, true); // pay {2}

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Commander's Plate", 1);
        // {1} to cast + {2} paid for the copy
        assertTappedCount("Island", true, 3);
        // token copy still created from last known information
        assertPermanentCount(playerA, "Commander's Plate", 1);
        assertPowerToughness(playerA, "Commander's Plate", 2, 2);
    }
}
