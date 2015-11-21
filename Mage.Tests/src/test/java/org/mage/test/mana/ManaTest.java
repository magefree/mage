package org.mage.test.mana;

import mage.Mana;
import mage.constants.ColoredManaSymbol;
import mage.constants.ManaType;
import mage.filter.FilterMana;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;

/**
 * Custom unit tests for {link Mana}.
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
        expectedException.expectMessage("The passed in Mana can not be null");

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
        Mana mana = new Mana(1, 2, 3, 4, 5, 6, 7);

        // then
        assertEquals(1, mana.getRed());
        assertEquals(2, mana.getGreen());
        assertEquals(3, mana.getBlue());
        assertEquals(4, mana.getWhite());
        assertEquals(5, mana.getBlack());
        assertEquals(6, mana.getColorless());
        assertEquals(7, mana.getAny());
    }


    @Test
    public void shouldNotAllowNegativeIntegers() {
        // given

        // when
        Mana mana = new Mana(-1, 2, 3, 4, 5, 6, 7);

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
        Mana mana = Mana.ColorlessMana(-1);

        //then
        assertEquals(0, mana.getColorless());
    }


    @Test
    public void shouldAddMana() {
        // given
        Mana thisMana = new Mana(1, 2, 3, 4, 5, 6, 7);
        Mana thatMana = new Mana(1, 2, 3, 4, 5, 6, 7);


        // when
        thisMana.add(thatMana);

        // then
        assertEquals(2, thisMana.getRed());
        assertEquals(4, thisMana.getGreen());
        assertEquals(6, thisMana.getBlue());
        assertEquals(8, thisMana.getWhite());
        assertEquals(10, thisMana.getBlack());
        assertEquals(12, thisMana.getColorless());
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
        mana.increaseColorless();

        // then
        assertEquals(1, mana.getColorless());
    }

    @Test
    public void shouldSubtractMana() {
        // given
        Mana thisMana = new Mana(2, 2, 2, 2, 2, 2, 2);
        Mana thatMana = new Mana(1, 1, 1, 1, 1, 1, 1);

        // when
        thisMana.subtract(thatMana);

        // then
        assertEquals(1, thisMana.getRed());
        assertEquals(1, thisMana.getGreen());
        assertEquals(1, thisMana.getBlue());
        assertEquals(1, thisMana.getWhite());
        assertEquals(1, thisMana.getBlack());
        assertEquals(1, thisMana.getColorless());
        assertEquals(1, thisMana.getAny());
    }


    @Test
    public void shouldSubtractCost() {
        // given
        Mana thisMana = new Mana(2, 2, 2, 2, 2, 2, 2);
        Mana thatMana = new Mana(10, 1, 1, 1, 10, 1, 1);

        // when
        thisMana.subtractCost(thatMana);

        // then
        assertEquals(-8, thisMana.getRed());
        assertEquals(1, thisMana.getGreen());
        assertEquals(1, thisMana.getBlue());
        assertEquals(1, thisMana.getWhite());
        assertEquals(-8, thisMana.getBlack());
        assertEquals(1, thisMana.getColorless());
        assertEquals(1, thisMana.getAny());
    }


    @Test
    public void shouldUseExistingManaToPayColorless() {
        // given
        Mana available = new Mana();
        available.setRed(7);

        Mana cost = new Mana();
        cost.setRed(4);
        cost.setColorless(2);

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
        cost.setColorless(2);

        // when
        available.subtractCost(cost);
    }


    @Test
    public void shouldReturnCount() {
        // given
        Mana mana = new Mana(1, 2, 3, 4, 5, 6, 7);
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
        Mana mana = new Mana(1, 2, 3, 0, 3, 6, 2);

        // when
        String ret = mana.toString();

        // then
        assertEquals("{6}{R}{G}{G}{U}{U}{U}{B}{B}{B}{Any}{Any}", ret);
    }

    @Test
    public void shouldClearMana() {
        // given
        Mana mana = new Mana(1, 2, 3, 4, 5, 6, 7);

        // when
        mana.clear();

        // then
        assertEquals(0, mana.getRed());
        assertEquals(0, mana.getGreen());
        assertEquals(0, mana.getBlue());
        assertEquals(0, mana.getWhite());
        assertEquals(0, mana.getBlack());
        assertEquals(0, mana.getColorless());
        assertEquals(0, mana.getAny());
    }


    @Test
    public void shouldReturnCopy() {
        // given
        Mana mana = new Mana(1, 2, 3, 4, 5, 6, 7);

        // when
        Mana copy = mana.copy();

        // then
        assertEquals(mana, copy); // are equal
        assertFalse(mana == copy); // are not the same object
    }

    @Test
    public void shouldGetColorByColoredManaSymbol() {
        // given
        Mana mana = new Mana(1, 1, 1, 1, 1, 1, 1);

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
        Mana mana = new Mana(1, 1, 1, 1, 1, 1, 1);

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
        Mana newMana = new Mana(1, 2, 3, 4, 5, 6, 7);

        // when
        mana.setToMana(newMana);

        // then
        assertEquals(mana, newMana);
        assertFalse(mana == newMana);
    }


    @Test
    public void shouldHaveEqualManaValue() {
        // given
        Mana mana = new Mana(1, 2, 3, 4, 5, 6, 7);
        Mana newMana = new Mana(1, 2, 3, 4, 5, 6, 7);

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
        mana.setColorless(-4);
        mana.setAny(-4);

        // then
        assertEquals(0, mana.getRed());
        assertEquals(0, mana.getGreen());
        assertEquals(0, mana.getBlue());
        assertEquals(0, mana.getWhite());
        assertEquals(0, mana.getBlack());
        assertEquals(0, mana.getColorless());
        assertEquals(0, mana.getAny());
    }
}
