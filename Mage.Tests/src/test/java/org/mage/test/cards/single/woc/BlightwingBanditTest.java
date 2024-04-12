package org.mage.test.cards.single.woc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class BlightwingBanditTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.b.BlightwingBandit Blightwing Bandit} {3}{B}
     * Creature — Faerie Rogue
     * Flying, deathtouch
     * Whenever you cast your first spell during each opponent’s turn, look at the top card of that player’s library, then exile it face down. You may play that card for as long as it remains exiled, and mana of any type can be spent to cast it.
     * 2/2
     */
    private static final String bandit = "Blightwing Bandit";

    @Test
    public void test_Simple() {
        setStrictChooseMode(true);
        skipInitShuffling();

        addCard(Zone.BATTLEFIELD, playerA, bandit);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        addCard(Zone.HAND, playerA, "Lightning Bolt");
        addCard(Zone.LIBRARY, playerB, "Fire // Ice", 1); // split card just to check it works on both faces

        castSpell(2, PhaseStep.UPKEEP, playerA, "Lightning Bolt", playerB);
        waitStackResolved(2, PhaseStep.UPKEEP); // resolve trigger from bandit
        checkPlayableAbility("can cast fire", 2, PhaseStep.UPKEEP, playerA, "Cast Fire", true);
        checkPlayableAbility("can cast ice", 2, PhaseStep.UPKEEP, playerA, "Cast Ice", true);
        castSpell(2, PhaseStep.UPKEEP, playerA, "Ice", bandit);

        setStopAt(2, PhaseStep.DRAW);
        execute();

        assertGraveyardCount(playerB, "Fire // Ice", 1);
        assertTappedCount("Mountain", true, 3);
        assertHandCount(playerA, 1); // draw from Ice
        assertTapped(bandit, true); // tapped from Ice
        assertLife(playerB, 20 - 3);
    }
}
