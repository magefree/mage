package org.mage.test.serverside.cards.effects;

import mage.Constants;
import mage.filter.Filter;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author BetaSteward
 */
public class TestActivatedContinuousEffect extends CardTestPlayerBase {

    @Test
    public void testCard() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Mountain", 3);
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Captive Flame");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "White Knight");

        activateAbility(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "{R}:", "White Knight");

        setStopAt(1, Constants.PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertPermanentCount(playerA, "White Knight", 1);
        assertPowerToughness(playerA, "White Knight", 3, 2, Filter.ComparisonScope.Any);

    }

    @Test
    public void testCard2() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Mountain", 3);
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Captive Flame");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "White Knight");

        activateAbility(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "{R}:", "White Knight");
        activateAbility(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "{R}:", "White Knight");

        setStopAt(1, Constants.PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertPermanentCount(playerA, "White Knight", 1);
        assertPowerToughness(playerA, "White Knight", 4, 2, Filter.ComparisonScope.Any);

    }

}
