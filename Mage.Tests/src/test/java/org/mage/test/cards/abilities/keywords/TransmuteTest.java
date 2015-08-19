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
 * 702.52. Transmute
 *
 * 702.52a Transmute is an activated ability that functions only while the card
 * with transmute is in a player’s hand. “Transmute [cost]” means “[Cost],
 * Discard this card: Search your library for a card with the same converted
 * mana cost as the discarded card, reveal that card, and put it into your hand.
 * Then shuffle your library. Play this ability only any time you could play a
 * sorcery.”
 *
 * 702.52b Although the transmute ability is playable only if the card is in a
 * player’s hand, it continues to exist while the object is in play and in all
 * other zones. Therefore objects with transmute will be affected by effects
 * that depend on objects having one or more activated abilities.
 *
 * @author LevelX2
 */
public class TransmuteTest extends CardTestPlayerBase {

    @Test
    public void searchSimpleOneManaCmcSpell() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        // Target creature gets -3/-0 until end of turn.
        // Transmute {1}{U}{U}
        addCard(Zone.HAND, playerA, "Dizzy Spell");

        addCard(Zone.LIBRARY, playerA, "Lightning Bolt");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Transmute {1}{U}{U}");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Dizzy Spell", 1);
        assertHandCount(playerA, "Lightning Bolt", 1);
    }

    @Test
    public void searchSplittedCardOneManaCmcSpell() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        addCard(Zone.HAND, playerA, "Dizzy Spell");

        addCard(Zone.LIBRARY, playerA, "Wear // Tear");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Transmute {1}{U}{U}");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Dizzy Spell", 1);
        assertHandCount(playerA, "Wear", 1); // Filter search can only search for one side of a split card
        assertHandCount(playerA, "Tear", 1); // Filter search can only search for one side of a split card
    }

}
