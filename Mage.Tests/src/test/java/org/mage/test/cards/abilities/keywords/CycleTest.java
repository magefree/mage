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
 * @author LevelX2
 */
public class CycleTest extends CardTestPlayerBase {

    /**
     * 702.28. Cycling
     *  702.28a Cycling is an activated ability that functions only while the card with cycling is in a player’s hand. 
     *          “Cycling [cost]” means “[Cost], Discard this card: Draw a card.”
     *  702.28b Although the cycling ability is playable only if the card is in a player’s hand, it continues to exist
     *          while the object is in play and in all other zones. Therefore objects with cycling will be affected by
     *          effects that depend on objects having one or more activated abilities.
     *  702.28c Some cards with cycling have abilities that trigger when they’re cycled. “When you cycle [this card]” means
     *          “When you discard [this card] to pay a cycling cost.” These abilities trigger from whatever zone the card 
     *          winds up in after it’s cycled.
     *  702.28d Typecycling is a variant of the cycling ability. “[Type]cycling [cost]” means “[Cost], Discard this card:
     *          Search your library for a [type] card, reveal it, and put it into your hand. Then shuffle your library.” 
     *          This type is usually a subtype (as in “mountaincycling”) but can be any card type, subtype, supertype, or
     *          combination thereof (as in “basic landcycling”).
     *  702.28e Typecycling abilities are cycling abilities, and typecycling costs are cycling costs. Any cards that trigger
     *          when a player cycles a card will trigger when a card is discarded to pay a typecycling cost. Any effect that
     *          stops players from cycling cards will stop players from activating cards’ typecycling abilities. Any effect
     *          that increases or reduces a cycling cost will increase or reduce a typecycling cost.
     */

    @Test
    public void CycleAndTriggerTest() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);
        // Destroy all creatures. They can't be regenerated. Draw a card for each creature destroyed this way.
        // Cycling {3}{B}{B}
        // When you cycle Decree of Pain, all creatures get -2/-2 until end of turn.
        addCard(Zone.HAND, playerA, "Decree of Pain");
        
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Pillarfield Ox", 1);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cycling {3}{B}{B}");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertHandCount(playerA, 1);        
        
        assertGraveyardCount(playerA, "Decree of Pain", 1);
        assertPermanentCount(playerA, "Silvercoat Lion", 0);
        
        assertPermanentCount(playerB, "Pillarfield Ox", 1);
        assertPowerToughness(playerB, "Pillarfield Ox", 0, 2);
        

    }

    /**
     * Cycle from graveyard or battlefield may not work
     */
    @Test
    public void CycleFromGraveyard() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);
        // Destroy all creatures. They can't be regenerated. Draw a card for each creature destroyed this way.
        // Cycling {3}{B}{B}
        // When you cycle Decree of Pain, all creatures get -2/-2 until end of turn.
        addCard(Zone.GRAVEYARD, playerA, "Decree of Pain");
        // Protection from black
        // Cycling {2} ({2}, Discard this card: Draw a card.)
        addCard(Zone.BATTLEFIELD, playerB, "Disciple Of Grace");
        
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cycling {3}{B}{B}");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Cycling {2}");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertHandCount(playerA, 0);                
        assertHandCount(playerB, 0);        
        
        assertGraveyardCount(playerA, "Decree of Pain", 1);
        assertPermanentCount(playerB, "Disciple Of Grace", 1);       

    }

}
