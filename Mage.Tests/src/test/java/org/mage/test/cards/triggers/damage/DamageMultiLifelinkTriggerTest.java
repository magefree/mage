package org.mage.test.cards.triggers.damage;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Ignore;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author xenohedron
 */
public class DamageMultiLifelinkTriggerTest extends CardTestPlayerBase {

    private static final String hatchling = "Kraken Hatchling"; // 0/4
    private static final String wishcoin = "Wishcoin Crab"; // 2/5

    private static final String devil = "Forge Devil"; // 1/1
    // ETB 1 dmg to any target and 1 dmg to you
    private static final String embermage = "Reckless Embermage"; // 2/2
    // 1R: 1 dmg to any target and 1 dmg to itself

    private static final String arc = "Arc Trail";
    // Arc Trail deals 2 damage to any target and 1 damage to another target.
    private static final String cone = "Cone of Flame";
    // Cone of Flame deals 1 damage to any target, 2 damage to another target, and 3 damage to a third target.
    private static final String chaar = "Char";
    // Char deals 4 damage to any target and 2 damage to you.
    private static final String outrage = "Chandra's Outrage";
    // Chandra's Outrage deals 4 damage to target creature and 2 damage to that creature's controller.
    private static final String fury = "Chandra's Fury";
    // Chandra's Fury deals 4 damage to target player or planeswalker
    // and 1 damage to each creature that player or that planeswalker's controller controls.

    private static final String whip = "Whip of Erebos";
    // Creatures you control have lifelink.
    private static final String firesong = "Firesong and Sunspeaker";
    // Red instant and sorcery spells you control have lifelink.
    private static final String pridemate = "Ajani's Pridemate"; // 2/2
    // Whenever you gain life, put a +1/+1 counter on this creature.

    private void setupBattlefield() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
        addCard(Zone.BATTLEFIELD, playerA, whip);
        addCard(Zone.BATTLEFIELD, playerA, firesong);
        addCard(Zone.BATTLEFIELD, playerA, pridemate);
        addCard(Zone.BATTLEFIELD, playerA, hatchling);
        addCard(Zone.BATTLEFIELD, playerB, wishcoin);
    }

    @Test
    @Ignore
    public void testCreatureDamageTargetAndYou() {
        setupBattlefield();
        addCard(Zone.HAND, playerA, devil);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, devil);
        addTarget(playerA, wishcoin);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 20 - 1 + 2);
        assertLife(playerB, 20);
        assertDamageReceived(playerB, wishcoin, 1);
        assertCounterCount(pridemate, CounterType.P1P1, 1);
    }

    @Test
    @Ignore
    public void testCreatureDamageTargetAndSelf() {
        setupBattlefield();
        addCard(Zone.BATTLEFIELD, playerA, embermage);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}{R}: ");
        addTarget(playerA, wishcoin);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 20 + 2);
        assertLife(playerB, 20);
        assertDamageReceived(playerB, wishcoin, 1);
        assertDamageReceived(playerB, embermage, 1);
        assertCounterCount(pridemate, CounterType.P1P1, 1);
    }

    @Test
    @Ignore
    public void testSpellDamageTargetAndTarget() {
        setupBattlefield();
        addCard(Zone.HAND, playerA, arc);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, arc);
        addTarget(playerA, wishcoin);
        addTarget(playerA, hatchling);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 20 + 3);
        assertLife(playerB, 20);
        assertDamageReceived(playerB, wishcoin, 2);
        assertDamageReceived(playerA, hatchling, 1);
        assertCounterCount(pridemate, CounterType.P1P1, 1);
    }

    @Test
    @Ignore
    public void testSpellDamageThreeTargets() {
        setupBattlefield();
        addCard(Zone.HAND, playerA, cone);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, cone);
        addTarget(playerA, wishcoin);
        addTarget(playerA, hatchling);
        addTarget(playerA, firesong);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 20 + 6);
        assertLife(playerB, 20);
        assertDamageReceived(playerB, wishcoin, 3);
        assertDamageReceived(playerA, hatchling, 2);
        assertDamageReceived(playerA, firesong, 1);
        assertCounterCount(pridemate, CounterType.P1P1, 1);
    }

    @Test
    @Ignore
    public void testSpellDamageTargetAndYou() {
        setupBattlefield();
        addCard(Zone.HAND, playerA, chaar);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, chaar);
        addTarget(playerA, wishcoin);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 20 - 2 + 6);
        assertLife(playerB, 20);
        assertDamageReceived(playerB, wishcoin, 4);
        assertCounterCount(pridemate, CounterType.P1P1, 1);
    }

    @Test
    @Ignore
    public void testSpellDamageTargetAndController() {
        setupBattlefield();
        addCard(Zone.HAND, playerA, outrage);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, outrage);
        addTarget(playerA, wishcoin);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 20 + 6);
        assertLife(playerB, 20 - 2);
        assertDamageReceived(playerB, wishcoin, 4);
        assertCounterCount(pridemate, CounterType.P1P1, 1);
    }

    @Test
    @Ignore
    public void testSpellDamagePlayerAndControlled() {
        setupBattlefield();
        addCard(Zone.HAND, playerA, fury);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, fury);
        addTarget(playerA, playerB);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 20 + 5);
        assertLife(playerB, 20 - 4);
        assertDamageReceived(playerB, wishcoin, 1);
        assertCounterCount(pridemate, CounterType.P1P1, 1);
    }

}
