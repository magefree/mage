package org.mage.test.cards.single.mh3;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class TamiyoInquisitiveStudentTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.t.TamiyoInquisitiveStudent Tamiyo, Inquisitive Student} {U}
     * Legendary Creature — Moonfolk Wizard
     * Flying
     * Whenever Tamiyo, Inquisitive Student attacks, investigate.
     * When you draw your third card in a turn, exile Tamiyo, then return her to the battlefield transformed under her owner's control.
     * 0/3
     * // {@link mage.cards.t.TamiyoSeasonedScholar Tamiyo, Seasoned Scholar}
     * +2: Until your next turn, whenever a creature attacks you or a planeswalker you control, it gets -1/-0 until end of turn.
     * −3: Return target instant or sorcery card from your graveyard to your hand. If it’s a green card, add one mana of any color.
     * −7: Draw cards equal to half the number of cards in your library, rounded up. You get an emblem with “You have no maximum hand size.”
     */
    private static final String tamiyo = "Tamiyo, Inquisitive Student";
    private static final String tamiyoPW = "Tamiyo, Seasoned Scholar";

    @Test
    public void test_Trigger_Attack() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, tamiyo);

        attack(1, playerA, tamiyo, playerB);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, tamiyo, 1);
        assertPermanentCount(playerA, "Clue Token", 1);
    }

    @Test
    public void test_Trigger_Transform() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, tamiyo);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        addCard(Zone.HAND, playerA, "Divination", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Divination");
        checkPermanentCount("1: Tamiyo didn't transform after draw 2", 1, PhaseStep.PRECOMBAT_MAIN, playerA, tamiyo, 1);

        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Divination");
        // Tamiyo triggered, as playerA did draw its third card for the turn

        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, tamiyo, 0);
        assertPermanentCount(playerA, tamiyoPW, 1);
    }

    @Test
    public void test_PlusTwo() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, tamiyoPW);
        addCard(Zone.BATTLEFIELD, playerA, "Memnite"); // 1/1
        addCard(Zone.BATTLEFIELD, playerB, "Elite Vanguard"); // 2/1
        addCard(Zone.BATTLEFIELD, playerB, "Centaur Courser"); // 3/3

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "+2");

        attack(1, playerA, "Memnite", playerB);

        attack(2, playerB, "Elite Vanguard", tamiyoPW);
        attack(2, playerB, "Centaur Courser", playerA);
        setChoice(playerA, "Until"); // Stack the triggers

        setStopAt(2, PhaseStep.END_COMBAT);
        execute();

        assertLife(playerB, 20 - 1);
        assertLife(playerA, 20 - (3 - 1));
        assertCounterCount(playerA, tamiyoPW, CounterType.LOYALTY, 2 + 2 - (2 - 1));
    }

    @Test
    public void test_Minus3_NonGreen() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, tamiyoPW);
        addCard(Zone.GRAVEYARD, playerA, "Lightning Bolt");

        addCounters(1, PhaseStep.PRECOMBAT_MAIN, playerA, tamiyoPW, CounterType.LOYALTY, 2);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "-3");
        addTarget(playerA, "Lightning Bolt");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertHandCount(playerA, "Lightning Bolt", 1);
        assertCounterCount(playerA, tamiyoPW, CounterType.LOYALTY, 1);
    }

    @Test
    public void test_Minus3_Green() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, tamiyoPW);
        addCard(Zone.GRAVEYARD, playerA, "Regrowth");
        addCard(Zone.HAND, playerA, "Lightning Bolt");

        addCounters(1, PhaseStep.PRECOMBAT_MAIN, playerA, tamiyoPW, CounterType.LOYALTY, 2);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "-3");
        addTarget(playerA, "Regrowth");
        setChoice(playerA, "Red"); // choose that color of mana
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerB, 20 - 3);
        assertHandCount(playerA, "Regrowth", 1);
        assertCounterCount(playerA, tamiyoPW, CounterType.LOYALTY, 1);
    }

    @Test
    public void test_Minus7() {
        setStrictChooseMode(true);
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.LIBRARY, playerA, "Taiga", 45);

        addCard(Zone.BATTLEFIELD, playerA, tamiyoPW);

        addCounters(1, PhaseStep.PRECOMBAT_MAIN, playerA, tamiyoPW, CounterType.LOYALTY, 6);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "-7");

        setStopAt(2, PhaseStep.UPKEEP);
        execute();

        assertHandCount(playerA, 23); // Drew half of library, and emblem mean no maximum end size
        assertCounterCount(playerA, tamiyoPW, CounterType.LOYALTY, 1);
    }
}
