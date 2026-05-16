package org.mage.test.cards.cost.omen;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.view.CardView;
import mage.view.GameView;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class OmenCardsTest extends CardTestPlayerBase {

    @Test
    public void testOmenStackViewUsesParentCardImage() {
        addCard(Zone.HAND, playerA, "Dirgur Island Dragon");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Bear Cub");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Skimming Strike", "Bear Cub");
        runCode("check omen stack image", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            GameView gameView = getGameView(playerA);
            CardView spellView = gameView.getStack().values().stream().findFirst().orElse(null);
            Assert.assertNotNull("omen spell must be on stack", spellView);

            Assert.assertEquals("wrong displayed spell name", "Skimming Strike", spellView.getName());
            Assert.assertEquals("wrong set code", "TDM", spellView.getExpansionSetCode());
            Assert.assertFalse("wrong card number", spellView.getCardNumber().isEmpty());
            Assert.assertEquals("wrong image file name", "Dirgur Island Dragon", spellView.getImageFileName());
            Assert.assertEquals("wrong image number", 0, spellView.getImageNumber());
            Assert.assertTrue("wrong uses various art", spellView.getUsesVariousArt());
        });
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();
    }

    @Test
    public void testDirgurIslandDragonShuffle() {
        setStrictChooseMode(true);
        skipInitShuffling();
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.HAND, playerA, "Dirgur Island Dragon");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Bear Cub");
        addCard(Zone.LIBRARY, playerA, "Mountain");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Skimming Strike", "Bear Cub");
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();
        assertLibraryCount(playerA, "Dirgur Island Dragon", 1);
        assertTapped("Bear Cub", true);
        assertHandCount(playerA, 1);
    }

    @Test
    public void testDirgurIslandDragonShuffleAndPlay() {
        setStrictChooseMode(true);
        skipInitShuffling();
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 6);
        addCard(Zone.BATTLEFIELD, playerB, "Bear Cub");
        addCard(Zone.LIBRARY, playerA, "Mountain");
        addCard(Zone.HAND, playerA, "Dirgur Island Dragon");

        castSpell(2, PhaseStep.BEGIN_COMBAT, playerA, "Skimming Strike", "Bear Cub");
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Dirgur Island Dragon");
        setStopAt(3, PhaseStep.END_TURN);
        execute();
        assertPermanentCount(playerA, "Dirgur Island Dragon", 1);
    }

    @Test
    public void testCounteredInGraveyard() {
        setStrictChooseMode(true);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.BATTLEFIELD, playerB,"Island", 4);
        addCard(Zone.BATTLEFIELD, playerB,"Bear Cub");
        addCard(Zone.HAND, playerA, "Dirgur Island Dragon");
        addCard(Zone.HAND, playerB, "Counterspell");

        castSpell(2, PhaseStep.BEGIN_COMBAT, playerA, "Skimming Strike", "Bear Cub");
        castSpell(2, PhaseStep.BEGIN_COMBAT, playerB, "Counterspell", "Skimming Strike", "Skimming Strike", StackClause.WHILE_ON_STACK);
        attack(2, playerB, "Bear Cub");
        setStopAt(2, PhaseStep.END_TURN);
        execute();
        assertGraveyardCount(playerA, "Dirgur Island Dragon", 1);
        assertLife(playerA, 20 - 2);
    }

    @Test
    public void testGraveyardCast() {
        setStrictChooseMode(true);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Kess, Dissident Mage");
        addCard(Zone.GRAVEYARD, playerA, "Dirgur Island Dragon");
        addCard(Zone.BATTLEFIELD, playerB, "Bear Cub");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Skimming Strike", "Bear Cub");
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLibraryCount(playerA, "Dirgur Island Dragon", 1);
    }
}
