package org.mage.test.commander.piper;

import mage.ObjectColor;
import mage.cards.Card;
import mage.constants.CommanderCardType;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author TheElk801
 */
public class ThePrismaticPiperTest7 extends ThePrismaticPiperBaseTest {

    // Decklist:
    // 97 Mountain
    // 1 Forest
    // SB: 2 The Prismatic Piper

    public ThePrismaticPiperTest7() {
        super(7);
    }

    @Test
    public void testColor() {
        execute();

        List<Card> commanders = new ArrayList<>(currentGame.getCommanderCardsFromCommandZone(playerA, CommanderCardType.ANY));
        Card piper1;
        Card piper2;
        if (commanders.get(0).getColor(currentGame).isRed()) {
            piper1 = commanders.get(0);
            piper2 = commanders.get(1);
        } else {
            piper1 = commanders.get(1);
            piper2 = commanders.get(0);
        }
        Assert.assertEquals("One Piper must be red", piper1.getColor(currentGame), ObjectColor.RED);
        Assert.assertEquals("One Piper must be green", piper2.getColor(currentGame), ObjectColor.GREEN);
    }
}
