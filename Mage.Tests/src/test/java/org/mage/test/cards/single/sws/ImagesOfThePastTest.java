package org.mage.test.cards.single.sws;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */

public class ImagesOfThePastTest extends CardTestPlayerBase {

    @Test
    public void test_Playable() {

        // Images of the Past
        // Return up to two target creature cards from your graveyard to the battlefield, then exile those creatures.
        addCard(Zone.HAND, playerA, "Images of the Past"); // {G}{W}
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        //
        addCard(Zone.GRAVEYARD, playerA, "Balduvian Bears");
        addCard(Zone.GRAVEYARD, playerA, "Silvercoat Lion");
        addCard(Zone.GRAVEYARD, playerA, "Lightning Bolt");
        //
        // Healer of the Pride
        // Whenever another creature enters the battlefield under your control, you gain 2 life.
        addCard(Zone.BATTLEFIELD, playerA, "Healer of the Pride", 1);
        //
        // Syr Konrad, the Grim
        // Whenever another creature dies, or a creature card is put into a graveyard from anywhere other than
        // the battlefield, or a creature card leaves your graveyard, Syr Konrad, the Grim deals 1 damage to each opponent.
        addCard(Zone.BATTLEFIELD, playerA, "Syr Konrad, the Grim", 1);


        // activate and rise 2x dies triggers (adds 2x Spirit)
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Images of the Past", "Balduvian Bears^Silvercoat Lion");
        setChoice(playerA, "Whenever another creature dies", 2); // 2x triggers from Syr
        setChoice(playerA, "Whenever another creature enters"); // 2x triggers from Healer
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        //
        checkExileCount("after", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Balduvian Bears", 1);
        checkExileCount("after", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Silvercoat Lion", 1);
        checkExileCount("after", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", 0);
        checkGraveyardCount("after", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Balduvian Bears", 0);
        checkGraveyardCount("after", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Silvercoat Lion", 0);
        checkGraveyardCount("after", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", 1);
        //
        // trigger result to check enter battlefield (4x life from Healer)
        checkLife("after", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 20 + 4);
        // trigger result to check grave leave (2x damage from Syr)
        checkLife("after", 1, PhaseStep.PRECOMBAT_MAIN, playerB, 20 - 2);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }
}
