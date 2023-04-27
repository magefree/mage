package org.mage.test.cards.single.war;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class GodEternalKefnetTest extends CardTestPlayerBase {

    @Test
    public void test_Reduce_NormalSpell() {
        removeAllCardsFromHand(playerA);
        removeAllCardsFromLibrary(playerA);
        skipInitShuffling();

        // You may reveal the first card you draw each turn as you draw it. Whenever you reveal an instant or sorcery
        // card this way, copy that card and you may cast the copy. That copy costs {2} less to cast.
        addCard(Zone.BATTLEFIELD, playerA, "God-Eternal Kefnet");
        //
        // Precision Bolt deals 3 damage to any target.
        addCard(Zone.LIBRARY, playerA, "Precision Bolt"); // sorcery {2}{R}
        addCard(Zone.LIBRARY, playerA, "Grizzly Bears"); // creature
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");

        // draw on tune 3 - bear - ignore
        setChoice(playerA, true);

        // draw on tune 5 - bolt - reveal and cast
        setChoice(playerA, true); // reveal
        setChoice(playerA, true); // cast
        addTarget(playerA, playerB);

        setStrictChooseMode(true);
        setStopAt(5, PhaseStep.END_TURN);
        execute();

        assertHandCount(playerA, "Precision Bolt", 1);
        assertGraveyardCount(playerA, "Precision Bolt", 0);
        assertLife(playerB, 20 - 3);
    }

    @Test
    public void test_Reduce_Split() {
        removeAllCardsFromHand(playerA);
        removeAllCardsFromLibrary(playerA);
        skipInitShuffling();

        // You may reveal the first card you draw each turn as you draw it. Whenever you reveal an instant or sorcery
        // card this way, copy that card and you may cast the copy. That copy costs {2} less to cast.
        addCard(Zone.BATTLEFIELD, playerA, "God-Eternal Kefnet");
        //
        // Fire {1}{R}
        // Fire deals 2 damage divided as you choose among one or two target creatures and/or players.
        // Ice {1}{U}
        // Tap target permanent.
        // Draw a card.
        addCard(Zone.LIBRARY, playerA, "Fire // Ice");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");

        showAvailableMana("before", 1, PhaseStep.PRECOMBAT_MAIN, playerA);
        setChoice(playerA, true); // reveal
        setChoice(playerA, true); // cast
        setChoice(playerA, "Cast Fire");
        addTargetAmount(playerA, playerB, 2);

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertHandCount(playerA, "Fire // Ice", 1);
        assertGraveyardCount(playerA, "Fire // Ice", 0);
        assertLife(playerB, 20 - 2);
    }
}