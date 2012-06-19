package org.mage.test.combat;

import junit.framework.Assert;
import mage.Constants;
import mage.counters.CounterType;
import mage.game.permanent.Permanent;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * Test restrictions for choosing attackers and blockers.
 *
 * @author noxx
 */
public class AttackBlockRestrictionsTest extends CardTestPlayerBase {

    @Test
    public void testFlyingVsNonFlying() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Captain of the Mists");
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Mist Raven");

        attack(2, playerB, "Mist Raven");
        block(2, playerA, "Captain of the Mists", "Mist Raven");

        setStopAt(2, Constants.PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 18);
        assertLife(playerB, 20);

        Permanent mistRaven = getPermanent("Mist Raven", playerB.getId());
        Assert.assertTrue("Should be tapped because of attacking", mistRaven.isTapped());
    }

    /**
     * Tests attacking creature doesn't untap after being blocked by Wall of Frost
     */
    @Test
    public void testWallofWrost() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Wall of Frost");
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Craw Wurm");

        attack(2, playerB, "Craw Wurm");
        block(2, playerA, "Wall of Frost", "Craw Wurm");

        setStopAt(4, Constants.PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        Permanent crawWurm = getPermanent("Craw Wurm", playerB.getId());
        Assert.assertTrue("Should be tapped because of being blocked by Wall of Frost", crawWurm.isTapped());
    }

    /**
     * Tests card that says that it can't block specific cards
     * Hunted Ghoul:
     *   Hunted Ghoul can't block Humans.
     */
    @Test
    public void testFilteredBlocking() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Hunted Ghoul");
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Elite Vanguard");

        attack(2, playerB, "Elite Vanguard");
        block(2, playerA, "Hunted Ghoul", "Elite Vanguard");

        setStopAt(4, Constants.PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 18);
        assertLife(playerB, 20);
    }

    /**
     * Tests card that says that it can't block specific cards still can block others
     * Hunted Ghoul:
     *   Hunted Ghoul can't block Humans.
     */
    @Test
    public void testFilteredBlocking2() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Hunted Ghoul");
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Craw Wurm");

        attack(2, playerB, "Craw Wurm");
        block(2, playerA, "Hunted Ghoul", "Craw Wurm");

        setStopAt(4, Constants.PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertPermanentCount(playerA, "Hunted Ghoul", 0);
    }

    /**
     * Tests "Creatures with flying can't block creatures you control"
     */
    @Test
    public void testBowerPassage() {
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Bower Passage");

        addCard(Constants.Zone.BATTLEFIELD, playerB, "Elite Vanguard");
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Arbor Elf");
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Assault Griffin");

        addCard(Constants.Zone.BATTLEFIELD, playerA, "Angelic Wall");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Air Elemental");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Llanowar Elves");

        // non flying vs. flying
        attack(2, playerB, "Elite Vanguard");
        block(2, playerA, "Angelic Wall", "Elite Vanguard");
        // non flying vs. non flying
        attack(2, playerB, "Arbor Elf");
        block(2, playerA, "Llanowar Elves", "Arbor Elf");
        // flying vs. flying
        attack(2, playerB, "Assault Griffin");
        block(2, playerA, "Air Elemental", "Assault Griffin");

        setStopAt(2, Constants.PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 15);
        assertLife(playerB, 20);
    }

    /**
     * Tests restriction effect going away after card is destroyed
     */
    @Test
    public void testBowerPassageDestroyed() {
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Bower Passage");

        addCard(Constants.Zone.BATTLEFIELD, playerB, "Elite Vanguard");
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Arbor Elf");
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Assault Griffin");

        addCard(Constants.Zone.BATTLEFIELD, playerA, "Angelic Wall");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Air Elemental");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Llanowar Elves");

        addCard(Constants.Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Constants.Zone.HAND, playerA, "Naturalize");

        // non flying vs. flying
        attack(2, playerB, "Elite Vanguard");
        block(2, playerA, "Angelic Wall", "Elite Vanguard");
        // non flying vs. non flying
        attack(2, playerB, "Arbor Elf");
        block(2, playerA, "Llanowar Elves", "Arbor Elf");
        // flying vs. flying
        attack(2, playerB, "Assault Griffin");
        block(2, playerA, "Air Elemental", "Assault Griffin");

        castSpell(2, Constants.PhaseStep.DECLARE_ATTACKERS, playerA, "Naturalize", "Bower Passage");

        setStopAt(2, Constants.PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerB, "Bower Passage", 0);

        assertLife(playerA, 20);
        assertLife(playerB, 20);
    }

    /**
     * Tests "Creatures with flying can't block creatures you control"
     */
    @Test
    public void testChampionOfLambholt() {
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Champion of Lambholt");

        addCard(Constants.Zone.BATTLEFIELD, playerB, "Elite Vanguard");
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Arbor Elf");
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Assault Griffin");

        addCard(Constants.Zone.BATTLEFIELD, playerB, "Plains", 5);
        addCard(Constants.Zone.HAND, playerB, "Baneslayer Angel");

        addCard(Constants.Zone.BATTLEFIELD, playerA, "Angelic Wall");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Air Elemental");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Llanowar Elves");

        castSpell(2, Constants.PhaseStep.PRECOMBAT_MAIN, playerB, "Baneslayer Angel");

        // non flying vs. flying
        attack(2, playerB, "Elite Vanguard");
        block(2, playerA, "Angelic Wall", "Elite Vanguard"); // can't block
        // non flying vs. non flying
        attack(2, playerB, "Arbor Elf");
        block(2, playerA, "Llanowar Elves", "Arbor Elf"); // can block
        // flying vs. flying
        attack(2, playerB, "Assault Griffin");
        block(2, playerA, "Air Elemental", "Assault Griffin"); // can block

        setStopAt(2, Constants.PhaseStep.END_TURN);
        execute();

        assertPowerToughness(playerB, "Champion of Lambholt", 2, 2);

        assertLife(playerA, 17);
        assertLife(playerB, 20);
    }

    /**
     * Tests Unblockable
     */
    @Test
    public void testUnblockable() {
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Blighted Agent");

        addCard(Constants.Zone.BATTLEFIELD, playerA, "Blighted Agent");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Llanowar Elves");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Birds of Paradise");

        attack(2, playerB, "Blighted Agent");
        block(2, playerA, "Blighted Agent", "Blighted Agent");
        block(2, playerA, "Llanowar Elves", "Blighted Agent");
        block(2, playerA, "Birds of Paradise", "Blighted Agent");

        setStopAt(2, Constants.PhaseStep.END_TURN);
        execute();

        assertCounterCount(playerA, CounterType.POISON, 1);
    }
}
