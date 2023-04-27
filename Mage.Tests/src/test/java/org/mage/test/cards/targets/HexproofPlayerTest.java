/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mage.test.cards.targets;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author jeffwadsworth
 */
public class HexproofPlayerTest extends CardTestPlayerBase {
    
    /*
    Test hexproof gained via both a static ability from a permanent and from an instant spell
    */

    @Test
    public void leyLineOfSanctityOpponentCantTargetTest() {
        https://github.com/magefree/mage/issues/5630
        setStrictChooseMode(true);
        addCard(Zone.BATTLEFIELD, playerA, "Leyline of Sanctity");  // controller has hexproof
        addCard(Zone.BATTLEFIELD, playerB, "Liliana Vess"); // target player discards a card
        addCard(Zone.HAND, playerA, "Memnite");
        
        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerB,
                "+1: Target player discards a card.", playerA);
        
        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, 0);
        
    }
    
    @Test
    public void leyLineOfSanctityControllerCanTargetThemselfTest() {
        https://github.com/magefree/mage/issues/5630
        setStrictChooseMode(true);
        addCard(Zone.BATTLEFIELD, playerA, "Leyline of Sanctity");  // controller has hexproof
        addCard(Zone.BATTLEFIELD, playerA, "Liliana Vess"); // target player discards a card
        addCard(Zone.HAND, playerA, "Memnite", 1);
        
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA,
                "+1: Target player discards a card.", playerA);
        
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, 1);
        
    }
    
    @Test
    public void veilOfSummerOpponentCantTargetTest() {
        https://github.com/magefree/mage/issues/5630
        setStrictChooseMode(true);
        addCard(Zone.HAND, playerA, "Veil of Summer");  // Instant : controller has hexproof
        addCard(Zone.BATTLEFIELD, playerB, "Liliana Vess"); // target player discards a card
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);  // mana of Veil of Summer
        addCard(Zone.HAND, playerA, "Memnite");
        
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA, "Veil of Summer");  // controller has hexproof
        
        activateAbility(2, PhaseStep.POSTCOMBAT_MAIN, playerB,
                "+1: Target player discards a card.", playerA);
        
        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, 1);  // the Veil of Summer card only, no discarded card
    }
    
    @Test
    public void veilOfSummerControllerCanTargetThemselfTest() {
        https://github.com/magefree/mage/issues/5630
        setStrictChooseMode(true);
        addCard(Zone.HAND, playerA, "Veil of Summer");  // Instant : controller has hexproof
        addCard(Zone.BATTLEFIELD, playerA, "Liliana Vess"); // target player discards a card
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);  // mana of Veil of Summer
        addCard(Zone.HAND, playerA, "Memnite");
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Veil of Summer");  // controller has hexproof
        
        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA,
                "+1: Target player discards a card.", playerA);
        
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, 2);
    }
}
