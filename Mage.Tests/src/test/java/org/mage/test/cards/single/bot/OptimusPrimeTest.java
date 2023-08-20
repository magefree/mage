package org.mage.test.cards.single.bot;

import mage.abilities.keyword.TrampleAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author xenohedron
 */
public class OptimusPrimeTest extends CardTestPlayerBase {
    
    private static final String optimus = "Optimus Prime, Hero";
    /*
     * Optimus Prime, Hero {3}{U}{R}{W}
     * Legendary Artifact Creature — Robot
     *
     * More Than Meets the Eye {2}{U}{R}{W} (You may cast this card converted for {2}{U}{R}{W}.)
     * At the beginning of each end step, bolster 1. (Choose a creature with the least toughness among creatures you control and put a +1/+1 counter on it.)
     * When Optimus Prime dies, return it to the battlefield converted under its owner’s control.
     * 4/8
     *
     * Optimus Prime, Autobot Leader
     * Legendary Artifact — Vehicle
     *
     * Living metal (As long as it’s your turn, this Vehicle is also a creature.)
     * Trample
     * Whenever you attack, bolster 2. The chosen creature gains trample until end of turn.
     * When that creature deals combat damage to a player this turn, convert Optimus Prime.
     * 6/8
     */
    private static final String ghoul = "Gutless Ghoul"; // 2/2 "{1}, Sacrifice a creature: You gain 2 life."
    private static final String myr = "Omega Myr"; // 1/2
    private static final String centaur = "Centaur Courser"; // 3/3

    @Test
    public void testOptimusPrime() {
        addCard(Zone.BATTLEFIELD, playerA, optimus);
        addCard(Zone.BATTLEFIELD, playerA, ghoul);
        addCard(Zone.BATTLEFIELD, playerA, myr);
        addCard(Zone.BATTLEFIELD, playerA, "Wastes");
        addCard(Zone.BATTLEFIELD, playerB, centaur);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}, Sacrifice"); // gain 2 life
        setChoice(playerA, optimus); // sac, returns transformed

        attack(1, playerA, ghoul, playerB);
        addTarget(playerA, ghoul); // choice for bolster, becomes a 4/4 with trample
        block(1, playerB, centaur, ghoul);
        setChoice(playerA, "X=3"); // assign 3 damage to centaur, 1 damage tramples over
        // optimus triggers and transforms
        // at end step, bolster 1, only target is myr

        setStopAt(2, PhaseStep.UPKEEP);
        setStrictChooseMode(true);
        execute();

        assertLife(playerA, 20 + 2);
        assertLife(playerB, 20 - 1);
        assertGraveyardCount(playerB, centaur, 1);
        assertGraveyardCount(playerA, optimus, 0);
        assertPowerToughness(playerA, optimus, 4, 8);
        assertPowerToughness(playerA, ghoul, 4, 4);
        assertPowerToughness(playerA, myr, 2, 3);
        assertAbility(playerA, ghoul, TrampleAbility.getInstance(), false);

    }

}
