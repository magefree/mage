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

package org.mage.test.cards.abilities.keywords;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author BetaSteward
 */
public class ChampionTest extends CardTestPlayerBase {

    /**
     * 702.71. Champion
     * 702.71a Champion represents two triggered abilities. “Champion an [object]” means “When this
     * permanent enters the battlefield, sacrifice it unless you exile another [object] you control” and
     * “When this permanent leaves the battlefield, return the exiled card to the battlefield under its
     * owner’s control.”
     * 
     * 702.71b The two abilities represented by champion are linked. See rule 607, “Linked Abilities.”
     * 
     * 702.71c A permanent is “championed” by another permanent if the latter exiles the former as the
     * direct result of a champion ability.
     * 
     */

    /** 
     * Lightning Crafter
     * Creature — Goblin Shaman 3/3, 3R (4)
     * Champion a Goblin or Shaman (When this enters the battlefield, sacrifice 
     * it unless you exile another Goblin or Shaman you control. When this 
     * leaves the battlefield, that card returns to the battlefield.)
     * {T}: Lightning Crafter deals 3 damage to target creature or player.
     *
     */

    @Test
    public void testChampionCreature() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Goblin Roughrider");
        addCard(Zone.HAND, playerA, "Lightning Crafter");


        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Crafter");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Lightning Crafter", 1);
        assertExileCount("Goblin Roughrider", 1);

    }

    @Test
    public void testExiledCreatureReturns() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Goblin Roughrider");
        addCard(Zone.HAND, playerA, "Lightning Crafter");


        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Crafter");
        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: {source} deals 3 damage to target creature or player.", "Lightning Crafter");

        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Lightning Crafter", 0);
        assertPermanentCount(playerA, "Goblin Roughrider", 1);
        assertExileCount("Goblin Roughrider", 0);
        assertGraveyardCount(playerA, "Lightning Crafter", 1);

    }

}
