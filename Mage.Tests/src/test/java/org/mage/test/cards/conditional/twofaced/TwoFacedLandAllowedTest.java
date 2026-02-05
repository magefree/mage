package org.mage.test.cards.conditional.twofaced;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author matoro
 */
public class TwoFacedLandAllowedTest extends CardTestPlayerBase {

    /**
     * Tests that you are not allowed to play a land on the back of a transforming double-faced card.
     */
    @Test
    public void testTransformingNotAllowed() {
        setStrictChooseMode(true);
        addCard(Zone.HAND, playerA, "Ojer Taq, Deepest Foundation");
        checkPlayableAbility("Should not be able to play land", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Play Temple of Civilization", false);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

    /**
     * Tests that you are still allowed to play a land on the back of a modal double-faced card.
     */
    @Test
    public void testModalAllowed() {
        setStrictChooseMode(true);
        addCard(Zone.HAND, playerA, "Sink into Stupor");
        checkPlayableAbility("Should be able to play land", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Play Soporific Springs", true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

    /**
     * Tests that you are still allowed to play a land on the front of a transforming double-faced card.
     */
    @Test
    public void testTransformingFront() {
        setStrictChooseMode(true);
        addCard(Zone.HAND, playerA, "Balamb Garden, SeeD Academy");
        checkPlayableAbility("Should be able to play land", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Play Balamb Garden, SeeD Academy", true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

    /**
     * Tests that you can still transform a nonmodal double-faced card with a land on the back.
     */
    @Test
    public void testActivateTransformationAllowed() {
        setStrictChooseMode(true);
        addCard(Zone.BATTLEFIELD, playerA, "Tarrian's Journal");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{2}, {T}, Discard your hand: Transform {this}.");
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        checkPermanentCount("Successfully transformed", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "The Tomb of Aclazotz", 1);
    }
}
