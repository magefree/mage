package org.mage.test.mulligan;
import mage.game.mulligan.MulliganType;

public class SmoothedLondonMulliganTest extends LondonMulliganTest {
    protected MulliganType getMullType() {
        return MulliganType.SMOOTHED_LONDON;
    }
    protected int getCardsPerMull() {
        return 14;
    }
}
