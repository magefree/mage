package org.mage.test.cards.b;

import mage.cards.b.BanebladeScoundrel;
import mage.cards.b.BaneclawMarauder;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class BaneclawMarauderTest extends CardTestPlayerBase {

    @Test
    public void test_blockingCreaturesAreDebuffed_blockersdie() {
        // Arrange
        String giant = "Highland Giant"; // 3/4
        String elf = "Elvish Warrior"; // 2/3
        addCard(Zone.BATTLEFIELD, playerA, BaneclawMarauder.CARDNAME, 1);
        addCard(Zone.BATTLEFIELD, playerB, giant, 1);
        addCard(Zone.BATTLEFIELD, playerB, elf, 1);

        // Act
        attack(1, playerA, BanebladeScoundrel.CARDNAME);
        block(1, playerB, giant, BanebladeScoundrel.CARDNAME);
        block(1, playerB, elf, BanebladeScoundrel.CARDNAME);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        setStrictChooseMode(true);
        execute();

        // Assert
        assertGraveyardCount(playerA, BanebladeScoundrel.CARDNAME, 0);
        assertGraveyardCount(playerB, giant, 1);
        assertGraveyardCount(playerB, elf, 1);
        assertLife(playerB, 18);
    }

    @Test
    public void test_blockingCreaturesAreDebuffed_blockersSurvive() {
        // Arrange
        String golem = "Hexplate Golem"; // 5/7
        addCard(Zone.BATTLEFIELD, playerA, BanebladeScoundrel.CARDNAME, 1);
        addCard(Zone.BATTLEFIELD, playerB, golem, 1);

        // Act
        attack(1, playerA, BanebladeScoundrel.CARDNAME);
        block(1, playerB, golem, BanebladeScoundrel.CARDNAME);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        setStrictChooseMode(true);
        execute();

        // Assert
        assertGraveyardCount(playerA, BanebladeScoundrel.CARDNAME, 1);
        assertGraveyardCount(playerB, golem, 0);
        assertPowerToughness(playerB, golem, 4, 6);
        assertLife(playerB, 20);
    }
}
