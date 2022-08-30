package org.mage.test.cards.single.mor;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * {@link mage.cards.s.SharedAnimosity Shared Animosity}
 * {2}{R}
 * Enchantment
 * Whenever a creature you control attacks, it gets +1/+0 until end of turn for each other attacking creature that shares a creature type with it.
 *
 * @author Alex-Vasile
 */
public class SharedAnimosityTest extends CardTestPlayerBase {

    private static final String sharedAnimosity = "Shared Animosity";

    /**
     * Reported bug: https://github.com/magefree/mage/issues/9428
     *      "Shared Animosity Giving a +1/+0 to a single attacking creature,
     *      Silvar, Devourer of the Free attacked alone and got the +1".
     */
    @Test
    public void attackingAlone() {
        // 4/2
        String silvar = "Silvar, Devourer of the Free";
        addCard(Zone.BATTLEFIELD, playerA, sharedAnimosity);
        addCard(Zone.BATTLEFIELD, playerA, silvar);

        attack(1, playerA, silvar);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();
        assertPowerToughness(playerA, silvar, 4, 2); // Should not have gotten a boost
        assertLife(playerB, 20 - 4);
    }

    /**
     * Attack with 3 creatures.
     * One shares types with both of the others.
     * The other two only share a type with the 3rd, but not with each other.
     */
    @Test
    public void attackingWithOthers() {
        // 4/2
        // Cat Nightmare
        String silvar = "Silvar, Devourer of the Free";
        // 2/2
        // Cat Soldier
        String ajanisPridemate = "Ajani's Pridemate";
        // 1/5
        // Gloom Pangolin
        String gloomPangolin = "Gloom Pangolin";

        addCard(Zone.BATTLEFIELD, playerA, sharedAnimosity);
        addCard(Zone.BATTLEFIELD, playerA, ajanisPridemate);
        addCard(Zone.BATTLEFIELD, playerA, gloomPangolin);
        addCard(Zone.BATTLEFIELD, playerA, silvar);

        attack(1, playerA, silvar);
        attack(1, playerA, ajanisPridemate);
        attack(1, playerA, gloomPangolin);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();
        assertPowerToughness(playerA, silvar, 4+2, 2); // Shares with both the pridemate and the pangolin
        assertPowerToughness(playerA, ajanisPridemate, 2+1, 2); // Shares only with Silvar
        assertPowerToughness(playerA, gloomPangolin, 1+1, 5); // Shares only with Silvar
        assertLife(playerB, 20 - (4+2) - (2+1) - (1+1));
    }
}
