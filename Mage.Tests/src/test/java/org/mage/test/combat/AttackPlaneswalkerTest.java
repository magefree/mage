
package org.mage.test.combat;

import mage.abilities.keyword.HexproofAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * Check if attacking a planswalker and removing loyality counter from damage
 * works
 *
 * @author LevelX2
 */
public class AttackPlaneswalkerTest extends CardTestPlayerBase {

    @Test
    public void testAttackPlaneswalker() {
        addCard(Zone.BATTLEFIELD, playerA, "Kiora, the Crashing Wave");
        addCard(Zone.BATTLEFIELD, playerB, "Giant Tortoise");

        attack(2, playerB, "Giant Tortoise", "Kiora, the Crashing Wave");

        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertPermanentCount(playerA, "Kiora, the Crashing Wave", 1);
        assertPermanentCount(playerB, "Giant Tortoise", 1);
        assertCounterCount("Kiora, the Crashing Wave", CounterType.LOYALTY, 1);
    }

    /**
     * Tests that giving a planeswalker hexproof does not prevent opponent from
     * attacking it
     */
    @Test
    public void testAttackPlaneswalkerWithHexproof() {
        /*
         Simic Charm English
         Instant, UG
         Choose one — Target creature gets +3/+3 until end of turn;
                       or permanents you control gain hexproof until end of turn;
                       or return target creature to its owner's hand.
         */
        addCard(Zone.HAND, playerA, "Simic Charm");
        addCard(Zone.BATTLEFIELD, playerA, "Island");
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Zone.BATTLEFIELD, playerA, "Kiora, the Crashing Wave");

        addCard(Zone.BATTLEFIELD, playerB, "Giant Tortoise");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA, "Simic Charm");
        setModeChoice(playerA, "2");

        attack(2, playerB, "Giant Tortoise", "Kiora, the Crashing Wave");

        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertPermanentCount(playerA, "Kiora, the Crashing Wave", 1);
        assertPermanentCount(playerB, "Giant Tortoise", 1);
        assertCounterCount("Kiora, the Crashing Wave", CounterType.LOYALTY, 1);
    }

    /*
     * Reported bug: see issue #3328
     * Players unable to attack Planeswalker with Privileged Position on battlefield.
     */
    @Test
    public void testAttackPlaneswalkerWithHexproofPrivilegedPosition() {

        /*
         Privileged Position {2}{G/W}{G/W}{G/W}
        Enchantment
        Other permanents you control have hexproof.
         */
        String pPosition = "Privileged Position";
        String sorin = "Sorin, Solemn Visitor"; // planeswalker {2}{W}{B} 4 loyalty
        String memnite = "Memnite"; // {0} 1/1

        addCard(Zone.BATTLEFIELD, playerB, pPosition);
        addCard(Zone.BATTLEFIELD, playerB, sorin);
        addCard(Zone.BATTLEFIELD, playerA, memnite);

        attack(3, playerA, memnite, sorin);

        setStopAt(3, PhaseStep.END_COMBAT);
        execute();

        assertPermanentCount(playerB, pPosition, 1);
        assertPermanentCount(playerB, sorin, 1);
        assertTapped(memnite, true);
        assertLife(playerB, 20);
        assertCounterCount(sorin, CounterType.LOYALTY, 3);
        assertAbility(playerB, sorin, HexproofAbility.getInstance(), true);
    }

    /**
     * Tests that attacking a planeswalker triggers and resolves Silent Skimmer
     * correctly
     */
    @Test
    public void testAttackPlaneswalkerTriggers() {
        addCard(Zone.BATTLEFIELD, playerA, "Kiora, the Crashing Wave");

        // Devoid, Flying
        // Whenever Silent Skimmer attacks, defending player loses 2 life.
        addCard(Zone.BATTLEFIELD, playerB, "Silent Skimmer");

        attack(2, playerB, "Silent Skimmer", "Kiora, the Crashing Wave");

        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 18);
        assertLife(playerB, 20);

        assertPermanentCount(playerA, "Kiora, the Crashing Wave", 1);
        assertPermanentCount(playerB, "Silent Skimmer", 1);
        assertCounterCount("Kiora, the Crashing Wave", CounterType.LOYALTY, 2);
    }

    @Test
    public void testAttackedPlaneswalkerDestroyed() {
        addCard(Zone.BATTLEFIELD, playerA, "Kiora, the Crashing Wave");
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears");
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears");
        addCard(Zone.BATTLEFIELD, playerB, "Plains");
        addCard(Zone.BATTLEFIELD, playerB, "Swamp");
        addCard(Zone.HAND, playerB, "Despark");

        attack(2, playerB, "Grizzly Bears", "Kiora, the Crashing Wave");
        castSpell(2, PhaseStep.DECLARE_ATTACKERS, playerB, "Despark");
        addTarget(playerB, "Kiora, the Crashing Wave");
        block(2, playerA, "Grizzly Bears", "Grizzly Bears");

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertExileCount(playerA, "Kiora, the Crashing Wave", 1);
        assertGraveyardCount(playerA, "Grizzly Bears", 1);
        assertGraveyardCount(playerB, "Grizzly Bears", 1);
    }
}
