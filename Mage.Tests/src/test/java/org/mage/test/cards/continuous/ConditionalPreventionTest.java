package org.mage.test.cards.continuous;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.condition.common.NotMyTurnCondition;
import mage.abilities.decorator.ConditionalPreventionEffect;
import mage.abilities.effects.common.PreventAllDamageToAllEffect;
import mage.abilities.effects.common.PreventAllDamageToPlayersEffect;
import mage.constants.Duration;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class ConditionalPreventionTest extends CardTestPlayerBase {

    // conditional effects go to layered, but there are prevention effects list too

    @Test
    public void test_NotPreventDamage() {
        addCard(Zone.BATTLEFIELD, playerA, "Balduvian Bears", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.HAND, playerA, "Lightning Bolt", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Balduvian Bears");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Balduvian Bears", 0);
        assertHandCount(playerA, "Lightning Bolt", 0);
    }

    @Test
    public void test_PreventDamageNormal() {
        addCustomCardWithAbility("prevent", playerA, new SimpleStaticAbility(new PreventAllDamageToAllEffect(Duration.WhileOnBattlefield, StaticFilters.FILTER_PERMANENT)));

        addCard(Zone.BATTLEFIELD, playerA, "Balduvian Bears", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.HAND, playerA, "Lightning Bolt", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Balduvian Bears");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Balduvian Bears", 1);
        assertHandCount(playerA, "Lightning Bolt", 0);
    }

    @Test
    public void test_PreventDamageConditionalActive() {
        addCustomCardWithAbility("prevent", playerA, new SimpleStaticAbility(
                new ConditionalPreventionEffect(
                        new PreventAllDamageToAllEffect(Duration.WhileOnBattlefield, StaticFilters.FILTER_PERMANENT),
                        MyTurnCondition.instance,
                        ""
                )
        ));

        addCard(Zone.BATTLEFIELD, playerA, "Balduvian Bears", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.HAND, playerA, "Lightning Bolt", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Balduvian Bears");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Balduvian Bears", 1);
        assertHandCount(playerA, "Lightning Bolt", 0);
    }

    @Test
    public void test_PreventDamageConditionalNotActive() {
        addCustomCardWithAbility("prevent", playerA, new SimpleStaticAbility(
                new ConditionalPreventionEffect(
                        new PreventAllDamageToAllEffect(Duration.WhileOnBattlefield, StaticFilters.FILTER_PERMANENT),
                        NotMyTurnCondition.instance,
                        ""
                )
        ));

        addCard(Zone.BATTLEFIELD, playerA, "Balduvian Bears", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.HAND, playerA, "Lightning Bolt", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Balduvian Bears");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Balduvian Bears", 0);
        assertHandCount(playerA, "Lightning Bolt", 0);
    }

    @Test
    public void test_PreventDamageConditionalNotActiveWithOtherEffect() {
        addCustomCardWithAbility("prevent", playerA, new SimpleStaticAbility(
                new ConditionalPreventionEffect(
                        new PreventAllDamageToAllEffect(Duration.WhileOnBattlefield, StaticFilters.FILTER_PERMANENT),
                        new PreventAllDamageToPlayersEffect(Duration.WhileOnBattlefield, false),
                        NotMyTurnCondition.instance,
                        ""
                )
        ));

        addCard(Zone.BATTLEFIELD, playerA, "Balduvian Bears", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.HAND, playerA, "Lightning Bolt", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Balduvian Bears"); // will prevent
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerA); // will not prevent

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Balduvian Bears", 0); // not prevented, dies
        assertLife(playerA, 20); // prevented, no damage
        assertHandCount(playerA, "Lightning Bolt", 0);
    }

    @Test
    public void test_PreventableCombatDamage() {
        // Prevent all damage that would be dealt to creatures.
        addCard(Zone.BATTLEFIELD, playerA, "Bubble Matrix", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Balduvian Bears", 1);
        //
        addCard(Zone.BATTLEFIELD, playerB, "Balduvian Bears", 1);

        // player A must do damage
        attack(1, playerA, "Balduvian Bears", playerB);

        // player B can't do damage (bears must block and safe)
        attack(4, playerB, "Balduvian Bears", playerA);
        block(4, playerA, "Balduvian Bears", "Balduvian Bears");

        setStrictChooseMode(true);
        setStopAt(4, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Balduvian Bears", 1);
        assertPermanentCount(playerB, "Balduvian Bears", 1);
        assertLife(playerA, 20);
        assertLife(playerB, 20 - 2);
    }

    @Test
    public void test_UnpreventableCombatDamage() {
        // Combat damage that would be dealt by creatures you control can't be prevented.
        addCard(Zone.BATTLEFIELD, playerB, "Questing Beast", 1);
        //
        // Prevent all damage that would be dealt to creatures.
        addCard(Zone.BATTLEFIELD, playerA, "Bubble Matrix", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Balduvian Bears", 1);
        //
        addCard(Zone.BATTLEFIELD, playerB, "Balduvian Bears", 1);

        // player A must do damage
        attack(1, playerA, "Balduvian Bears", playerB);

        // player B must be prevented by Bubble Matrix, but can't (Questing Beast)
        // a -> b -- can't do damage (matrix)
        // b -> a -- can do damage (matrix -> quest)
        attack(4, playerB, "Balduvian Bears", playerA);
        block(4, playerA, "Balduvian Bears", "Balduvian Bears");

        setStrictChooseMode(true);
        setStopAt(4, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Balduvian Bears", 0);
        assertPermanentCount(playerB, "Balduvian Bears", 1);
        assertLife(playerA, 20);
        assertLife(playerB, 20 - 2);
    }

    @Test
    public void test_PreventSomeDamage_Normal() {
        // Kicker-Sacrifice a land.
        // Prevent the next 3 damage that would be dealt this turn to any number of target creatures and/or players, divided as you choose.
        // If Pollen Remedy was kicked, prevent the next 6 damage this way instead.
        addCard(Zone.HAND, playerA, "Pollen Remedy", 1); // {W}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        addCard(Zone.HAND, playerA, "Swamp", 1); // for kicker
        //
        addCard(Zone.BATTLEFIELD, playerA, "Balduvian Bears", 1);
        //
        addCard(Zone.HAND, playerA, "Lightning Bolt", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);

        // add shield for 3 damage
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Pollen Remedy");
        setChoice(playerA, false); // no kicker
        addTargetAmount(playerA, "Balduvian Bears", 3);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkGraveyardCount("shield", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Pollen Remedy", 1);

        // 6 damage to die (if no shield then can cast only 1 bolt)
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Balduvian Bears");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Balduvian Bears");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        checkGraveyardCount("after", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", 2);
        checkGraveyardCount("after", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Balduvian Bears", 1);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_PreventSomeDamage_Kicked() {
        // Kicker-Sacrifice a land.
        // Prevent the next 3 damage that would be dealt this turn to any number of target creatures and/or players, divided as you choose.
        // If Pollen Remedy was kicked, prevent the next 6 damage this way instead.
        addCard(Zone.HAND, playerA, "Pollen Remedy", 1); // {W}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1); // for kicker
        //
        addCard(Zone.BATTLEFIELD, playerA, "Balduvian Bears", 1);
        //
        addCard(Zone.HAND, playerA, "Lightning Bolt", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);

        // add shield for 6 damage
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Pollen Remedy");
        setChoice(playerA, true); // use kicker
        setChoice(playerA, "Swamp"); // kicker cost
        addTargetAmount(playerA, "Balduvian Bears", 6);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkGraveyardCount("shield", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Pollen Remedy", 1);

        // 9 damage to die (if no shield then can cast only 1 bolt)
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Balduvian Bears");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Balduvian Bears");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Balduvian Bears");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        checkGraveyardCount("after", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", 3);
        checkGraveyardCount("after", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Balduvian Bears", 1);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }
}
