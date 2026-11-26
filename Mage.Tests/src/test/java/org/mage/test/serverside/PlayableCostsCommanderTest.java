package org.mage.test.serverside;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.players.PlayableObjectStats;
import mage.players.Player;
import mage.view.CardView;
import mage.view.GameView;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestCommanderDuelBase;

import java.util.Arrays;
import java.util.List;

/**
 * GUI tests: real costs to pay instead printed costs, commander games
 *
 * @author DarioBarrabinoDev
 */
public class PlayableCostsCommanderTest extends CardTestCommanderDuelBase {

    @Test
    public void test_Cost_WithFreeAlternativeCost() {
        // Deadly Rollick: if you control a commander, you may cast this card without paying its mana cost
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 6);
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears", 1); // something to target
        addCard(Zone.HAND, playerA, "Deadly Rollick", 1); // {3}{B}

        runCode("no commander on battlefield", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            Assert.assertEquals("must show the printed cost",
                    Arrays.asList("{3}{B}"), getStats(player).getPlayableCostsToPay());
        });

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Ob Nixilis of the Black Oath");

        // all lands are tapped now, so only the free cast is left
        runCode("commander on battlefield", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, (info, player, game) -> {
            PlayableObjectStats stats = getStats(player);
            List<String> costsToPay = stats.getPlayableCostsToPay();
            Assert.assertTrue("must be playable without any mana", stats.getPlayableAmount() > 0);
            Assert.assertTrue("must show the printed cost: " + costsToPay, costsToPay.contains("{3}{B}"));
            Assert.assertTrue("must show the free cast: " + costsToPay, costsToPay.contains("{0}"));
        });

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Ob Nixilis of the Black Oath", 1);
    }

    private PlayableObjectStats getStats(Player player) {
        GameView gameView = getGameView(player);
        CardView cardView = gameView.getMyHand().values().stream()
                .filter(c -> c.getName().equals("Deadly Rollick")).findFirst().get();
        return gameView.getCanPlayObjects().getStats(cardView.getId());
    }
}
