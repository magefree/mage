package org.mage.test.cards.single.mkc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestCommander4Players;

/**
 *
 * @author jantizio
 */
public class NellyBorcaImpulsiveAccuserTest extends CardTestCommander4Players {

    /*
    Nelly Borca, Impulsive Accuser
    {2}{R}{W}
    Legendary Creature - Human Detective
    Vigilance
    Whenever Nelly Borca, Impulsive Accuser attacks, suspect target creature. Then goad all suspected creatures.
    Whenever one or more creatures an opponent controls deal combat damage to one or more of your opponents, you and the controller of those creatures each draw a card.
    2/4
    */
    private static final String nellyBorcaImpulsiveAccuser = "Nelly Borca, Impulsive Accuser";
    private static final String bearCub = "Bear Cub";
    private static final String grizzlyBears = "Grizzly Bears";
    private static final String adornedPouncer = "Adorned Pouncer";

    private final int testTurn = 4;

    @Test
    public void testOneCreatureOneOpponent() {
        setStrictChooseMode(true);


        addCard(Zone.BATTLEFIELD, playerA, nellyBorcaImpulsiveAccuser);
        addCard(Zone.BATTLEFIELD, playerB, bearCub);

        // One creature attacks one opponent
        attack(testTurn, playerB, bearCub, playerC);
        setStopAt(testTurn, PhaseStep.COMBAT_DAMAGE);

        checkStackObject("draw 1 each", testTurn, PhaseStep.COMBAT_DAMAGE, playerA,
                "Whenever one or more creatures an opponent controls deal combat damage to one or more of your opponents," +
                        " you and the controller of those creatures each draw a card.", 1);

        execute();

        assertHandCount(playerA, 2);
        assertHandCount(playerB, 2);
        assertHandCount(playerC, 1);
        assertHandCount(playerD, 1);

    }

    @Test
    public void testTwoCreaturesOneOpponent() {
        setStrictChooseMode(true);


        addCard(Zone.BATTLEFIELD, playerA, nellyBorcaImpulsiveAccuser);
        addCard(Zone.BATTLEFIELD, playerB, bearCub);
        addCard(Zone.BATTLEFIELD, playerB, grizzlyBears);


        // Two creature attacks one opponent
        attack(testTurn, playerB, bearCub, playerC);
        attack(testTurn, playerB, grizzlyBears, playerC);
        setStopAt(testTurn, PhaseStep.COMBAT_DAMAGE);

        checkStackObject("draw 1 each", testTurn, PhaseStep.COMBAT_DAMAGE, playerA,
                "Whenever one or more creatures an opponent controls deal combat damage to one or more of your opponents," +
                        " you and the controller of those creatures each draw a card.", 1);

        execute();

        assertHandCount(playerA, 2);
        assertHandCount(playerB, 2);
        assertHandCount(playerC, 1);
        assertHandCount(playerD, 1);

    }

    @Test
    public void testTwoCreaturesTwoOpponents() {
        setStrictChooseMode(true);


        addCard(Zone.BATTLEFIELD, playerA, nellyBorcaImpulsiveAccuser);
        addCard(Zone.BATTLEFIELD, playerB, bearCub);
        addCard(Zone.BATTLEFIELD, playerB, grizzlyBears);


        // Two creature attacks two opponents
        attack(testTurn, playerB, bearCub, playerC);
        attack(testTurn, playerB, grizzlyBears, playerD);
        setStopAt(testTurn, PhaseStep.COMBAT_DAMAGE);

        checkStackObject("draw 1 each", testTurn, PhaseStep.COMBAT_DAMAGE, playerA,
                "Whenever one or more creatures an opponent controls deal combat damage to one or more of your opponents," +
                        " you and the controller of those creatures each draw a card.", 1);

        execute();

        assertHandCount(playerA, 2);
        assertHandCount(playerB, 2);
        assertHandCount(playerC, 1);
        assertHandCount(playerD, 1);
    }

    @Test
    public void testOneCreatureAttackNelly() {
        setStrictChooseMode(true);


        addCard(Zone.BATTLEFIELD, playerA, nellyBorcaImpulsiveAccuser);
        addCard(Zone.BATTLEFIELD, playerB, bearCub);


        // One creature attacks the Nelly Borca player
        attack(testTurn, playerB, bearCub, playerA);
        setStopAt(testTurn, PhaseStep.COMBAT_DAMAGE);

        checkStackSize("Empty Stack", testTurn, PhaseStep.COMBAT_DAMAGE, playerA, 0);

        execute();

        assertHandCount(playerA, 1);
        assertHandCount(playerB, 1);
        assertHandCount(playerC, 1);
        assertHandCount(playerD, 1);
    }

    @Test
    public void testTwoCreaturesAttackNellyAndOpponent() {
        setStrictChooseMode(true);


        addCard(Zone.BATTLEFIELD, playerA, nellyBorcaImpulsiveAccuser);
        addCard(Zone.BATTLEFIELD, playerB, bearCub);
        addCard(Zone.BATTLEFIELD, playerB, grizzlyBears);


        // One creature attacks the Nelly Borca player and another creature attack another player
        attack(testTurn, playerB, bearCub, playerA);
        attack(testTurn, playerB, grizzlyBears, playerC);
        setStopAt(testTurn, PhaseStep.COMBAT_DAMAGE);

        checkStackObject("draw 1 each", testTurn, PhaseStep.COMBAT_DAMAGE, playerA,
                "Whenever one or more creatures an opponent controls deal combat damage to one or more of your opponents," +
                        " you and the controller of those creatures each draw a card.", 1);

        execute();

        assertHandCount(playerA, 2);
        assertHandCount(playerB, 2);
        assertHandCount(playerC, 1);
        assertHandCount(playerD, 1);
    }

    @Test
    public void testNellyAttacks() {
        setStrictChooseMode(true);


        addCard(Zone.BATTLEFIELD, playerA, nellyBorcaImpulsiveAccuser);
        addCard(Zone.BATTLEFIELD, playerB, bearCub);


        // Nelly Borca attacks an opponent
        attack(1, playerA, nellyBorcaImpulsiveAccuser, playerB);
        addTarget(playerA, bearCub);
        setStopAt(1, PhaseStep.DECLARE_ATTACKERS);


        checkStackObject("suspect creature", 1, PhaseStep.DECLARE_ATTACKERS, playerA,
                "Whenever {this} attacks, suspect target creature. Then goad all suspected creatures.", 1);

        execute();

        assertHandCount(playerA, 1);
        assertHandCount(playerB, 0);
        assertHandCount(playerC, 0);
        assertHandCount(playerD, 0);
    }

    @Test
    public void testNellyPlayerAttacks() {
        setStrictChooseMode(true);


        addCard(Zone.BATTLEFIELD, playerA, nellyBorcaImpulsiveAccuser);
        addCard(Zone.BATTLEFIELD, playerA, grizzlyBears);
        addCard(Zone.BATTLEFIELD, playerB, bearCub);


        // Nelly Borca player attacks an opponent but not with Nelly Borca
        attack(1, playerA, grizzlyBears, playerB);
        setStopAt(1, PhaseStep.DECLARE_ATTACKERS);


        checkStackSize("Empty Stack", 1, PhaseStep.DECLARE_ATTACKERS, playerA, 0);

        execute();

        assertHandCount(playerA, 1);
        assertHandCount(playerB, 0);
        assertHandCount(playerC, 0);
        assertHandCount(playerD, 0);
    }

    @Test
    public void testOneCreatureDoubleStrikeOneOpponent() {
        setStrictChooseMode(true);


        addCard(Zone.BATTLEFIELD, playerA, nellyBorcaImpulsiveAccuser);
        addCard(Zone.BATTLEFIELD, playerB, adornedPouncer);

        // One creature with double strike attacks one opponent
        attack(testTurn, playerB, adornedPouncer, playerC);
        setStopAt(testTurn, PhaseStep.COMBAT_DAMAGE);

        checkStackObject("draw 1 each first strike", testTurn, PhaseStep.FIRST_COMBAT_DAMAGE, playerA,
                "Whenever one or more creatures an opponent controls deal combat damage to one or more of your opponents," +
                        " you and the controller of those creatures each draw a card.", 1);

        checkStackObject("draw 1 each second strike", testTurn, PhaseStep.COMBAT_DAMAGE, playerA,
                "Whenever one or more creatures an opponent controls deal combat damage to one or more of your opponents," +
                        " you and the controller of those creatures each draw a card.", 1);

        execute();

        assertHandCount(playerA, 3);
        assertHandCount(playerB, 3);
        assertHandCount(playerC, 1);
        assertHandCount(playerD, 1);

    }
}