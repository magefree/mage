
package org.mage.test.cards.triggers.delayed;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestCommander4Players;

import static org.mage.test.player.TestPlayer.CHOICE_SKIP;

/**
 *
 * @author jimga150
 */
public class DamagedBatchTests extends CardTestCommander4Players {

    @Test
    public void DamageBatchForOnePermanent2To1Test() {

        //Check that one creature being dealt damage by 2 sources at the same time = 1 trigger of DAMAGED_BATCH_FOR_ONE_PERMANENT

        addCard(Zone.BATTLEFIELD, playerA, "Donna Noble", 1); // Legendary Creature — Human 2/4 {3}{R}

        addCard(Zone.BATTLEFIELD, playerB, "Memnite", 1); // Artifact Creature — Construct 1/1 {0}
        addCard(Zone.BATTLEFIELD, playerB, "Expedition Envoy", 1); // Creature — Human Scout Ally 2/1 {W}

        attack(1, playerA, "Donna Noble", playerB);
        block(1, playerB, "Memnite", "Donna Noble");
        block(1, playerB, "Expedition Envoy", "Donna Noble");
        setChoice(playerA, CHOICE_SKIP); // Assign default damage

        //Target this player with Donna Noble
        addTarget(playerA, playerB);

        setStopAt(2, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertLife(playerA, currentGame.getStartingLife());
        assertLife(playerB, currentGame.getStartingLife() - 3);

    }

    @Test
    public void DamageBatchForOnePermanent2EventTest() {

        //Check that one creature being dealt damage at 2 different times (double strike in this case) = 2 triggers of DAMAGED_BATCH_FOR_ONE_PERMANENT

        addCard(Zone.BATTLEFIELD, playerA, "Donna Noble", 1); // Legendary Creature — Human 2/4 {3}{R}

        addCard(Zone.BATTLEFIELD, playerB, "Adorned Pouncer", 1); // Creature — Cat 1/1 {1}{W}

        attack(1, playerA, "Donna Noble", playerB);
        block(1, playerB, "Adorned Pouncer", "Donna Noble");

        //Target this player with Donna Noble
        addTarget(playerA, playerB);

        //Target this player with Donna Noble (second trigger)
        addTarget(playerA, playerB);

        setStopAt(2, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertLife(playerA, currentGame.getStartingLife());
        assertLife(playerB, currentGame.getStartingLife() - 2);

    }

}
