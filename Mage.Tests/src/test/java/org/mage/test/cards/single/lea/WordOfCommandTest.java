package org.mage.test.cards.single.lea;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class WordOfCommandTest extends CardTestPlayerBase {

    // {B}{B} Instant
    // Look at target opponent's hand and choose a card from it. You control that player until Word of Command
    // finishes resolving. The player plays that card if able. While doing so, the player can activate mana abilities
    // only if they're from lands that player controls and only if mana they produce is spent to activate other mana
    // abilities of lands the player controls and/or to play that card.
    private static final String word = "Word of Command";

    @Test
    public void test_ConvokePaysWholeCost() {
        addCard(Zone.HAND, playerA, word);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        // {2}{R}{R} Instant, convoke
        // Stoke the Flames deals 4 damage to any target.
        addCard(Zone.HAND, playerB, "Stoke the Flames");
        addCard(Zone.BATTLEFIELD, playerB, "Goblin Racketeer", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, word, playerB);
        setChoice(playerA, "Stoke the Flames");
        setChoice(playerA, true); // acknowledge control of B
        addTarget(playerB, playerA);
        addTarget(playerB, "Goblin Racketeer", 4);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20 - 4);
        assertGraveyardCount(playerB, "Stoke the Flames", 1);
        assertTappedCount("Goblin Racketeer", true, 4);
    }
}
