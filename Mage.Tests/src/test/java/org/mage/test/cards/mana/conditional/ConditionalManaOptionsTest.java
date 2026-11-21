package org.mage.test.cards.mana.conditional;

import mage.abilities.mana.ManaOptions;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

import static org.mage.test.utils.ManaOptionsTestUtils.assertManaOptions;

/**
 * Available mana calculation with restricted mana from several sources.
 * <p>
 * 106.6.
 * Some spells or abilities that produce mana restrict how that mana can be spent, have an additional effect
 * that affects the spell or ability that mana is spent on, or create a delayed triggered ability (see rule
 * 603.7a) that triggers when that mana is spent. This doesn't affect the mana's type.
 *
 * @author JayDi85
 */
public class ConditionalManaOptionsTest extends CardTestPlayerBase {

    @Test
    public void test_SameRestriction_SeveralSources() {
        // {T}: Add one mana of any color. Spend this mana only to cast a creature spell.
        addCard(Zone.BATTLEFIELD, playerA, "Ancient Ziggurat", 3);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        ManaOptions manaOptions = playerA.getAvailableManaTest(currentGame);
        Assert.assertEquals("mana variations don't fit: " + manaOptions, 1, manaOptions.size());
        assertManaOptions("{Any}{Any}{Any}[{CreatureCastManaCondition}]", manaOptions);
    }

    @Test
    public void test_SameRestriction_ManyCreatures() {
        // Creatures you control have vigilance and "{T}: Add one mana of any color. Spend this mana only to cast a creature spell."
        addCard(Zone.BATTLEFIELD, playerA, "Inga and Esika");
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears", 11); // bear 2/2

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        ManaOptions manaOptions = playerA.getAvailableManaTest(currentGame);
        Assert.assertEquals("mana variations don't fit: " + manaOptions, 1, manaOptions.size());
        assertManaOptions("{Any}{Any}{Any}{Any}{Any}{Any}{Any}{Any}{Any}{Any}{Any}{Any}[{CreatureCastManaCondition}]", manaOptions);
    }

    @Test
    public void test_SameRestriction_SourcesWithManaChoice() {
        // Frogs you control have "{T}: Add {G} or {U}. Spend this mana only to cast a creature spell."
        addCard(Zone.BATTLEFIELD, playerA, "Clement, the Worrywort"); // frog
        addCard(Zone.BATTLEFIELD, playerA, "Spore Frog", 2); // frog 1/1

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        ManaOptions manaOptions = playerA.getAvailableManaTest(currentGame);
        Assert.assertEquals("mana variations don't fit: " + manaOptions, 4, manaOptions.size());
        assertManaOptions("{G}{G}{G}[{CreatureCastManaCondition}]", manaOptions);
        assertManaOptions("{G}{G}{U}[{CreatureCastManaCondition}]", manaOptions);
        assertManaOptions("{G}{U}{U}[{CreatureCastManaCondition}]", manaOptions);
        assertManaOptions("{U}{U}{U}[{CreatureCastManaCondition}]", manaOptions);
    }

    @Test
    public void test_SameRestriction_WithBasicLands() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        // Creatures you control have vigilance and "{T}: Add one mana of any color. Spend this mana only to cast a creature spell."
        addCard(Zone.BATTLEFIELD, playerA, "Inga and Esika");
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears", 2); // bear 2/2
        addCard(Zone.BATTLEFIELD, playerA, "Forest");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        ManaOptions manaOptions = playerA.getAvailableManaTest(currentGame);
        Assert.assertEquals("mana variations don't fit: " + manaOptions, 2, manaOptions.size());
        assertManaOptions("{G}{G}", manaOptions); // for any spell
        assertManaOptions("{G}{G}{Any}{Any}{Any}[{CreatureCastManaCondition}]", manaOptions); // for creature spells
    }

    @Test
    public void test_SameRestriction_WithDualLands() {
        addCard(Zone.BATTLEFIELD, playerA, "Taiga"); // {R} or {G}
        // Creatures you control have vigilance and "{T}: Add one mana of any color. Spend this mana only to cast a creature spell."
        addCard(Zone.BATTLEFIELD, playerA, "Inga and Esika");
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears", 2); // bear 2/2
        addCard(Zone.BATTLEFIELD, playerA, "Savannah"); // {G} or {W}

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        ManaOptions manaOptions = playerA.getAvailableManaTest(currentGame);
        Assert.assertEquals("mana variations don't fit: " + manaOptions, 4 + 4, manaOptions.size());
        // for any spell
        assertManaOptions("{R}{G}", manaOptions);
        assertManaOptions("{R}{W}", manaOptions);
        assertManaOptions("{G}{G}", manaOptions);
        assertManaOptions("{G}{W}", manaOptions);
        // for creature spells
        assertManaOptions("{R}{G}{Any}{Any}{Any}[{CreatureCastManaCondition}]", manaOptions);
        assertManaOptions("{R}{W}{Any}{Any}{Any}[{CreatureCastManaCondition}]", manaOptions);
        assertManaOptions("{G}{G}{Any}{Any}{Any}[{CreatureCastManaCondition}]", manaOptions);
        assertManaOptions("{G}{W}{Any}{Any}{Any}[{CreatureCastManaCondition}]", manaOptions);
    }

    @Test
    public void test_DifferentRestrictions() {
        // Creatures you control have vigilance and "{T}: Add one mana of any color. Spend this mana only to cast a creature spell."
        addCard(Zone.BATTLEFIELD, playerA, "Inga and Esika");
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears", 2); // bear 2/2
        // {T}: Add one mana of any color. Spend this mana only to cast a multicolored spell.
        addCard(Zone.BATTLEFIELD, playerA, "Pillar of the Paruns", 2);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        ManaOptions manaOptions = playerA.getAvailableManaTest(currentGame);
        Assert.assertEquals("mana variations don't fit: " + manaOptions, 3, manaOptions.size());
        assertManaOptions("{Any}{Any}{Any}[{CreatureCastManaCondition}]", manaOptions); // creature spells
        assertManaOptions("{Any}{Any}[{SpellCastManaCondition}]", manaOptions); // multicolored spells
        assertManaOptions("{Any}{Any}{Any}{Any}{Any}[{SpellCastManaCondition}{CreatureCastManaCondition}]", manaOptions); // multicolored creature spells
    }

    @Test
    public void test_DifferentRestrictions_AnyOfConditions() {
        // "an Elemental spell or a Chandra planeswalker spell" mana with creature mana:
        // a creature spell that is not an Elemental can be paid by the creature mana only
        // {T}: Add one mana of any color. Spend this mana only to cast a creature spell.
        addCard(Zone.BATTLEFIELD, playerA, "Ancient Ziggurat");
        // {T}: Add {R}. Spend this mana only to cast an Elemental spell or a Chandra planeswalker spell.
        addCard(Zone.BATTLEFIELD, playerA, "Chandra's Embercat");
        //
        addCard(Zone.HAND, playerA, "Grizzly Bears"); // {1}{G}, bear
        addCard(Zone.HAND, playerA, "Fire Elemental"); // {3}{R}{R}, elemental

        checkPlayableAbility("one mana for a bear", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Grizzly Bears", false);
        checkPlayableAbility("two mana for an elemental", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Fire Elemental", false);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_SameRestrictionWithDifferentChoices() {
        // As Cavern of Souls enters, choose a creature type.
        // {T}: Add {C}.
        // {T}: Add one mana of any color. Spend this mana only to cast a creature spell of the chosen type, and that spell can't be countered.
        addCard(Zone.HAND, playerA, "Cavern of Souls", 2);

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cavern of Souls");
        setChoice(playerA, "Bear"); // first Cavern for bears

        playLand(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Cavern of Souls");
        setChoice(playerA, "Elf"); // second Cavern for elves

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        ManaOptions manaOptions = playerA.getAvailableManaTest(currentGame);
        Assert.assertEquals("mana variations don't fit: " + manaOptions, 4, manaOptions.size());
        assertManaOptions("{C}{C}", manaOptions); // any spell
        assertManaOptions("{C}{Any}[{CavernOfSoulsManaCondition}]", manaOptions); // bear or elf creature spell, one of it
        assertManaOptions("{Any}{Any}[{CavernOfSoulsManaCondition}{CavernOfSoulsManaCondition}]", manaOptions); // bear elf creature spell
    }
}
