package org.mage.test.cards.abilities.keywords;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author weirddan455
 */
public class WardTest extends CardTestPlayerBase {

    @Test
    public void wardMultipleAbilities() {
        addCard(Zone.HAND, playerA, "Solitude"); // https://github.com/magefree/mage/issues/8378 Test that ward counters correct ability
        addCard(Zone.HAND, playerA, "Healer's Hawk"); // Card to pitch to Solitude
        addCard(Zone.BATTLEFIELD, playerB, "Waterfall Aerialist");  // Flying, Ward 2
        setStrictChooseMode(true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Solitude");
        setChoice(playerA, "Yes"); // Use alternate casting cost
        setChoice(playerA, "Healer's Hawk");
        setChoice(playerA, "When {this} enters the battlefield, exile up to one other target creature"); // Put exile trigger on the stack first (evoke trigger will resolve first)
        addTarget(playerA, "Waterfall Aerialist");
        setChoice(playerA, "No"); // Do not pay Ward cost
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertExileCount("Healer's Hawk", 1);
        assertGraveyardCount(playerA, "Solitude", 1);
        assertPermanentCount(playerB, "Waterfall Aerialist", 1);
        assertAllCommandsUsed();
    }
}
