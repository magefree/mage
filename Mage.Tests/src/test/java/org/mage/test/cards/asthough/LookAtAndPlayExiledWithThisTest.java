package org.mage.test.cards.asthough;

import mage.cards.Card;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.util.CardUtil;
import mage.view.CardView;
import mage.view.GameView;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class LookAtAndPlayExiledWithThisTest extends CardTestPlayerBase {

    @Test
    public void KheruMindEater() {
        addCard(Zone.BATTLEFIELD, playerA, "Kheru Mind-Eater");
        addCard(Zone.HAND, playerB, "Swamp", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 3);
        addCard(Zone.HAND, playerB, "Act of Treason");

        attack(1, playerA, "Kheru Mind-Eater");
        addTarget(playerB, "Swamp");
        waitStackResolved(1, PhaseStep.POSTCOMBAT_MAIN);
        runCode("face down exile", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, (info, player, game) -> {
            checkExiledCardView("Swamp", false);
        });
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Act of Treason", "Kheru Mind-Eater");
        waitStackResolved(2, PhaseStep.POSTCOMBAT_MAIN);
        runCode("face down exile", 2, PhaseStep.POSTCOMBAT_MAIN, playerB, (info, player, game) -> {
            checkExiledCardView("Swamp", true);
        });
        playLand(3, PhaseStep.POSTCOMBAT_MAIN, playerA, "Swamp");
        setStopAt(3, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();
        assertExileCount(playerB, 0);
        assertPermanentCount(playerA, "Swamp", 1);
    }

    private void checkExiledCardView(String expectedCardName, boolean shouldOppSee) {
        Card card = currentGame.getExile().getAllCards(currentGame, playerB.getId()).get(0);
        GameView gameView = getGameView(playerA);
        CardView controllerView = gameView.getExile()
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
        String controllerName = "Face Down: " + expectedCardName;
        String opponentName = "Face Down" + (shouldOppSee ? ": " + expectedCardName : "");
        Assert.assertTrue("Card should be face down", card.isFaceDown(currentGame));
        Assert.assertTrue("Controller - can't play", card.getAbilities(currentGame).stream().anyMatch(ability -> !CardUtil.isInformationAbility(ability)));
        Assert.assertEquals("Controller - wrong name", controllerName, controllerView.getName());
        Assert.assertEquals("Opponent - wrong name", opponentName, opponentView.getName());
    }

    @Test
    public void ColfenorsPlans() {
        addCard(Zone.HAND, playerA, "Colfenor's Plans");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);
        addCard(Zone.LIBRARY, playerA, "Forest", 1);
        addCard(Zone.LIBRARY, playerA, "Llanowar Elves", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Colfenor's Plans");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        runCode("face down exile", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            checkExiledCardView("Llanowar Elves", false);
        });
        playLand(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Forest");
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Llanowar Elves");
        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
    }
}
