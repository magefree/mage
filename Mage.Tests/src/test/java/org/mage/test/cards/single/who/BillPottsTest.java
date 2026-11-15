package org.mage.test.cards.single.who;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class BillPottsTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.b.BillPotts Bill Potts} {3}{R}
     * Legendary Creature - Human 2/4
     * Whenever you cast an instant or sorcery spell that targets only Bill Potts or activate an ability that targets
     * only Bill Potts, copy that spell or ability. You may choose new targets for the copy. This ability triggers only
     * once each turn.
     * Doctor's companion
     */
    private static final String bill = "Bill Potts";

    @Test
    public void test_SpellTargetsOnlyBill_Copy() {
        addCard(Zone.BATTLEFIELD, playerA, bill);
        //
        addCard(Zone.HAND, playerA, "Giant Growth"); // {G}, target creature gets +3/+3 until end of turn
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Giant Growth", bill);
        setChoice(playerA, false); // no new target for copy

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPowerToughness(playerA, bill, 2 + 3 + 3, 4 + 3 + 3);
    }

    @Test
    public void test_AbilityTargetsOnlyBill_Copy() {
        addCard(Zone.BATTLEFIELD, playerA, bill);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Prodigal Pyromancer"); // {T}: This creature deals 1 damage to any target.

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: {this} deals", bill);
        setChoice(playerA, false); // no new target for copy

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertDamageReceived(playerA, bill, 1 + 1);
    }

    @Test
    public void test_AbilityTargetsOther_NoCopy() {
        addCard(Zone.BATTLEFIELD, playerA, bill);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Prodigal Pyromancer"); // {T}: This creature deals 1 damage to any target.

        // no copy dialog
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: {this} deals", playerB);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerB, 20 - 1);
    }
}
