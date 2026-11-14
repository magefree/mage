package org.mage.test.cards.single.ktk;

import mage.constants.CardType;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class SarkhanTheDragonspeakerTest extends CardTestPlayerBase {

    private static final String sarkhan = "Sarkhan, the Dragonspeaker";
    // +1: Until end of turn, Sarkhan becomes a legendary 4/4 red Dragon creature with flying, indestructible, and haste.

    /**
     * The Sarkhan that comes back from the blink is a new object (400.7), so the +1 has nothing left to turn
     * into a Dragon.
     */
    @Test
    public void test_BlinkedInResponse() {
        addCard(Zone.BATTLEFIELD, playerA, sarkhan);
        addCustomEffect_TargetBlink(playerA);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "+1:");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "target blink", sarkhan, "+1:");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertType(sarkhan, CardType.CREATURE, false);
        assertType(sarkhan, CardType.PLANESWALKER, true);
    }
}
