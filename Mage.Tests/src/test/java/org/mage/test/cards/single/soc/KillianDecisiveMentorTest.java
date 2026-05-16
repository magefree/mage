package org.mage.test.cards.single.soc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.game.permanent.Permanent;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestMultiPlayerBaseWithRangeAll;

import java.util.Collections;

public class KillianDecisiveMentorTest extends CardTestMultiPlayerBaseWithRangeAll {

    @Test
    public void testGoadFromEnchantmentEnteringLastsUntilKillianControllersNextTurn() {
        setStrictChooseMode(true);

        // Scenario: PlayerA controls Killian. An enchantment enters under PlayerA's control and Killian
        // taps and goads PlayerD's Grizzly Bears.
        //
        // This four-player fixture is seated A -> D -> C -> B and uses left attack, so PlayerD's next
        // legal attack is against PlayerC. The important duration check is that the creature is still
        // goaded during PlayerD's turn, then stops being goaded as PlayerA's next turn begins.
        addCard(Zone.BATTLEFIELD, playerA, "Killian, Decisive Mentor");
        addCard(Zone.HAND, playerA, "Blind Obedience");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerD, "Grizzly Bears");

        // Step 1: PlayerA casts an enchantment. Killian's trigger targets PlayerD's Grizzly Bears.
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Blind Obedience");
        addTarget(playerA, "Grizzly Bears");

        // Step 2: After the trigger resolves, Grizzly Bears is tapped and goaded by PlayerA.
        runCode("Grizzly Bears is goaded by PlayerA after Killian trigger resolves", 1, PhaseStep.BEGIN_COMBAT, playerA,
                (info, player, game) -> {
                    Permanent bears = getPermanent("Grizzly Bears");
                    Assert.assertTrue("Killian should tap the targeted creature", bears.isTapped());
                    assertGoadedByOnly("Grizzly Bears", playerA);
                });

        // Step 3: On PlayerD's turn, Grizzly Bears untaps and must attack PlayerC, not PlayerA.
        checkAttackers("Goaded Grizzly Bears must attack during PlayerD's combat", 2, playerD, "Grizzly Bears");

        // Step 4: At the beginning of PlayerA's next turn, the "until your next turn" goad effect has expired.
        runCode("Grizzly Bears is no longer goaded on PlayerA's next turn", 5, PhaseStep.PRECOMBAT_MAIN, playerA,
                (info, player, game) -> assertNotGoaded("Grizzly Bears"));

        setStopAt(5, PhaseStep.BEGIN_COMBAT);
        execute();
    }

    private void assertGoadedByOnly(String creatureName, TestPlayer player) {
        Permanent permanent = getPermanent(creatureName);
        Assert.assertEquals(
                "Creature should be goaded only by " + player.getName(),
                Collections.singleton(player.getId()),
                permanent.getGoadingPlayers()
        );
    }

    private void assertNotGoaded(String creatureName) {
        Permanent permanent = getPermanent(creatureName);
        Assert.assertTrue("Creature should not be goaded", permanent.getGoadingPlayers().isEmpty());
    }
}
