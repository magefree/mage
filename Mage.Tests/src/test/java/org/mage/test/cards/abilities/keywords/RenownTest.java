
package org.mage.test.cards.abilities.keywords;

import mage.abilities.keyword.MenaceAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.game.permanent.Permanent;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class RenownTest extends CardTestPlayerBase {

    @Test
    public void testKnightOfThePilgrimsRoad() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        // Renown 1 (When this creature deals combat damage to a player, if it isn't renowned, put a +1/+1 counter on it and it becomes renowned.)
        addCard(Zone.HAND, playerA, "Knight of the Pilgrim's Road"); // 3/2

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Knight of the Pilgrim's Road");

        attack(3, playerA, "Knight of the Pilgrim's Road"); // 3 damage
        attack(5, playerA, "Knight of the Pilgrim's Road"); // 4 damage
        attack(7, playerA, "Knight of the Pilgrim's Road"); // 4 damage

        setStopAt(7, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPowerToughness(playerA, "Knight of the Pilgrim's Road", 4, 3);

        assertLife(playerA, 20);
        assertLife(playerB, 9);

    }

    /**
     * Test renown trigger
     */
    @Test
    public void testRelicSeeker() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        // Renown 1 (When this creature deals combat damage to a player, if it isn't renowned, put a +1/+1 counter on it and it becomes renowned.)
        // When Relic Seeker becomes renowned, you may search your library for an Equipment card, reveal it, put it into your hand, then shuffle your library.
        addCard(Zone.HAND, playerA, "Relic Seeker"); // 2/2

        addCard(Zone.LIBRARY, playerA, "Veteran's Sidearm");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Relic Seeker");

        attack(3, playerA, "Relic Seeker"); // 2 damage
        attack(5, playerA, "Relic Seeker"); // 3 damage

        setStopAt(5, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPowerToughness(playerA, "Relic Seeker", 3, 3);
        assertHandCount(playerA, "Veteran's Sidearm", 1);

        assertLife(playerA, 20);
        assertLife(playerB, 15);

    }

    /**
     * Test renown state
     */
    @Test
    public void testHonoredHierarch() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        // As long as Honored Hierarch is renowned, it has vigilance and "{T}: Add one mana of any color."
        addCard(Zone.HAND, playerA, "Honored Hierarch"); // 1/1

        addCard(Zone.LIBRARY, playerA, "Veteran's Sidearm");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Honored Hierarch");

        attack(3, playerA, "Honored Hierarch"); // 1 damage
        attack(5, playerA, "Honored Hierarch"); // 2 damage

        setStopAt(5, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPowerToughness(playerA, "Honored Hierarch", 2, 2);
        assertTapped("Honored Hierarch", false);
        assertAbility(playerA, "Honored Hierarch", VigilanceAbility.getInstance(), true);

        assertLife(playerA, 20);
        assertLife(playerB, 17);

    }

    /**
     * Test renown > 1
     */
    @Test
    public void testRhoxMaulers() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 5);
        // Trample
        // Renown 2
        addCard(Zone.HAND, playerA, "Rhox Maulers"); // 4/4

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Rhox Maulers");

        attack(3, playerA, "Rhox Maulers"); // 4 damage
        attack(5, playerA, "Rhox Maulers"); // 6 damage
        attack(7, playerA, "Rhox Maulers"); // 6 damage

        setStopAt(7, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPowerToughness(playerA, "Rhox Maulers", 6, 6);

        assertLife(playerA, 20);
        assertLife(playerB, 4);

    }

    /**
     * Test renown is gone after zone change
     *
     * /**
     * Test renown is gone after zone change
     */
    @Test
    public void testRenownGoneAfterZoneChange() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 6);
        // Trample
        // Renown 2
        addCard(Zone.HAND, playerA, "Rhox Maulers"); // 4/4
        addCard(Zone.HAND, playerA, "Cloudshift"); // 4/4

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Rhox Maulers");
        castSpell(5, PhaseStep.POSTCOMBAT_MAIN, playerA, "Cloudshift", "Rhox Maulers");

        attack(3, playerA, "Rhox Maulers"); // 4 damage
        attack(5, playerA, "Rhox Maulers"); // 6 damage
        attack(7, playerA, "Rhox Maulers"); // 4 damage

        setStopAt(7, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 6);

        assertPowerToughness(playerA, "Rhox Maulers", 6, 6); // renown again in turn 7 after the attack
        Permanent rhoxMaulers = getPermanent("Rhox Maulers", playerA);
        Assert.assertEquals("may not be renown", true, rhoxMaulers.isRenowned());

    }

    /*
     Test renown can be gained again after zone change
     */
    @Test
    public void testRenownGainedGainAfterZoneChange() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        // Renown 1
        // As long as Goblin Glory Chaser is renowned, it has menace.
        addCard(Zone.HAND, playerA, "Goblin Glory Chaser"); // 1/1 {R}
        addCard(Zone.HAND, playerA, "Cloudshift");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Goblin Glory Chaser");
        castSpell(5, PhaseStep.POSTCOMBAT_MAIN, playerA, "Cloudshift", "Goblin Glory Chaser");

        attack(3, playerA, "Goblin Glory Chaser"); // 1 damage
        attack(5, playerA, "Goblin Glory Chaser"); // 2 damage
        attack(7, playerA, "Goblin Glory Chaser"); // 1 damage
        attack(9, playerA, "Goblin Glory Chaser"); // 2 damage

        setStopAt(9, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        Permanent goblin = getPermanent("Goblin Glory Chaser", playerA);
        Assert.assertEquals("has has renown", true, goblin.isRenowned());
        assertAbility(playerA, "Goblin Glory Chaser", new MenaceAbility(), true);
        assertPowerToughness(playerA, "Goblin Glory Chaser", 2, 2);

        assertLife(playerA, 20);
        assertLife(playerB, 14);

    }

    /**
     * Ability doesn't trigger when renowned. ("Whenever an opponent casts a
     * noncreature spell, if ~ is renowned, ~ deals 2 damage to that player.")
     */
    @Test
    public void testScabClanBerserker() {
        // Renown 1
        // Whenever an opponent casts a noncreature spell, if Scab-Clan Berserker is renowned, Scab-Clan Berserker deals 2 damage to that player.
        addCard(Zone.BATTLEFIELD, playerA, "Scab-Clan Berserker"); // 2/2  {1}{R}{R}

        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);
        addCard(Zone.HAND, playerB, "Lightning Bolt");

        attack(3, playerA, "Scab-Clan Berserker"); // 1 damage
        castSpell(3, PhaseStep.POSTCOMBAT_MAIN, playerB, "Lightning Bolt", playerA);

        setStopAt(3, PhaseStep.END_TURN);
        execute();

        Permanent berserker = getPermanent("Scab-Clan Berserker", playerA);
        Assert.assertEquals("has has renown", true, berserker.isRenowned());
        assertPowerToughness(playerA, "Scab-Clan Berserker", 3, 3);

        assertLife(playerA, 17); // Lightning Bolt
        assertLife(playerB, 16); // 2 from attack 2 from triggered ability

    }

    /**
     * Enshrouding Mist (new card from ORI) isn't untapping renowned creatures.
     */
    @Test
    public void testEnshroudingMist() {
        // Renown 1
        // Whenever an opponent casts a noncreature spell, if Scab-Clan Berserker is renowned, Scab-Clan Berserker deals 2 damage to that player.
        addCard(Zone.BATTLEFIELD, playerB, "Scab-Clan Berserker"); // 2/2  {1}{R}{R}

        addCard(Zone.BATTLEFIELD, playerB, "Plains", 1);
        // Target creature gets +1/+1 until end of turn. Prevent all damage that would dealt to it this turn. If it's renowned, untap it.
        addCard(Zone.HAND, playerB, "Enshrouding Mist");

        attack(2, playerB, "Scab-Clan Berserker"); // 1 damage
        castSpell(2, PhaseStep.POSTCOMBAT_MAIN, playerB, "Enshrouding Mist", "Scab-Clan Berserker");

        setStopAt(2, PhaseStep.END_TURN);
        execute();

        Permanent berserker = getPermanent("Scab-Clan Berserker", playerB);
        Assert.assertEquals("has has renown", true, berserker.isRenowned());
        assertPowerToughness(playerB, "Scab-Clan Berserker", 4, 4); // +1 from renowned + 1 from Enshrouding Mist
        assertTapped("Scab-Clan Berserker", false);

        assertLife(playerA, 18); // Lightning Bolt
        assertLife(playerB, 20);

    }
}
