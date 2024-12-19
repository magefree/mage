package org.mage.test.cards.single.dmc;

import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.PayVariableLifeCost;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.InfectAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class VerrakWarpedSengirTest extends CardTestPlayerBase {

    @Test
    public void test_CopyOnPayLife_DirectCost() {
        // Whenever you activate an ability that isn’t a mana ability, if life was paid to activate it,
        // you may pay that much life again. If you do, copy that ability. You may choose new targets for the copy.
        addCard(Zone.BATTLEFIELD, playerA, "Verrak, Warped Sengir");
        //
        // {1}{B}, Pay 2 life: Draw a card.
        addCard(Zone.BATTLEFIELD, playerA, "Arguel's Blood Fast");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);

        checkHandCount("before", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 0);

        // activate and copy it
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}{B}, Pay 2 life");
        setChoice(playerA, true); // pay for copy

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        setStrictChooseMode(true);
        execute();

        assertHandCount(playerA, 1 + 1); // from x2 draws
        assertLife(playerA, 20 - 2 - 2); // from x2 life pays
    }

    @Test
    public void test_CopyOnPayLife_VariableCost() {
        // there are no real cards with X life in activated ability, so generate it here
        addCustomCardWithAbility("test", playerA, new SimpleActivatedAbility(
                new GainLifeEffect(10), new PayVariableLifeCost()
        ));

        // Whenever you activate an ability that isn’t a mana ability, if life was paid to activate it,
        // you may pay that much life again. If you do, copy that ability. You may choose new targets for the copy.
        addCard(Zone.BATTLEFIELD, playerA, "Verrak, Warped Sengir");

        // activate and copy it
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Pay X life: You gain 10 life");
        setChoice(playerA, "X=3");
        setChoice(playerA, true); // pay for copy

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        setStrictChooseMode(true);
        execute();

        assertLife(playerA, 20 - 2 * 3 + 2 * 10); // x2: -3 for cost, +10 for resolve
    }

    @Test
    public void test_CopyOnPayLife_PhyrexianCost() {
        // Whenever you activate an ability that isn’t a mana ability, if life was paid to activate it,
        // you may pay that much life again. If you do, copy that ability. You may choose new targets for the copy.
        addCard(Zone.BATTLEFIELD, playerA, "Verrak, Warped Sengir");
        //
        // {B/P}: Pestilent Souleater gains infect until end of turn.
        // ({B/P} can be paid with either {B} or 2 life. A creature with infect deals damage to creatures in the
        // form of -1/-1 counters and to players in the form of poison counters.)
        addCard(Zone.BATTLEFIELD, playerA, "Pestilent Souleater");

        checkAbility("before", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Pestilent Souleater", InfectAbility.class, false);

        // activate and copy it
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{B/P}:");
        setChoice(playerA, true); // pay 2 life instead B
        setChoice(playerA, true); // pay for copy
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkAbility("after", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Pestilent Souleater", InfectAbility.class, true);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        setStrictChooseMode(true);
        execute();

        assertLife(playerA, 20 - 2 * 2); // x2 pays
    }

    @Test
    public void test_CopyOnPayLife_PaymentModification() {
        // bug: https://github.com/magefree/mage/issues/10119

        // Whenever you activate an ability that isn’t a mana ability, if life was paid to activate it,
        // you may pay that much life again. If you do, copy that ability. You may choose new targets for the copy.
        addCard(Zone.BATTLEFIELD, playerA, "Verrak, Warped Sengir");
        //
        // For each {B} in a cost, you may pay 2 life rather than pay that mana.
        addCard(Zone.BATTLEFIELD, playerA, "K'rrik, Son of Yawgmoth");
        //
        // {4}{B}: Each opponent loses 2 life and you gain 2 life. xxx
        addCard(Zone.BATTLEFIELD, playerA, "Kami of Jealous Thirst");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);

        // activate and copy it
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{4}{B}: Each opponent");
        setChoice(playerA, true); // pay 2 life instead B
        setChoice(playerA, true); // pay for copy
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        setStrictChooseMode(true);
        execute();

        assertLife(playerA, 20 - 2 * 2 + 2 * 2); // x2 pays, x2 gains
        assertLife(playerB, 20 - 2 * 2); // x2 lose
    }

    @Test
    public void test_MustNotTriggerOnDiscardCost() {
        // bug: https://github.com/magefree/mage/issues/12089

        // Whenever you activate an ability that isn’t a mana ability, if life was paid to activate it,
        // you may pay that much life again. If you do, copy that ability. You may choose new targets for the copy.
        addCard(Zone.BATTLEFIELD, playerA, "Verrak, Warped Sengir");
        //
        // Pay 2 life, Sacrifice another creature: Search your library for a card, put that card into your hand, then shuffle.
        addCard(Zone.BATTLEFIELD, playerA, "Razaketh, the Foulblooded");

        // activate without copy trigger (discard cost pay will remove Verrak before activate the ability)
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Pay 2 life, Sacrifice");
        setChoice(playerA, "Verrak, Warped Sengir"); // sacrifice cost
        addTarget(playerA, "Mountain"); // search lib

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        setStrictChooseMode(true);
        execute();
    }
}
