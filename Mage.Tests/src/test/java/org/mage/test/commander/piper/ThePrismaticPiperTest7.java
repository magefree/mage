package org.mage.test.commander.piper;

import mage.ObjectColor;
import mage.abilities.mana.ManaOptions;
import mage.cards.Card;
import mage.constants.CommanderCardType;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.utils.ManaOptionsTestUtils;

import java.util.ArrayList;
import java.util.List;

import static org.mage.test.utils.ManaOptionsTestUtils.assertDuplicatedManaOptions;

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

    @Test
    public void testManaOptions() {
        addCard(Zone.BATTLEFIELD, playerA, "Command Tower");
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        ManaOptions manaOptions = playerA.getAvailableManaTest(currentGame);
        assertDuplicatedManaOptions(manaOptions);
        Assert.assertEquals("mana variations don't fit", 2, manaOptions.size());
        ManaOptionsTestUtils.assertManaOptions("{R}", manaOptions);
        ManaOptionsTestUtils.assertManaOptions("{G}", manaOptions);
    }
}
