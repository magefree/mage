package org.mage.test.game.FreeformUnlimitedCommander;

import mage.game.FreeformUnlimitedCommanderMatch;
import mage.game.match.Match;
import mage.game.match.MatchOptions;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.MageTestPlayerBase;

public class FreeformUnlimitedCommanderMatchTest extends MageTestPlayerBase {

    @Test
    public void test_construct_returnsFreeformPlusCommanderMatch() {
        // Arrange
        MatchOptions options = new MatchOptions(
                "test name",
                "test match name",
                false,
                2
        );

        // Act
        Match match = new FreeformUnlimitedCommanderMatch(options);

        // Assert
        Assert.assertEquals(FreeformUnlimitedCommanderMatch.class, match.getClass());
    }

    @Test
    public void test_getName_returnsTestName() {
        // Arrange
        MatchOptions options = new MatchOptions(
                "test name",
                "test match name",
                false,
                2
        );
        Match match = new FreeformUnlimitedCommanderMatch(options);

        // Act
        String actual = match.getName();

        // Assert
        Assert.assertEquals("test name", actual);
    }

    @Test
    public void test_getOptions_returnsOriginalOptions() {
        // Arrange
        MatchOptions options = new MatchOptions(
                "test name",
                "test match name",
                false,
                2
        );
        Match match = new FreeformUnlimitedCommanderMatch(options);

        // Act
        MatchOptions actual = match.getOptions();

        // Assert
        Assert.assertEquals(options, actual);
    }
}
