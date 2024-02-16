package org.mage.test.game.FreeformUnlimitedCommander;

import mage.constants.MultiplayerAttackOption;
import mage.constants.RangeOfInfluence;
import mage.game.FreeformUnlimitedCommander;
import mage.game.mulligan.LondonMulligan;
import mage.game.mulligan.Mulligan;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.MageTestPlayerBase;

public class FreeformUnlimitedCommanderTest extends MageTestPlayerBase {

    @Test
    public void test_construct_returnsFreeformPlusCommander() {
        // Arrange
        Mulligan mulligan = new LondonMulligan(1);
        int startLife = 40;
        int startHandSize = 7;

        // Assert
        FreeformUnlimitedCommander game = new FreeformUnlimitedCommander(
                MultiplayerAttackOption.MULTIPLE,
                RangeOfInfluence.ALL,
                mulligan,
                startLife,
                startHandSize
        );

        // Assert
        Assert.assertEquals(FreeformUnlimitedCommander.class, game.getClass());
    }
}
