package mage;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Custom unit tests for {@link ManaSymbol}
 */
public class ManaSymbolTest {

    @Test
    public void shouldCreateWhiteManaSymbol() {
        // given

        // when
        ManaSymbol w = ManaSymbol.W;

        // then
        assertEquals("{W}", w.toString());
        assertFalse(w.isBlack());
        assertFalse(w.isBlue());
        assertFalse(w.isRed());
        assertFalse(w.isGreen());
        assertFalse(w.isColorless());
        assertFalse(w.isSnow());
        assertFalse(w.isPhyrexian());
        assertFalse(w.isGeneric());
        assertFalse(w.isHybrid());

        assertTrue(w.isColored());
        assertTrue(w.isWhite());
        assertTrue(w.isPrimary());
        assertTrue(w.isMonocolored());

        assertEquals(null, w.getManaSymbol1());
        assertEquals(null, w.getManaSymbol1());
    }

    @Test
     public void shouldCreateBlueManaSymbol() {
        // given

        // when
        ManaSymbol u = ManaSymbol.U;

        // then
        assertEquals("{U}", u.toString());
        assertFalse(u.isBlack());
        assertFalse(u.isWhite());
        assertFalse(u.isRed());
        assertFalse(u.isGreen());
        assertFalse(u.isColorless());
        assertFalse(u.isSnow());
        assertFalse(u.isPhyrexian());
        assertFalse(u.isGeneric());
        assertFalse(u.isHybrid());

        assertTrue(u.isColored());
        assertTrue(u.isBlue());
        assertTrue(u.isPrimary());
        assertTrue(u.isMonocolored());

        assertEquals(null, u.getManaSymbol1());
        assertEquals(null, u.getManaSymbol1());
    }


    @Test
    public void shouldCreateBlackManaSymbol() {
        // given

        // when
        ManaSymbol b = ManaSymbol.B;

        // then
        assertEquals("{B}", b.toString());
        assertFalse(b.isBlue());
        assertFalse(b.isWhite());
        assertFalse(b.isRed());
        assertFalse(b.isGreen());
        assertFalse(b.isColorless());
        assertFalse(b.isSnow());
        assertFalse(b.isPhyrexian());
        assertFalse(b.isGeneric());
        assertFalse(b.isHybrid());

        assertTrue(b.isColored());
        assertTrue(b.isBlack());
        assertTrue(b.isPrimary());
        assertTrue(b.isMonocolored());

        assertEquals(null, b.getManaSymbol1());
        assertEquals(null, b.getManaSymbol1());
    }

    @Test
    public void shouldCreateRedManaSymbol() {
        // given

        // when
        ManaSymbol r = ManaSymbol.R;

        // then
        assertEquals("{R}", r.toString());
        assertFalse(r.isBlue());
        assertFalse(r.isWhite());
        assertFalse(r.isBlack());
        assertFalse(r.isGreen());
        assertFalse(r.isColorless());
        assertFalse(r.isSnow());
        assertFalse(r.isPhyrexian());
        assertFalse(r.isGeneric());
        assertFalse(r.isHybrid());

        assertTrue(r.isColored());
        assertTrue(r.isRed());
        assertTrue(r.isPrimary());
        assertTrue(r.isMonocolored());

        assertEquals(null, r.getManaSymbol1());
        assertEquals(null, r.getManaSymbol1());
    }


    @Test
    public void shouldCreateGreenManaSymbol() {
        // given

        // when
        ManaSymbol g = ManaSymbol.G;

        // then
        assertEquals("{G}", g.toString());
        assertFalse(g.isBlue());
        assertFalse(g.isWhite());
        assertFalse(g.isBlack());
        assertFalse(g.isRed());
        assertFalse(g.isColorless());
        assertFalse(g.isSnow());
        assertFalse(g.isPhyrexian());
        assertFalse(g.isGeneric());
        assertFalse(g.isHybrid());

        assertTrue(g.isColored());
        assertTrue(g.isGreen());
        assertTrue(g.isPrimary());
        assertTrue(g.isMonocolored());

        assertEquals(null, g.getManaSymbol1());
        assertEquals(null, g.getManaSymbol1());
    }

}