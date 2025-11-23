package org.mage.test.cards.triggers.damage;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author xenohedron
 */
public class DamageMultiTriggerTest extends CardTestPlayerBase {

    private static final String hatchling = "Kraken Hatchling"; // 0/4
    private static final String wishcoin = "Wishcoin Crab"; // 2/5

    private static final String brothers = "Brothers of Fire"; // 2/2
    // 1RR: 1 dmg to any target and 1 dmg to you
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

    private static final String tamanoa = "Tamanoa"; // 2/4
    // Whenever a noncreature source you control deals damage, you gain that much life.
    private static final String spiritLink = "Spirit Link"; // 2/2
    // Whenever enchanted creature deals damage, you gain that much life.
    private static final String pridemate = "Ajani's Pridemate"; // 2/2
    // Whenever you gain life, put a +1/+1 counter on this creature.

    private void setupBattlefield() {
        addCard(Zone.BATTLEFIELD, playerA, "Plateau", 5);
        addCard(Zone.BATTLEFIELD, playerA, tamanoa);
        addCard(Zone.HAND, playerA, spiritLink);
        addCard(Zone.BATTLEFIELD, playerA, pridemate);
        addCard(Zone.BATTLEFIELD, playerA, hatchling);
        addCard(Zone.BATTLEFIELD, playerB, wishcoin);
    }

    @Test
    public void testCreatureDamageTargetAndYou() {
        setupBattlefield();
        addCard(Zone.BATTLEFIELD, playerA, brothers);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, spiritLink, brothers);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}{R}{R}: ");
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
    public void testCreatureDamageTargetAndSelf() {
        setupBattlefield();
        addCard(Zone.BATTLEFIELD, playerA, embermage);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, spiritLink, embermage);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}{R}: ");
        addTarget(playerA, wishcoin);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 20 + 2);
        assertLife(playerB, 20);
        assertDamageReceived(playerB, wishcoin, 1);
        assertDamageReceived(playerA, embermage, 1);
        assertCounterCount(pridemate, CounterType.P1P1, 1);
    }

    @Test
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
    public void testSpellDamageThreeTargets() {
        setupBattlefield();
        addCard(Zone.HAND, playerA, cone);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, cone);
        addTarget(playerA, wishcoin);
        addTarget(playerA, hatchling);
        addTarget(playerA, tamanoa);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 20 + 6);
        assertLife(playerB, 20);
        assertDamageReceived(playerB, wishcoin, 1);
        assertDamageReceived(playerA, hatchling, 2);
        assertDamageReceived(playerA, tamanoa, 3);
        assertCounterCount(pridemate, CounterType.P1P1, 1);
    }

    @Test
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
