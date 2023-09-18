package org.mage.test.cards.single.m21;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class BarrinTolarianArchmageTest extends CardTestPlayerBase {

    private static final String barrin = "Barrin, Tolarian Archmage";

    @Test
    public void returnOwnCreature(){
        // When Barrin, Tolarian Archmage enters the battlefield, return up to one other target creature or planeswalker to its owner's hand.
        addCard(Zone.HAND, playerA, barrin);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears");
        addCard(Zone.LIBRARY, playerA, "Forest");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, barrin);
        addTarget(playerA, "Grizzly Bears");
        setStrictChooseMode(true);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        // At the beginning of your end step, if a permanent was put into your hand from the battlefield this turn, draw a card.
        assertHandCount(playerA, 2);
    }

    @Test
    public void returnOwnPlanesWalker(){
        // When Barrin, Tolarian Archmage enters the battlefield, return up to one other target creature or planeswalker to its owner's hand.
        addCard(Zone.HAND, playerA, barrin);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Basri Ket");
        addCard(Zone.LIBRARY, playerA, "Forest");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, barrin);
        addTarget(playerA, "Basri Ket");
        setStrictChooseMode(true);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        // At the beginning of your end step, if a permanent was put into your hand from the battlefield this turn, draw a card.
        assertHandCount(playerA, 2);
    }

    @Test
    public void returnOpponentsPlanesWalker(){
        // When Barrin, Tolarian Archmage enters the battlefield, return up to one other target creature or planeswalker to its owner's hand.
        addCard(Zone.HAND, playerA, barrin);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        addCard(Zone.BATTLEFIELD, playerB, "Basri Ket");
        addCard(Zone.LIBRARY, playerA, "Forest");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, barrin);
        addTarget(playerA, "Basri Ket");
        setStrictChooseMode(true);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        // At the beginning of your end step, if a permanent was put into your hand from the battlefield this turn, draw a card.
        assertHandCount(playerA, 0);
    }

}
