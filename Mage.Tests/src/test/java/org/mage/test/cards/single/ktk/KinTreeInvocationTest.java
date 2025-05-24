package org.mage.test.cards.single.ktk;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class KinTreeInvocationTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.k.KinTreeInvocation Kin-Tree Invocation} {B}{G}
     * Sorcery
     * Create an X/X black and green Spirit Warrior creature token, where X is the greatest toughness among creatures you control.
     */
    private static final String invocation = "Kin-Tree Invocation";

    @Test
    public void test_no_creature() {
        addCard(Zone.HAND, playerA, invocation);
        addCard(Zone.BATTLEFIELD, playerA, "Glorious Anthem", 1); // Creatures you control get +1/+1.
        addCard(Zone.BATTLEFIELD, playerA, "Bayou", 2);

        addCard(Zone.BATTLEFIELD, playerB, "Barbarian Horde", 1); // 3/3 {3}{R}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, invocation);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Spirit Warrior Token", 1);
        assertPowerToughness(playerA, "Spirit Warrior Token", 1, 1);
    }

    @Test
    public void test_varied_creatures() {
        addCard(Zone.HAND, playerA, invocation);
        addCard(Zone.BATTLEFIELD, playerA, "Axegrinder Giant", 1); // 6/4
        addCard(Zone.BATTLEFIELD, playerA, "Ancient Carp", 1); // 2/5
        addCard(Zone.BATTLEFIELD, playerA, "Goblin Piker", 1); // 2/1
        addCard(Zone.BATTLEFIELD, playerA, "Bayou", 2);

        addCard(Zone.BATTLEFIELD, playerB, "Barbarian Horde", 1); // 3/3 {3}{R}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, invocation);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Spirit Warrior Token", 1);
        assertPowerToughness(playerA, "Spirit Warrior Token", 5, 5);
    }
}
