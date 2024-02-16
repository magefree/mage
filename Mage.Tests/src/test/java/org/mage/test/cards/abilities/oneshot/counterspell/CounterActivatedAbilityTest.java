package org.mage.test.cards.abilities.oneshot.counterspell;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class CounterActivatedAbilityTest extends CardTestPlayerBase {

    /**
     * The Card 'Sqelch' is bugged. In a game versus a human I tried to counter
     * a Toloria West activation as well as a Elspeth Sun's Champion activation
     * to make the game roll back to the begining of the turn and to show a pop
     * pup with an error message which I cannot post here due to forum
     * limitations.
     *
     */
    @Test
    public void testSquelch() {
        // +1: Create three 1/1 white Soldier creature tokens.
        // -3: Destroy all creatures with power 4 or greater.
        // -7: You get an emblem with "Creatures you control get +2/+2 and have flying."
        addCard(Zone.BATTLEFIELD, playerA, "Elspeth, Sun's Champion", 1);

        // Counter target activated ability
        addCard(Zone.HAND, playerB, "Squelch", 1); // Instant {1}{U}
        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "+1");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Squelch");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerB, "Squelch", 1);
        assertPermanentCount(playerA, "Soldier Token", 0);

    }

}
