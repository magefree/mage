package org.mage.test.deck;

import mage.cards.decks.Deck;
import mage.deck.FreeformUnlimitedCommander;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.MageTestPlayerBase;

public class FreeformUnlimitedCommanderTest extends MageTestPlayerBase {

    @Test
    public void test_construct_returnsFreeformPlusCommander() {
        // Act
        FreeformUnlimitedCommander deck = new FreeformUnlimitedCommander();

        // Assert
        Assert.assertEquals(FreeformUnlimitedCommander.class, deck.getClass());
    }

    @Test
    public void test_getDeckMinSize_returns0() {
        // Arrange
        FreeformUnlimitedCommander deck = new FreeformUnlimitedCommander();

        // Act
        int actual = deck.getDeckMinSize();

        // Assert
        Assert.assertEquals(0, actual);
    }

    @Test
    public void test_getSideboardMinSize_returns0() {
        // Arrange
        FreeformUnlimitedCommander deck = new FreeformUnlimitedCommander();

        // Act
        int actual = deck.getSideboardMinSize();

        // Assert
        Assert.assertEquals(0, actual);
    }

    @Test
    public void test_validate_returnsTrue() {
        // Arrange
        FreeformUnlimitedCommander deck = new FreeformUnlimitedCommander();
        Deck example = new Deck();

        // Act
        boolean actual = deck.validate(example);

        // Assert
        Assert.assertTrue(actual);
    }
}
