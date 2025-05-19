package org.mage.test.cards.abilities.oneshot;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class OneShotNonTargetTest extends CardTestPlayerBase {
    @Test
    public void YorionChooseAfterTriggerTest() {
        addCard(Zone.HAND, playerA, "Yorion, Sky Nomad");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 7);
        addCard(Zone.HAND, playerA, "Resolute Reinforcements");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Yorion, Sky Nomad");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, true);
        checkPermanentCount("Yorion on battlefield", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Yorion, Sky Nomad", 1);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Resolute Reinforcements");
        setChoice(playerA, "Resolute Reinforcements");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertTokenCount(playerA, "Soldier Token", 2);
        assertPermanentCount(playerA, "Yorion, Sky Nomad", 1);
        assertPermanentCount(playerA, "Resolute Reinforcements", 1);
        assertTappedCount("Plains", true, 7);
    }
}
