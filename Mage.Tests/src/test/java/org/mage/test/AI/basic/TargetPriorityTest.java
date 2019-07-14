package org.mage.test.AI.basic;

import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageMultiEffect;
import mage.constants.CardType;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanentAmount;
import org.junit.Ignore;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBaseAI;

/**
 * @author JayDi85
 */
public class TargetPriorityTest extends CardTestPlayerBaseAI {

    // TODO: enable _target_ tests after computerPlayer.chooseTarget will be reworks like chooseTargetAmount

    @Test
    @Ignore
    public void test_target_PriorityKillByBigPT() {
        addCard(Zone.HAND, playerA, "Lightning Bolt");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        //
        addCard(Zone.BATTLEFIELD, playerB, "Memnite", 3); // 1/1
        addCard(Zone.BATTLEFIELD, playerB, "Balduvian Bears", 3); // 2/2
        addCard(Zone.BATTLEFIELD, playerB, "Ashcoat Bear", 3); // 2/2 with ability
        addCard(Zone.BATTLEFIELD, playerB, "Golden Bear", 3); // 4/3
        addCard(Zone.BATTLEFIELD, playerB, "Battering Sliver", 3); // 4/4 with ability

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerB, "Memnite", 3);
        assertPermanentCount(playerB, "Balduvian Bears", 3);
        assertPermanentCount(playerB, "Ashcoat Bear", 3);
        assertPermanentCount(playerB, "Golden Bear", 3 - 1);
        assertPermanentCount(playerB, "Battering Sliver", 3);
    }

    @Test
    @Ignore
    public void test_target_PriorityByKillByLowPT() {
        addCard(Zone.HAND, playerA, "Lightning Bolt");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        //
        addCard(Zone.BATTLEFIELD, playerB, "Memnite", 3); // 1/1
        //addCard(Zone.BATTLEFIELD, playerB, "Balduvian Bears", 3); // 2/2
        //addCard(Zone.BATTLEFIELD, playerB, "Ashcoat Bear", 3); // 2/2 with ability
        //addCard(Zone.BATTLEFIELD, playerB, "Golden Bear", 3); // 4/3
        addCard(Zone.BATTLEFIELD, playerB, "Battering Sliver", 3); // 4/4 with ability

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerB, "Memnite", 3 - 1);
        //assertPermanentCount(playerB, "Balduvian Bears", 3);
        //assertPermanentCount(playerB, "Ashcoat Bear", 3);
        //assertPermanentCount(playerB, "Golden Bear", 3);
        assertPermanentCount(playerB, "Battering Sliver", 3);
    }

    @Test
    @Ignore
    public void test_target_PriorityKillByExtraPoints() {
        addCard(Zone.HAND, playerA, "Lightning Bolt");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        //
        addCard(Zone.BATTLEFIELD, playerB, "Memnite", 3); // 1/1
        addCard(Zone.BATTLEFIELD, playerB, "Balduvian Bears", 3); // 2/2
        addCard(Zone.BATTLEFIELD, playerB, "Ashcoat Bear", 3); // 2/2 with ability
        //addCard(Zone.BATTLEFIELD, playerB, "Golden Bear", 3); // 4/3
        addCard(Zone.BATTLEFIELD, playerB, "Battering Sliver", 3); // 4/4 with ability

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerB, "Memnite", 3);
        assertPermanentCount(playerB, "Balduvian Bears", 3);
        assertPermanentCount(playerB, "Ashcoat Bear", 3 - 1);
        //assertPermanentCount(playerB, "Golden Bear", 3);
        assertPermanentCount(playerB, "Battering Sliver", 3);
    }

    @Test
    @Ignore // TODO: enable it after chooseTarget will be rewrites like choseTargetAmount
    public void test_target_KillCreatureNotDamage() {
        // https://github.com/magefree/mage/issues/4497
        // choose target for damage selects wrong target

        addCard(Zone.HAND, playerA, "Burning Sun's Avatar"); // 3 damage to target on enter
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1, true); // avatar can be cast on 3 turn
        //
        addCard(Zone.BATTLEFIELD, playerB, "Ancient Brontodon", 1); // 9/9
        addCard(Zone.BATTLEFIELD, playerB, "Balduvian Bears", 1); // 2/2, will be +2 counters
        addCard(Zone.BATTLEFIELD, playerB, "Arbor Elf", 1); // 1/1

        // prepare, A can't cast avatar until mana is tapped
        addCounters(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Balduvian Bears", CounterType.P1P1, 2);
        checkPermanentCounters("counters", 1, PhaseStep.BEGIN_COMBAT, playerB, "Balduvian Bears", CounterType.P1P1, 2);

        // AI cast avatar on turn 3 and target 1 creature to kil by 3 damage

        //setStrictChooseMode(true); // AI
        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerA, "Burning Sun's Avatar", 1);
        assertPermanentCount(playerB, "Ancient Brontodon", 1); // can't be killed
        assertPermanentCount(playerB, "Balduvian Bears", 1); // 2/2, but with counters is 4/4, can't be killed
        assertPermanentCount(playerB, "Arbor Elf", 0); // must be killed
        assertGraveyardCount(playerB, "Arbor Elf", 1);
    }

    @Test
    @Ignore // do not enable it in production, only for devs
    public void test_target_Performance() {
        int cardsMultiplier = 10;

        addCard(Zone.HAND, playerA, "Lightning Bolt", 1 * cardsMultiplier);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1 * cardsMultiplier);
        //
        addCard(Zone.BATTLEFIELD, playerB, "Memnite", 1 * cardsMultiplier); // 1/1
        addCard(Zone.BATTLEFIELD, playerB, "Balduvian Bears", 1 * cardsMultiplier); // 2/2
        addCard(Zone.BATTLEFIELD, playerB, "Ashcoat Bear", 1 * cardsMultiplier); // 2/2 with ability
        addCard(Zone.BATTLEFIELD, playerB, "Golden Bear", 1 * cardsMultiplier); // 4/3
        addCard(Zone.BATTLEFIELD, playerB, "Battering Sliver", 1 * cardsMultiplier); // 4/4 with ability

        //castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt");

        showHand("after", 1, PhaseStep.BEGIN_COMBAT, playerA);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        assertAllCommandsUsed();

        /* disabled checks cause target is not yet fixed, see comments at the start file
        assertPermanentCount(playerB, "Memnite", 1 * cardsMultiplier);
        assertPermanentCount(playerB, "Balduvian Bears", 1 * cardsMultiplier);
        assertPermanentCount(playerB, "Ashcoat Bear", 1 * cardsMultiplier);
        assertPermanentCount(playerB, "Golden Bear", 1 * cardsMultiplier - 1);
        assertPermanentCount(playerB, "Battering Sliver", 1 * cardsMultiplier);
         */
    }


    // TARGET AMOUNT

    @Test
    public void test_targetAmount_PriorityKillByBigPT() {
        addCard(Zone.HAND, playerA, "Flames of the Firebrand"); // damage 3
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        //
        addCard(Zone.BATTLEFIELD, playerB, "Memnite", 3); // 1/1
        addCard(Zone.BATTLEFIELD, playerB, "Balduvian Bears", 3); // 2/2
        addCard(Zone.BATTLEFIELD, playerB, "Ashcoat Bear", 3); // 2/2 with ability
        addCard(Zone.BATTLEFIELD, playerB, "Golden Bear", 3); // 4/3
        addCard(Zone.BATTLEFIELD, playerB, "Battering Sliver", 3); // 4/4 with ability

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Flames of the Firebrand");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerB, "Memnite", 3);
        assertPermanentCount(playerB, "Balduvian Bears", 3);
        assertPermanentCount(playerB, "Ashcoat Bear", 3);
        assertPermanentCount(playerB, "Golden Bear", 3 - 1);
        assertPermanentCount(playerB, "Battering Sliver", 3);
    }

    @Test
    public void test_targetAmount_PriorityByKillByLowPT() {
        addCard(Zone.HAND, playerA, "Flames of the Firebrand"); // damage 3
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        //
        addCard(Zone.BATTLEFIELD, playerB, "Memnite", 3); // 1/1
        addCard(Zone.BATTLEFIELD, playerB, "Balduvian Bears", 3); // 2/2
        //addCard(Zone.BATTLEFIELD, playerB, "Ashcoat Bear", 3); // 2/2 with ability
        //addCard(Zone.BATTLEFIELD, playerB, "Golden Bear", 3); // 4/3
        addCard(Zone.BATTLEFIELD, playerB, "Battering Sliver", 3); // 4/4 with ability

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Flames of the Firebrand");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerB, "Memnite", 3 - 1);
        assertPermanentCount(playerB, "Balduvian Bears", 3 - 1);
        //assertPermanentCount(playerB, "Ashcoat Bear", 3);
        //assertPermanentCount(playerB, "Golden Bear", 3);
        assertPermanentCount(playerB, "Battering Sliver", 3);
    }

    @Test
    public void test_targetAmount_PriorityKillByExtraPoints() {
        addCard(Zone.HAND, playerA, "Flames of the Firebrand"); // damage 3
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        //
        addCard(Zone.BATTLEFIELD, playerB, "Memnite", 3); // 1/1
        addCard(Zone.BATTLEFIELD, playerB, "Balduvian Bears", 3); // 2/2
        addCard(Zone.BATTLEFIELD, playerB, "Ashcoat Bear", 3); // 2/2 with ability
        //addCard(Zone.BATTLEFIELD, playerB, "Golden Bear", 3); // 4/3
        addCard(Zone.BATTLEFIELD, playerB, "Battering Sliver", 3); // 4/4 with ability

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Flames of the Firebrand");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerB, "Memnite", 3 - 1);
        assertPermanentCount(playerB, "Balduvian Bears", 3);
        assertPermanentCount(playerB, "Ashcoat Bear", 3 - 1);
        //assertPermanentCount(playerB, "Golden Bear", 3);
        assertPermanentCount(playerB, "Battering Sliver", 3);
    }

    @Test
    public void test_targetAmount_NormalCase() {
        Ability ability = new SimpleActivatedAbility(Zone.ALL, new DamageMultiEffect(3), new ManaCostsImpl("R"));
        ability.addTarget(new TargetCreaturePermanentAmount(3));
        addCustomCardWithAbility("damage 3", playerA, ability);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        //
        addCard(Zone.BATTLEFIELD, playerB, "Memnite", 3); // 1/1
        addCard(Zone.BATTLEFIELD, playerB, "Balduvian Bears", 3); // 2/2
        addCard(Zone.BATTLEFIELD, playerB, "Ashcoat Bear", 3); // 2/2 with ability
        addCard(Zone.BATTLEFIELD, playerB, "Golden Bear", 3); // 4/3
        addCard(Zone.BATTLEFIELD, playerB, "Battering Sliver", 3); // 4/4 with ability

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{R}: ");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerB, "Memnite", 3);
        assertPermanentCount(playerB, "Balduvian Bears", 3);
        assertPermanentCount(playerB, "Ashcoat Bear", 3);
        assertPermanentCount(playerB, "Golden Bear", 3 - 1);
        assertPermanentCount(playerB, "Battering Sliver", 3);
    }

    @Test
    public void test_targetAmount_BadCase() {
        // choose targets as enters battlefield (e.g. can't be canceled)
        SpellAbility spell = new SpellAbility(new ManaCostsImpl("R"), "damage 3", Zone.HAND);
        Ability ability = new EntersBattlefieldTriggeredAbility(new DamageMultiEffect(3));
        ability.addTarget(new TargetCreaturePermanentAmount(3));
        addCustomCardWithSpell(playerA, spell, ability, CardType.ENCHANTMENT);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Memnite", 3); // 1/1
        addCard(Zone.BATTLEFIELD, playerA, "Balduvian Bears", 3); // 2/2
        addCard(Zone.BATTLEFIELD, playerA, "Ashcoat Bear", 3); // 2/2 with ability
        addCard(Zone.BATTLEFIELD, playerA, "Golden Bear", 3); // 4/3
        addCard(Zone.BATTLEFIELD, playerA, "Battering Sliver", 3); // 4/4 with ability

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "damage 3");

        // must damage x3 Balduvian Bears by -1 to keep alive
        checkDamage("pt after", 1, PhaseStep.BEGIN_COMBAT, playerA, "Balduvian Bears", 1);
        showBattlefield("after", 1, PhaseStep.BEGIN_COMBAT, playerA);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerA, "damage 3", 1);

        assertPermanentCount(playerA, "Memnite", 3);
        assertPermanentCount(playerA, "Balduvian Bears", 3);
        assertPermanentCount(playerA, "Ashcoat Bear", 3);
        assertPermanentCount(playerA, "Golden Bear", 3);
        assertPermanentCount(playerA, "Battering Sliver", 3);
    }

    @Test
    @Ignore // do not enable it in production, only for devs
    public void test_targetAmount_Performance() {
        int cardsMultiplier = 3;

        Ability ability = new SimpleActivatedAbility(Zone.ALL, new DamageMultiEffect(3), new ManaCostsImpl("R"));
        ability.addTarget(new TargetCreaturePermanentAmount(3));
        addCustomCardWithAbility("damage 3", playerA, ability);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        //
        addCard(Zone.BATTLEFIELD, playerB, "Memnite", 1 * cardsMultiplier); // 1/1
        addCard(Zone.BATTLEFIELD, playerB, "Balduvian Bears", 1 * cardsMultiplier); // 2/2
        addCard(Zone.BATTLEFIELD, playerB, "Ashcoat Bear", 1 * cardsMultiplier); // 2/2 with ability
        addCard(Zone.BATTLEFIELD, playerB, "Golden Bear", 1 * cardsMultiplier); // 4/3
        addCard(Zone.BATTLEFIELD, playerB, "Battering Sliver", 1 * cardsMultiplier); // 4/4 with ability

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{R}: ");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerB, "Memnite", 1 * cardsMultiplier);
        assertPermanentCount(playerB, "Balduvian Bears", 1 * cardsMultiplier);
        assertPermanentCount(playerB, "Ashcoat Bear", 1 * cardsMultiplier);
        assertPermanentCount(playerB, "Golden Bear", 1 * cardsMultiplier - 1);
        assertPermanentCount(playerB, "Battering Sliver", 1 * cardsMultiplier);
    }
}
