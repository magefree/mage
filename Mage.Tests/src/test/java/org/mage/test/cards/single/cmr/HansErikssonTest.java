package org.mage.test.cards.single.cmr;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */

public class HansErikssonTest extends CardTestPlayerBase {

    @Test
    public void test_RevealLand() {
        removeAllCardsFromHand(playerA);
        removeAllCardsFromLibrary(playerA);

        // Whenever Hans Eriksson attacks, reveal the top card of your library. If it's a creature card, put it
        // onto the battlefield tapped and attacking defending player or a planeswalker they control. Otherwise,
        // put that card into your hand. When you put a creature card onto the battlefield this way,
        // it fights Hans Eriksson.
        addCard(Zone.BATTLEFIELD, playerA, "Hans Eriksson", 1); // 1/4
        addCard(Zone.LIBRARY, playerA, "Swamp", 1);

        // attack and put to hand
        attack(1, playerA, "Hans Eriksson", playerB);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 20 - 1);
        assertHandCount(playerA, "Swamp", 1);
    }

    @Test
    public void test_RevealCreature_NoPlaneswalkerChoose() {
        removeAllCardsFromHand(playerA);
        removeAllCardsFromLibrary(playerA);

        // Whenever Hans Eriksson attacks, reveal the top card of your library. If it's a creature card, put it
        // onto the battlefield tapped and attacking defending player or a planeswalker they control. Otherwise,
        // put that card into your hand. When you put a creature card onto the battlefield this way,
        // it fights Hans Eriksson.
        addCard(Zone.BATTLEFIELD, playerA, "Hans Eriksson", 1);
        addCard(Zone.LIBRARY, playerA, "Grizzly Bears", 1);

        // attack and put to battle
        attack(1, playerA, "Hans Eriksson", playerB); // 1/4


        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 20 - 1 - 2); // damage from hans and bears
    }

    @Test
    public void test_RevealCreature_PlaneswalkerChoose() {
        removeAllCardsFromHand(playerA);
        removeAllCardsFromLibrary(playerA);

        // Whenever Hans Eriksson attacks, reveal the top card of your library. If it's a creature card, put it
        // onto the battlefield tapped and attacking defending player or a planeswalker they control. Otherwise,
        // put that card into your hand. When you put a creature card onto the battlefield this way,
        // it fights Hans Eriksson.
        addCard(Zone.BATTLEFIELD, playerA, "Hans Eriksson", 1);
        addCard(Zone.LIBRARY, playerA, "Grizzly Bears", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Chandra, Flame's Fury", 1);

        // attack and put to battle (you must choose target for bears to attack: player or planeswalker)
        attack(1, playerA, "Hans Eriksson", playerB); // 1/4
        setChoice(playerA, "Chandra, Flame's Fury");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 20 - 1); // damage from hans
        assertCounterCount(playerB, "Chandra, Flame's Fury", CounterType.LOYALTY, 4 - 2); // damage from bears
    }
}
