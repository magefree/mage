package org.mage.test.cards.single.m11;

import mage.abilities.keyword.TrampleAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class OverwhelmingStampedeTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.o.OverwhelmingStampede Overwhelming Stampede} {3}{G}{G}
     * Sorcery
     * Until end of turn, creatures you control gain trample and get +X/+X, where X is the greatest power among creatures you control.
     */
    private static final String stampede = "Overwhelming Stampede";

    @Test
    public void test_simple() {

        addCard(Zone.HAND, playerA, stampede, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Borderland Minotaur", 1); // 4/3
        addCard(Zone.BATTLEFIELD, playerA, "Elite Vanguard", 1); // 2/1
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears", 1); // 2/2
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 5);

        addCard(Zone.BATTLEFIELD, playerB, "Goblin Piker", 1); // 2/1
        addCard(Zone.BATTLEFIELD, playerB, "Merfolk of the Pearl Trident", 1); // 1/1

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, stampede);

        checkAbility("minotaur gets trample", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Borderland Minotaur", TrampleAbility.class, true);
        checkAbility("vanguard gets trample", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Elite Vanguard", TrampleAbility.class, true);
        checkAbility("bears gets trample", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Grizzly Bears", TrampleAbility.class, true);
        checkAbility("opp's piker no trample", 1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Goblin Piker", TrampleAbility.class, false);
        checkAbility("opp's merfolk no trample", 1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Merfolk of the Pearl Trident", TrampleAbility.class, false);

        checkPT("minotaur gets +4/+4", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Borderland Minotaur", 4 + 4, 3 + 4);
        checkPT("vanguard gets +4/+4", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Elite Vanguard", 2 + 4, 1 + 4);
        checkPT("bears gets +4/+4", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Grizzly Bears", 2 + 4, 2 + 4);
        checkPT("opp's piker no PT change", 1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Goblin Piker", 2, 1);
        checkPT("opp's merfolk no PT change", 1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Merfolk of the Pearl Trident", 1, 1);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.PRECOMBAT_MAIN);
        execute();

        // check effect ended.
        assertPowerToughness(playerA, "Borderland Minotaur", 4, 3);
        assertPowerToughness(playerA, "Elite Vanguard", 2, 1);
        assertAbility(playerA, "Borderland Minotaur", TrampleAbility.getInstance(), false);
        assertAbility(playerA, "Elite Vanguard", TrampleAbility.getInstance(), false);
    }
}
