package org.mage.test.cards.single.msc;

import mage.constants.PhaseStep;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * 700.2b
 * The controller of a modal triggered ability chooses the mode(s) as part of putting that ability on the stack. If
 * one of the modes would be illegal (due to an inability to choose legal targets, for example), that mode can’t be
 * chosen. If no mode is chosen, the ability is removed from the stack. (See rule 603.3c.)
 *
 * @author JayDi85
 */
public class TyphoidMaryFracturedTest extends CardTestPlayerBase {

    @Test
    public void test_Discarded_ChooseMode() {
        // Whenever Typhoid Mary attacks, choose one at random. If you discarded a card this turn, you choose one instead.
        // * Mary -- Create a Treasure token.
        // * Typhoid Mary -- Draw a card.
        // * Bloody Mary -- Each opponent loses 2 life and you gain 2 life.
        addCard(Zone.BATTLEFIELD, playerA, "Typhoid Mary, Fractured"); // 3/3
        //
        // As an additional cost to cast this spell, discard a card. Draw two cards.
        addCard(Zone.HAND, playerA, "Tormenting Voice"); // {1}{R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.HAND, playerA, "Grizzly Bears"); // card to discard

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Tormenting Voice");
        setChoice(playerA, "Grizzly Bears"); // discard cost
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        attack(1, playerA, "Typhoid Mary, Fractured");
        setModeChoice(playerA, "3"); // Bloody Mary

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 20 + 2);
        assertLife(playerB, 20 - 2 - 3);
        assertPermanentCount(playerA, "Treasure", 0);
    }

    @Test
    public void test_NotDiscarded_RandomMode() {
        // Whenever Typhoid Mary attacks, choose one at random. If you discarded a card this turn, you choose one instead.
        // * Mary -- Create a Treasure token.
        // * Typhoid Mary -- Draw a card.
        // * Bloody Mary -- Each opponent loses 2 life and you gain 2 life.
        addCard(Zone.BATTLEFIELD, playerA, "Typhoid Mary, Fractured"); // 3/3

        attack(1, playerA, "Typhoid Mary, Fractured");
        // no mode choice - random

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertOneRandomMode(0);
    }

    @Test
    public void test_DiscardedInResponse_StillRandomMode() {
        // Whenever Typhoid Mary attacks, choose one at random. If you discarded a card this turn, you choose one instead.
        // * Mary -- Create a Treasure token.
        // * Typhoid Mary -- Draw a card.
        // * Bloody Mary -- Each opponent loses 2 life and you gain 2 life.
        addCard(Zone.BATTLEFIELD, playerA, "Typhoid Mary, Fractured"); // 3/3
        //
        // As an additional cost to cast this spell, discard a card. Draw two cards.
        addCard(Zone.HAND, playerA, "Thrill of Possibility"); // {1}{R}, instant
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.HAND, playerA, "Grizzly Bears"); // card to discard

        // the mode was chosen at random as the trigger was put on the stack, a discard in response changes nothing
        attack(1, playerA, "Typhoid Mary, Fractured");
        castSpell(1, PhaseStep.DECLARE_ATTACKERS, playerA, "Thrill of Possibility");
        setChoice(playerA, "Grizzly Bears"); // discard cost
        waitStackResolved(1, PhaseStep.DECLARE_ATTACKERS);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerA, "Grizzly Bears", 1);
        assertOneRandomMode(2); // thrill draws
    }

    private void assertOneRandomMode(int otherDraws) {
        int treasureMode = currentGame.getBattlefield().count(
                new FilterControlledPermanent(SubType.TREASURE, "Treasure"),
                playerA.getId(), null, currentGame
        );
        int drawMode = playerA.getHand().size() - otherDraws;
        int lifeMode = playerA.getLife() == 20 + 2 ? 1 : 0;
        Assert.assertEquals("must apply exactly one random mode", 1, treasureMode + drawMode + lifeMode);
        Assert.assertEquals("opponent life", 20 - 3 - 2 * lifeMode, playerB.getLife());
    }
}
