package mage;

import mage.abilities.SpellAbility;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.ManaCostImpl;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.constants.ColoredManaSymbol;
import mage.constants.ManaType;
import mage.filter.FilterMana;
import mage.util.CardUtil;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;


/**
 * Custom unit tests for {@link Mana}.
 *
 * @author githubpoixen@github.com
 */
public class ManaTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void shouldNotAllowNullCopyConstructor() {
        // given
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage("The passed in mana can not be null");

        // when
        Mana nullMana = null;
        new Mana(nullMana);
    }

    @Test
    public void shouldCreateManaFromCopy() {
        // given
        Mana original = new Mana();
        original.increaseBlack();

        // when
        Mana copy = new Mana(original);

        // then
        assertEquals(0, copy.getGreen());
        assertEquals(0, copy.getRed());
        assertEquals(1, copy.getBlack());
        assertEquals(0, copy.getBlue());
        assertEquals(0, copy.getWhite());
    }

    @Test
    public void shouldCreateManaFromGreenColoredManaSymbol() {
        // given
        ColoredManaSymbol symbol = ColoredManaSymbol.G;

        // when
        Mana mana = new Mana(symbol);

        // then
        assertEquals(1, mana.getGreen());
        assertEquals(0, mana.getRed());
        assertEquals(0, mana.getBlack());
        assertEquals(0, mana.getBlue());
        assertEquals(0, mana.getWhite());
    }

    @Test
    public void shouldCreateManaFromRedColoredManaSymbol() {
        // given
        ColoredManaSymbol symbol = ColoredManaSymbol.R;

        // when
        Mana mana = new Mana(symbol);

        // then
        assertEquals(0, mana.getGreen());
        assertEquals(1, mana.getRed());
        assertEquals(0, mana.getBlack());
        assertEquals(0, mana.getBlue());
        assertEquals(0, mana.getWhite());
    }

    @Test
    public void shouldCreateManaFromBlackColoredManaSymbol() {
        // given
        ColoredManaSymbol symbol = ColoredManaSymbol.B;

        // when
        Mana mana = new Mana(symbol);

        // then
        assertEquals(0, mana.getGreen());
        assertEquals(0, mana.getRed());
        assertEquals(1, mana.getBlack());
        assertEquals(0, mana.getBlue());
        assertEquals(0, mana.getWhite());
    }

    @Test
    public void shouldCreateManaFromBlueColoredManaSymbol() {
        // given
        ColoredManaSymbol symbol = ColoredManaSymbol.U;

        // when
        Mana mana = new Mana(symbol);

        // then
        assertEquals(0, mana.getGreen());
        assertEquals(0, mana.getRed());
        assertEquals(0, mana.getBlack());
        assertEquals(1, mana.getBlue());
        assertEquals(0, mana.getWhite());
    }

    @Test
    public void shouldCreateManaFromWhiteColoredManaSymbol() {
        // given
        ColoredManaSymbol symbol = ColoredManaSymbol.W;

        // when
        Mana mana = new Mana(symbol);

        // then
        assertEquals(0, mana.getGreen());
        assertEquals(0, mana.getRed());
        assertEquals(0, mana.getBlack());
        assertEquals(0, mana.getBlue());
        assertEquals(1, mana.getWhite());
    }

    @Test
    public void shouldNotCreateManaFromNullColoredManaSymbol() {
        // given
        ColoredManaSymbol nullSymbol = null;

        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage("The passed in ColoredManaSymbol can not be null");

        // when
        new Mana(nullSymbol);
    }

    @Test
    public void shouldCreateManaFromIntegers() {

        // when
        Mana mana = new Mana(4, 3, 5, 1, 2, 6, 7, 8);

        // then
        assertEquals(1, mana.getRed());
        assertEquals(2, mana.getGreen());
        assertEquals(3, mana.getBlue());
        assertEquals(4, mana.getWhite());
        assertEquals(5, mana.getBlack());
        assertEquals(6, mana.getGeneric());
        assertEquals(7, mana.getAny());
        assertEquals(8, mana.getColorless());
    }

    @Test
    public void shouldNotAllowNegativeIntegers() {
        // given

        // when
        Mana mana = new Mana(4, 3, 5, -1, 2, 6, 7, 0);

        // then
        assertEquals(0, mana.getRed());
    }

    @Test
    public void shouldCreateRedMana() {

        // when
        Mana mana = Mana.RedMana(1);

        // then
        assertEquals(1, mana.getRed());
    }

    @Test
    public void shouldCreateGreenMana() {

        // when
        Mana mana = Mana.GreenMana(1);

        // then
        assertEquals(1, mana.getGreen());
    }

    @Test
    public void shouldCreateBlueMana() {

        // when
        Mana mana = Mana.BlueMana(1);

        // then
        assertEquals(1, mana.getBlue());
    }

    @Test
    public void shouldCreateWhiteMana() {

        // when
        Mana mana = Mana.WhiteMana(1);

        // then
        assertEquals(1, mana.getWhite());
    }

    @Test
    public void shouldCreateBlackMana() {

        // when
        Mana mana = Mana.BlackMana(1);

        // then
        assertEquals(1, mana.getBlack());
    }

    @Test
    public void shouldCreateGenericMana() {

        // when
        Mana mana = Mana.GenericMana(1);

        // then
        assertEquals(1, mana.getGeneric());
    }

    @Test
    public void shouldCreateColorlessMana() {

        // when
        Mana mana = Mana.ColorlessMana(1);

        // then
        assertEquals(1, mana.getColorless());
    }

    @Test
    public void shouldNotAllowNegativeRedMana() {
        // given

        // when
        Mana mana = Mana.RedMana(-1);

        //then
        assertEquals(0, mana.getRed());
    }

    @Test
    public void shouldNotAllowNegativeGreenMana() {
        // given

        // when
        Mana mana = Mana.GreenMana(-1);

        //then
        assertEquals(0, mana.getGreen());
    }

    @Test
    public void shouldNotAllowNegativeBlueMana() {
        // given

        // when
        Mana mana = Mana.BlueMana(-1);

        //then
        assertEquals(0, mana.getBlue());
    }

    @Test
    public void shouldNotAllowNegativeWhiteMana() {
        // given

        // when
        Mana mana = Mana.WhiteMana(-1);

        //then
        assertEquals(0, mana.getWhite());
    }

    @Test
    public void shouldNotAllowNegativeBlackMana() {
        // given

        // when
        Mana mana = Mana.BlackMana(-1);

        //then
        assertEquals(0, mana.getBlack());
    }

    @Test
    public void shouldNotAllowNegativeColorlessMana() {
        // given

        // when
        Mana mana = Mana.GenericMana(-1);

        //then
        assertEquals(0, mana.getGeneric());
    }

    @Test
    public void shouldAddMana() {
        // given
        Mana thisMana = new Mana(1, 2, 3, 4, 5, 6, 7, 0);
        Mana thatMana = new Mana(1, 2, 3, 4, 5, 6, 7, 0);

        // when
        thisMana.add(thatMana);

        // then
        assertEquals(2, thisMana.getWhite());
        assertEquals(4, thisMana.getBlue());
        assertEquals(6, thisMana.getBlack());
        assertEquals(8, thisMana.getRed());
        assertEquals(10, thisMana.getGreen());
        assertEquals(12, thisMana.getGeneric());
        assertEquals(14, thisMana.getAny());
    }

    @Test
    public void shouldIncreaseRedMana() {
        // given
        Mana mana = new Mana();

        // when
        mana.increaseRed();

        // then
        assertEquals(1, mana.getRed());
    }

    @Test
    public void shouldIncreaseGreenMana() {
        // given
        Mana mana = new Mana();

        // when
        mana.increaseGreen();

        // then
        assertEquals(1, mana.getGreen());
    }

    @Test
    public void shouldIncreaseBlueMana() {
        // given
        Mana mana = new Mana();

        // when
        mana.increaseBlue();

        // then
        assertEquals(1, mana.getBlue());
    }

    @Test
    public void shouldIncreaseWhiteMana() {
        // given
        Mana mana = new Mana();

        // when
        mana.increaseWhite();

        // then
        assertEquals(1, mana.getWhite());
    }

    @Test
    public void shouldIncreaseBlackMana() {
        // given
        Mana mana = new Mana();

        // when
        mana.increaseBlack();

        // then
        assertEquals(1, mana.getBlack());
    }

    @Test
    public void shouldIncreaseColorlessMana() {
        // given
        Mana mana = new Mana();

        // when
        mana.increaseGeneric();

        // then
        assertEquals(1, mana.getGeneric());
    }

    @Test
    public void shouldSubtractMana() {
        // given
        Mana thisMana = new Mana(2, 2, 2, 2, 2, 2, 2, 2);
        Mana thatMana = new Mana(1, 1, 1, 1, 1, 1, 1, 1);

        // when
        thisMana.subtract(thatMana);

        // then
        assertEquals(1, thisMana.getRed());
        assertEquals(1, thisMana.getGreen());
        assertEquals(1, thisMana.getBlue());
        assertEquals(1, thisMana.getWhite());
        assertEquals(1, thisMana.getBlack());
        assertEquals(1, thisMana.getGeneric());
        assertEquals(1, thisMana.getAny());
        assertEquals(1, thisMana.getColorless());
    }

    @Test
    public void shouldSubtractCost() {
        // given
        Mana thisMana = new Mana(2, 2, 2, 2, 2, 2, 2, 0);
        Mana thatMana = new Mana(1, 1, 10, 10, 1, 1, 1, 0);

        // when
        thisMana.subtractCost(thatMana);

        // then
        assertEquals(-8, thisMana.getRed());
        assertEquals(1, thisMana.getGreen());
        assertEquals(1, thisMana.getBlue());
        assertEquals(1, thisMana.getWhite());
        assertEquals(-8, thisMana.getBlack());
        assertEquals(1, thisMana.getGeneric());
        assertEquals(1, thisMana.getAny());
    }

    @Test
    public void shouldUseExistingManaToPayColorless() {
        // given
        Mana available = new Mana();
        available.setRed(7);

        Mana cost = new Mana();
        cost.setRed(4);
        cost.setGeneric(2);

        // when
        available.subtractCost(cost);

        // then
        assertEquals(1, available.getRed());
    }

    @Test
    public void shouldThrowExceptionOnUnavailableColorless() {
        // given
        expectedException.expect(ArithmeticException.class);
        expectedException.expectMessage("Not enough mana to pay colorless");
        Mana available = new Mana();
        available.setRed(4);

        Mana cost = new Mana();
        cost.setRed(4);
        cost.setGeneric(2);

        // when
        available.subtractCost(cost);
    }

    @Test
    public void shouldReturnCount() {
        // given
        Mana mana = new Mana(4, 3, 5, 1, 2, 6, 7, 0);
        FilterMana filter = new FilterMana();
        filter.setBlack(true);

        // when
        int totalCount = mana.count();
        int coloredCount = mana.countColored();
        int filteredMana = mana.count(filter);

        // then
        assertEquals(28, totalCount);
        assertEquals(22, coloredCount);
        assertEquals(5, filteredMana);
    }

    @Test
    public void shouldReturnString() {
        // given
        Mana mana = new Mana(0, 3, 3, 1, 2, 6, 2, 0);

        // when
        String ret = mana.toString();

        // then
        assertEquals("{6}{U}{U}{U}{B}{B}{B}{R}{G}{G}{Any}{Any}", ret);
    }

    @Test
    public void shouldClearMana() {
        // given
        Mana mana = new Mana(1, 2, 3, 4, 5, 6, 7, 0);

        // when
        mana.clear();

        // then
        assertEquals(0, mana.getRed());
        assertEquals(0, mana.getGreen());
        assertEquals(0, mana.getBlue());
        assertEquals(0, mana.getWhite());
        assertEquals(0, mana.getBlack());
        assertEquals(0, mana.getGeneric());
        assertEquals(0, mana.getAny());
    }

    @Test
    public void shouldReturnCopy() {
        // given
        Mana mana = new Mana(1, 2, 3, 4, 5, 6, 7, 0);

        // when
        Mana copy = mana.copy();

        // then
        assertEquals(mana, copy); // are equal
        assertNotSame(mana, copy); // are not the same object
    }

    @Test
    public void shouldGetColorByColoredManaSymbol() {
        // given
        Mana mana = new Mana(1, 1, 1, 1, 1, 1, 1, 0);

        // when
        int redMana = mana.getColor(ColoredManaSymbol.R);
        int greenMana = mana.getColor(ColoredManaSymbol.G);
        int blueMana = mana.getColor(ColoredManaSymbol.U);
        int blackMana = mana.getColor(ColoredManaSymbol.B);
        int whiteMana = mana.getColor(ColoredManaSymbol.W);

        // then
        assertEquals(1, redMana);
        assertEquals(1, greenMana);
        assertEquals(1, blueMana);
        assertEquals(1, blackMana);
        assertEquals(1, whiteMana);
    }

    @Test
    public void shouldGetColorByManaType() {
        // given
        Mana mana = new Mana(1, 1, 1, 1, 1, 1, 1, 0);

        // when
        int redMana = mana.get(ManaType.RED);
        int greenMana = mana.get(ManaType.GREEN);
        int blueMana = mana.get(ManaType.BLUE);
        int blackMana = mana.get(ManaType.BLACK);
        int whiteMana = mana.get(ManaType.WHITE);
        int colorlessMana = mana.get(ManaType.COLORLESS);

        // then
        assertEquals(1, redMana);
        assertEquals(1, greenMana);
        assertEquals(1, blueMana);
        assertEquals(1, blackMana);
        assertEquals(1, whiteMana);
        assertEquals(1, colorlessMana);
    }

    @Test
    public void shouldSetManaFromType() {
        // given
        Mana mana = new Mana();

        // when
        mana.set(ManaType.BLACK, 3);
        mana.set(ManaType.BLUE, 4);
        mana.set(ManaType.RED, 5);
        mana.set(ManaType.GREEN, 6);
        mana.set(ManaType.WHITE, 7);
        mana.set(ManaType.COLORLESS, 8);

        // then
        assertEquals(3, mana.getBlack());
        assertEquals(4, mana.getBlue());
        assertEquals(5, mana.getRed());
        assertEquals(6, mana.getGreen());
        assertEquals(7, mana.getWhite());
        assertEquals(8, mana.getColorless());
    }

    @Test
    public void shouldSetToMana() {
        // given
        Mana mana = new Mana();
        Mana newMana = new Mana(1, 2, 3, 4, 5, 6, 7, 0);

        // when
        mana.setToMana(newMana);

        // then
        assertEquals(mana, newMana);
        assertNotSame(mana, newMana);
    }

    @Test
    public void shouldHaveEqualManaValue() {
        // given
        Mana mana = new Mana(1, 2, 3, 4, 5, 6, 7, 0);
        Mana newMana = new Mana(1, 2, 3, 4, 5, 6, 7, 0);

        // when
        boolean equalMana = mana.equalManaValue(newMana);

        // then
        assertTrue(equalMana);
    }

    @Test
    public void shouldGetDifferentColors() {
        // given
        Mana mana = new Mana();
        mana.setRed(3);
        mana.setGreen(2);

        // when
        int colors = mana.getDifferentColors();

        // then
        assertEquals(2, colors);
    }

    @Test
    public void shouldNotSetManaLessThanZero() {
        // given
        Mana mana = new Mana();

        // when
        mana.setRed(-4);
        mana.setGreen(-4);
        mana.setBlue(-4);
        mana.setWhite(-4);
        mana.setBlack(-4);
        mana.setGeneric(-4);
        mana.setAny(-4);

        // then
        assertEquals(0, mana.getRed());
        assertEquals(0, mana.getGreen());
        assertEquals(0, mana.getBlue());
        assertEquals(0, mana.getWhite());
        assertEquals(0, mana.getBlack());
        assertEquals(0, mana.getGeneric());
        assertEquals(0, mana.getAny());
    }

    @Test
    public void shouldNotOverflow() {
        // given
        Mana mana = new Mana();

        // when
        mana.setRed(Integer.MAX_VALUE);
        mana.increaseRed();
        mana.setGreen(Integer.MAX_VALUE);
        mana.increaseGreen();
        mana.setBlue(Integer.MAX_VALUE);
        mana.increaseBlue();
        mana.setWhite(Integer.MAX_VALUE);
        mana.increaseWhite();
        mana.setBlack(Integer.MAX_VALUE);
        mana.increaseBlack();
        mana.setGeneric(Integer.MAX_VALUE);
        mana.increaseGeneric();
        mana.setAny(Integer.MAX_VALUE);

        // then
        assertEquals(Integer.MAX_VALUE, mana.getRed());
        assertEquals(Integer.MAX_VALUE, mana.getGreen());
        assertEquals(Integer.MAX_VALUE, mana.getBlue());
        assertEquals(Integer.MAX_VALUE, mana.getWhite());
        assertEquals(Integer.MAX_VALUE, mana.getBlack());
        assertEquals(Integer.MAX_VALUE, mana.getGeneric());
        assertEquals(Integer.MAX_VALUE, mana.getAny());
    }

    /**
     * Mana.enough is used to check if a spell can be cast with an given amount
     * of avalable mana
     */
    @Test
    public void testManaEnough() {
        assertAvailableManaEnough("{G}", 1, "", true);
        assertAvailableManaEnough("{G}", 0, "{G}", true);
        assertAvailableManaEnough("{R}", 0, "{G}", false);
        assertAvailableManaEnough("{B}", 0, "{G}", false);
        assertAvailableManaEnough("{U}", 0, "{G}", false);
        assertAvailableManaEnough("{W}", 0, "{G}", false);
        assertAvailableManaEnough("{W}", 0, "{C}", false);

        assertAvailableManaEnough("{R}", 1, "", true);
        assertAvailableManaEnough("{R}", 0, "{R}", true);
        assertAvailableManaEnough("{G}", 0, "{R}", false);
        assertAvailableManaEnough("{B}", 0, "{R}", false);
        assertAvailableManaEnough("{U}", 0, "{R}", false);
        assertAvailableManaEnough("{W}", 0, "{R}", false);

        assertAvailableManaEnough("{U}{B}{W}{G}{R}", 4, "{R}", true);
        assertAvailableManaEnough("{U}{B}{W}{G}{R}", 3, "{R}{B}", true);

        assertAvailableManaEnough("{U}{U}{U}{G}{G}{2}", 2, "{U}{U}{G}{R}{B}", true);

        assertAvailableManaEnough("{2}{U}{U}", 0, "{U}{U}{U}{U}", true);
        assertAvailableManaEnough("{2}{U}{U}", 0, "{4}", false);
        assertAvailableManaEnough("{2}{U}{U}", 0, "{B}{B}{4}", false);

        assertAvailableManaEnough("{G}",    0, "{G/W}", true);
        assertAvailableManaEnough("{G}{W}", 0, "{G/W}{G/W}", true);
        assertAvailableManaEnough("{W}{W}", 0, "{G/W}{G/W}", true);
        assertAvailableManaEnough("{G}{G}", 0, "{G/W}{G/W}", true);

        assertAvailableManaEnough("{C}", 1, "", false);
        assertAvailableManaEnough("{C}", 0, "{C}", true);
        assertAvailableManaEnough("{C}", 0, "{G}", false);
        assertAvailableManaEnough("{C}", 0, "{R}", false);
        assertAvailableManaEnough("{C}", 0, "{B}", false);
        assertAvailableManaEnough("{C}", 0, "{W}", false);
        assertAvailableManaEnough("{C}", 0, "{U}", false);
    }

    /**
     * Mana.enough is used to check if a spell can be cast with an given amount
     * of avalable mana
     */
    @Test
    public void testManaReduction() {
        // cost - reduction - rest
        assertManaReduction("{G}{G}",       "{G}", "{G}");
        assertManaReduction("{1}{G}{G}",    "{G}", "{1}{G}");
        assertManaReduction("{B}{B}",       "{B}", "{B}");
        assertManaReduction("{1}{B}{B}",    "{B}", "{1}{B}");
        assertManaReduction("{W}{W}",       "{W}", "{W}");
        assertManaReduction("{1}{W}{W}",    "{W}", "{1}{W}");
        assertManaReduction("{U}{U}",       "{U}", "{U}");
        assertManaReduction("{1}{U}{U}",    "{U}", "{1}{U}");
        assertManaReduction("{R}{R}",       "{R}", "{R}");
        assertManaReduction("{1}{R}{R}",    "{R}", "{1}{R}");

        assertManaReduction("{R}{G}{B}{U}{W}", "{R}{G}{B}{U}{W}", "{0}");

        // Hybrid Mana
        assertManaReduction("{2/B}{2/B}{2/B}", "{B}{B}", "{2/B}");
        assertManaReduction("{2/B}{2/B}{2/B}", "{B}{B}{B}", "{0}");
        assertManaReduction("{2/W}{2/W}{2/W}", "{W}{W}", "{2/W}");
        assertManaReduction("{2/W}{2/W}{2/W}", "{W}{W}{W}", "{0}");

        assertManaReduction("{G/B}{G/B}{G/B}", "{B}{G}{B}", "{0}");
    }

    /**
     * Mana.needed is used by the AI to know how much mana it needs in order to be able to play a card.
     *
     * TODO: How should generic and any be handled?
     */
    @Test
    public void manaNeededWorks() {
        testManaNeeded(
                new Mana(ManaType.COLORLESS, 1), // Available
                new Mana(ManaType.COLORLESS, 2), // Cost
                new Mana(ManaType.COLORLESS, 1)  // Needed
        );
        testManaNeeded(
                new Mana(ManaType.RED,      1), // Avaiable
                new Mana(ManaType.GENERIC,  1), // Cost
                new Mana()                           // Needed
        );
        testManaNeeded(
                new Mana(ManaType.COLORLESS, 1), // Avaiable
                new Mana(ManaType.GENERIC,   1), // Cost
                new Mana()                            // Needed
        );
        testManaNeeded(
                new Mana(),                                                                         // Available
                new Mana(2, 0, 0, 0, 0, 2, 0, 0), // Cost
                new Mana(2, 0, 0, 0, 0, 2, 0, 0)  // Needed
        );
    }

    /**
     * Test that {@link Mana#getMoreValuableMana(Mana, Mana)} works as intended.
     * All calls to getMoreValuableMana are run twice, with the second time having the inputs flipped to make sure the same result is given.
     */
    @Test
    public void moreValuableManaTest() {
        final Mana anyMana        = Mana.AnyMana(1);
        final Mana genericMana    = Mana.GenericMana(1);
        final Mana colorlessMana  = Mana.ColorlessMana(1);

        final Mana whiteMana      = Mana.WhiteMana(1);
        final Mana blueMana       = Mana.BlueMana(1);
        final Mana blackMana      = Mana.BlackMana(1);
        final Mana redMana        = Mana.RedMana(1);
        final Mana greenMana      = Mana.GreenMana(1);

        final List<Mana> coloredManas = Arrays.asList(whiteMana, blueMana, blackMana, redMana, greenMana);

        // 1. A color of WUBURG is not more valuable than any other
        for (Mana coloredMana1 : coloredManas) {
            for (Mana coloredMana2 : coloredManas) {
                assertNull(coloredMana1 + " and " + coloredMana2 + " should not be comparable.", Mana.getMoreValuableMana(coloredMana1, coloredMana2));
                assertNull(coloredMana1 + " and " + coloredMana2 + " should not be comparable.", Mana.getMoreValuableMana(coloredMana2, coloredMana1));
            }
        }

        // 2. Generic is less valuable than any other type of mana
        final List<Mana> nonGenericManas = Arrays.asList(whiteMana, blueMana, blackMana, redMana, greenMana, colorlessMana);
        for (Mana coloredMana : nonGenericManas) {
            assertEquals(coloredMana, Mana.getMoreValuableMana(coloredMana, genericMana));
            assertEquals(coloredMana, Mana.getMoreValuableMana(genericMana, coloredMana));
        }
        assertEquals(anyMana, Mana.getMoreValuableMana(genericMana, anyMana));
        assertEquals(anyMana, Mana.getMoreValuableMana(anyMana, genericMana));

        // 3. ANY mana is more valuable than generic or a specific color
        for (Mana coloredMana : coloredManas) {
            assertEquals(anyMana, Mana.getMoreValuableMana(coloredMana, anyMana));
            assertEquals(anyMana, Mana.getMoreValuableMana(anyMana, coloredMana));
        }

        // 4. Colorless mana is not comparable with colored mana or ANY mana
        for (Mana coloredMana : coloredManas) {
            assertNull(Mana.getMoreValuableMana(colorlessMana, coloredMana));
            assertNull(Mana.getMoreValuableMana(coloredMana, colorlessMana));
        }
        assertNull(Mana.getMoreValuableMana(anyMana, colorlessMana));
        assertNull(Mana.getMoreValuableMana(colorlessMana, anyMana));

        // 5. Mana is more valuable if it has more of any type of mana but not less of any type (other than generic)
        final List<Mana> allManas = Arrays.asList(whiteMana, blueMana, blackMana, redMana, greenMana, colorlessMana, genericMana, anyMana);
        for (Mana specificMana : nonGenericManas) {
            for (Mana toAddMana : allManas) {
                Mana manaToCompare = specificMana.copy();
                manaToCompare.add(toAddMana);
                manaToCompare.add(toAddMana);

                assertEquals(manaToCompare, Mana.getMoreValuableMana(specificMana, manaToCompare));
                assertEquals(manaToCompare, Mana.getMoreValuableMana(manaToCompare, specificMana));
            }
        }

        // 6. Greater amount of mana but no less of any kind other than generic
        final List<Mana> nonAnyManas = Arrays.asList(whiteMana, blueMana, blackMana, redMana, greenMana, colorlessMana, genericMana);
        Mana manaBase = new ManaCostsImpl<>("{1}{W}{U}{B}{R}{G}{C}").getMana();
        Mana manaToCompare = manaBase.copy();

        // To avoid the copying that goes with it, manaToCompare is edited in place and always
        // reset back to its base state at the end of each outer loop.
        for (Mana manaToAddTwice : nonAnyManas) {
            manaToCompare.add(manaToAddTwice);
            manaToCompare.add(manaToAddTwice);
            for (Mana manaToSubtract : nonAnyManas) {
                manaToCompare.subtract(manaToSubtract);

                if (manaToSubtract == genericMana || manaToSubtract == manaToAddTwice) {
                    assertEquals(manaToCompare, Mana.getMoreValuableMana(manaBase, manaToCompare));
                    assertEquals(manaToCompare, Mana.getMoreValuableMana(manaToCompare, manaBase));
                } else if (manaToAddTwice == genericMana ){
                    assertEquals(manaBase, Mana.getMoreValuableMana(manaBase, manaToCompare));
                    assertEquals(manaBase, Mana.getMoreValuableMana(manaToCompare, manaBase));
                } else {
                    assertNull(Mana.getMoreValuableMana(manaBase, manaToCompare));
                    assertNull(Mana.getMoreValuableMana(manaToCompare, manaBase));
                }

                manaToCompare.add(manaToSubtract);
            }
            manaToCompare.subtract(manaToAddTwice);
            manaToCompare.subtract(manaToAddTwice);
        }
    }

    /**
     * Checks if the mana needed calculations produces the expected needed mana amount.
     *
     * @param available         The mana currently available.
     * @param cost              The mana needed for a cost.
     * @param neededExpected    The mana expected to be required to pay the cost.
     */
    private void testManaNeeded(Mana available, Mana cost, Mana neededExpected) {
        Mana neededActual = cost.needed(available);
        Assert.assertTrue(
                "The mana needed to pay " + cost + " given " + available
                        + " should have been " + neededExpected + " but was calculate to be " + neededActual,
                neededActual.equalManaValue(neededExpected)
        );
    }

    /**
     * Checks if the given available Mana is enough to pay a given mana cost
     *
     * @param manaCostsToPay    The mana cost that needs to be paid.
     * @param availablyAny      The amount of generic mana available.
     * @param available         The colored and colorless mana available.
     * @param expected          boolean indicating if the available mana is expected to cover the mana cost.
     */
    private void assertAvailableManaEnough(String manaCostsToPay, int availablyAny, String available, boolean expected) {
        ManaCost unpaid = new ManaCostsImpl<>(manaCostsToPay);
        ManaCost costAvailable = new ManaCostsImpl<>(available);
        Mana manaAvailable = costAvailable.getMana();
        manaAvailable.setAny(availablyAny);
        if (expected) {
            Assert.assertTrue("The available Mana " + costAvailable.getText() + " should be enough to pay the costs " + unpaid.getText(), unpaid.getMana().enough(manaAvailable));
        } else {
            Assert.assertFalse("The available Mana " + costAvailable.getText() + " shouldn't be enough to pay the costs " + unpaid.getText(), unpaid.getMana().enough(manaAvailable));
        }
    }

    /**
     * Checks if a given mana reduction left the expected amount of mana costs
     *
     * @param manaCostsToPay    The mana cost before reductions are applied.
     * @param manaToReduce      The amount and types of many to reduced the cost by.
     * @param restMana          The expected amount of mana left
     */
    private void assertManaReduction(String manaCostsToPay, String manaToReduce, String restMana) {
        SpellAbility spellAbility = new SpellAbility(new ManaCostsImpl<>(manaCostsToPay), "Test");
        CardUtil.adjustCost(spellAbility, new ManaCostsImpl<>(manaToReduce), true);
        Assert.assertEquals(
                "The mana cost to pay " + manaCostsToPay + " reduced by " + manaToReduce +
                        " should left " + restMana + " but the rest was " + spellAbility.getManaCostsToPay(),
                spellAbility.getManaCostsToPay().getText(),
                restMana
        );
    }

}
