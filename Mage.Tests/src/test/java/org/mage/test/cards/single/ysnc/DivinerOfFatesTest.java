package org.mage.test.cards.single.ysnc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class DivinerOfFatesTest extends CardTestPlayerBase {

    @Test
    public void testConniveDiscardsNonlandAndSeeksCreature() {
        removeAllCardsFromLibrary(playerA);
        skipInitShuffling();
        addCard(Zone.LIBRARY, playerA, "Hill Giant");
        addCard(Zone.LIBRARY, playerA, "Island"); // drawn by connive
        addCard(Zone.HAND, playerA, "Grizzly Bears");
        addCard(Zone.HAND, playerA, "Diviner of Fates");
        addCard(Zone.BATTLEFIELD, playerA, "Plains");
        addCard(Zone.BATTLEFIELD, playerA, "Island");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Diviner of Fates");
        setChoice(playerA, "Grizzly Bears");
        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertHandCount(playerA, "Hill Giant", 1);
        assertHandCount(playerA, "Island", 1);
        assertGraveyardCount(playerA, "Grizzly Bears", 1);
        assertPowerToughness(playerA, "Diviner of Fates", 3, 4);
        assertLibraryCount(playerA, 0);
    }

    @Test
    public void testBatchDiscardSeeksOneCardMatchingEitherDiscardedType() {
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.LIBRARY, playerA, "Island", 2);
        addCard(Zone.LIBRARY, playerA, "Grizzly Bears");
        addCard(Zone.BATTLEFIELD, playerA, "Diviner of Fates");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.HAND, playerA, "Mind Rot");
        addCard(Zone.HAND, playerA, "Forest");
        addCard(Zone.HAND, playerA, "Shock");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Mind Rot", playerA);
        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertHandCount(playerA, "Island", 1);
        assertLibraryCount(playerA, "Island", 1);
        assertLibraryCount(playerA, "Grizzly Bears", 1);
        assertGraveyardCount(playerA, "Forest", 1);
        assertGraveyardCount(playerA, "Shock", 1);
        assertPowerToughness(playerA, "Diviner of Fates", 2, 3);
    }

    @Test
    public void testAllCardTypesOfDiscardedCardQualify() {
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.LIBRARY, playerA, "Sol Ring");
        addCard(Zone.LIBRARY, playerA, "Forest");
        addCard(Zone.BATTLEFIELD, playerA, "Diviner of Fates");
        addCard(Zone.BATTLEFIELD, playerA, "Noose Constrictor");
        addCard(Zone.HAND, playerA, "Ornithopter");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Discard a card:");
        setChoice(playerA, "Ornithopter");
        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertHandCount(playerA, "Sol Ring", 1);
        assertLibraryCount(playerA, "Forest", 1);
        assertGraveyardCount(playerA, "Ornithopter", 1);
    }

    @Test
    public void testOncePerTurnResetsOnOpponentsTurn() {
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.LIBRARY, playerA, "Island", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Diviner of Fates");
        addCard(Zone.BATTLEFIELD, playerA, "Noose Constrictor");
        addCard(Zone.HAND, playerA, "Forest", 3);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Discard a card:");
        setChoice(playerA, "Forest");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Discard a card:");
        setChoice(playerA, "Forest");
        checkHandCardCount("only one seek this turn", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Island", 1);
        activateAbility(2, PhaseStep.UPKEEP, playerA, "Discard a card:");
        setChoice(playerA, "Forest");
        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertHandCount(playerA, "Island", 2);
        assertLibraryCount(playerA, "Island", 1);
        assertGraveyardCount(playerA, "Forest", 3);
    }

    @Test
    public void testOpponentDiscardDoesNotUseTrigger() {
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.LIBRARY, playerA, "Island");
        addCard(Zone.BATTLEFIELD, playerA, "Diviner of Fates");
        addCard(Zone.BATTLEFIELD, playerA, "Noose Constrictor");
        addCard(Zone.BATTLEFIELD, playerB, "Noose Constrictor");
        addCard(Zone.HAND, playerA, "Forest");
        addCard(Zone.HAND, playerB, "Forest");
        activateAbility(1, PhaseStep.UPKEEP, playerB, "Discard a card:");
        setChoice(playerB, "Forest");
        checkHandCardCount("opponent discard does not seek", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Island", 0);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Discard a card:");
        setChoice(playerA, "Forest");
        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertHandCount(playerA, "Island", 1);
        assertGraveyardCount(playerB, "Forest", 1);
    }

    @Test
    public void testSeekUsesDiscardedCardsEvenWhenExiledInstead() {
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.LIBRARY, playerA, "Island");
        addCard(Zone.BATTLEFIELD, playerA, "Diviner of Fates");
        addCard(Zone.BATTLEFIELD, playerA, "Noose Constrictor");
        addCard(Zone.BATTLEFIELD, playerB, "Leyline of the Void");
        addCard(Zone.HAND, playerA, "Forest");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Discard a card:");
        setChoice(playerA, "Forest");
        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertHandCount(playerA, "Island", 1);
        assertExileCount(playerA, "Forest", 1);
        assertGraveyardCount(playerA, 0);
    }

    @Test
    public void testNoMatchingCardDoesNotSeekDifferentType() {
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.LIBRARY, playerA, "Grizzly Bears");
        addCard(Zone.BATTLEFIELD, playerA, "Diviner of Fates");
        addCard(Zone.BATTLEFIELD, playerA, "Noose Constrictor");
        addCard(Zone.HAND, playerA, "Forest");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Discard a card:");
        setChoice(playerA, "Forest");
        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertHandCount(playerA, 0);
        assertLibraryCount(playerA, "Grizzly Bears", 1);
    }
}
