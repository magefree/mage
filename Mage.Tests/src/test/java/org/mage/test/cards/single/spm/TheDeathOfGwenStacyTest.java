package org.mage.test.cards.single.spm;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestCommander4Players;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author Jmlundeen
 */
public class TheDeathOfGwenStacyTest extends CardTestCommander4Players {

    /*
    The Death of Gwen Stacy
    {2}{B}
    Enchantment - Saga
    (As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)
    I -- Destroy target creature.
    II -- Each player may discard a card. Each player who doesn't loses 3 life.
    III -- Exile any number of target players' graveyards.
    */
    private static final String theDeathOfGwenStacy = "The Death of Gwen Stacy";

    @Test
    public void testTheDeathOfGwenStacy() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, theDeathOfGwenStacy);
        addCard(Zone.HAND, playerC, "Mountain");
        addCard(Zone.HAND, playerB, "Mountain");

        addTarget(playerA, "Mountain");
        addTarget(playerD, TestPlayer.TARGET_SKIP);
        addTarget(playerC, "Mountain");
        addTarget(playerB, "Mountain");

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertLife(playerC, 20);
        assertLife(playerD, 20 - 3);
    }
}