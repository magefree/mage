package org.mage.test.cards.single.ltr;

import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

import mage.constants.PhaseStep;
import mage.constants.Zone;

/**
 * @author Xanderhall
 */
public class ForgeAnewTest extends CardTestPlayerBase {
    
    static final String FORGE_ANEW = "Forge Anew";
    static final String EQUIPMENT = "Bronze Sword";
    static final String CREATURE = "Silvercoat Lion";

    /**
     * Test implementation of card
     */

    @Test
    public void testForgeAnew() {
        addCard(Zone.BATTLEFIELD, playerA, CREATURE, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Zone.HAND, playerA, FORGE_ANEW, 1);
        addCard(Zone.GRAVEYARD, playerA, EQUIPMENT, 1);

        // Test return from graveyard
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, FORGE_ANEW);
        addTarget(playerA, EQUIPMENT, 1);

        // Test can equip at instant speed for 0 (all mana tapped)
        activateAbility(1, PhaseStep.END_COMBAT, playerA, "Equip {3}", CREATURE);
        setChoice(playerA, true); // Choose to equip for free

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        
        // Make sure it is attached
        assertAttachedTo(playerA, EQUIPMENT, CREATURE, true);
    }
}
