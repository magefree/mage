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
package org.mage.test.cards.copy;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author BetaSteward
 */
public class SpelltwineTest extends CardTestPlayerBase {

    /**
     * Spelltwine Sorcery, 5U (6) Exile target instant or sorcery card from your
     * graveyard and target instant or sorcery card from an opponent's
     * graveyard. Copy those cards. Cast the copies if able without paying their
     * mana costs. Exile Spelltwine.
     *
     */
    @Test
    public void testCopyCards() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 6);
        addCard(Zone.HAND, playerA, "Spelltwine");
        addCard(Zone.GRAVEYARD, playerA, "Lightning Bolt");
        addCard(Zone.GRAVEYARD, playerB, "Shock");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Spelltwine");
        addTarget(playerA, "Lightning Bolt");
        addTarget(playerA, "Shock");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertExileCount("Spelltwine", 1);
        assertExileCount("Lightning Bolt", 1);
        assertExileCount("Shock", 1);
        assertLife(playerB, 15);

    }

    /**
     * In a game of Commander, I cast Spelltwine, targeting Impulse and
     * Blasphemous Act. This triggered my Mirari, which I paid the 3 and copied
     * the Spelltwine. I chose new targets for the copy, naming Path to Exile
     * and Shape Anew. Somehow, the original Spelltwine was completely lost
     * after this, failing to be in the stack box or resolve all.
     */
    @Test
    public void testCopyCardsMirari() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 9);
        // Exile target instant or sorcery card from your graveyard and target instant or sorcery card from an opponent's graveyard.
        // Copy those cards. Cast the copies if able without paying their mana costs. Exile Spelltwine.
        addCard(Zone.HAND, playerA, "Spelltwine"); // {5}{U}
        // Look at the top four cards of your library. Put one of them into your hand and the rest on the bottom of your library in any order.
        addCard(Zone.GRAVEYARD, playerA, "Impulse");
        // You draw two cards and you lose 2 life.
        addCard(Zone.GRAVEYARD, playerA, "Night's Whisper");
        // Blasphemous Act costs {1} less to cast for each creature on the battlefield.
        // Blasphemous Act deals 13 damage to each creature.
        addCard(Zone.GRAVEYARD, playerB, "Blasphemous Act");
        // Draw two cards.
        addCard(Zone.GRAVEYARD, playerB, "Divination");

        // Whenever you cast an instant or sorcery spell, you may pay {3}. If you do, copy that spell. You may choose new targets for the copy.
        addCard(Zone.BATTLEFIELD, playerA, "Mirari", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Spelltwine");
        addTarget(playerA, "Impulse");
        addTarget(playerA, "Blasphemous Act");
        setChoice(playerA, "Yes"); //  pay {3} and copy spell
        setChoice(playerA, "Yes"); // Change targets
        addTarget(playerA, "Night's Whisper");
        addTarget(playerA, "Divination");
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertExileCount("Impulse", 1);
        assertExileCount("Blasphemous Act", 1);
        assertExileCount("Spelltwine", 1);
        assertExileCount("Night's Whisper", 1);
        assertExileCount("Divination", 1);

        assertHandCount(playerA, 5);

        assertLife(playerA, 18);
        assertLife(playerB, 20);

    }
}
