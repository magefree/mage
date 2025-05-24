package org.mage.test.cards.single.avr;

import mage.abilities.keyword.TrampleAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class CraterhoofBehemothTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.c.CraterhoofBehemoth Craterhoof Behemoth} {5}{G}{G}{G}
     * Creature — Beast
     * Haste
     * When this creature enters, creatures you control gain trample and get +X/+X until end of turn, where X is the number of creatures you control.
     * 5/5
     */
    private static final String hoof = "Craterhoof Behemoth";

    @Test
    public void test_simple() {

        addCard(Zone.HAND, playerA, hoof, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Elite Vanguard", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 8);

        addCard(Zone.BATTLEFIELD, playerB, "Goblin Piker", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Merfolk of the Pearl Trident", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, hoof);

        checkAbility("hoof gets trample", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, hoof, TrampleAbility.class, true);
        checkAbility("vanguard gets trample", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Elite Vanguard", TrampleAbility.class, true);
        checkAbility("bears gets trample", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Grizzly Bears", TrampleAbility.class, true);
        checkAbility("opp's piker no trample", 1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Goblin Piker", TrampleAbility.class, false);
        checkAbility("opp's merfolk no trample", 1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Merfolk of the Pearl Trident", TrampleAbility.class, false);

        checkPT("hoof gets +3/+3", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, hoof, 5 + 3, 5 + 3);
        checkPT("vanguard gets +3/+3", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Elite Vanguard", 2 + 3, 1 + 3);
        checkPT("bears gets +3/+3", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Grizzly Bears", 2 + 3, 2 + 3);
        checkPT("opp's piker no PT change", 1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Goblin Piker", 2, 1);
        checkPT("opp's merfolk no PT change", 1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Merfolk of the Pearl Trident", 1, 1);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.PRECOMBAT_MAIN);
        execute();

        // check effect ended.
        assertPowerToughness(playerA, hoof, 5, 5);
        assertPowerToughness(playerA, "Elite Vanguard", 2, 1);
        assertAbility(playerA, hoof, TrampleAbility.getInstance(), false);
        assertAbility(playerA, "Elite Vanguard", TrampleAbility.getInstance(), false);
    }
}
