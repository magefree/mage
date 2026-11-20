

package org.mage.test.cards.abilities.other;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author BetaSteward
 */
public class ChiefEngineerTest extends CardTestPlayerBase {

    /** 
     * Chief Engineer
     * Creature — Vedalken Artificer 1/3, 1U (2)
     * Artifact spells you cast have convoke. (Your creatures can help cast 
     * those spells. Each creature you tap while casting an artifact spell pays 
     * for {1} or one mana of that creature's color.)
     *
     */

    @Test
    public void testGainsConvoke() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Chief Engineer");
        // makes Fortress Crab an artifact spell
        addCard(Zone.BATTLEFIELD, playerA, "Encroaching Mycosynth");
        addCard(Zone.BATTLEFIELD, playerA, "Alpha Myr");
        // {3}{U}
        addCard(Zone.HAND, playerA, "Fortress Crab");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Fortress Crab");
        addTarget(playerA, "Chief Engineer^Alpha Myr");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Fortress Crab", 1);
        assertTapped("Chief Engineer", true);
        assertTapped("Alpha Myr", true);

    }
   
}
