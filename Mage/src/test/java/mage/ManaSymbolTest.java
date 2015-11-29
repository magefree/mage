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
        ManaSymbol w = mage.ManaSymbol.W;

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


    @Test
    public void shouldCreateHybridWUManaSymbol() {
        // given

        // when
        ManaSymbol hybridWU = ManaSymbol.HYBRID_WU;

        // then
        assertEquals("{W/U}", hybridWU.toString());
        assertFalse(hybridWU.isGreen());
        assertFalse(hybridWU.isBlack());
        assertFalse(hybridWU.isRed());
        assertFalse(hybridWU.isSnow());
        assertFalse(hybridWU.isPhyrexian());
        assertFalse(hybridWU.isPrimary());
        assertFalse(hybridWU.isGeneric());
        assertFalse(hybridWU.isColorless());
        assertFalse(hybridWU.isMonocolored());

        assertTrue(hybridWU.isWhite());
        assertTrue(hybridWU.isBlue());
        assertTrue(hybridWU.isHybrid());
        assertTrue(hybridWU.isColored());

        assertEquals(ManaSymbol.W, hybridWU.getManaSymbol1());
        assertEquals(ManaSymbol.U, hybridWU.getManaSymbol2());
    }


    @Test
    public void shouldCreateHybridWBManaSymbol() {
        // given

        // when
        ManaSymbol hybridWB = ManaSymbol.HYBRID_WB;

        // then
        assertEquals("{W/B}", hybridWB.toString());
        assertFalse(hybridWB.isBlue());
        assertFalse(hybridWB.isGreen());
        assertFalse(hybridWB.isRed());
        assertFalse(hybridWB.isSnow());
        assertFalse(hybridWB.isPhyrexian());
        assertFalse(hybridWB.isPrimary());
        assertFalse(hybridWB.isGeneric());
        assertFalse(hybridWB.isColorless());
        assertFalse(hybridWB.isMonocolored());

        assertTrue(hybridWB.isWhite());
        assertTrue(hybridWB.isBlack());
        assertTrue(hybridWB.isHybrid());
        assertTrue(hybridWB.isColored());

        assertEquals(ManaSymbol.W, hybridWB.getManaSymbol1());
        assertEquals(ManaSymbol.B, hybridWB.getManaSymbol2());
    }


    @Test
    public void shouldCreateHybridUBManaSymbol() {
        // given

        // when
        ManaSymbol hybridUB = ManaSymbol.HYBRID_UB;

        // then
        assertEquals("{U/B}", hybridUB.toString());
        assertFalse(hybridUB.isWhite());
        assertFalse(hybridUB.isGreen());
        assertFalse(hybridUB.isRed());
        assertFalse(hybridUB.isSnow());
        assertFalse(hybridUB.isPhyrexian());
        assertFalse(hybridUB.isPrimary());
        assertFalse(hybridUB.isGeneric());
        assertFalse(hybridUB.isColorless());
        assertFalse(hybridUB.isMonocolored());

        assertTrue(hybridUB.isBlue());
        assertTrue(hybridUB.isBlack());
        assertTrue(hybridUB.isHybrid());
        assertTrue(hybridUB.isColored());

        assertEquals(ManaSymbol.U, hybridUB.getManaSymbol1());
        assertEquals(ManaSymbol.B, hybridUB.getManaSymbol2());
    }


    @Test
    public void shouldCreateHybridURManaSymbol() {
        // given

        // when
        ManaSymbol hybridUR = ManaSymbol.HYBRID_UR;

        // then
        assertEquals("{U/R}", hybridUR.toString());
        assertFalse(hybridUR.isBlack());
        assertFalse(hybridUR.isWhite());
        assertFalse(hybridUR.isGreen());
        assertFalse(hybridUR.isSnow());
        assertFalse(hybridUR.isPhyrexian());
        assertFalse(hybridUR.isPrimary());
        assertFalse(hybridUR.isGeneric());
        assertFalse(hybridUR.isColorless());
        assertFalse(hybridUR.isMonocolored());

        assertTrue(hybridUR.isBlue());
        assertTrue(hybridUR.isRed());
        assertTrue(hybridUR.isHybrid());
        assertTrue(hybridUR.isColored());

        assertEquals(ManaSymbol.U, hybridUR.getManaSymbol1());
        assertEquals(ManaSymbol.R, hybridUR.getManaSymbol2());
    }


    @Test
    public void shouldCreateHybridBRManaSymbol() {
        // given

        // when
        ManaSymbol hybridBR = ManaSymbol.HYBRID_BR;

        // then
        assertEquals("{B/R}", hybridBR.toString());
        assertFalse(hybridBR.isBlue());
        assertFalse(hybridBR.isWhite());
        assertFalse(hybridBR.isGreen());
        assertFalse(hybridBR.isSnow());
        assertFalse(hybridBR.isPhyrexian());
        assertFalse(hybridBR.isPrimary());
        assertFalse(hybridBR.isGeneric());
        assertFalse(hybridBR.isColorless());
        assertFalse(hybridBR.isMonocolored());

        assertTrue(hybridBR.isBlack());
        assertTrue(hybridBR.isRed());
        assertTrue(hybridBR.isHybrid());
        assertTrue(hybridBR.isColored());

        assertEquals(ManaSymbol.B, hybridBR.getManaSymbol1());
        assertEquals(ManaSymbol.R, hybridBR.getManaSymbol2());
    }


    @Test
    public void shouldCreateHybridBGManaSymbol() {
        // given

        // when
        ManaSymbol hybridBG = ManaSymbol.HYBRID_BG;

        // then
        assertEquals("{B/G}", hybridBG.toString());
        assertFalse(hybridBG.isRed());
        assertFalse(hybridBG.isBlue());
        assertFalse(hybridBG.isWhite());
        assertFalse(hybridBG.isSnow());
        assertFalse(hybridBG.isPhyrexian());
        assertFalse(hybridBG.isPrimary());
        assertFalse(hybridBG.isGeneric());
        assertFalse(hybridBG.isColorless());
        assertFalse(hybridBG.isMonocolored());

        assertTrue(hybridBG.isBlack());
        assertTrue(hybridBG.isGreen());
        assertTrue(hybridBG.isHybrid());
        assertTrue(hybridBG.isColored());

        assertEquals(ManaSymbol.B, hybridBG.getManaSymbol1());
        assertEquals(ManaSymbol.G, hybridBG.getManaSymbol2());
    }


    @Test
    public void shouldCreateHybridRGManaSymbol() {
        // given

        // when
        ManaSymbol hybridRG = ManaSymbol.HYBRID_RG;

        // then
        assertEquals("{R/G}", hybridRG.toString());
        assertFalse(hybridRG.isBlack());
        assertFalse(hybridRG.isBlue());
        assertFalse(hybridRG.isWhite());
        assertFalse(hybridRG.isSnow());
        assertFalse(hybridRG.isPhyrexian());
        assertFalse(hybridRG.isPrimary());
        assertFalse(hybridRG.isGeneric());
        assertFalse(hybridRG.isColorless());
        assertFalse(hybridRG.isMonocolored());

        assertTrue(hybridRG.isRed());
        assertTrue(hybridRG.isGreen());
        assertTrue(hybridRG.isHybrid());
        assertTrue(hybridRG.isColored());

        assertEquals(ManaSymbol.R, hybridRG.getManaSymbol1());
        assertEquals(ManaSymbol.G, hybridRG.getManaSymbol2());
    }


    @Test
    public void shouldCreateHybridRWManaSymbol() {
        // given

        // when
        ManaSymbol hybridRW = ManaSymbol.HYBRID_RW;

        // then
        assertEquals("{R/W}", hybridRW.toString());
        assertFalse(hybridRW.isGreen());
        assertFalse(hybridRW.isBlack());
        assertFalse(hybridRW.isBlue());
        assertFalse(hybridRW.isSnow());
        assertFalse(hybridRW.isPhyrexian());
        assertFalse(hybridRW.isPrimary());
        assertFalse(hybridRW.isGeneric());
        assertFalse(hybridRW.isColorless());
        assertFalse(hybridRW.isMonocolored());

        assertTrue(hybridRW.isRed());
        assertTrue(hybridRW.isWhite());
        assertTrue(hybridRW.isHybrid());
        assertTrue(hybridRW.isColored());

        assertEquals(ManaSymbol.R, hybridRW.getManaSymbol1());
        assertEquals(ManaSymbol.W, hybridRW.getManaSymbol2());
    }


    @Test
    public void shouldCreateHybridGWManaSymbol() {
        // given

        // when
        ManaSymbol hybridGW = ManaSymbol.HYBRID_GW;

        // then
        assertEquals("{G/W}", hybridGW.toString());
        assertFalse(hybridGW.isRed());
        assertFalse(hybridGW.isBlack());
        assertFalse(hybridGW.isBlue());
        assertFalse(hybridGW.isSnow());
        assertFalse(hybridGW.isPhyrexian());
        assertFalse(hybridGW.isPrimary());
        assertFalse(hybridGW.isGeneric());
        assertFalse(hybridGW.isColorless());
        assertFalse(hybridGW.isMonocolored());

        assertTrue(hybridGW.isGreen());
        assertTrue(hybridGW.isWhite());
        assertTrue(hybridGW.isHybrid());
        assertTrue(hybridGW.isColored());

        assertEquals(ManaSymbol.G, hybridGW.getManaSymbol1());
        assertEquals(ManaSymbol.W, hybridGW.getManaSymbol2());
    }


    @Test
    public void shouldCreateHybridGUManaSymbol() {
        // given

        // when
        ManaSymbol hybridGU = ManaSymbol.HYBRID_GU;

        // then
        assertEquals("{G/U}", hybridGU.toString());
        assertFalse(hybridGU.isWhite());
        assertFalse(hybridGU.isRed());
        assertFalse(hybridGU.isBlack());
        assertFalse(hybridGU.isSnow());
        assertFalse(hybridGU.isPhyrexian());
        assertFalse(hybridGU.isPrimary());
        assertFalse(hybridGU.isGeneric());
        assertFalse(hybridGU.isColorless());
        assertFalse(hybridGU.isMonocolored());

        assertTrue(hybridGU.isGreen());
        assertTrue(hybridGU.isBlue());
        assertTrue(hybridGU.isHybrid());
        assertTrue(hybridGU.isColored());

        assertEquals(ManaSymbol.G, hybridGU.getManaSymbol1());
        assertEquals(ManaSymbol.U, hybridGU.getManaSymbol2());
    }


    @Test
    public void shouldCreateWPhyrexianMana() {
        // given

        // when
        ManaSymbol phyW = ManaSymbol.PHYREXIAN_W;

        // then
        assertEquals("{W/P}", phyW.toString());
        assertFalse(phyW.isGreen());
        assertFalse(phyW.isBlue());
        assertFalse(phyW.isRed());
        assertFalse(phyW.isBlack());
        assertFalse(phyW.isPrimary());
        assertFalse(phyW.isGeneric());
        assertFalse(phyW.isColorless());
        assertFalse(phyW.isHybrid());
        assertFalse(phyW.isSnow());

        assertTrue(phyW.isWhite());
        assertTrue(phyW.isPhyrexian());
        assertTrue(phyW.isColored());
        assertTrue(phyW.isMonocolored());

        assertEquals(ManaSymbol.W, phyW.getManaSymbol1());
        assertEquals(null, phyW.getManaSymbol2());
    }


    @Test
    public void shouldCreateGPhyrexianMana() {
        // given

        // when
        ManaSymbol phyG = ManaSymbol.PHYREXIAN_G;

        // then
        assertEquals("{G/P}", phyG.toString());
        assertFalse(phyG.isWhite());
        assertFalse(phyG.isBlue());
        assertFalse(phyG.isRed());
        assertFalse(phyG.isBlack());
        assertFalse(phyG.isPrimary());
        assertFalse(phyG.isGeneric());
        assertFalse(phyG.isColorless());
        assertFalse(phyG.isHybrid());
        assertFalse(phyG.isSnow());

        assertTrue(phyG.isGreen());
        assertTrue(phyG.isPhyrexian());
        assertTrue(phyG.isColored());
        assertTrue(phyG.isMonocolored());

        assertEquals(ManaSymbol.G, phyG.getManaSymbol1());
        assertEquals(null, phyG.getManaSymbol2());
    }


    @Test
    public void shouldCreateRPhyrexianMana() {
        // given

        // when
        ManaSymbol phyR = ManaSymbol.PHYREXIAN_R;

        // then
        assertEquals("{R/P}", phyR.toString());
        assertFalse(phyR.isGreen());
        assertFalse(phyR.isWhite());
        assertFalse(phyR.isBlue());
        assertFalse(phyR.isBlack());
        assertFalse(phyR.isPrimary());
        assertFalse(phyR.isGeneric());
        assertFalse(phyR.isColorless());
        assertFalse(phyR.isHybrid());
        assertFalse(phyR.isSnow());

        assertTrue(phyR.isRed());
        assertTrue(phyR.isPhyrexian());
        assertTrue(phyR.isColored());
        assertTrue(phyR.isMonocolored());

        assertEquals(ManaSymbol.R, phyR.getManaSymbol1());
        assertEquals(null, phyR.getManaSymbol2());
    }


    @Test
    public void shouldCreateBPhyrexianMana() {
        // given

        // when
        ManaSymbol phyB = ManaSymbol.PHYREXIAN_B;

        // then
        assertEquals("{B/P}", phyB.toString());
        assertFalse(phyB.isRed());
        assertFalse(phyB.isGreen());
        assertFalse(phyB.isWhite());
        assertFalse(phyB.isBlue());
        assertFalse(phyB.isPrimary());
        assertFalse(phyB.isGeneric());
        assertFalse(phyB.isColorless());
        assertFalse(phyB.isHybrid());
        assertFalse(phyB.isSnow());

        assertTrue(phyB.isBlack());
        assertTrue(phyB.isPhyrexian());
        assertTrue(phyB.isColored());
        assertTrue(phyB.isMonocolored());

        assertEquals(ManaSymbol.B, phyB.getManaSymbol1());
        assertEquals(null, phyB.getManaSymbol2());
    }


    @Test
    public void shouldCreateUPhyrexianMana() {
        // given

        // when
        ManaSymbol phyU = ManaSymbol.PHYREXIAN_U;

        // then
        assertEquals("{U/P}", phyU.toString());
        assertFalse(phyU.isBlack());
        assertFalse(phyU.isRed());
        assertFalse(phyU.isGreen());
        assertFalse(phyU.isWhite());
        assertFalse(phyU.isPrimary());
        assertFalse(phyU.isGeneric());
        assertFalse(phyU.isColorless());
        assertFalse(phyU.isHybrid());
        assertFalse(phyU.isSnow());

        assertTrue(phyU.isBlue());
        assertTrue(phyU.isPhyrexian());
        assertTrue(phyU.isColored());
        assertTrue(phyU.isMonocolored());

        assertEquals(ManaSymbol.U, phyU.getManaSymbol1());
        assertEquals(null, phyU.getManaSymbol2());
    }


    @Test
    public void shouldCreateSnowMana() {
        // given

        // when
        ManaSymbol snow = ManaSymbol.SNOW;

        // then
        assertEquals("{S}", snow.toString());
        assertFalse(snow.isGreen());
        assertFalse(snow.isBlue());
        assertFalse(snow.isWhite());
        assertFalse(snow.isRed());
        assertFalse(snow.isBlack());
        assertFalse(snow.isPhyrexian());
        assertFalse(snow.isPrimary());
        assertFalse(snow.isGeneric());
        assertFalse(snow.isColorless());
        assertFalse(snow.isMonocolored());
        assertFalse(snow.isHybrid());
        assertFalse(snow.isColored());

        assertTrue(snow.isSnow());

        assertEquals(null, snow.getManaSymbol1());
        assertEquals(null, snow.getManaSymbol2());
    }
}