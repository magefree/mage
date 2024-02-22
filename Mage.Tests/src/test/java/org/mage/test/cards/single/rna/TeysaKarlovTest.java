package org.mage.test.cards.single.rna;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author xenohedron
 */
public class TeysaKarlovTest extends CardTestPlayerBase {

    /* Teysa Karlov {2}{W}{B} 2/4
     * Legendary Creature — Human Advisor
     * If a creature dying causes a triggered ability of a permanent you control to trigger, that ability triggers an additional time.
     * Creature tokens you control have vigilance and lifelink.
     */
    private static final String teysa = "Teysa Karlov";

    /* Revel in Riches {4}{B}
     * Enchantment
     * Whenever a creature an opponent controls dies, create a Treasure token.
     * At the beginning of your upkeep, if you control ten or more Treasures, you win the game.
     */
    private static final String revel = "Revel in Riches";

    private static final String bloodthrone = "Bloodthrone Vampire"; // 1/1
    // Sacrifice a creature: Bloodthrone Vampire gets +2/+2 until end of turn.

    private static final String ministrant = "Ministrant of Obligation"; // 2/1 Afterlife 2

    @Test
    public void testTriggers() {
        addCard(Zone.BATTLEFIELD, playerA, teysa);
        addCard(Zone.BATTLEFIELD, playerB, revel);
        addCard(Zone.BATTLEFIELD, playerA, bloodthrone);
        addCard(Zone.BATTLEFIELD, playerA, ministrant);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sacrifice");
        setChoice(playerA, ministrant);
        setChoice(playerA, "Afterlife"); // order identical triggers

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPowerToughness(playerA, bloodthrone, 3, 3);
        assertGraveyardCount(playerA, ministrant, 1);
        assertPermanentCount(playerA, "Spirit Token", 4);
        assertPermanentCount(playerB, "Treasure Token", 1);
    }
}
