package org.mage.test.cards.flip;

import mage.abilities.mana.ManaOptions;
import mage.constants.ManaType;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

import static org.mage.test.utils.ManaOptionsTestUtils.assertDuplicatedManaOptions;
import static org.mage.test.utils.ManaOptionsTestUtils.assertManaOptions;

/**
 *
 * @author LevelX2
 */
public class SasayaOrochiAscendantTest extends CardTestPlayerBase {

    @Test
    public void testSasayasEssence() {
        addCard(Zone.HAND, playerA, "Plains", 7);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);

        // Reveal your hand: If you have seven or more land cards in your hand, flip Sasaya, Orochi Ascendant.
        // Sasaya's Essence: Legendary Enchantment
        // Whenever a land you control is tapped for mana, for each other land you control with the same name, add one mana of any type that land produced.
        addCard(Zone.BATTLEFIELD, playerA, "Sasaya, Orochi Ascendant", 1);
        // Mana pools don't empty as steps and phases end.
        addCard(Zone.HAND, playerA, "Upwelling", 1); // Enchantment {3}{G}

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Reveal your hand: If you have seven or more land cards in your hand, flip");

        activateManaAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "{T}: Add {G}");
        activateManaAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "{T}: Add {G}");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Upwelling");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Sasaya's Essence", 1);
        assertPermanentCount(playerA, "Upwelling", 1);

        assertManaPool(playerA, ManaType.GREEN, 2);

        ManaOptions manaOptions = playerA.getAvailableManaTest(currentGame);
        assertDuplicatedManaOptions(manaOptions);

        Assert.assertEquals("mana variations don't fit", 1, manaOptions.size());
        assertManaOptions("{G}{G}{G}{G}{G}", manaOptions);

    }

    @Test
    public void testSasayasEssence2() {
        addCard(Zone.HAND, playerA, "Plains", 7);
        addCard(Zone.BATTLEFIELD, playerA, "Brushland", 3);

        // Reveal your hand: If you have seven or more land cards in your hand, flip Sasaya, Orochi Ascendant.
        // Sasaya's Essence: Legendary Enchantment
        // Whenever a land you control is tapped for mana, for each other land you control with the same name, add one mana of any type that land produced.
        addCard(Zone.BATTLEFIELD, playerA, "Sasaya, Orochi Ascendant", 1);
        // Mana pools don't empty as steps and phases end.
        addCard(Zone.HAND, playerA, "Upwelling", 1); // Enchantment {3}{G}

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Reveal your hand: If you have seven or more land cards in your hand, flip");
        activateManaAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "{T}: Add {G}");
        activateManaAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "{T}: Add {G}");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Upwelling");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Sasaya's Essence", 1);
        assertPermanentCount(playerA, "Upwelling", 1);

        assertManaPool(playerA, ManaType.GREEN, 2);

        assertLife(playerA, 18);

        ManaOptions manaOptions = playerA.getAvailableManaTest(currentGame);
        assertDuplicatedManaOptions(manaOptions);

        Assert.assertEquals("mana variations don't fit", 3, manaOptions.size());
        assertManaOptions("{C}{C}{C}{G}{G}", manaOptions);
        assertManaOptions("{G}{G}{G}{G}{G}", manaOptions);
        assertManaOptions("{G}{G}{W}{W}{W}", manaOptions);

    }

    @Test
    public void testSasayasEssence3() {
        addCard(Zone.HAND, playerA, "Plains", 7);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);
        // {1}, {T}: Add {R}{G}.
        addCard(Zone.BATTLEFIELD, playerA, "Mossfire Valley", 2);

        // Reveal your hand: If you have seven or more land cards in your hand, flip Sasaya, Orochi Ascendant.
        // Sasaya's Essence: Legendary Enchantment
        // Whenever a land you control is tapped for mana, for each other land you control with the same name, add one mana of any type that land produced.
        addCard(Zone.BATTLEFIELD, playerA, "Sasaya, Orochi Ascendant", 1);
        // Mana pools don't empty as steps and phases end.
        addCard(Zone.HAND, playerA, "Upwelling", 1); // Enchantment {3}{G}

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Reveal your hand: If you have seven or more land cards in your hand, flip");
        activateManaAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "{T}: Add {G}");
        activateManaAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "{T}: Add {G}");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Upwelling");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Sasaya's Essence", 1);
        assertPermanentCount(playerA, "Upwelling", 1);

        assertManaPool(playerA, ManaType.GREEN, 2);

        assertLife(playerA, 20);

        ManaOptions manaOptions = playerA.getAvailableManaTest(currentGame);
        assertDuplicatedManaOptions(manaOptions);

        Assert.assertEquals("mana variations don't fit", 4, manaOptions.size());
        assertManaOptions("{R}{R}{R}{R}{G}{G}{G}{G}{G}", manaOptions);
        assertManaOptions("{R}{R}{R}{G}{G}{G}{G}{G}{G}", manaOptions);
        assertManaOptions("{R}{R}{G}{G}{G}{G}{G}{G}{G}", manaOptions);
        assertManaOptions("{R}{G}{G}{G}{G}{G}{G}{G}{G}", manaOptions);       
    }

}
