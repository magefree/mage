package org.mage.test.cards.single.dis;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class MagewrightStoneTest extends CardTestPlayerBase {

    private static final String magewrightStone = "Magewright's Stone";


    @Test
    public void untapRosheenMeanderer() {
        String meanderer = "Rosheen Meanderer";
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.BATTLEFIELD, playerA, meanderer, 1, true);
        addCard(Zone.BATTLEFIELD, playerA, magewrightStone);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}, {T}: Untap target creature", meanderer);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertTapped(magewrightStone, true);
        assertTapped(meanderer, false);

    }
}
