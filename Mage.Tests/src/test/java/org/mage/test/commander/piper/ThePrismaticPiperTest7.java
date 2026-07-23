package org.mage.test.commander.piper;

import mage.ObjectColor;
import mage.abilities.mana.ManaOptions;
import mage.cards.Card;
import mage.constants.CommanderCardType;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mage.test.utils.ManaOptionsTestUtils;

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
        Assertions.assertEquals(piper1.getColor(currentGame), ObjectColor.RED, "One Piper must be red");
        Assertions.assertEquals(piper2.getColor(currentGame), ObjectColor.GREEN, "One Piper must be green");
    }

    @Test
    public void testManaOptions() {
        addCard(Zone.BATTLEFIELD, playerA, "Command Tower");
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        ManaOptions manaOptions = playerA.getAvailableManaTest(currentGame);

        Assertions.assertEquals(2, manaOptions.size(), "mana variations don't fit");
        ManaOptionsTestUtils.assertManaOptions("{R}", manaOptions);
        ManaOptionsTestUtils.assertManaOptions("{G}", manaOptions);
    }
}
