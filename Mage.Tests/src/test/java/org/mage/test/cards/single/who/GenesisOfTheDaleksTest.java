package org.mage.test.cards.single.who;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class GenesisOfTheDaleksTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.g.GenesisOfTheDaleks Genesis of the Daleks} {4}{B}{B}
     * Enchantment — Saga
     * I, II, III — Create a 3/3 black Dalek artifact creature token with menace for each lore counter on Genesis of the Daleks.
     * IV — Target opponent faces a villainous choice — Destroy all Dalek creatures and each of your opponents loses life equal to the total power of Daleks that died this turn, or destroy all non-Dalek creatures.
     */
    private static final String genesis = "Genesis of the Daleks";

    @Test
    public void test_SimplePlay() {
        addCard(Zone.HAND, playerA, genesis, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 6);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, genesis);

        checkPermanentCount("T1: after I", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Dalek Token", 1);

        // turn 3
        checkPermanentCount("T3: after II", 3, PhaseStep.POSTCOMBAT_MAIN, playerA, "Dalek Token", 3);

        // turn 5
        checkPermanentCount("T5: after III", 5, PhaseStep.POSTCOMBAT_MAIN, playerA, "Dalek Token", 6);

        // turn 7
        addTarget(playerA, playerB);
        setChoice(playerB, true); // choose Destroy all Dalek creatures and each of your opponents loses life equal to the total power of Daleks that died this turn

        setStrictChooseMode(true);
        setStopAt(7, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, 6);
        assertLife(playerB, 20 - 18);
    }
}
