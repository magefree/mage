package org.mage.test.cards.single.ncc;

import mage.abilities.keyword.IndestructibleAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * Weathered Sentinels
 * {3}
 * Artifact Creature — Wall
 * Defender, vigilance, reach, trample
 * Weathered Sentinels can attack players who attacked you during their last turn as though it didn’t have defender.
 * Whenever Weathered Sentinels attacks, it gets +3/+3 and gains indestructible until end of turn.
 */
public class WeatheredSentinelsTest extends CardTestPlayerBase {

    private static final String weatheredSentinels = "Weathered Sentinels";
    // 1/1 Haste attacker
    private static final String gingerBrute = "Gingerbrute";

    /**
     * Should not be able to attack a player who did not attack you on their last turn
     */
    @Test
    public void testCantAttackNonAttacker() {
        addCard(Zone.BATTLEFIELD, playerA, weatheredSentinels);

        attack(1, playerA, weatheredSentinels);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        try {
            execute();
        } catch (Throwable e) {
            if (!e.getMessage().contains("Player PlayerA must have 0 actions but found 1")) {
                Assert.fail("Should have had error about playerA not being able to attack, but got:\n" + e.getMessage());
            }
        }
    }

    /**
     * Should be able to attack a player that attacked you on their last turn, and it should get +3/+3 and indestructible until end of turn.
     */
    @Test
    public void testCanAttackAttacker() {
        addCard(Zone.BATTLEFIELD, playerA, weatheredSentinels);
        addCard(Zone.BATTLEFIELD, playerB, gingerBrute);

        // Attack playerA
        attack(2, playerB, gingerBrute);

        // Attack back
        attack(3, playerA, weatheredSentinels);

        // Check that Weathered Sentinels has a +3/+3 and indestructible
        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertAbility(playerA, weatheredSentinels, IndestructibleAbility.getInstance(), true);
        assertPowerToughness(playerA, weatheredSentinels, 5, 8);

        // Check that Weathered Sentinels lost the abilities next turn
        setStopAt(4, PhaseStep.PRECOMBAT_MAIN);
        execute();
    }
}
