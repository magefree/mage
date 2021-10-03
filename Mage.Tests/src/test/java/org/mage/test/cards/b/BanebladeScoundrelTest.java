package org.mage.test.cards.b;

import mage.cards.b.BanebladeScoundrel;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.filter.Filter;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class BanebladeScoundrelTest extends CardTestPlayerBase {

    @Test
    public void test_blockingCreaturesAreDebuffed_blockersDie() {
        // Arrange
        String elf = "Elvish Warrior"; // 2/3
        addCard(Zone.BATTLEFIELD, playerA, BanebladeScoundrel.CARDNAME, 1);
        addCard(Zone.BATTLEFIELD, playerB, elf, 2);

        // Act
        attack(1, playerA, BanebladeScoundrel.CARDNAME);
        block(1, playerB, elf + ":0", BanebladeScoundrel.CARDNAME);
        block(1, playerB, elf + ":1", BanebladeScoundrel.CARDNAME);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        setStrictChooseMode(true);
        execute();

        // Assert
        assertGraveyardCount(playerA, BanebladeScoundrel.CARDNAME, 0);
        assertGraveyardCount(playerB, elf, 2);
    }

    @Test
    public void test_blockingCreaturesAreDebuffed_blockersSurvive() {
        // Arrange
        String golem = "Obsianus Golem"; // 4/6
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
        assertPowerToughness(playerB, golem, 3, 5);
    }
}
