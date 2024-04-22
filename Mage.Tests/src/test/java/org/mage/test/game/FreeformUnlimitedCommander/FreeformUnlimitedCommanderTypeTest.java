package org.mage.test.game.FreeformUnlimitedCommander;

import mage.game.FreeformUnlimitedCommanderType;
import mage.game.match.MatchType;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.MageTestPlayerBase;

public class FreeformUnlimitedCommanderTypeTest extends MageTestPlayerBase {

    @Test
    public void test_construct_returnsFreeformPlusCommanderType() {
        // Act
        MatchType gameType = new FreeformUnlimitedCommanderType();

        // Assert
        Assert.assertEquals(FreeformUnlimitedCommanderType.class, gameType.getClass());
    }

    @Test
    public void test_toString_returnsFreeformPlusCommander() {
        // Arrange
        MatchType gametype = new FreeformUnlimitedCommanderType();

        // Assert
        Assert.assertEquals("Freeform Unlimited Commander", gametype.toString());
    }

    @Test
    public void test_getName_returnsFreeformPlusCommander() {
        // Arrange
        MatchType gametype = new FreeformUnlimitedCommanderType();

        // Assert
        Assert.assertEquals("Freeform Unlimited Commander", gametype.getName());
    }

    @Test
    public void test_getMinPlayers_returns3() {
        // Arrange
        MatchType gametype = new FreeformUnlimitedCommanderType();

        // Assert
        Assert.assertEquals(2, gametype.getMinPlayers());
    }

    @Test
    public void test_getMaxPlayers_returns10() {
        // Arrange
        MatchType gametype = new FreeformUnlimitedCommanderType();

        // Assert
        Assert.assertEquals(10, gametype.getMaxPlayers());
    }

    @Test
    public void test_getNumTeams_returns0() {
        // Arrange
        MatchType gametype = new FreeformUnlimitedCommanderType();

        // Assert
        Assert.assertEquals(0, gametype.getNumTeams());
    }

    @Test
    public void test_getPlayersPerTeam_returns0() {
        // Arrange
        MatchType gametype = new FreeformUnlimitedCommanderType();

        // Assert
        Assert.assertEquals(0, gametype.getPlayersPerTeam());
    }

    @Test
    public void test_isUseRange_returnsTrue() {
        // Arrange
        MatchType gametype = new FreeformUnlimitedCommanderType();

        // Assert
        Assert.assertTrue(gametype.isUseRange());
    }

    @Test
    public void test_isUseAttackOption_returnsTrue() {
        // Arrange
        MatchType gametype = new FreeformUnlimitedCommanderType();

        // Assert
        Assert.assertTrue(gametype.isUseAttackOption());
    }

    @Test
    public void test_isSideboardingAllowed_returnsTrue() {
        // Arrange
        MatchType gametype = new FreeformUnlimitedCommanderType();

        // Assert
        Assert.assertTrue(gametype.isSideboardingAllowed());
    }
}
