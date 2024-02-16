package org.mage.test.cards.single.khm;

import mage.abilities.keyword.HasteAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class TyvarKellTest extends CardTestPlayerBase {

    @Test
    public void test_Emblem_Normal() {
        removeAllCardsFromHand(playerA);

        // −6: You get an emblem with "Whenever you cast an Elf spell, it gains haste until end of turn and you draw two cards."
        addCard(Zone.BATTLEFIELD, playerA, "Tyvar Kell");
        //
        addCard(Zone.HAND, playerA, "Arbor Elf", 1); // {G}
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);

        // prepare emblem
        addCounters(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Tyvar Kell", CounterType.LOYALTY, 10);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "-6:");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkHandCount("before", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 1); // 1x elf

        // cast elf and draw
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Arbor Elf");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkHandCount("after", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 2); // 2x from draw
        checkAbility("after", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Arbor Elf", HasteAbility.class, true);

        // check that it can attack
        attack(1, playerA, "Arbor Elf", playerB);

        // haste must end on next turn
        checkAbility("end", 2, PhaseStep.PRECOMBAT_MAIN, playerA, "Arbor Elf", HasteAbility.class, false);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 20 - 1); // 1x from elf
    }

    @Test
    public void test_Emblem_Blink() {
        removeAllCardsFromHand(playerA);

        // −6: You get an emblem with "Whenever you cast an Elf spell, it gains haste until end of turn and you draw two cards."
        addCard(Zone.BATTLEFIELD, playerA, "Tyvar Kell");
        //
        addCard(Zone.HAND, playerA, "Arbor Elf", 1); // {G}
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        //
        // Exile target creature you control, then return that card to the battlefield under your control.
        addCard(Zone.HAND, playerA, "Cloudshift", 1); // {W}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);

        // prepare emblem
        addCounters(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Tyvar Kell", CounterType.LOYALTY, 10);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "-6:");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkHandCount("before", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 2); // 1x elf + 1x shift

        // cast elf and draw
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Arbor Elf");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkHandCount("after", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 3); // 2x from draw + 1x shift
        checkAbility("after", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Arbor Elf", HasteAbility.class, true);

        // blink elf, so it must lose haste
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cloudshift", "Arbor Elf");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkAbility("blink", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Arbor Elf", HasteAbility.class, false);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }
}
