package org.mage.test.cards.single.woe;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class DecadentDragonTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.d.DecadentDragon Decadent Dragon} {2}{R}{R}
     * Creature — Dragon
     * Flying, trample
     * Whenever Decadent Dragon attacks, create a Treasure token.
     * 4/4
     * Expensive Taste {2}{B}
     * Instant — Adventure
     * Exile the top two cards of target opponent’s library face down. You may look at and play those cards for as long as they remain exiled.
     */
    private static final String dragon = "Decadent Dragon";

    @Test
    public void test_MDFC_And_Split() {
        setStrictChooseMode(true);
        skipInitShuffling();

        addCard(Zone.HAND, playerA, dragon);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3 + 4);
        addCard(Zone.LIBRARY, playerB, "Pain // Suffering", 1); // can cast Pain, but Suffering cost {3}{R} so can't be cast
        addCard(Zone.LIBRARY, playerB, "Blackbloom Rogue", 1); // mdfc {2}{B} with land Blackbloom Bog on the back.
        addCard(Zone.HAND, playerB, "Abandon Hope"); // to discard to Pain

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Expensive Taste", playerB);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        checkPlayableAbility("can cast Pain", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Pain", true);
        checkPlayableAbility("can not cast Suffering", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Suffering", false);
        checkPlayableAbility("can cast Blackbloom Rogue", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Blackbloom Rogue", true);
        checkPlayableAbility("can play Blackbloom Bog", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Play Blackbloom Bog", true);

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Blackbloom Bog");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Pain", playerB);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerB, "Pain // Suffering", 1);
        assertGraveyardCount(playerB, "Abandon Hope", 1);
        assertTappedCount("Blackbloom Bog", true, 1);
        assertTappedCount("Swamp", true, 4);
    }
}
