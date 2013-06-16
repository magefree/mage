package org.mage.test.combat;

import junit.framework.Assert;
import mage.constants.PhaseStep;
import mage.constants.Zone;
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
        addCard(Zone.BATTLEFIELD, playerA, "Captain of the Mists");
        addCard(Zone.BATTLEFIELD, playerB, "Mist Raven");

        attack(2, playerB, "Mist Raven");
        block(2, playerA, "Captain of the Mists", "Mist Raven");

        setStopAt(2, PhaseStep.END_TURN);
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
        addCard(Zone.BATTLEFIELD, playerA, "Wall of Frost");
        addCard(Zone.BATTLEFIELD, playerB, "Craw Wurm");

        attack(2, playerB, "Craw Wurm");
        block(2, playerA, "Wall of Frost", "Craw Wurm");

        setStopAt(4, PhaseStep.END_TURN);
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
        addCard(Zone.BATTLEFIELD, playerA, "Hunted Ghoul");
        addCard(Zone.BATTLEFIELD, playerB, "Elite Vanguard");

        attack(2, playerB, "Elite Vanguard");
        block(2, playerA, "Hunted Ghoul", "Elite Vanguard");

        setStopAt(4, PhaseStep.END_TURN);
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
        addCard(Zone.BATTLEFIELD, playerA, "Hunted Ghoul");
        addCard(Zone.BATTLEFIELD, playerB, "Craw Wurm");

        attack(2, playerB, "Craw Wurm");
        block(2, playerA, "Hunted Ghoul", "Craw Wurm");

        setStopAt(4, PhaseStep.END_TURN);
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
        addCard(Zone.BATTLEFIELD, playerB, "Bower Passage");

        addCard(Zone.BATTLEFIELD, playerB, "Elite Vanguard");
        addCard(Zone.BATTLEFIELD, playerB, "Arbor Elf");
        addCard(Zone.BATTLEFIELD, playerB, "Assault Griffin");
        addCard(Zone.BATTLEFIELD, playerB, "Sky Ruin Drake");

        addCard(Zone.BATTLEFIELD, playerA, "Angelic Wall");
        addCard(Zone.BATTLEFIELD, playerA, "Air Elemental");
        addCard(Zone.BATTLEFIELD, playerA, "Llanowar Elves");
        addCard(Zone.BATTLEFIELD, playerA, "Sentinel Spider");

        // attacker vs. blocker:
        // non flying vs. flying
        attack(2, playerB, "Elite Vanguard");
        block(2, playerA, "Angelic Wall", "Elite Vanguard");
        // non flying vs. non flying
        attack(2, playerB, "Arbor Elf");
        block(2, playerA, "Llanowar Elves", "Arbor Elf");
        // flying vs. flying
        attack(2, playerB, "Assault Griffin");
        block(2, playerA, "Air Elemental", "Assault Griffin");
        // flying vs. reach
        attack(2, playerB, "Sky Ruin Drake");
        block(2, playerA, "Sentinel Spider", "Sky Ruin Drake");

        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 15);
        assertLife(playerB, 20);
    }

    /**
     * Tests restriction effect going away after card is destroyed
     */
    @Test
    public void testBowerPassageDestroyed() {
        addCard(Zone.BATTLEFIELD, playerB, "Bower Passage");

        addCard(Zone.BATTLEFIELD, playerB, "Elite Vanguard");
        addCard(Zone.BATTLEFIELD, playerB, "Arbor Elf");
        addCard(Zone.BATTLEFIELD, playerB, "Assault Griffin");

        addCard(Zone.BATTLEFIELD, playerA, "Angelic Wall");
        addCard(Zone.BATTLEFIELD, playerA, "Air Elemental");
        addCard(Zone.BATTLEFIELD, playerA, "Llanowar Elves");

        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.HAND, playerA, "Naturalize");

        // attacker vs. blocker:
        // non flying vs. flying
        attack(2, playerB, "Elite Vanguard");
        block(2, playerA, "Angelic Wall", "Elite Vanguard");
        // non flying vs. non flying
        attack(2, playerB, "Arbor Elf");
        block(2, playerA, "Llanowar Elves", "Arbor Elf");
        // flying vs. flying
        attack(2, playerB, "Assault Griffin");
        block(2, playerA, "Air Elemental", "Assault Griffin");

        castSpell(2, PhaseStep.DECLARE_ATTACKERS, playerA, "Naturalize", "Bower Passage");

        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerB, "Bower Passage", 0);

        assertLife(playerA, 20);
        assertLife(playerB, 20);
    }

    /**
     * Tests "Creatures with power less than Champion of Lambholt's power can't block creatures you control."
     */
    @Test
    public void testChampionOfLambholt() {
        //  Champion of Lambholt: Creature — Human Warrior 1/1, 1GG
        //  - Creatures with power less than Champion of Lambholt's power can't block creatures you control.
        //  - Whenever another creature enters the battlefield under your control, put a +1/+1 counter on Champion of Lambholt.
        addCard(Zone.BATTLEFIELD, playerB, "Champion of Lambholt");

        // Elite Vanguard: Creature — Human Soldier 2/1, W
        addCard(Zone.BATTLEFIELD, playerB, "Elite Vanguard");
        // Arbor Elf: Creature — Elf Druid 1/1, G - {T}: Untap target Forest.
        addCard(Zone.BATTLEFIELD, playerB, "Arbor Elf");
        addCard(Zone.BATTLEFIELD, playerB, "Assault Griffin");

        addCard(Zone.BATTLEFIELD, playerB, "Plains", 5);
        //  Baneslayer Angel: Creature — Angel 5/5, 3WW - Flying, first strike, lifelink, protection from Demons and from Dragons
        addCard(Zone.HAND, playerB, "Baneslayer Angel");

        // Angelic Wall: Creature — Wall 0/4, 1W - Defender, flying
        addCard(Zone.BATTLEFIELD, playerA, "Angelic Wall");
        // Air Elemental: Creature — Elemental 4/4, 3UU - Flying
        addCard(Zone.BATTLEFIELD, playerA, "Air Elemental");
        // Llanowar Elves: Creature — Elf Druid 1/1, G - {T}: Add {G} to your mana pool.
        addCard(Zone.BATTLEFIELD, playerA, "Llanowar Elves");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Baneslayer Angel");

        // non flying vs. flying
        attack(2, playerB, "Elite Vanguard");
        block(2, playerA, "Angelic Wall", "Elite Vanguard"); // can't block - Elite Vanguard does 2 damage
        // non flying vs. non flying
        attack(2, playerB, "Arbor Elf");
        block(2, playerA, "Llanowar Elves", "Arbor Elf"); // can't block - Arbor Elf does 1 damage
        // flying vs. flying
        attack(2, playerB, "Assault Griffin");
        block(2, playerA, "Air Elemental", "Assault Griffin"); // can block

        setStopAt(2, PhaseStep.END_TURN);
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
        addCard(Zone.BATTLEFIELD, playerB, "Blighted Agent");

        addCard(Zone.BATTLEFIELD, playerA, "Blighted Agent");
        addCard(Zone.BATTLEFIELD, playerA, "Llanowar Elves");
        addCard(Zone.BATTLEFIELD, playerA, "Birds of Paradise");

        attack(2, playerB, "Blighted Agent");
        block(2, playerA, "Blighted Agent", "Blighted Agent");
        block(2, playerA, "Llanowar Elves", "Blighted Agent");
        block(2, playerA, "Birds of Paradise", "Blighted Agent");

        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertCounterCount(playerA, CounterType.POISON, 1);
    }
    
    @Test
    public void testUnblockableTormentedSoul() {
        addCard(Zone.BATTLEFIELD, playerB, "Tormented Soul");
        
        addCard(Zone.BATTLEFIELD, playerA, "Memnite");
        
        attack(2, playerB, "Tormented Soul");
        block(2, playerA, "Tormented Soul", "Memnite");
        
        setStopAt(2, PhaseStep.END_TURN);
        execute();
        
        assertPermanentCount(playerA, "Memnite", 1);
        assertPermanentCount(playerB, "Tormented Soul", 1);
        
        assertLife(playerA, 19);
    }

    /**
     * Reproduced bug in combat blocking related to singleton classes
     */
    @Test
    public void testFlyingVsNonFlying2() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Savannah Lions");
        addCard(Zone.BATTLEFIELD, playerA, "Azure Drake");
        addCard(Zone.BATTLEFIELD, playerA, "Aven Squire");
        addCard(Zone.BATTLEFIELD, playerA, "Llanowar Elves");
        addCard(Zone.HAND, playerA, "Turn to Frog");

        addCard(Zone.BATTLEFIELD, playerB, "Walking Corpse");
        addCard(Zone.BATTLEFIELD, playerB, "Llanowar Elves");

        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Turn to Frog", "Aven Squire");

        attack(3, playerA, "Llanowar Elves");
        attack(3, playerA, "Azure Drake");
        attack(3, playerA, "Aven Squire");
        attack(3, playerA, "Savannah Lions");

        block(3, playerB, "Llanowar Elves", "Azure Drake"); // won't be able to block
        block(3, playerB, "Walking Corpse", "Aven Squire"); // able to block because of Turn to Frog

        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 15);
        assertLife(playerA, 20);

        assertPermanentCount(playerB, "Aven Squire", 0);
        assertPermanentCount(playerB, "Walking Corpse", 1);
        assertPermanentCount(playerB, "Llanowar Elves", 1);
    }
}
