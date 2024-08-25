package org.mage.test.cards.single.jud;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class AnuridBarkripperTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.a.AnuridBarkripper Anurid Barkripper} {1}{G}{G}
     * Creature — Frog Beast
     * Threshold — Anurid Barkripper gets +2/+2 as long as seven or more cards are in your graveyard.
     * 2/2
     */
    private static final String barkripper = "Anurid Barkripper";

    @Test
    public void test_Threshold_Simple() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, barkripper);
        addCard(Zone.BATTLEFIELD, playerB, barkripper);
        addCard(Zone.GRAVEYARD, playerA, "Forest", 6);
        addCard(Zone.GRAVEYARD, playerB, "Forest", 7);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPowerToughness(playerA, barkripper, 2, 2);
        assertPowerToughness(playerB, barkripper, 2 + 2, 2 + 2);
    }

}
