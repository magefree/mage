package org.mage.test.cards.single._40k;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class AnrakyrTheTravellerTest extends CardTestPlayerBase {

    private static final String ANRAKYR = "Anrakyr the Traveller";

    @Test
    public void testCastArtifactCardForLifeFromHand_ShouldCastForLife() {
        // prepare
        final String cryptek = "Cryptek";
        addCard(Zone.BATTLEFIELD, playerA, ANRAKYR);
        addCard(Zone.HAND, playerA, cryptek);

        // execute
        attack(1, playerA, ANRAKYR);
        setChoice(playerA, true);
        setChoice(playerA, cryptek);
        setChoice(playerA, true);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        // assert
        assertLife(playerA, 20 - 4);
        assertPermanentCount(playerA, cryptek, 1);
    }

    @Test
    public void testNotEnoughLife_ShouldNotCast() {
        // prepare
        final String cryptek = "Cryptek";
        addCard(Zone.BATTLEFIELD, playerA, ANRAKYR);
        addCard(Zone.HAND, playerA, cryptek);
        setLife(playerA, 3);

        // execute
        attack(1, playerA, ANRAKYR);
        setChoice(playerA, true);
        setChoice(playerA, cryptek);
        setChoice(playerA, true);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        // assert
        assertLife(playerA, 3);
        assertPermanentCount(playerA, cryptek, 0);
    }

    @Test
    public void testCastArtifactCardForLifeFromGraveyard_ShouldCastForLife() {
        // prepare
        final String cryptek = "Cryptek";
        addCard(Zone.BATTLEFIELD, playerA, ANRAKYR);
        addCard(Zone.GRAVEYARD, playerA, cryptek);

        // execute
        attack(1, playerA, ANRAKYR);
        setChoice(playerA, true);
        setChoice(playerA, cryptek);
        setChoice(playerA, true);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        // assert
        assertLife(playerA, 20 - 4);
        assertPermanentCount(playerA, cryptek, 1);
        assertGraveyardCount(playerA, cryptek, 0);
    }

    @Test
    public void testLesserMasticore_ShouldCast() {
        // prepare
        final String lesserMasticore = "Lesser Masticore";
        final String cryptek = "Cryptek";
        addCard(Zone.BATTLEFIELD, playerA, ANRAKYR);
        addCard(Zone.HAND, playerA, lesserMasticore);
        addCard(Zone.HAND, playerA, cryptek);

        // execute
        attack(1, playerA, ANRAKYR);
        setChoice(playerA, true);
        setChoice(playerA, lesserMasticore);
        setChoice(playerA, true);
        setChoice(playerA, cryptek);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        // assert
        assertLife(playerA, 20 - 2);
        assertPermanentCount(playerA, lesserMasticore, 1);
    }

    @Test
    public void testLesserMasticoreNoCardInHand_ShouldNotCast() {
        // prepare
        final String lesserMasticore = "Lesser Masticore";
        addCard(Zone.BATTLEFIELD, playerA, ANRAKYR);
        addCard(Zone.HAND, playerA, lesserMasticore);

        // execute
        attack(1, playerA, ANRAKYR);
        setChoice(playerA, true);
        setChoice(playerA, lesserMasticore);
        setChoice(playerA, true);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        // assert
        assertLife(playerA, 20);
        assertPermanentCount(playerA, lesserMasticore, 0);
    }

    @Test
    public void testEverflowingChaliceMultikicker1_ShouldCast() {
        // prepare
        final String everflowingChalice = "Everflowing Chalice";
        final String swamp = "Swamp";
        addCard(Zone.BATTLEFIELD, playerA, ANRAKYR);
        addCard(Zone.BATTLEFIELD, playerA, swamp);
        addCard(Zone.BATTLEFIELD, playerA, swamp);
        addCard(Zone.HAND, playerA, everflowingChalice);

        // execute
        attack(1, playerA, ANRAKYR);
        setChoice(playerA, true);
        setChoice(playerA, everflowingChalice);
        setChoice(playerA, true);

        // multikicker
        setChoice(playerA, true);
        setChoice(playerA, false);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        // assert
        assertLife(playerA, 20);
        assertPermanentCount(playerA, everflowingChalice, 1);
        assertCounterCount(playerA, everflowingChalice, CounterType.CHARGE, 1);
    }

    @Test
    public void testEverflowingChaliceMultikicker0_ShouldCast() {
        // prepare
        final String everflowingChalice = "Everflowing Chalice";
        addCard(Zone.BATTLEFIELD, playerA, ANRAKYR);
        addCard(Zone.HAND, playerA, everflowingChalice);

        // execute
        attack(1, playerA, ANRAKYR);
        setChoice(playerA, true);
        setChoice(playerA, everflowingChalice);
        setChoice(playerA, true);

        // multikicker
        setChoice(playerA, false);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        // assert
        assertLife(playerA, 20);
        assertPermanentCount(playerA, everflowingChalice, 1);
        assertCounterCount(playerA, everflowingChalice, CounterType.CHARGE, 0);
    }

    @Test
    public void testEverflowingChaliceMultikicker1NoMana_ShouldNotCast() {
        // prepare
        final String everflowingChalice = "Everflowing Chalice";
        addCard(Zone.BATTLEFIELD, playerA, ANRAKYR);
        addCard(Zone.HAND, playerA, everflowingChalice);

        // execute
        attack(1, playerA, ANRAKYR);
        setChoice(playerA, true);
        setChoice(playerA, everflowingChalice);
        setChoice(playerA, true);

        // multikicker
        setChoice(playerA, true);
        setChoice(playerA, false);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        // assert
        assertLife(playerA, 20);
        assertPermanentCount(playerA, everflowingChalice, 0);
    }
}