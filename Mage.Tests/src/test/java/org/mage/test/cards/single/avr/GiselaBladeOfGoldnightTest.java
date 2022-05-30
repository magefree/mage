package org.mage.test.cards.single.avr;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * Gisela, Blade of Goldnight:
 * If a source would deal damage to an opponent or a permanent an opponent controls, that source deals double that damage to that player or permanent instead.
 * If a source would deal damage to you or a permanent you control, prevent half that damage, rounded up.
 *
 * @author noxx
 */
public class GiselaBladeOfGoldnightTest extends CardTestPlayerBase {

    @Test
    public void testCard() {
        addCard(Zone.HAND, playerA, "Shock");
        addCard(Zone.HAND, playerA, "Lightning Bolt", 4);

        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Gisela, Blade of Goldnight");
        addCard(Zone.BATTLEFIELD, playerA, "Devout Chaplain");
        addCard(Zone.BATTLEFIELD, playerA, "Corpse Traders");
        addCard(Zone.BATTLEFIELD, playerA, "Llanowar Elves");

        addCard(Zone.BATTLEFIELD, playerB, "Air Elemental", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Elite Vanguard", 1);

        // Shock does 4 damage (2 doubled) to the 4/4, killing it.
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Shock", "Air Elemental");
        // Lightning Bolt does 1 damage (3 / 2 rounded down)
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerA);
        // Lightning Bolt does 6 damage (3 doubled)
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        // 1 damage to the 2/2 NOT killing it
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt");
        addTarget(playerA, "Devout Chaplain");
        // 1 damage to the 1/1 killing it
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt");
        addTarget(playerA, "Llanowar Elves");

        attack(2, playerB, "Elite Vanguard");

        setStopAt(2, PhaseStep.END_TURN);
        execute();

        // 1 from Lightning Bolt + 1 from Elite Vanguard
        assertLife(playerA, 18);
        // 6 from the doubled Lightning Bolt
        assertLife(playerB, 14);

        assertPermanentCount(playerA, "Devout Chaplain", 1);
        assertPermanentCount(playerA, "Llanowar Elves", 0);

        assertPermanentCount(playerB, "Air Elemental", 0);
    }

    @Test
    public void test_DamageToOpponent_WithGisela() {
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 6);
        addCard(Zone.BATTLEFIELD, playerB, "Gisela, Blade of Goldnight");
        addCard(Zone.HAND, playerB, "Banefire");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Banefire", playerA);
        setChoice(playerB, "X=5");

        setStopAt(2, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        // Player A should take double damage
        assertLife(playerA, 20 - 10);
    }

    @Test
    public void test_DamageToOpponentsCreature_WithGisela() {
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Gisela, Blade of Goldnight");
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // 2/2
        addCard(Zone.HAND, playerB, "Banefire");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Banefire", "Grizzly Bears");
        setChoice(playerB, "X=1");

        setStopAt(2, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        // creature takes 2 damage and dies
        assertPermanentCount(playerA, "Grizzly Bears", 0);
        assertLife(playerA, 20);
    }

    @Test
    public void test_DamageToPlayer_Preventable() {
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 5);
        addCard(Zone.HAND, playerB, "Banefire");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Banefire", playerA);
        setChoice(playerB, "X=4");

        setStopAt(2, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        // Player A should take the full 4 damage
        assertLife(playerA, 20 - 4);
    }

    @Test
    public void test_DamageToPlayer_Unpreventable() {
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 6);
        addCard(Zone.HAND, playerB, "Banefire");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Banefire", playerA);
        setChoice(playerB, "X=5");

        setStopAt(2, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        // Player A should take the full 5 damage
        assertLife(playerA, 15);
    }

    @Test
    public void test_DamageToPlayer_PreventableWithGisela() {
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Gisela, Blade of Goldnight");
        addCard(Zone.HAND, playerB, "Banefire");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Banefire", playerA);
        setChoice(playerB, "X=4");

        setStopAt(2, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        // Player A should take half damage 2 (prevent 4/2 round up = 2 damage)
        assertLife(playerA, 20 - 2);
    }

    @Test
    public void test_DamageToPlayer_UnpreventableWithGisela() {
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 6);
        addCard(Zone.BATTLEFIELD, playerA, "Gisela, Blade of Goldnight");
        addCard(Zone.HAND, playerB, "Banefire");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Banefire", playerA);
        setChoice(playerB, "X=5");

        setStopAt(2, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        // Player A should take full damage 5 (wrong result: 2 damage)
        assertLife(playerA, 20 - 5);
    }

    @Test
    public void test_DamageToCreature_PreventableWithGisela() {
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Gisela, Blade of Goldnight");
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // 2/2
        addCard(Zone.HAND, playerB, "Banefire");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Banefire", "Grizzly Bears");
        setChoice(playerB, "X=2");

        setStopAt(2, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        // creature must be alife, half damage done
        assertPermanentCount(playerA, "Grizzly Bears", 1);
        assertLife(playerA, 20);
    }

    @Test
    public void test_DamageToCreature_UnpreventableWithGisela() {
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 7);
        addCard(Zone.BATTLEFIELD, playerA, "Gisela, Blade of Goldnight");
        addCard(Zone.BATTLEFIELD, playerA, "Colossal Dreadmaw"); // 6/6
        addCard(Zone.HAND, playerB, "Banefire");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Banefire", "Colossal Dreadmaw");
        setChoice(playerB, "X=6");

        setStopAt(2, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        // creature must die, full damage
        assertPermanentCount(playerA, "Colossal Dreadmaw", 0);
        assertLife(playerA, 20);
    }
}
