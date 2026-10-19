package org.mage.test.cards.single.ylci;

import mage.constants.PhaseStep;
import mage.constants.Zone;

import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class LandloreNavigatorTest extends CardTestPlayerBase {

    @Test
    public void testAttackAndEarlierArtifactCount() {
        addCard(Zone.BATTLEFIELD, playerA, "Landlore Navigator");
        addCard(Zone.HAND, playerA, "Memnite");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Memnite");
        attack(1, playerA, "Landlore Navigator");
        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.CLEANUP);
        execute();

        assertPermanentCount(playerA, "Map Token", 1);
        assertPermanentCount(playerA, "Thieving Magpie", 1);
    }

    @Test
    public void testOneArtifactIsNotEnoughAndOpponentDoesNotCount() {
        addCard(Zone.BATTLEFIELD, playerA, "Landlore Navigator");
        addCard(Zone.HAND, playerB, "Memnite");
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Memnite");
        attack(3, playerA, "Landlore Navigator");
        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.CLEANUP);
        execute();

        assertPermanentCount(playerA, "Map Token", 1);
        assertPermanentCount(playerA, "Thieving Magpie", 0);
    }

}
