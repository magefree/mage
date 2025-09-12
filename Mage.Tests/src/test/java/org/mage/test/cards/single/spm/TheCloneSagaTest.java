package org.mage.test.cards.single.spm;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author Jmlundeen
 */
public class TheCloneSagaTest extends CardTestPlayerBase {

    /*
    The Clone Saga
    {3}{U}
    Enchantment - Saga
    (As this Saga enters step, add a lore counter. Sacrifice after III.)
    I -- Surveil 3.
    II -- When you next cast a creature spell this turn, copy it, except the copy isn't legendary.
    III -- Choose a card name. Whenever a creature with the chosen name deals combat damage to a player this turn, draw a card.
    */
    private static final String theCloneSaga = "The Clone Saga";

    /*
    Ragavan, Nimble Pilferer
    {R}
    Legendary Creature - Monkey Pirate
    Whenever Ragavan, Nimble Pilferer deals combat damage to a player, create a Treasure token and exile the top card of that player's library. Until end of turn, you may cast that card.
    Dash {1}{R}
    2/1
    */
    private static final String ragavanNimblePilferer = "Ragavan, Nimble Pilferer";

    @Test
    public void testTheCloneSaga() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, theCloneSaga);
        addCard(Zone.HAND, playerA, ragavanNimblePilferer);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");

        addTarget(playerA, TestPlayer.TARGET_SKIP);
        setChoice(playerA, "Mountain", 2);

        setChoice(playerA, ragavanNimblePilferer);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, ragavanNimblePilferer);
        setChoice(playerA, "Cast with no alternative cost");

        attack(3, playerA, ragavanNimblePilferer);
        attack(3, playerA, ragavanNimblePilferer);
        setChoice(playerA, "Whenever a creature with the chosen name", 2);
        setChoice(playerA, "Whenever {this} deals");

        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 20 - 2 - 2);
        assertHandCount(playerA, 1 + 1 + 1); // 1 draw + 2 triggers
        assertPermanentCount(playerA, "Treasure Token", 2);
    }
}