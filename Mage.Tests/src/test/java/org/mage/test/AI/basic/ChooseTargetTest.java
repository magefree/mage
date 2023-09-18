package org.mage.test.AI.basic;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class ChooseTargetTest extends CardTestPlayerBase {

    @Test
    public void test_chooseTargetCard_Manual() {
        // At the beginning of your end step, choose a creature card in an opponent's graveyard, then that player chooses a creature card in your graveyard.
        // You may return those cards to the battlefield under their owners' control.
        addCard(Zone.BATTLEFIELD, playerA, "Dawnbreak Reclaimer", 1);
        //
        addCard(Zone.GRAVEYARD, playerA, "Silvercoat Lion", 1);
        addCard(Zone.GRAVEYARD, playerB, "Silvercoat Lion", 1);

        setChoice(playerA, "Silvercoat Lion");
        setChoice(playerB, "Silvercoat Lion");
        setChoice(playerA, true);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Silvercoat Lion", 1);
        assertPermanentCount(playerB, "Silvercoat Lion", 1);
    }

    @Test
    public void test_chooseTargetCard_AI() {
        // At the beginning of your end step, choose a creature card in an opponent's graveyard, then that player chooses a creature card in your graveyard.
        // You may return those cards to the battlefield under their owners' control.
        addCard(Zone.BATTLEFIELD, playerA, "Dawnbreak Reclaimer", 1);
        //
        addCard(Zone.GRAVEYARD, playerA, "Silvercoat Lion", 1);
        addCard(Zone.GRAVEYARD, playerB, "Silvercoat Lion", 1);

        // AI must choose itself (strict mode must be disabled)
        //setChoice(playerA, "Silvercoat Lion");
        //setChoice(playerB, "Silvercoat Lion");
        //setChoice(playerA, true);

        //setStrictChooseMode(true);
        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Silvercoat Lion", 1);
        assertPermanentCount(playerB, "Silvercoat Lion", 1);
    }

}
