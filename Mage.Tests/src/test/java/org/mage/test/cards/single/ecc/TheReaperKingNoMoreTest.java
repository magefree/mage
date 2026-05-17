package org.mage.test.cards.single.ecc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class TheReaperKingNoMoreTest extends CardTestPlayerBase {

    @Test
    public void testDoesNotTriggerForTokenWithMinusOneMinusOneCounter() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, "The Reaper, King No More");
        addCard(Zone.BATTLEFIELD, playerA, "Wastes", 6);
        addCard(Zone.HAND, playerB, "Sprout");
        addCard(Zone.BATTLEFIELD, playerB, "Forest");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Sprout");
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "The Reaper, King No More");
        addTarget(playerA, "Saproling Token");
        addTarget(playerA, TestPlayer.TARGET_SKIP); // Reaper can target up to two creatures.

        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "The Reaper, King No More", 1);
        assertPermanentCount(playerB, "Saproling Token", 0);
        assertGraveyardCount(playerB, "Sprout", 1);
    }

    @Test
    public void testReturnsNontokenCreatureCardWithMinusOneMinusOneCounter() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, "The Reaper, King No More");
        addCard(Zone.BATTLEFIELD, playerA, "Wastes", 6);
        addCard(Zone.BATTLEFIELD, playerB, "Memnite");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "The Reaper, King No More");
        addTarget(playerA, "Memnite");
        addTarget(playerA, TestPlayer.TARGET_SKIP); // Reaper can target up to two creatures.
        setChoice(playerA, true); // Return the dead nontoken creature card.

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "The Reaper, King No More", 1);
        assertPermanentCount(playerA, "Memnite", 1);
        assertPermanentCount(playerB, "Memnite", 0);
        assertGraveyardCount(playerB, "Memnite", 0);
    }
}
