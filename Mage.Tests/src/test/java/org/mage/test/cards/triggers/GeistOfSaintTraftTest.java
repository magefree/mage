
package org.mage.test.cards.triggers;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author LevelX2
 */
public class GeistOfSaintTraftTest extends CardTestPlayerBase {

    /**
     * Geist of Saint Traft - Legendary Spirit Cleric 2/2, {1}{W}{U}
     *
     * Hexproof Whenever Geist of Saint Traft attacks, put a 4/4 white Angel
     * creature token with flying onto the battlefield tapped and attacking.
     * Exile that token at end of combat.
     *
     */
    @Test
    public void testTokenwillBeCreated() {

        addCard(Zone.BATTLEFIELD, playerB, "Geist of Saint Traft", 1);

        attack(2, playerB, "Geist of Saint Traft");

        setStopAt(2, PhaseStep.COMBAT_DAMAGE);
        execute();

        assertPermanentCount(playerB, "Geist of Saint Traft", 1);
        assertPowerToughness(playerB, "Geist of Saint Traft", 2, 2);
        assertPermanentCount(playerB, "Angel Token", 1);
        assertPowerToughness(playerB, "Angel Token", 4, 4);

        assertLife(playerA, 14);
        assertLife(playerB, 20);
    }

    @Test
    public void testTokenwillBeExiled() {

        addCard(Zone.BATTLEFIELD, playerB, "Geist of Saint Traft", 1);

        attack(2, playerB, "Geist of Saint Traft");

        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerB, "Geist of Saint Traft", 1);
        assertPowerToughness(playerB, "Geist of Saint Traft", 2, 2);
        assertPermanentCount(playerB, "Angel Token", 0);

        assertLife(playerA, 14);
        assertLife(playerB, 20);
    }
}
