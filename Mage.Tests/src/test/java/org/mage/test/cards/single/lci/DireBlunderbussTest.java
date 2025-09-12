package org.mage.test.cards.single.lci;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;


public class DireBlunderbussTest extends CardTestPlayerBase {

    /*
    Dire Blunderbuss
    Color Indicator: RedArtifact — Equipment

    Equipped creature gets +3/+0 and has “Whenever this creature attacks, you may sacrifice an artifact other than Dire Blunderbuss. When you do, this creature deals damage equal to its power to target creature.”

    Equip {1}
     */
    private static final String direBlunderBuss = "Dire Blunderbuss";

    @Test
    public void DireBlunderbussTest() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Zone.BATTLEFIELD, playerA, direBlunderBuss);
        addCard(Zone.BATTLEFIELD, playerA, "Balduvian Bears");
        addCard(Zone.BATTLEFIELD, playerB, "Balduvian Bears@bearsB");
        addCard(Zone.BATTLEFIELD, playerA, "Tormod's Crypt");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip", "Balduvian Bears");

        attack(1, playerA, "Balduvian Bears");
        setChoice(playerA, true);
        setChoice(playerA, "Tormod's Crypt");
        addTarget(playerA, "@bearsB");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerB, "Balduvian Bears", 1);
        assertLife(playerB, 20 - 2 - 3);
    }
}
