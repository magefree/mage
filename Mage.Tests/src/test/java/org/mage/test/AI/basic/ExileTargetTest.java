package org.mage.test.AI.basic;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestCommander4Players;

/**
 * @author JayDi85
 */
public class ExileTargetTest extends CardTestCommander4Players {

    // Player order: A -> D -> C -> B
    @Test
    public void test_chooseOpponentTargets() {
        // AI sometimes chooses own permanents in multiplayer game instead opponents

        // When Oblivion Ring enters the battlefield, exile another target nonland permanent.
        // When Oblivion Ring leaves the battlefield, return the exiled card to the battlefield under its owner’s control.
        addCard(Zone.HAND, playerA, "Oblivion Ring", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Apex Altisaur", 1); // 10/10
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1); // 2/2
        addCard(Zone.BATTLEFIELD, playerC, "Balduvian Bears", 1); // 2/2

        // must select opponent's Balduvian Bears
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Oblivion Ring");
        //addTarget(playerA, "Balduvian Bears"); // disable to activate AI target choose

        //setStrictChooseMode(true); // disable strict mode to activate AI for choosing
        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertPermanentCount(playerC, "Balduvian Bears", 0);
    }
}
