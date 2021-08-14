
package org.mage.test.cards.triggers;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class DrawTriggeredTest extends CardTestPlayerBase {

    /*
     * Day's Undoing - Doesn't create card draw triggers "Specifically, it
     * doesn't work with Chasm Skulker.
     *
     * Steps to reproduce:
     1) Have Chasm Skulker on the battlefield.
     2) Cast Day's Undoing.
     3) You will draw 7 cards, but Chasm Skulker's ""when you draw a card"" trigger does not trigger." ==> What is correct
     */
    @Test
    public void DaysUndoingTriggeredDrewEventAreRemovedTest() {
        // Each player shuffles their hand and graveyard into their library, then draws seven cards. If it's your turn, end the turn.
        addCard(Zone.HAND, playerA, "Day's Undoing");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);

        // Whenever you draw a card, put a +1/+1 counter on Chasm Skulker.
        // When Chasm Skulker dies, put X 1/1 blue Squid creature tokens with islandwalk onto the battlefield, where X is the number of +1/+1 counters on Chasm Skulker.
        addCard(Zone.BATTLEFIELD, playerB, "Chasm Skulker", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Day's Undoing");

        setStopAt(2, PhaseStep.UNTAP);
        execute();

        assertExileCount("Day's Undoing", 1);
        assertPermanentCount(playerB, "Chasm Skulker", 1);
        assertPowerToughness(playerB, "Chasm Skulker", 1, 1);

    }

    /**
     * Consecrated Sphinx does not trigger when "Edric, Spymaster of Trest" lets
     * my opponent draw cards.
     */
    @Test
    public void EdricSpymasterOfTrestTest() {
        // Flying
        // Whenever an opponent draws a card, you may draw two cards.
        addCard(Zone.BATTLEFIELD, playerA, "Consecrated Sphinx", 1);

        // Whenever a creature deals combat damage to one of your opponents, its controller may draw a card.
        addCard(Zone.BATTLEFIELD, playerB, "Edric, Spymaster of Trest", 1);

        attack(2, playerB, "Edric, Spymaster of Trest");

        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertHandCount(playerB, 2); // 1 from start of turn and 1 from Edric
        assertHandCount(playerA, 4); // 2 * 2 from Sphinx = 4

    }

    /**
     * two consecrated sphinxes do not work properly, only gives one player
     * additional draw
     *
     */
    @Test
    public void TwoConsecratedSphinxDifferentPlayers() {
        // Flying
        // Whenever an opponent draws a card, you may draw two cards.
        addCard(Zone.BATTLEFIELD, playerA, "Consecrated Sphinx", 1);

        // Flying
        // Whenever an opponent draws a card, you may draw two cards.
        addCard(Zone.BATTLEFIELD, playerB, "Consecrated Sphinx", 1);

        setChoice(playerA, true);
        setChoice(playerA, false);
        setChoice(playerA, false);

        setChoice(playerB, true);
        setChoice(playerB, false);
        setStopAt(2, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertHandCount(playerB, 3); // 1 from start of turn 1 and 4 from Opponents draw of 2 cards
        assertHandCount(playerA, 2); // 2 from Sphinx triggered by the normal draw

    }

    @Test
    public void TwoConsecratedSphinxSamePlayer() {
        // Flying
        // Whenever an opponent draws a card, you may draw two cards.
        addCard(Zone.BATTLEFIELD, playerA, "Consecrated Sphinx", 2);

        setStopAt(2, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertHandCount(playerB, 1); // 1 from start of turn 1 and 4 from Opponents draw of 2 cards
        assertHandCount(playerA, 4); // 2 from Sphinx triggered by the normal draw

    }
}
