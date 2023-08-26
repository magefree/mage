package org.mage.test.mulligan;
import mage.game.mulligan.MulliganType;

public class SmoothedLondonMulliganTest extends LondonMulliganTest {
    @Override
    protected MulliganType getMullType() {
        return MulliganType.SMOOTHED_LONDON;
    }
    @Override
    protected int getCardsPerMull() {
        return 14;
    }
}
