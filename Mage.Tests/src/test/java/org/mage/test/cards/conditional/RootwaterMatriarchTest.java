package org.mage.test.cards.conditional;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author jeff
 */
public class RootwaterMatriarchTest extends CardTestPlayerBase {

    @Test
    public void testTargetFail() {

        addCard(Zone.BATTLEFIELD, playerA, "Rootwater Matriarch");
        addCard(Zone.BATTLEFIELD, playerB, "Memnite");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Gain control of target creature for as long as that creature is enchanted.", "Memnite");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Rootwater Matriarch", 1);
        assertPermanentCount(playerB, "Memnite", 1);
    }

    @Test
    public void testTargetSuccess() {
        // {T}: Gain control of target creature for as long as that creature is enchanted
        addCard(Zone.BATTLEFIELD, playerA, "Rootwater Matriarch");
        addCard(Zone.BATTLEFIELD, playerA, "Island");
        addCard(Zone.BATTLEFIELD, playerB, "Memnite");

        addCard(Zone.HAND, playerA, "Flight");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Flight", "Memnite");

        activateAbility(1, PhaseStep.BEGIN_COMBAT, playerA, "{T}: Gain control of target creature for as long as that creature is enchanted.", "Memnite");

        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertPermanentCount(playerA, "Rootwater Matriarch", 1);
        assertPermanentCount(playerA, "Memnite", 1);

    }

    @Test
    public void testGainControlEnchantedTargetAndRWLeavesPlay() {

        addCard(Zone.BATTLEFIELD, playerA, "Rootwater Matriarch");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Memnite");
        addCard(Zone.HAND, playerA, "Unsummon");
        addCard(Zone.HAND, playerA, "Flight");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Flight", "Memnite");

        activateAbility(1, PhaseStep.BEGIN_COMBAT, playerA, "{T}: Gain control of target creature for as long as that creature is enchanted.", "Memnite");

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Unsummon", "Rootwater Matriarch");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Rootwater Matriarch", 0);
        assertPermanentCount(playerA, "Memnite", 1);
    }

    @Test
    public void testGainControlEnchantedTargetAndAuraIsDisenchanted() {

        addCard(Zone.BATTLEFIELD, playerA, "Rootwater Matriarch");
        addCard(Zone.BATTLEFIELD, playerA, "Island");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Memnite");
        addCard(Zone.HAND, playerA, "Disenchant");
        addCard(Zone.HAND, playerA, "Flight");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Flight", "Memnite");

        activateAbility(1, PhaseStep.BEGIN_COMBAT, playerA, "{T}: Gain control of target creature for as long as that creature is enchanted.", "Memnite");

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Disenchant", "Flight");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Rootwater Matriarch", 1);
        assertPermanentCount(playerB, "Memnite", 1);
    }
}
