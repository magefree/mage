package org.mage.test.cards.single.eoc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class BalothPrimeTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.b.BalothPrime Baloth Prime} {3}{G}
     * Creature — Beast
     * This creature enters tapped with six stun counters on it. (If a permanent with a stun counter would become untapped, remove one from it instead.)
     * Whenever you sacrifice a land, create a tapped 4/4 green Beast creature token and untap this creature.
     * {4}, Sacrifice a land: You gain 2 life.
     * 10/10
     */
    private static final String baloth = "Baloth Prime";

    @Test
    public void test_Simple() {
        addCard(Zone.HAND, playerA, baloth);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 10);
        addCard(Zone.BATTLEFIELD, playerA, "Wastes", 2);

        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {C}", 2); // activate both Wastes
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, baloth, true);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{4}, Sacrifice a land: You gain 2 life");
        setChoice(playerA, "Wastes");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{4}, Sacrifice a land: You gain 2 life");
        setChoice(playerA, "Wastes");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        setStrictChooseMode(true);
        execute();

        assertGraveyardCount(playerA, "Wastes", 2);
        assertTappedCount("Forest", true, 10);
        assertCounterCount(playerA, baloth, CounterType.STUN, 6 - 2);
        assertLife(playerA, 20 + 2 + 2);
        assertPermanentCount(playerA, "Beast Token", 2);
    }
}
