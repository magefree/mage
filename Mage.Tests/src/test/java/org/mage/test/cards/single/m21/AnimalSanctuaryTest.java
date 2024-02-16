package org.mage.test.cards.single.m21;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class AnimalSanctuaryTest extends CardTestPlayerBase {

    private static final String sanctuary = "Animal Sanctuary";
    private static final String bird = "Birds of Paradise";
    private static final String cat = "Ajani's Pridemate";
    private static final String dog = "Wild Mongrel";
    private static final String goat = "Mountain Goat";
    private static final String ox = "Raging Bull";
    private static final String snake = "Anaconda";

    @Test
    public void boostBird(){
        addCard(Zone.BATTLEFIELD, playerA, sanctuary);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerA, bird);

        // {2}, {T}: Put a +1/+1 counter on target Bird, Cat, Dog, Goat, Ox, or Snake.
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{2}, {T}: ", bird);
        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertCounterCount(playerA, bird, CounterType.P1P1, 1);
    }

    @Test
    public void boostCat(){
        addCard(Zone.BATTLEFIELD, playerA, sanctuary);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerA, cat);

        // {2}, {T}: Put a +1/+1 counter on target Bird, Cat, Dog, Goat, Ox, or Snake.
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{2}, {T}: ", cat);
        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertCounterCount(playerA, cat, CounterType.P1P1, 1);
    }

    @Test
    public void boostDog(){
        addCard(Zone.BATTLEFIELD, playerA, sanctuary);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerA, dog);

        // {2}, {T}: Put a +1/+1 counter on target Bird, Cat, Dog, Goat, Ox, or Snake.
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{2}, {T}: ", dog);
        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertCounterCount(playerA, dog, CounterType.P1P1, 1);
    }

    @Test
    public void boostGoat(){
        addCard(Zone.BATTLEFIELD, playerA, sanctuary);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerA, goat);

        // {2}, {T}: Put a +1/+1 counter on target Bird, Cat, Dog, Goat, Ox, or Snake.
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{2}, {T}: ", goat);
        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertCounterCount(playerA, goat, CounterType.P1P1, 1);
    }

    @Test
    public void boostOx(){
        addCard(Zone.BATTLEFIELD, playerA, sanctuary);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerA, ox);

        // {2}, {T}: Put a +1/+1 counter on target Bird, Cat, Dog, Goat, Ox, or Snake.
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{2}, {T}: ", ox);
        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertCounterCount(playerA, ox, CounterType.P1P1, 1);
    }

    @Test
    public void boostSnake(){
        addCard(Zone.BATTLEFIELD, playerA, sanctuary);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerA, snake);

        // {2}, {T}: Put a +1/+1 counter on target Bird, Cat, Dog, Goat, Ox, or Snake.
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{2}, {T}: ", snake);
        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertCounterCount(playerA, snake, CounterType.P1P1, 1);
    }

}
