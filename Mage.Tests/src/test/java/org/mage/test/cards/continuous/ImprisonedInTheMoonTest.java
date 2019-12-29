/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mage.test.cards.continuous;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public class ImprisonedInTheMoonTest extends CardTestPlayerBase {
    
    /* 
     * Reported bug: Urza land enchanted with Imprisoned In the Moon incorrectly makes it so other Urza lands only tap for 1 <> .
     * 
     * Card ruling: https://gatherer.wizards.com/Pages/Card/Details.aspx?multiverseid=414360
     *   If the enchanted permanent is a land and has land types, it retains those types even though it loses any intrinsic mana abilities associated with them. 
     *   For example, a Plains enchanted by Imprisoned in the Moon is still a Plains, but it can't tap for {W}, only for {C}.
    */
    @Test
    public void testEnchantUrzaLand() {
        
        // Imprisoned in the Moon - Enchantment - Aura - {2}{U}
        // Enchant creature, land, or planeswalker
        // Enchanted permanent is a colorless land with "Tap: Add Colorless" and loses all other card types and abilities.
        addCard(Zone.HAND, playerA, "Imprisoned in the Moon", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        addCard(Zone.BATTLEFIELD, playerB, "Urza's Mine", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Urza's Tower", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Urza's Power Plant", 1);
        addCard(Zone.HAND, playerB, "Wurmcoil Engine", 1); // {6} Wurm - 6/6 Deathtouch and Lifelink
        addCard(Zone.HAND, playerB, "Bonesplitter", 1); // {1} Equipament
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Imprisoned in the Moon");
        addTarget(playerA, "Urza's Mine");
        // 6 total mana available - 3 from Tower, 2 from Powerplant, 1 from enchanted Mine
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Wurmcoil Engine");
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Bonesplitter");
        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();
                
        assertPermanentCount(playerA, "Imprisoned in the Moon", 1);
        assertPermanentCount(playerB, "Wurmcoil Engine", 1); 
        assertHandCount(playerB,"Wurmcoil Engine", 0);        
        assertHandCount(playerB, "Bonesplitter", 1); // should never be cast - wurmcoil uses up all mana
        assertPermanentCount(playerB, "Bonesplitter", 0);
    }
}
