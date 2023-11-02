package org.mage.test.cards.single.vis;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class MatopiGolemTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.m.MatopiGolem} <br>
     * Matopi Golem {5} <br>
     * Artifact Creature — Golem <br>
     * {1}: Regenerate Matopi Golem. When it regenerates this way, put a -1/-1 counter on it. <br>
     * 3/3
     */
    private static final String golem = "Matopi Golem";

    @Test
    public void test_ReflexiveOnRegenerate() {
        setStrictChooseMode(true);
        addCard(Zone.BATTLEFIELD, playerA, golem);
        addCard(Zone.HAND, playerA, "Doom Blade");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);

        activateAbility(1, PhaseStep.UPKEEP, playerA, "{1}: Regenerate");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Doom Blade", golem);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, golem, 1);
        assertTapped(golem, true);
        assertPowerToughness(playerA, golem, 3 - 1, 3 - 1);
    }
}
