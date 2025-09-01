package org.mage.test.cards.single.thb;

import mage.abilities.keyword.TrampleAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class TheTriumphOfAnaxTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.t.TheTriumphOfAnax The Triumph of Anax} {2}{R}
     * Enchantment — Saga
     * I, II, III — Until end of turn, target creature gains trample and gets +X/+0, where X is the number of lore counters on this Saga.
     * IV — Target creature you control fights up to one target creature you don’t control.
     */
    private static final String triumph = "The Triumph of Anax";

    @Test
    public void test_SimplePlay() {
        addCard(Zone.HAND, playerA, triumph, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Memnite", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Ornithopter", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, triumph);
        addTarget(playerA, "Memnite");

        checkPT("T1: Memnite is 2/1", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Memnite", 2, 1);
        checkAbility("T1: Memnite has trample", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Memnite", TrampleAbility.class, true);

        // turn 3
        addTarget(playerA, "Memnite");
        checkPT("T3: Memnite is 3/1", 3, PhaseStep.POSTCOMBAT_MAIN, playerA, "Memnite", 3, 1);
        checkAbility("T3: Memnite has trample", 3, PhaseStep.POSTCOMBAT_MAIN, playerA, "Memnite", TrampleAbility.class, true);

        // turn 5
        addTarget(playerA, "Memnite");
        checkPT("T4: Memnite is 4/1", 5, PhaseStep.POSTCOMBAT_MAIN, playerA, "Memnite", 4, 1);
        checkAbility("T4: Memnite has trample", 5, PhaseStep.POSTCOMBAT_MAIN, playerA, "Memnite", TrampleAbility.class, true);

        // turn 7
        addTarget(playerA, "Memnite");
        addTarget(playerA, "Ornithopter");

        setStrictChooseMode(true);
        setStopAt(7, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerA, triumph, 1);
        assertDamageReceived(playerB, "Ornithopter", 1);
        assertPowerToughness(playerA, "Memnite", 1, 1);
        assertAbility(playerA, "Memnite", TrampleAbility.getInstance(), false);
    }
}
