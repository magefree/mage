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
        assertEquals(null, w.getManaSymbol2());
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
        assertEquals(null, u.getManaSymbol2());
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
        assertEquals(null, b.getManaSymbol2());
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
        assertEquals(null, r.getManaSymbol2());
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
        assertEquals(null, g.getManaSymbol2());
    }

    @Test
    public void shouldCreateGenericManaSymbol() {
        // given

        // when
        ManaSymbol x = ManaSymbol.X;

        // then
        assertEquals("{X}", x.toString());
        assertFalse(x.isBlue());
        assertFalse(x.isWhite());
        assertFalse(x.isBlack());
        assertFalse(x.isRed());
        assertFalse(x.isGreen());
        assertFalse(x.isColored());
        assertFalse(x.isSnow());
        assertFalse(x.isPhyrexian());
        assertFalse(x.isHybrid());
        assertFalse(x.isPrimary());
        assertFalse(x.isMonocolored());

        assertTrue(x.isGeneric());
        assertTrue(x.isColorless());

        assertEquals(null, x.getManaSymbol1());
        assertEquals(null, x.getManaSymbol2());
    }


    @Test
    public void shouldCreateNumericManaSymbol() {
        // given

        // when
        ManaSymbol numeric = ManaSymbol.NUMERIC;

        // then
        assertEquals("{N/A}", numeric.toString());
        assertFalse(numeric.isBlue());
        assertFalse(numeric.isWhite());
        assertFalse(numeric.isBlack());
        assertFalse(numeric.isRed());
        assertFalse(numeric.isGreen());
        assertFalse(numeric.isColored());
        assertFalse(numeric.isSnow());
        assertFalse(numeric.isPhyrexian());
        assertFalse(numeric.isHybrid());
        assertFalse(numeric.isPrimary());
        assertFalse(numeric.isMonocolored());

        assertTrue(numeric.isGeneric());
        assertTrue(numeric.isColorless());

        assertEquals(null, numeric.getManaSymbol1());
        assertEquals(null, numeric.getManaSymbol2());
    }


    @Test
    public void shouldCreateMonoColoredHybridWManaSymbol() {
        // given

        // when
        ManaSymbol monoHybridW = ManaSymbol.MONOCOLORED_HYBRID_W;

        // then
        assertEquals("{2/W}", monoHybridW.toString());
        assertFalse(monoHybridW.isBlue());
        assertFalse(monoHybridW.isBlack());
        assertFalse(monoHybridW.isRed());
        assertFalse(monoHybridW.isGreen());
        assertFalse(monoHybridW.isSnow());
        assertFalse(monoHybridW.isPhyrexian());
        assertFalse(monoHybridW.isPrimary());
        assertFalse(monoHybridW.isGeneric());
        assertFalse(monoHybridW.isColorless());

        assertTrue(monoHybridW.isMonocolored());
        assertTrue(monoHybridW.isHybrid());
        assertTrue(monoHybridW.isWhite());
        assertTrue(monoHybridW.isColored());

        assertEquals(ManaSymbol.W, monoHybridW.getManaSymbol1());
        assertEquals(null, monoHybridW.getManaSymbol2());
    }


    @Test
    public void shouldCreateMonoColoredHybridUManaSymbol() {
        // given

        // when
        ManaSymbol monoHybridU = ManaSymbol.MONOCOLORED_HYBRID_U;

        // then
        assertEquals("{2/U}", monoHybridU.toString());
        assertFalse(monoHybridU.isWhite());
        assertFalse(monoHybridU.isBlack());
        assertFalse(monoHybridU.isRed());
        assertFalse(monoHybridU.isGreen());
        assertFalse(monoHybridU.isSnow());
        assertFalse(monoHybridU.isPhyrexian());
        assertFalse(monoHybridU.isPrimary());
        assertFalse(monoHybridU.isGeneric());
        assertFalse(monoHybridU.isColorless());

        assertTrue(monoHybridU.isMonocolored());
        assertTrue(monoHybridU.isHybrid());
        assertTrue(monoHybridU.isBlue());
        assertTrue(monoHybridU.isColored());

        assertEquals(ManaSymbol.U, monoHybridU.getManaSymbol1());
        assertEquals(null, monoHybridU.getManaSymbol2());
    }


    @Test
    public void shouldCreateMonoColoredHybridBManaSymbol() {
        // given

        // when
        ManaSymbol monoHybridB = ManaSymbol.MONOCOLORED_HYBRID_B;

        // then
        assertEquals("{2/B}", monoHybridB.toString());
        assertFalse(monoHybridB.isBlue());
        assertFalse(monoHybridB.isWhite());
        assertFalse(monoHybridB.isRed());
        assertFalse(monoHybridB.isGreen());
        assertFalse(monoHybridB.isSnow());
        assertFalse(monoHybridB.isPhyrexian());
        assertFalse(monoHybridB.isPrimary());
        assertFalse(monoHybridB.isGeneric());
        assertFalse(monoHybridB.isColorless());

        assertTrue(monoHybridB.isMonocolored());
        assertTrue(monoHybridB.isHybrid());
        assertTrue(monoHybridB.isBlack());
        assertTrue(monoHybridB.isColored());

        assertEquals(ManaSymbol.B, monoHybridB.getManaSymbol1());
        assertEquals(null, monoHybridB.getManaSymbol2());
    }


    @Test
    public void shouldCreateMonoColoredHybridRManaSymbol() {
        // given

        // when
        ManaSymbol monoHybridR = ManaSymbol.MONOCOLORED_HYBRID_R;

        // then
        assertEquals("{2/R}", monoHybridR.toString());
        assertFalse(monoHybridR.isBlack());
        assertFalse(monoHybridR.isBlue());
        assertFalse(monoHybridR.isWhite());
        assertFalse(monoHybridR.isGreen());
        assertFalse(monoHybridR.isSnow());
        assertFalse(monoHybridR.isPhyrexian());
        assertFalse(monoHybridR.isPrimary());
        assertFalse(monoHybridR.isGeneric());
        assertFalse(monoHybridR.isColorless());

        assertTrue(monoHybridR.isMonocolored());
        assertTrue(monoHybridR.isHybrid());
        assertTrue(monoHybridR.isRed());
        assertTrue(monoHybridR.isColored());

        assertEquals(ManaSymbol.R, monoHybridR.getManaSymbol1());
        assertEquals(null, monoHybridR.getManaSymbol2());
    }


    @Test
    public void shouldCreateMonoColoredHybridGManaSymbol() {
        // given

        // when
        ManaSymbol monoHybridG = ManaSymbol.MONOCOLORED_HYBRID_G;

        // then
        assertEquals("{2/G}", monoHybridG.toString());
        assertFalse(monoHybridG.isBlack());
        assertFalse(monoHybridG.isBlue());
        assertFalse(monoHybridG.isWhite());
        assertFalse(monoHybridG.isRed());
        assertFalse(monoHybridG.isSnow());
        assertFalse(monoHybridG.isPhyrexian());
        assertFalse(monoHybridG.isPrimary());
        assertFalse(monoHybridG.isGeneric());
        assertFalse(monoHybridG.isColorless());

        assertTrue(monoHybridG.isMonocolored());
        assertTrue(monoHybridG.isHybrid());
        assertTrue(monoHybridG.isGreen());
        assertTrue(monoHybridG.isColored());

        assertEquals(ManaSymbol.G, monoHybridG.getManaSymbol1());
        assertEquals(null, monoHybridG.getManaSymbol2());
    }
}