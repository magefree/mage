package org.mage.test.serverside.cards.effects;

import mage.constants.PhaseStep;
import mage.constants.Zone;
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
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        // {R}: Target creature gets +1/+0 until end of turn.
        addCard(Zone.BATTLEFIELD, playerA, "Captive Flame");
        addCard(Zone.BATTLEFIELD, playerA, "White Knight");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{R}:", "White Knight");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertPermanentCount(playerA, "White Knight", 1);
        assertPowerToughness(playerA, "White Knight", 3, 2, Filter.ComparisonScope.Any);

    }

    @Test
    public void testCard2() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        // {R}: Target creature gets +1/+0 until end of turn.
        addCard(Zone.BATTLEFIELD, playerA, "Captive Flame");
        addCard(Zone.BATTLEFIELD, playerA, "White Knight");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{R}:", "White Knight");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{R}:", "White Knight");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertPermanentCount(playerA, "White Knight", 1);
        assertPowerToughness(playerA, "White Knight", 4, 2, Filter.ComparisonScope.Any);

    }

}
