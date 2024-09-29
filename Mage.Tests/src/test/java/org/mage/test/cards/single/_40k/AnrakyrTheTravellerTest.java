package org.mage.test.cards.single._40k;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class AnrakyrTheTravellerTest extends CardTestPlayerBase {

    // Lord of the Pyrrhian Legions — Whenever Anrakyr the Traveller attacks, you may cast an artifact spell from your
    // hand or graveyard by paying life equal to its mana value rather than paying its mana cost.
    private static final String ANRAKYR = "Anrakyr the Traveller";

    private static final String CRYPTEK = "Cryptek";

    // Multikicker {2}
    // Everflowing Chalice enters the battlefield with a charge counter on it for each time it was kicked.
    private static final String EVERFLOWING_CHALICE = "Everflowing Chalice";

    // As an additional cost to cast this spell, discard a card.
    private static final String LESSER_MASTICORE = "Lesser Masticore";

    @Test
    public void testCastArtifactCardForLifeFromHand_ShouldCastForLife() {
        // prepare
        addCard(Zone.BATTLEFIELD, playerA, ANRAKYR);
        addCard(Zone.HAND, playerA, CRYPTEK);

        // execute
        attack(1, playerA, ANRAKYR);
        setChoice(playerA, true); // confirm use of Anrakyr's ability
        setChoice(playerA, CRYPTEK); // select Cryptek to cast
        setChoice(playerA, true); // confirm casting spell by paying life instead of mana

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        // assert
        assertLife(playerA, 20 - 4);
        assertPermanentCount(playerA, CRYPTEK, 1);
    }

    @Test
    public void testNotEnoughLife_ShouldNotCast() {
        // prepare
        addCard(Zone.BATTLEFIELD, playerA, ANRAKYR);
        addCard(Zone.HAND, playerA, CRYPTEK);
        setLife(playerA, 3);

        // execute
        attack(1, playerA, ANRAKYR);
        setChoice(playerA, true); // confirm use of Anrakyr's ability
        setChoice(playerA, CRYPTEK); // select Cryptek to cast
        setChoice(playerA, true); // confirm casting spell by paying life instead of mana

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        // assert
        assertLife(playerA, 3);
        assertPermanentCount(playerA, CRYPTEK, 0);
    }

    @Test
    public void testCastArtifactCardForLifeFromGraveyard_ShouldCastForLife() {
        // prepare
        addCard(Zone.BATTLEFIELD, playerA, ANRAKYR);
        addCard(Zone.GRAVEYARD, playerA, CRYPTEK);

        // execute
        attack(1, playerA, ANRAKYR);
        setChoice(playerA, true); // confirm use of Anrakyr's ability
        setChoice(playerA, CRYPTEK); // select Cryptek to cast
        setChoice(playerA, true); // confirm casting spell by paying life instead of mana

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        // assert
        assertLife(playerA, 20 - 4);
        assertPermanentCount(playerA, CRYPTEK, 1);
        assertGraveyardCount(playerA, CRYPTEK, 0);
    }

    @Test
    public void testLesserMasticore_ShouldCast() {
        // prepare
        addCard(Zone.BATTLEFIELD, playerA, ANRAKYR);
        addCard(Zone.HAND, playerA, LESSER_MASTICORE);
        addCard(Zone.HAND, playerA, CRYPTEK);

        // execute
        attack(1, playerA, ANRAKYR);
        setChoice(playerA, true); // confirm use of Anrakyr's ability
        setChoice(playerA, LESSER_MASTICORE); // select Lesser Masticore to cast
        setChoice(playerA, true); // confirm casting spell by paying life instead of mana
        setChoice(playerA, CRYPTEK); // select Cryptek to discard to pay for Lesser Masticore

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        // assert
        assertLife(playerA, 20 - 2);
        assertPermanentCount(playerA, LESSER_MASTICORE, 1);
    }

    @Test
    public void testLesserMasticoreNoCardInHand_ShouldNotCast() {
        // prepare
        addCard(Zone.BATTLEFIELD, playerA, ANRAKYR);
        addCard(Zone.HAND, playerA, LESSER_MASTICORE);

        // execute
        attack(1, playerA, ANRAKYR);
        setChoice(playerA, true); // confirm use of Anrakyr's ability
        setChoice(playerA, LESSER_MASTICORE); // select Lesser Masticore to cast
        setChoice(playerA, true); // confirm casting spell by paying life instead of mana

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        // assert
        assertLife(playerA, 20);
        assertPermanentCount(playerA, LESSER_MASTICORE, 0);
    }

    @Test
    public void testEverflowingChaliceMultikicker1_ShouldCast() {
        // prepare
        final String swamp = "Swamp";
        addCard(Zone.BATTLEFIELD, playerA, ANRAKYR);
        addCard(Zone.BATTLEFIELD, playerA, swamp);
        addCard(Zone.BATTLEFIELD, playerA, swamp);
        addCard(Zone.HAND, playerA, EVERFLOWING_CHALICE);

        // execute
        attack(1, playerA, ANRAKYR);
        setChoice(playerA, true); // confirm use of Anrakyr's ability
        setChoice(playerA, EVERFLOWING_CHALICE); // select Everflowing Chalice to cast
        setChoice(playerA, true); // confirm casting spell by paying life instead of mana

        // multikicker 1
        setChoice(playerA, true);
        setChoice(playerA, false);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        // assert
        assertLife(playerA, 20);
        assertPermanentCount(playerA, EVERFLOWING_CHALICE, 1);
        assertCounterCount(playerA, EVERFLOWING_CHALICE, CounterType.CHARGE, 1);
    }

    @Test
    public void testEverflowingChaliceMultikicker0_ShouldCast() {
        // prepare
        addCard(Zone.BATTLEFIELD, playerA, ANRAKYR);
        addCard(Zone.HAND, playerA, EVERFLOWING_CHALICE);

        // execute
        attack(1, playerA, ANRAKYR);
        setChoice(playerA, true); // confirm use of Anrakyr's ability
        setChoice(playerA, EVERFLOWING_CHALICE); // select Everflowing Chalice to cast
        setChoice(playerA, true); // confirm casting spell by paying life instead of mana

        // multikicker 0
        setChoice(playerA, false);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        // assert
        assertLife(playerA, 20);
        assertPermanentCount(playerA, EVERFLOWING_CHALICE, 1);
        assertCounterCount(playerA, EVERFLOWING_CHALICE, CounterType.CHARGE, 0);
    }

    @Test
    public void testEverflowingChaliceMultikicker1NoMana_ShouldNotCast() {
        // prepare
        addCard(Zone.BATTLEFIELD, playerA, ANRAKYR);
        addCard(Zone.HAND, playerA, EVERFLOWING_CHALICE);

        // execute
        attack(1, playerA, ANRAKYR);
        setChoice(playerA, true); // confirm use of Anrakyr's ability
        setChoice(playerA, EVERFLOWING_CHALICE); // select Everflowing Chalice to cast
        setChoice(playerA, true); // confirm casting spell by paying life instead of mana

        // multikicker 1
        setChoice(playerA, true);
        setChoice(playerA, false);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        // assert
        assertLife(playerA, 20);
        assertPermanentCount(playerA, EVERFLOWING_CHALICE, 0);
    }
}