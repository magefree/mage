

package org.mage.test.cards.rules;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */

public class ActivateAbilityOnlyLimitedTimesTest extends CardTestPlayerBase {

    /**
     * Wall of Roots    {1}{G}
     * Creature - Plant Wall
     * 0/5
     * Defender
     * Put a -0/-1 counter on Wall of Roots: Add {G}. Activate this ability only once each turn.
     *
     */
    @Test
    public void testAbilityCanBeActivatedTwice() {

        addCard(Zone.HAND, playerA, "Runeclaw Bear");
        addCard(Zone.BATTLEFIELD, playerA, "Wall of Roots", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Runeclaw Bear");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPowerToughness(playerA, "Wall of Roots", 0, 4);
        assertPermanentCount(playerA, "Runeclaw Bear", 1);

        assertLife(playerA, 20);
        assertLife(playerB, 20);

    }

    @Test
    public void testAbilityCantBeActivatedTwice() {

        addCard(Zone.HAND, playerA, "Garruk's Companion");
        addCard(Zone.BATTLEFIELD, playerA, "Wall of Roots",2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Garruk's Companion");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPowerToughness(playerA, "Wall of Roots", 0, 4);
        assertHandCount(playerA, "Garruk's Companion", 0);
        assertPermanentCount(playerA, "Garruk's Companion", 1);

        assertLife(playerA, 20);
        assertLife(playerB, 20);

    }

    /**
     * Momentary Blink {1}{W}
     * Instant
     * Exile target creature you control, then return it to the battlefield under its owner's control.
     * Flashback (You may cast this card from your graveyard for its flashback cost. Then exile it.)
     */
    @Test
    public void testAbilityCanBeActivatedTwiceIfBlinked() {

        addCard(Zone.HAND, playerA, "Wall of Wood",2);
        addCard(Zone.HAND, playerA, "Momentary Blink");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Wall of Roots");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Wall of Wood");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Momentary Blink", "Wall of Roots", "Cast Wall of Wood");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Wall of Wood");
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPowerToughness(playerA, "Wall of Roots", 0, 4);
        assertHandCount(playerA, "Momentary Blink", 0);
        assertPermanentCount(playerA, "Wall of Wood", 2);

        
        assertLife(playerA, 20);
        assertLife(playerB, 20);

    }

    @Test
    public void test_MaxActivationsIncrease() {
        // Each power-up ability of permanents you control can be activated an additional time.
        // Power-up -- {5}{R}{R}: Put two +1/+1 counters on Wonder Man.
        addCard(Zone.HAND, playerA, "Wonder Man, Hollywood Hero",1); // {3}{R}{R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5 + 7);
        //
        // Power-up -- {5}{U}: Put three +1/+1 counters on this creature.
        addCard(Zone.BATTLEFIELD, playerA, "Aerial Doombot",1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 6 * 3);

        // once per game
        checkPlayableAbility("t1 - can activate due first usage", 1,  PhaseStep.PRECOMBAT_MAIN, playerA, "Power-up &mdash; {5}{U}", true);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Power-up &mdash; {5}{U}");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        checkPlayableAbility("t1 - can't activate due x1 usage", 1,  PhaseStep.PRECOMBAT_MAIN, playerA, "Power-up &mdash; {5}{U}", false);

        // increase to x2 per game
        checkPlayableAbility("t3 - can't activate due x1 usage", 3,  PhaseStep.PRECOMBAT_MAIN, playerA, "Power-up &mdash; {5}{U}", false);
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Wonder Man, Hollywood Hero");
        waitStackResolved(3, PhaseStep.PRECOMBAT_MAIN, playerA);
        checkPlayableAbility("t3 - can activate due limit icrease", 3,  PhaseStep.PRECOMBAT_MAIN, playerA, "Power-up &mdash; {5}{U}", true);
        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Power-up &mdash; {5}{U}");
        waitStackResolved(3, PhaseStep.PRECOMBAT_MAIN, playerA);
        checkPlayableAbility("t3 - can't activate due x2 usage", 3,  PhaseStep.PRECOMBAT_MAIN, playerA, "Power-up &mdash; {5}{U}", false);

        checkPlayableAbility("t5 - can't activate due x2 usage", 5,  PhaseStep.PRECOMBAT_MAIN, playerA, "Power-up &mdash; {5}{U}", false);
        
        setStrictChooseMode(true);
        setStopAt(5, PhaseStep.END_TURN);
        execute();
    }
}