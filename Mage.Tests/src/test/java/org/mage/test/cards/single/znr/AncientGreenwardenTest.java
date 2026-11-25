package org.mage.test.cards.single.znr;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class AncientGreenwardenTest extends CardTestPlayerBase {

    @Test
    public void test_OneOrMoreLandsEnter_TriggersAdditionalTime() {
        // If a land entering the battlefield causes a triggered ability of a permanent you control to trigger,
        // that ability triggers an additional time.
        addCard(Zone.BATTLEFIELD, playerA, "Ancient Greenwarden");
        //
        // Whenever one or more tokens enter the battlefield under your control, each opponent loses 1 life and you gain 1 life.
        addCard(Zone.BATTLEFIELD, playerA, "Kambal, Profiteering Mayor");
        //
        // Create X 1/1 green Forest Dryad land creature tokens.
        addCard(Zone.HAND, playerA, "Awaken the Woods"); // {X}{G}{G}
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);

        // two lands enter at once - one trigger + one additional trigger
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Awaken the Woods");
        setChoice(playerA, "X=2");
        setChoice(playerA, "Whenever one or more tokens you control enter"); // two triggers order

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Forest Dryad Token", 2);
        assertLife(playerA, 20 + 2);
        assertLife(playerB, 20 - 2);
    }
}
