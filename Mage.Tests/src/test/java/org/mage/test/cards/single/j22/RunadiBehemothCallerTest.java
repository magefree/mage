package org.mage.test.cards.single.j22;

import mage.abilities.keyword.HasteAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author xenohedron
 */
public class RunadiBehemothCallerTest extends CardTestPlayerBase {

    private static final String runadi = "Runadi, Behemoth Caller";
    // Whenever you cast a creature spell with mana value 5 or greater, that creature enters the battlefield with
    //     X additional +1/+1 counters on it, where X is its mana value minus 4.
    // Creatures you control with three or more +1/+1 counters on them have haste.
    private static final String endlessOne = "Endless One"; // 0/0 for {X}, ETB with X +1/+1 counters
    private static final String kurgadon = "Kurgadon";
    // Whenever you cast a creature spell with mana value 6 or greater, put three +1/+1 counters on Kurgadon.

    @Test
    public void testXEnters() {

        addCard(Zone.BATTLEFIELD, playerA, runadi);
        addCard(Zone.HAND, playerA, endlessOne);
        addCard(Zone.BATTLEFIELD, playerA, "Wastes", 6);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, endlessOne);
        setChoice(playerA, "X=6");
        // should enter with two additional counters to be an 8/8
        setChoice(playerA, "Endless One"); // order replacement effects

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertPowerToughness(playerA, endlessOne, 8, 8);
        assertAbility(playerA, endlessOne, HasteAbility.getInstance(), true);
    }

    @Test
    public void testSpellXChecked() {

        addCard(Zone.BATTLEFIELD, playerA, kurgadon);
        addCard(Zone.HAND, playerA, endlessOne);
        addCard(Zone.BATTLEFIELD, playerA, "Wastes", 6);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, endlessOne);
        setChoice(playerA, "X=6");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertCounterCount(playerA, kurgadon, CounterType.P1P1, 3);
    }

}
