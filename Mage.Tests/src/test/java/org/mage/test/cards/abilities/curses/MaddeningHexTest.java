package org.mage.test.cards.abilities.curses;

import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

import mage.constants.PhaseStep;
import mage.constants.Zone;

/**
 * {@link mage.cards.m.MaddeningHex Maddening Hex}
 * {2}{R}
 * Enchantment — Aura Curse
 * Enchant player
 * Whenever enchanted player casts a noncreature spell, roll a d6. Maddening Hex deals damage to that player equal to the result. Then attach Maddening Hex to another one of your opponents chosen at random.
 *
 * @author alexander-novo
 */
public class MaddeningHexTest extends CardTestPlayerBase {
    private static final String hex = "Maddening Hex";

    /**
     * Reported bug: https://github.com/magefree/mage/issues/10302
     * 
     * If Maddening Hex is attached to the last one of your opponents (there are no other opponents to re-attach it to),
     * then it simply dettachs itself and dies from state based actions. Instead it should keep itself attached to that
     * opponent.
     */
    @Test
    public void lastOpponentTest() {
        // {R} - noncreature spell
        String bolt = "Lightning Bolt";
        String mountain = "Mountain";

        // The roll result for maddening hex trigger
        int hex_die_result = 6;

        setStrictChooseMode(true);

        // Necessary cards for the test
        addCard(Zone.HAND, playerA, hex);
        addCard(Zone.HAND, playerB, bolt);

        // Mana so that players can play their cards
        addCard(Zone.BATTLEFIELD, playerA, mountain, 3);
        addCard(Zone.BATTLEFIELD, playerB, mountain, 1);

        // Player A attaches hex to player B
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, hex, playerB);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, 1);

        // Player B casts a noncreature spell. Make sure hex triggers.
        // For some reason this must be done on turn 2. If done on turn 1, the test will break.
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, bolt, playerA);
        checkStackObject("after bolt", 2, PhaseStep.PRECOMBAT_MAIN, playerA,
                "Whenever enchanted player casts a noncreature spell", 1);

        this.setDieRollResult(playerA, hex_die_result);

        setStopAt(2, PhaseStep.PRECOMBAT_MAIN);
        execute();

        // Make sure player B lost life
        assertLife(playerB, 20 - hex_die_result);
        assertPermanentCount(playerA, hex, 1);
    }
}
