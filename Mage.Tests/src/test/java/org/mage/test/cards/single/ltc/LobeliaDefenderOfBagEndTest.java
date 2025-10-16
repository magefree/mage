package org.mage.test.cards.single.ltc;

import mage.cards.Card;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.util.CardUtil;
import mage.view.CardView;
import mage.view.GameView;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class LobeliaDefenderOfBagEndTest extends CardTestPlayerBase {

    private static final String lobelia = "Lobelia, Defender of Bag End";
    @Test
    public void testOppGainVisibility() {
        addCard(Zone.HAND, playerA, lobelia);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.HAND, playerB, "Act of Treason");
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 3);
        addCard(Zone.BATTLEFIELD, playerB, "Ornithopter");
        addCard(Zone.LIBRARY, playerB, "Bear Cub");
        skipInitShuffling();

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, lobelia);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Act of Treason", lobelia);
        waitStackResolved(2, PhaseStep.PRECOMBAT_MAIN);
        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerB, "{T}, Sacrifice an artifact");
        setModeChoice(playerB, "1");
        setChoice(playerB, "Ornithopter");
        waitStackResolved(2, PhaseStep.PRECOMBAT_MAIN);

        runCode("face down exile", 2, PhaseStep.PRECOMBAT_MAIN, playerB, ((info, player, game) -> {
            Card card = currentGame.getExile().getAllCards(currentGame, playerB.getId()).get(0);
            GameView gameView = getGameView(playerA);
            CardView ownerView = gameView.getExile()
                    .stream()
                    .flatMap(e -> e.values().stream())
                    .findFirst()
                    .orElse(null);
            gameView = getGameView(playerB);
            CardView opponentView = gameView.getExile()
                    .stream()
                    .flatMap(e -> e.values().stream())
                    .findFirst()
                    .orElse(null);
            String expectedName = "Face Down: Bear Cub";
            Assert.assertTrue("Card should be face down", card.isFaceDown(currentGame));
            Assert.assertTrue("Owner - can't play", card.getAbilities(currentGame).stream().anyMatch(ability -> !CardUtil.isInformationAbility(ability)));
            Assert.assertEquals("Owner - wrong name", expectedName, ownerView.getName());
            Assert.assertEquals("Opponent - wrong name", expectedName, opponentView.getName());
        }));
        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();
    }
}