package org.mage.test.cards.abilities.keywords;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBaseWithAIHelps;

/**
 * @author LevelX2
 */
public class SupportTest extends CardTestPlayerBaseWithAIHelps {

    /**
     * Support Ability can target its source. Its cannot really.
     */
    @Test
    public void testCreatureSupport() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 7);
        // When Gladehart Cavalry enters the battlefield, support 6. <i>(Put a +1/+1 counter on each of up to six other target creatures.)</i>
        // Whenever a creature you control with a +1/+1 counter on it dies, you gain 2 life.
        addCard(Zone.HAND, playerA, "Gladehart Cavalry"); // {5}{G}{G} 6/6

        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion"); // 2/2
        addCard(Zone.BATTLEFIELD, playerA, "Pillarfield Ox"); // 2/4

        // test framework do not support possible target checks, so allow AI to cast and choose maximum targets
        aiPlayPriority(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        //castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Gladehart Cavalry");
        //addTarget(playerA, "Silvercoat Lion^Pillarfield Ox^Gladehart Cavalry");// Gladehart Cavalry should not be allowed

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPowerToughness(playerA, "Silvercoat Lion", 2 + 1, 2 + 1);
        assertPowerToughness(playerA, "Pillarfield Ox", 2 + 1, 4 + 1);
        assertPowerToughness(playerA, "Gladehart Cavalry", 6, 6); // no counters
    }

}
