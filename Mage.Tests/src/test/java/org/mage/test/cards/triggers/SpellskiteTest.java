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
package org.mage.test.cards.triggers;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */

public class SpellskiteTest extends CardTestPlayerBase {

    /**
     * Tests that Wild Defiance triggers for Spellskite if spell target is changed to Spellskite
     */
    @Test
    public void testDisabledEffectOnChangeZone() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        // Whenever a creature you control becomes the target of an instant or sorcery spell, that creature gets +3/+3 until end of turn.
        addCard(Zone.BATTLEFIELD, playerA, "Wild Defiance", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Spellskite", 1);
        addCard(Zone.HAND, playerA, "Lightning Bolt");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{UP}: Change a target of target spell or ability to {this}.", "Lightning Bolt");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Lightning Bolt", 1);
        assertPermanentCount(playerA, "Spellskite", 1);

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertPowerToughness(playerA, "Spellskite", 3, 7);
        
    }

    /**
     * If Spellskite changes controller, its activated ability can activate but doesn't resolve properly.
     * 
     * The specific instance was a Spellskite controlled by Vedalken Shackles. Land was targeted by Frost Titan,
     * controller (not owner) of Spellskite paid the redirection cost, ability went on the stack, seemed to resolve,
     * target never changed.
     * 
     */
    /**
     * TODO: This test fails sometimes when building the complete Test Project -> Find the reason
     */
    @Test
    public void testAfterChangeOfController() {
        // {T}: Add one mana of any color to your mana pool. Spend this mana only to cast a multicolored spell.
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        // {2}, {tap}: Gain control of target creature with power less than or equal to the number of Islands you control for as long as Vedalken Shackles remains tapped.
        addCard(Zone.BATTLEFIELD, playerA, "Vedalken Shackles", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Island", 6);
        // {UP}: Change a target of target spell or ability to Spellskite.
        addCard(Zone.BATTLEFIELD, playerB, "Spellskite", 1);        
        // {4}{U}{U}
        // Whenever Frost Titan becomes the target of a spell or ability an opponent controls, counter that spell or ability unless its controller pays 2.        
        // Whenever Frost Titan enters the battlefield or attacks, tap target permanent. It doesn't untap during its controller's next untap step.
        addCard(Zone.HAND, playerB, "Frost Titan", 1);
        
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{2},{T}: Gain control", "Spellskite");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Frost Titan");
        addTarget(playerB, "Silvercoat Lion");
        
        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerA, "{UP}: Change a target", "stack ability (Whenever {this} enters ");

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Spellskite", 1);
        assertPermanentCount(playerB, "Frost Titan", 1);
        
        assertTapped("Spellskite", true);
        // (Battlefield) Tapped state is not equal (Silvercoat Lion) expected:<false> but was:<true>
        assertTapped("Silvercoat Lion", false);
        
    }    
    
    /**
     * Spellskite fails to redirect Cryptic Command on itself
     */
    @Test
    public void testSpellskite() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        // Choose two -
        // Counter target spell;
        // or return target permanent to its owner's hand;
        // or tap all creatures your opponents control;
        // or draw a card.        
        addCard(Zone.HAND, playerA, "Cryptic Command");
        
        addCard(Zone.BATTLEFIELD, playerB, "Spellskite", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Island", 1);
        addCard(Zone.HAND, playerB, "Lightning Bolt", 1);
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Lightning Bolt", playerA);
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cryptic Command", "mode=1Lightning Bolt^mode=2Silvercoat Lion", "Lightning Bolt");        
        setModeChoice(playerA, "1"); // Counter target spell
        setModeChoice(playerA, "2"); // return target permanent to its owner's hand        
        
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerB, "{UP}: Change a target of target spell or ability to {this}.", "Cryptic Command");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerB, "Lightning Bolt", 1);

        assertGraveyardCount(playerA, "Cryptic Command", 1);
        
        assertHandCount(playerB, "Spellskite", 1);
        assertPermanentCount(playerB, "Silvercoat Lion", 1);

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        
    }    
}