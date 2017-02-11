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
package org.mage.test.cards.abilities.other;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Ignore;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author jeffwadsworth
 */
public class AuratouchedMageTest extends CardTestPlayerBase {

    /**
     * Auratouched Mage Creature — Human Wizard 3/3, 5W When Auratouched Mage
     * enters the battlefield, search your library for an Aura card that could
     * enchant it. If Auratouched Mage is still on the battlefield, put that
     * Aura card onto the battlefield attached to it. Otherwise, reveal the Aura
     * card and put it into your hand. Then shuffle your library.
     *
     */
    @Test
    public void testAuratouchedMageEffectHasMadeIntoTypeArtifact() {
        //Any Aura card you find must be able to enchant Auratouched Mage as it currently exists, or as it most recently existed on the battlefield if it’s no 
        //longer on the battlefield. If an effect has made the Mage an artifact, for example, you could search for an Aura with “enchant artifact.”
        //Expected result: An effect has made Auratouched Mage into an artifact upon entering the battlefield.  An aura that only works on artifacts should work.
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 7);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 7);
        addCard(Zone.HAND, playerA, "Auratouched Mage"); //5W cost
        addCard(Zone.HAND, playerA, "Argent Mutation"); //2U cost.  Target is an artifact until end of turn
        addCard(Zone.LIBRARY, playerA, "Relic Ward"); //Only enchants an artifact permanent

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Auratouched Mage");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Argent Mutation", "Auratouched Mage");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);

        execute();

        assertPermanentCount(playerA, "Auratouched Mage", 1);
        assertPermanentCount(playerA, "Relic Ward", 1);

    }

    @Test
    public void testGainsLegalAura() {
        // Expected result: Brainwash gets placed on Auratouched Mage
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 7);
        addCard(Zone.HAND, playerA, "Auratouched Mage");
        addCard(Zone.LIBRARY, playerA, "Brainwash");//legal aura for Auratouched Mage

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Auratouched Mage");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);

        execute();

        assertPermanentCount(playerA, "Auratouched Mage", 1);
        assertPermanentCount(playerA, "Brainwash", 1);

    }
    
    /*
    @Ignore //If someone knows the way to elegantly handle the test mechanism in regards to no valid targets, please modify.  The test works fine in practice.
    @Test
    public void testAuratouchedMageNotOnBattlefield() {
        // Expected result: Auratouched Mage is exiled immediately after entering the battlefield, the legal aura (Brainwash) gets put into controller's hand
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 7);
        addCard(Zone.HAND, playerA, "Auratouched Mage");
        addCard(Zone.HAND, playerA, "Swords to Plowshares"); //exiles Auratouched Mage
        addCard(Zone.LIBRARY, playerA, "Brainwash"); //valid aura for Auratouched Mage
        addCard(Zone.LIBRARY, playerA, "Animate Wall"); //not a valid aura for the Auratouched Mage

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Auratouched Mage");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Swords to Plowshares", "Auratouched Mage");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);

        execute();

        assertPermanentCount(playerA, "Auratouched Mage", 0);
        assertPermanentCount(playerA, "Brainwash", 0);
        assertHandCount(playerA, "Brainwash", 1);
        assertLibraryCount(playerA, "Animate Wall", 1);

    }
*/

}
