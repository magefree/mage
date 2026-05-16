package org.mage.test.cards.single.soc;

import mage.abilities.keyword.FlyingAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class StridingShotcallerTest extends CardTestPlayerBase {

    @Test
    public void testPreparedRunThePlayScalesTargetsAndDraws() {
        setStrictChooseMode(true);
        skipInitShuffling();

        addCard(Zone.BATTLEFIELD, playerA, "Striding Shotcaller");
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears");
        addCard(Zone.BATTLEFIELD, playerA, "Tropical Island", 4);
        addCard(Zone.LIBRARY, playerA, "Island");

        attack(1, playerA, "Grizzly Bears", playerB);

        setChoice(playerA, "X=2");
        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Run the Play");
        addTarget(playerA, "Grizzly Bears^Striding Shotcaller");

        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPowerToughness(playerA, "Grizzly Bears", 3, 3);
        assertPowerToughness(playerA, "Striding Shotcaller", 1, 5);
        assertAbility(playerA, "Grizzly Bears", FlyingAbility.getInstance(), true);
        assertAbility(playerA, "Striding Shotcaller", FlyingAbility.getInstance(), true);
        assertHandCount(playerA, "Island", 1);
        assertGraveyardCount(playerA, "Run the Play", 0);
    }
}
