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
package org.mage.test.cards.enchantments;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class SpreadingSeasTest extends CardTestPlayerBase {

    /**
     * Played Spreading Seas on opps manland (e.g. Blinkmoth Nexus) . He
     * activated it on response, seas resolves but the manland loses creature
     * type what should not happened.
     *
     * 305.7. If an effect changes a land’s subtype to one or more of the basic
     * land types, the land no longer has its old land type. It loses all
     * abilities generated from its rules text and its old land types, and it
     * gains the appropriate mana ability for each new basic land type. Note
     * that this doesn’t remove any abilities that were granted to the land by
     * other effects. Changing a land’s subtype doesn’t add or remove any card
     * types (such as creature) or supertypes (such as basic, legendary, and
     * snow) the land may have. If a land gains one or more land types in
     * addition to its own, it keeps its land types and rules text, and it gains
     * the new land types and mana abilities.
     *
     */
    @Test
    public void testCreatureTypeStays() {
        // Enchant land
        // When Spreading Seas enters the battlefield, draw a card.
        // Enchanted land is an Island.
        addCard(Zone.HAND, playerA, "Spreading Seas", 1); // {1}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);

        addCard(Zone.BATTLEFIELD, playerB, "Island", 1);
        // Tap: Add 1 to your mana pool.
        // {1}: Blinkmoth Nexus becomes a 1/1 Blinkmoth artifact creature with flying until end of turn. It's still a land.
        // {1}, {T}: Target Blinkmoth gets +1/+1 until end of turn.
        addCard(Zone.BATTLEFIELD, playerB, "Blinkmoth Nexus");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Spreading Seas", "Blinkmoth Nexus");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerB, "{1}:", NO_TARGET, "Spreading Seas");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertHandCount(playerA, "Spreading Seas", 0);
        assertGraveyardCount(playerA, "Spreading Seas", 0);
        assertPowerToughness(playerB, "Blinkmoth Nexus", 1, 1);

        assertPermanentCount(playerA, "Spreading Seas", 1);
        assertHandCount(playerA, 1);
    }

    @Test
    public void testUtopiaSprawlWithSpreadingSeas(){
        addCard(Zone.HAND, playerA, "Spreading Seas", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 10);
        addCard(Zone.HAND, playerA, "Utopia Sprawl");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Utopia Sprawl","Forest");
        setChoice(playerA, "Green");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Spreading Seas", "Forest");
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();
        assertNotSubtype("Forest", "Forest");
    }


    @Test
    public void testSpreadingSeasWithUrzaLand(){
        addCard(Zone.HAND, playerA, "Spreading Seas", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Urza's Tower", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 10);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Spreading Seas", "Urza's Tower");
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();
        assertNotSubtype("Urza's Tower", "Urza's");
        assertNotSubtype("Urza's Tower", "Tower");
    }
}
