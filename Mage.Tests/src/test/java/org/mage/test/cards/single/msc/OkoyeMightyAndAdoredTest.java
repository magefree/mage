package org.mage.test.cards.single.msc;

import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author muz
 */
public class OkoyeMightyAndAdoredTest extends CardTestPlayerBase {

    // Okoye, Mighty and Adored {2}{G}{W}
    // When Okoye enters, you become the monarch.
    // At the beginning of combat on your turn, put a +1/+1 counter on target creature. Whenever that creature attacks the monarch this turn, it gains double strike and trample until end of turn.
    private static final String OKOYE = "Okoye, Mighty and Adored";

    private static final String BEARS = "Grizzly Bears";

    // Thorn of the Black Rose {3}{B}
    // When Thorn of the Black Rose enters, you become the monarch.
    private static final String THORN = "Thorn of the Black Rose";

    @Test
    public void testAttackingTheMonarchGainsDoubleStrikeAndTrample() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerA, BEARS);
        addCard(Zone.HAND, playerA, OKOYE);
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 4);
        addCard(Zone.HAND, playerB, THORN);

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, OKOYE);
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, THORN);

        addTarget(playerA, BEARS);
        attack(3, playerA, BEARS, playerB);

        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertCounterCount(playerA, BEARS, CounterType.P1P1, 1);
        assertAbility(playerA, BEARS, DoubleStrikeAbility.getInstance(), true);
        assertAbility(playerA, BEARS, TrampleAbility.getInstance(), true);
        assertLife(playerB, 20 - 6);
    }

    @Test
    public void testAttackingNonMonarchGainsNothing() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerA, BEARS);
        addCard(Zone.HAND, playerA, OKOYE);

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, OKOYE);

        addTarget(playerA, BEARS);
        attack(3, playerA, BEARS, playerB);

        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertCounterCount(playerA, BEARS, CounterType.P1P1, 1);
        assertAbility(playerA, BEARS, DoubleStrikeAbility.getInstance(), false);
        assertAbility(playerA, BEARS, TrampleAbility.getInstance(), false);
        assertLife(playerB, 20 - 3);
    }
}
