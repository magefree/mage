package org.mage.test.cards.single.dmu;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class ThePhasingOfZhalfirTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.t.ThePhasingOfZhalfir The Phasing of Zhalfir} {2}{U}{U}
     * Enchantment — Saga
     * Read ahead (Choose a chapter and start with that many lore counters. Add one after your draw step. Skipped chapters don’t trigger. Sacrifice after III.)
     * I, II — Another target nonland permanent phases out. It can’t phase in for as long as you control this Saga.
     * III — Destroy all creatures. For each creature destroyed this way, its controller creates a 2/2 black Phyrexian creature token.
     */
    private static final String phasing = "The Phasing of Zhalfir";

    @Test
    public void test_SimplePlay() {
        addCard(Zone.HAND, playerA, phasing, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Memnite", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Ornithopter", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Elite Vanguard", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, phasing);
        setChoiceAmount(playerA, 1); // chosing to start at I with Read ahead
        addTarget(playerA, "Memnite");

        checkPermanentCount("1: Memnite is phased out", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Memnite", 0);
        checkPermanentCount("1: Ornithopter not phased out", 1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Ornithopter", 1);

        // turn 3
        addTarget(playerA, "Ornithopter");

        checkPermanentCount("3: Memnite is phased out", 3, PhaseStep.POSTCOMBAT_MAIN, playerA, "Memnite", 0);
        checkPermanentCount("3: Ornithopter is phased out", 3, PhaseStep.POSTCOMBAT_MAIN, playerB, "Ornithopter", 0);

        // turn 5
        checkPermanentCount("5: Memnite is phased out", 5, PhaseStep.POSTCOMBAT_MAIN, playerA, "Memnite", 0);
        checkPermanentCount("5: Ornithopter is phased out", 5, PhaseStep.POSTCOMBAT_MAIN, playerB, "Ornithopter", 0);
        checkPermanentCount("5: Vanguard got destroyed", 5, PhaseStep.POSTCOMBAT_MAIN, playerA, "Elite Vanguard", 0);
        checkPermanentCount("5: phasing done after III", 5, PhaseStep.POSTCOMBAT_MAIN, playerA, phasing, 0);
        checkPermanentCount("5: has a Phyrexian creature token", 5, PhaseStep.POSTCOMBAT_MAIN, playerA, "Phyrexian Token", 1);

        // T6: Ornithopter phases in
        checkPermanentCount("6: Memnite is phased out", 6, PhaseStep.POSTCOMBAT_MAIN, playerA, "Memnite", 0);
        checkPermanentCount("6: Ornithopter not phased out", 6, PhaseStep.POSTCOMBAT_MAIN, playerB, "Ornithopter", 1);

        // T7: Memnite phases in

        setStrictChooseMode(true);
        setStopAt(7, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Memnite", 1);
        assertPermanentCount(playerB, "Ornithopter", 1);
    }
}
