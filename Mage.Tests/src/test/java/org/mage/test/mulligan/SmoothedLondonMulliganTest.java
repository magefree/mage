package org.mage.test.mulligan;
import mage.game.mulligan.MulliganType;

public class SmoothedLondonMulliganTest extends LondonMulliganTest {
    protected MulliganType get_mulligantype() {
        return MulliganType.SMOOTHED_LONDON;
    }
    protected int cards_per_mull() {
        return 14;
    }
}
