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
import mage.filter.Filter;
import org.junit.Ignore;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author jeffwadsworth
 */
public class LayerTests extends CardTestPlayerBase {
    @Test
    public void testBloodMoon_UrborgTombOfYawgmothInteraction() {
        // Blood Moon : Nonbasic lands are Mountains.
        // Urborg, Tomb of Yawgmoth : Each land is a Swamp in addition to its other types.
        // Expected behavior:  Urborg loses all abilities and becomes a Mountain.  The Plains does not have subtype Swamp due to this effect.
        addCard(Zone.BATTLEFIELD, playerA, "Blood Moon");
        addCard(Zone.BATTLEFIELD, playerA, "Urborg, Tomb of Yawgmoth", 1);  // non-basic land
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertType("Urborg, Tomb of Yawgmoth", CardType.LAND, "Mountain");  // Urborg is a Mountain now
        assertPermanentCount(playerA, "Swamp", 0);  // no Swamp subtypes on the battlefield
        assertPermanentCount(playerA, "Plains", 1); // the Plains is not affected by the Urborg
        assertType("Plains", CardType.LAND, "Plains");

    }
    
    @Ignore
    public void complexExampleFromLayersArticle() {
        /*In play there is a Grizzly Bears which has already been Giant Growthed, 
        a Bog Wraith enchanted by a Lignify, and Figure of Destiny with its 3rd ability activated. 
        I then cast a Mirrorweave targeting the Figure of Destiny. What does each creature look like?
        */
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears", 1);
        addCard(Zone.HAND, playerA, "Giant Growth", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Bog Wraith", 1);
        addCard(Zone.HAND, playerA, "Lignify", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Figure of Destiny", 1);
        addCard(Zone.HAND, playerA, "Mirrorweave", 1);
        
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 20);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 20);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 20);
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Giant Growth", "Grizzly Bears");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lignify", "Bog Wrath");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{R/W}{R/W}{R/W}:");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{R/W}{R/W}{R/W}{R/W}{R/W}{R/W}:");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Mirrorweave", "Figure of Destiny");
        
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();
        
        assertPermanentCount(playerA, "Figure of Destiny", 3);
        assertPowerToughness(playerA, "Figure of Destiny", 4, 4, Filter.ComparisonScope.All);
        assertPowerToughness(playerA, "Figure of Destiny", 8, 8, Filter.ComparisonScope.All);
        assertPowerToughness(playerA, "Figure of Destiny", 0, 4, Filter.ComparisonScope.All);

    }
}
