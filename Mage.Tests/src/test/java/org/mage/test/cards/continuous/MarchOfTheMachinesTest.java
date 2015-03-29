/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */

package org.mage.test.cards.continuous;

import mage.constants.CardType;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author BetaSteward
 */
public class MarchOfTheMachinesTest extends CardTestPlayerBase {
    
    /**
     * March of the Machines
     * Enchantment, 3U (4)
     * Each noncreature artifact is an artifact creature with power and toughness 
     * each equal to its converted mana cost. (Equipment that's a creature can't 
     * equip a creature.)
     * 
     */
        
    /**
     * Abzan Banner
     * Artifact, 3 (3)
     * {T}: Add {W}, {B}, or {G} to your mana pool.
     * {W}{B}{G}, {T}, Sacrifice Abzan Banner: Draw a card.
     */

    @Test
    public void testNonCreatureArtifactsBecomeCreatures() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Abzan Banner");
        addCard(Zone.BATTLEFIELD, playerA, "Alloy Myr");
        addCard(Zone.HAND, playerA, "March of the Machines");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "March of the Machines");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPowerToughness(playerA, "Alloy Myr", 2, 2);
        assertPermanentCount(playerA, "Abzan Banner", 1);
        assertPowerToughness(playerA, "Abzan Banner", 3, 3);
        assertType("Abzan Banner", CardType.CREATURE, true);
    }

    @Test
    public void testArtifactsRevertBack() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Abzan Banner");
        addCard(Zone.HAND, playerA, "March of the Machines");
        
        addCard(Zone.HAND, playerB, "Disenchant");
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "March of the Machines");    
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Disenchant", "March of the Machines");

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();
        
        assertPermanentCount(playerA, "Abzan Banner", 1);
        assertType("Abzan Banner", CardType.CREATURE, false);
    }

    @Test
    public void testEquipmentDetaches() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 6);
        addCard(Zone.BATTLEFIELD, playerA, "Avacyn's Collar");
        addCard(Zone.BATTLEFIELD, playerA, "Ornithopter");
        addCard(Zone.HAND, playerA, "March of the Machines");
        
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip", "Ornithopter");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "March of the Machines");

        setStopAt(1, PhaseStep.END_TURN);
        execute();
        
        assertPermanentCount(playerA, "Ornithopter", 1);
        assertPowerToughness(playerA, "Ornithopter", 0, 2);
        assertPermanentCount(playerA, "Avacyn's Collar", 1);
        assertPowerToughness(playerA, "Avacyn's Collar", 1, 1);
        assertType("Avacyn's Collar", CardType.CREATURE, true);
    }

    @Test
    public void testZeroCostIsDestroyed() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Accorder's Shield");
        addCard(Zone.HAND, playerA, "March of the Machines");
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "March of the Machines");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        
        assertPermanentCount(playerA, "Accorder's Shield", 0);
        assertGraveyardCount(playerA, "Accorder's Shield", 1);
    }
    
}
