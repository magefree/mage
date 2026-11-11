package org.mage.test.cards.single.msc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class ExtractPowerTest extends CardTestPlayerBase {

    @Test
    public void testCastFromFaceDownExileWithoutPaying() {
        setStrictChooseMode(true);
        skipInitShuffling();

        addCard(Zone.BATTLEFIELD, playerA, "Island", 6);
        addCard(Zone.HAND, playerA, "Extract Power");
        addCard(Zone.LIBRARY, playerB, "Plains", 7);
        addCard(Zone.LIBRARY, playerB, "Lightning Bolt");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Extract Power");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        checkPlayableAbility("cast from face-down exile", 1, PhaseStep.PRECOMBAT_MAIN, playerA,
                "Cast Lightning Bolt", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        setChoice(playerA, "Cast without paying its mana cost");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerB, 17);
        assertGraveyardCount(playerB, "Lightning Bolt", 1);
        assertTappedCount("Island", true, 6);
    }
}
