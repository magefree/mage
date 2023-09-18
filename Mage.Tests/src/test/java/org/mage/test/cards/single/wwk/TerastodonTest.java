package org.mage.test.cards.single.wwk;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class TerastodonTest extends CardTestPlayerBase {


    public final String terastodon = "Terastodon";
    public final String parallelLives = "Parallel Lives";

    @Test
    public void testAmountTokens() {
        addCard(Zone.HAND, playerA, terastodon);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.BATTLEFIELD, playerA, parallelLives);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, terastodon);

        addTarget(playerA, parallelLives + "^Mountain^Swamp");
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerA, 3);
        assertPermanentCount(playerA, "Elephant Token", 3);
    }
}
