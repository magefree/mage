package org.mage.test.cards.single.avr;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * Created by goesta on 11/02/2017.
 */
public class ChampionOfLambholtTest extends CardTestPlayerBase {

    @Test
    public void testThatEffectIsTriggeredByControllingPlayer() {
        addCard(Zone.BATTLEFIELD, playerA, "Champion of Lambholt");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);
        addCard(Zone.HAND, playerA, "Increasing Devotion");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Increasing Devotion");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPowerToughness(playerA, "Champion of Lambholt", 6, 6);
    }

    @Test
    public void testThatEffectIsNotTriggeredByOpponentPlayer() {
        addCard(Zone.BATTLEFIELD, playerA, "Champion of Lambholt");
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 5);
        addCard(Zone.HAND, playerB, "Increasing Devotion");

        checkPlayableAbility("before", 1, PhaseStep.PRECOMBAT_MAIN, playerB, "Cast Increasing", false);
//        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Increasing Devotion");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPowerToughness(playerA, "Champion of Lambholt", 1, 1);
    }
}
