package org.mage.test.cards.single.slx;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class HavengulLaboratoryTest extends CardTestPlayerBase {

    @Test
    public void test_LeavesAndTransform() {
        addCustomEffect_TargetDestroy(playerA);

        // Havengul Laboratory
        // At the beginning of your end step, if you sacrificed three or more Clues this turn, transform Havengul Laboratory.
        // Havengul Mystery
        // When this land transforms into Havengul Mystery, return target creature card from your graveyard to the battlefield.
        // When the creature put onto the battlefield with Havengul Mystery leaves the battlefield, transform Havengul Mystery.
        addCard(Zone.BATTLEFIELD, playerA, "Havengul Laboratory");
        addCard(Zone.GRAVEYARD, playerA, "Grizzly Bears");
        //
        // Draw a card. Investigate.
        addCard(Zone.HAND, playerA, "Deduce", 3); // instant {1}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3 * 2);
        //
        // Clue Token
        // {2}, Sacrifice this artifact: Draw a card.
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3 * 2);

        // prepare x3 clues
        checkPermanentCount("before", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Havengul Laboratory", 1);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Deduce");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Deduce");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Deduce");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        checkPermanentCount("on prepare", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Clue Token", 3);

        // use all clues and transform
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{2}, Sacrifice");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{2}, Sacrifice");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{2}, Sacrifice");
        addTarget(playerA, "Grizzly Bears"); // return to battlefield on transform
        waitStackResolved(1, PhaseStep.END_TURN);
        checkPermanentCount("on transform", 1, PhaseStep.END_TURN, playerA, "Havengul Laboratory", 0);
        checkPermanentCount("on transform", 1, PhaseStep.END_TURN, playerA, "Havengul Mystery", 1);
        checkPermanentCount("on transform", 1, PhaseStep.END_TURN, playerA, "Grizzly Bears", 1);

        // destroy and transform back to Havengul Laboratory
        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerA, "target destroy", "Grizzly Bears");
        waitStackResolved(2, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("on leaves", 2, PhaseStep.PRECOMBAT_MAIN, playerA, "Havengul Laboratory", 1);
        checkPermanentCount("on leaves", 2, PhaseStep.PRECOMBAT_MAIN, playerA, "Havengul Mystery", 0);
        checkPermanentCount("on leaves", 2, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears", 0);
        checkGraveyardCount("on leaves", 2, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears", 1);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.END_TURN);
        execute();
    }
}
