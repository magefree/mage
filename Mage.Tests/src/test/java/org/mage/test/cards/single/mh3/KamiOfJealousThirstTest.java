package org.mage.test.cards.single.mh3;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class KamiOfJealousThirstTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.k.KamiOfJealousThirst Kami of Jealous Thirst} {2}{B}
     * Creature — Spirit
     * Deathtouch
     * {4}{B}: Each opponent loses 2 life and you gain 2 life. This ability costs {4}{B} less to activate if you’ve drawn three or more cards this turn. Activate only once each turn.
     * 1/3
     */
    private static final String kami = "Kami of Jealous Thirst";

    @Test
    public void test_Activate_NoReduction() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, kami);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{4}{B}");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertTappedCount("Swamp", true, 5);
        assertLife(playerA, 20 + 2);
        assertLife(playerB, 20 - 2);
    }

    @Test
    public void test_Activate_Reduction() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, kami);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        addCard(Zone.HAND, playerA, "Concentrate");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Concentrate", true);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{4}{B}");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertTappedCount("Island", true, 4);
        assertLife(playerA, 20 + 2);
        assertLife(playerB, 20 - 2);
    }
}
