
package org.mage.test.cards.continuous;

import mage.abilities.keyword.FirstStrikeAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.filter.Filter;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * {@link mage.cards.s.SwarmSurge Swarm Surge}
 * {2}{B}
 * Devoid
 * Creatures you control get +2/+0 until end of turn.
 * Colorless creatures you control also gain first strike until end of turn.
 *
 * @author LevelX2
 */
public class SwarmSurgeTest extends CardTestPlayerBase {

    @Test
    public void testSwarmSurge() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 9);
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion");
        // Devoid
        // When Birthing Hulk enters the battlefield, put two 1/1 colorless Eldrazi Scion creature tokens onto the battlefield. They have "Sacrifice this creature: Add {C}."
        // {1}{C}: Regenerate Birthing Hulk.
        addCard(Zone.HAND, playerA, "Birthing Hulk"); // {6}{G}  5/4
        addCard(Zone.HAND, playerA, "Swarm Surge"); // {2}{B}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Birthing Hulk", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Swarm Surge");

        attack(1, playerA, "Silvercoat Lion");

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerA, "Swarm Surge", 1);

        assertPowerToughness(playerA, "Birthing Hulk", 7, 4);
        assertAbility(playerA, "Birthing Hulk", FirstStrikeAbility.getInstance(), true);
        assertPowerToughness(playerA, "Eldrazi Scion Token", 3, 1, Filter.ComparisonScope.All);
        assertAbility(playerA, "Eldrazi Scion Token", FirstStrikeAbility.getInstance(), true, 2);
        assertPowerToughness(playerA, "Silvercoat Lion", 4, 2);
        assertAbility(playerA, "Silvercoat Lion", FirstStrikeAbility.getInstance(), false);

        assertLife(playerA, 20);
        assertLife(playerB, 16);
    }

}
