package org.mage.test.cards.single.pip;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class CraigBooneNovacGuardTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.c.CraigBooneNovacGuard Craig Boone, Novac Guard} {1}{R}{W}
     * Legendary Creature — Human Soldier
     * Reach, lifelink
     * One for My Baby — Whenever you attack with two or more creatures, put two quest counters on Craig Boone, Novac Guard. When you do, Craig Boone deals damage equal to the number of quest counters on it to up to one target creature unless that creature’s controller has Craig Boone deal that much damage to them.
     * 3/3
     */
    private static final String craig = "Craig Boone, Novac Guard";

    @Test
    public void test_Trigger_Simple_DamageCreature() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, craig);
        addCard(Zone.BATTLEFIELD, playerA, "Memnite");
        addCard(Zone.BATTLEFIELD, playerA, "Elite Vanguard");
        addCard(Zone.BATTLEFIELD, playerB, "Indomitable Ancients");

        attack(1, playerA, "Memnite", playerB);
        attack(1, playerA, "Elite Vanguard", playerB);

        addTarget(playerA, "Indomitable Ancients");
        setChoice(playerB, false); // choose to not have damage dealt to them

        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertDamageReceived(playerB, "Indomitable Ancients", 2);
        assertLife(playerA, 20 + 2);
        assertLife(playerB, 20 - 3);
    }

    @Test
    public void test_Trigger_Simple_DamagePlayer() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, craig);
        addCard(Zone.BATTLEFIELD, playerA, "Memnite");
        addCard(Zone.BATTLEFIELD, playerA, "Elite Vanguard");
        addCard(Zone.BATTLEFIELD, playerB, "Indomitable Ancients");

        attack(1, playerA, "Memnite", playerB);
        attack(1, playerA, "Elite Vanguard", playerB);

        addTarget(playerA, "Indomitable Ancients");
        setChoice(playerB, true); // choose to not have damage dealt to them

        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertDamageReceived(playerB, "Indomitable Ancients", 0);
        assertLife(playerA, 20 + 2);
        assertLife(playerB, 20 - 3 - 2);
    }

    @Test
    public void test_Trigger_Simple_NoTarget_NoDamage() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, craig);
        addCard(Zone.BATTLEFIELD, playerA, "Memnite");
        addCard(Zone.BATTLEFIELD, playerA, "Elite Vanguard");
        addCard(Zone.BATTLEFIELD, playerB, "Indomitable Ancients");

        attack(1, playerA, "Memnite", playerB);
        attack(1, playerA, "Elite Vanguard", playerB);

        addTarget(playerA, TestPlayer.TARGET_SKIP);
        // No one has a choice to make

        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertDamageReceived(playerB, "Indomitable Ancients", 0);
        assertLife(playerA, 20);
        assertLife(playerB, 20 - 3);
    }

    @Test
    public void test_Trigger_DiesBeforeResolve() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, craig);
        addCard(Zone.BATTLEFIELD, playerA, "Memnite");
        addCard(Zone.BATTLEFIELD, playerA, "Elite Vanguard");
        addCard(Zone.BATTLEFIELD, playerB, "Indomitable Ancients");
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 2);
        addCard(Zone.HAND, playerB, "Doom Blade");

        attack(1, playerA, "Memnite", playerB);
        attack(1, playerA, "Elite Vanguard", playerB);

        castSpell(1, PhaseStep.DECLARE_ATTACKERS, playerB, "Doom Blade", craig);
        // The attack trigger is there, but since counters can not be put on Craig, no Reflexive Trigger

        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertDamageReceived(playerB, "Indomitable Ancients", 0);
        assertLife(playerA, 20);
        assertLife(playerB, 20 - 3);
        assertGraveyardCount(playerA, craig, 1);
    }

    @Test
    public void test_Trigger_DiesBeforeReflexiveResolve() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, craig);
        addCard(Zone.BATTLEFIELD, playerA, "Memnite");
        addCard(Zone.BATTLEFIELD, playerA, "Elite Vanguard");
        addCard(Zone.BATTLEFIELD, playerB, "Indomitable Ancients");
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 2);
        addCard(Zone.HAND, playerB, "Doom Blade");

        attack(1, playerA, "Memnite", playerB);
        attack(1, playerA, "Elite Vanguard", playerB);

        waitStackResolved(1, PhaseStep.DECLARE_ATTACKERS, playerB, true);
        addTarget(playerA, "Indomitable Ancients");
        setChoice(playerB, true); // choose to not have damage dealt to them
        castSpell(1, PhaseStep.DECLARE_ATTACKERS, playerB, "Doom Blade", craig);

        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertDamageReceived(playerB, "Indomitable Ancients", 0);
        assertLife(playerA, 20 + 2);
        assertLife(playerB, 20 - 3 - 2); // Still took 2 Damage when the reflexive trigger
        assertGraveyardCount(playerA, craig, 1);
    }
}
