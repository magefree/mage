package org.mage.test.cards.single.cmr;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */

public class RakshasaDebaserTest extends CardTestPlayerBase {

    @Test
    public void test_Playable() {
        // Whenever Rakshasa Debaser attacks, put target creature card from defending player's graveyard onto the battlefield under your control.
        addCard(Zone.BATTLEFIELD, playerA, "Rakshasa Debaser", 1);
        addCard(Zone.GRAVEYARD, playerB, "Balduvian Bears", 1);

        // attack and trigger
        attack(1, playerA, "Rakshasa Debaser", playerB);
        addTarget(playerA, "Balduvian Bears");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Balduvian Bears", 1); // from trigger
    }
}
