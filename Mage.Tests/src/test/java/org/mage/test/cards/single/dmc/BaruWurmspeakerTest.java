package org.mage.test.cards.single.dmc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class BaruWurmspeakerTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.b.BaruWurmspeaker Baru, Wurmspeaker} {2}{G}{G}
     * Legendary Creature — Human Druid
     * Wurms you control get +2/+2 and have trample.
     * {7}{G}, {T}: Create a 4/4 green Wurm creature token. This ability costs {X} less to activate, where X is the greatest power among Wurms you control.
     * 3/3
     */
    private static final String baru = "Baru, Wurmspeaker";

    @Test
    public void test_no_wurm() {
        addCard(Zone.BATTLEFIELD, playerA, baru);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 8);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{7}{G},");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertTapped(baru, true);
        assertPermanentCount(playerA, "Wurm Token", 1);
        assertTappedCount("Forest", true, 8);
    }

    @Test
    public void test_wurm() {
        addCard(Zone.BATTLEFIELD, playerA, baru);
        addCard(Zone.BATTLEFIELD, playerA, "Voracious Wurm"); // 2/2 Wurm
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{7}{G},");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertTapped(baru, true);
        assertPermanentCount(playerA, "Wurm Token", 1);
        assertTappedCount("Forest", true, 4);
    }
}
