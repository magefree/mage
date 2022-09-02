package org.mage.test.commander.piper;

import mage.abilities.mana.ManaOptions;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.utils.ManaOptionsTestUtils;

import static org.mage.test.utils.ManaOptionsTestUtils.assertDuplicatedManaOptions;

/**
 * @author TheElk801
 */
public class ThePrismaticPiperTest8 extends ThePrismaticPiperBaseTest {

    // Decklist:
    // 97 Mountain
    // 1 Island
    // 1 Forest
    // SB: 1 The Prismatic Piper
    // SB: 1 Kraum, Ludevic's Opus
    // SB: 1 Keruga, the Macrosage

    public ThePrismaticPiperTest8() {
        super(8);
    }

    @Test
    public void testColor() {
        setChoice(playerA, true); // Companion
        addCard(Zone.BATTLEFIELD, playerA, "Island", 8);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, piper, true);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Companion");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertColor(playerA, piper, "G", true);
        assertHandCount(playerA, "Keruga, the Macrosage", 1);
    }

    @Test
    public void testManaOptions() {
        setChoice(playerA, true); // Companion
        addCard(Zone.BATTLEFIELD, playerA, "Command Tower");
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        ManaOptions manaOptions = playerA.getAvailableManaTest(currentGame);
        assertDuplicatedManaOptions(manaOptions);
        Assert.assertEquals("mana variations don't fit", 3, manaOptions.size());
        ManaOptionsTestUtils.assertManaOptions("{U}", manaOptions);
        ManaOptionsTestUtils.assertManaOptions("{R}", manaOptions);
        ManaOptionsTestUtils.assertManaOptions("{G}", manaOptions);
    }
}
