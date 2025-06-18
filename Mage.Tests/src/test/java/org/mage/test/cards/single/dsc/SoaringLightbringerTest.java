package org.mage.test.cards.single.dsc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestCommander4Players;

/**
 * @author notgreat
 */
public class SoaringLightbringerTest extends CardTestCommander4Players {

    @Test
    public void test_AttacksTwo() {
        addCard(Zone.BATTLEFIELD, playerA, "Soaring Lightbringer");
        addCard(Zone.BATTLEFIELD, playerA, "Memnite");

        attack(1, playerA, "Soaring Lightbringer", playerB);
        attack(1, playerA, "Memnite", playerD);

        setChoice(playerA, "Whenever"); // Order triggers

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertTappedCount("Glimmer Token", true, 2);
        assertLife(playerB, 20-4-1);
        assertLife(playerD, 20-1-1);
    }
}
