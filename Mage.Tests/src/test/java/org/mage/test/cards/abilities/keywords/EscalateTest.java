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
public class EscalateTest extends CardTestPlayerBase {

    @Test
    public void testUseOneMode() {

        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        // Escalate {1} (Pay this cost for each mode chosen beyond the first.)
        // Choose one or more &mdash;
        // Creatures target player controls gain trample until end of turn.
        // Savage Alliance deals 2 damage to target creature;
        // Savage Alliance deals 1 damage to each creature target opponent controls.
        addCard(Zone.HAND, playerA, "Savage Alliance"); // Instant {2}{R}

        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Savage Alliance", "mode=2Silvercoat Lion");
        setModeChoice(playerA, "2");

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerB, "Silvercoat Lion", 1);
        assertGraveyardCount(playerA, "Savage Alliance", 1);
    }

    @Test
    public void testGaddockTeegInteraction_ThreeCMC_OneMode() {

        // Noncreature spells with converted mana cost {4} or greater can't be cast.
        // Noncreature spells with {X} in their mana costs can't be cast.
        addCard(Zone.BATTLEFIELD, playerB, "Gaddock Teeg"); // {W}{G} 2/2 Legendary Kithkin

        // Escalate {1} (Pay this cost for each mode chosen beyond the first.)
        // Choose one or more —
        // * Target player discards all the cards in his or her hand, then draws that many cards.
        // * Collective Defiance deals 4 damage to target creature.
        // * Collective Defiance deals 3 damage to target opponent.
        addCard(Zone.HAND, playerA, "Collective Defiance"); // {1}{R}{R} sorcery
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Collective Defiance", playerB);
        setModeChoice(playerA, "3"); // deal 3 dmg to opponent

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerB, "Gaddock Teeg", 1);
        assertGraveyardCount(playerA, "Collective Defiance", 1);
        assertLife(playerB, 17);
    }

    // Reported bug: Escalate CMC is not calculated correctly when more than 1 mode chosen
    // With 1 extra mode, the casting cost is {2}{R}{R} but the CMC should still be 3
    @Test
    public void testGaddockTeegInteraction_ThreeCMC_TwoModes() {

        // Noncreature spells with converted mana cost {4} or greater can't be cast.
        // Noncreature spells with {X} in their mana costs can't be cast.
        addCard(Zone.BATTLEFIELD, playerB, "Gaddock Teeg"); // {W}{G} 2/2 Legendary Kithkin

        // Escalate {1} (Pay this cost for each mode chosen beyond the first.)
        // Choose one or more —
        // * Target player discards all the cards in his or her hand, then draws that many cards.
        // * Collective Defiance deals 4 damage to target creature.
        // * Collective Defiance deals 3 damage to target opponent.
        addCard(Zone.HAND, playerA, "Collective Defiance"); // {1}{R}{R} sorcery
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Collective Defiance", "mode=2Gaddock Teeg");
        setModeChoice(playerA, "2"); // deal 4 dmg to target creature (gaddock teeg)
        setModeChoice(playerA, "3"); // deal 3 dmg to opponent

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Collective Defiance", 1);
        assertGraveyardCount(playerB, "Gaddock Teeg", 1);
        assertLife(playerB, 17);
    }
    
    @Test
    public void testSpellQuellerInteraction_ThreeCMC_ThreeModes() {
        
        // {1}{W}{U} Flash Flying 2/3 Spirit
        // When Spell Queller enters the battlefield, exile target spell with converted mana cost 4 or less.
        // When Spell Queller leaves the battlefield, the exiled card's owner may cast that card without paying its mana cost.
        addCard(Zone.HAND, playerB, "Spell Queller");
        addCard(Zone.BATTLEFIELD, playerB, "Wall of Omens"); // {1}{W} 0/4
        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 1);
        
        // Escalate {1} (Pay this cost for each mode chosen beyond the first.)
        // Choose one or more —
        // * Target player discards all the cards in his or her hand, then draws that many cards.
        // * Collective Defiance deals 4 damage to target creature.
        // * Collective Defiance deals 3 damage to target opponent.
        addCard(Zone.HAND, playerA, "Collective Defiance"); // {1}{R}{R} sorcery
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Collective Defiance", "mode=2Wall of Omens");
        setModeChoice(playerA, "1"); // opponent discards hand and draws that many
        setModeChoice(playerA, "2"); // deal 4 dmg to target creature (Wall of Omens)
        setModeChoice(playerA, "3"); // deal 3 dmg to opponent        
        addTarget(playerA, playerB); // mode 1
        addTarget(playerA, playerB); // mode 3
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Spell Queller");
        addTarget(playerB, "Collective Defiance");
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        
        assertPermanentCount(playerB, "Spell Queller", 1);
        assertHandCount(playerA, "Collective Defiance", 0);
        assertExileCount("Collective Defiance", 1);
        assertGraveyardCount(playerA, "Collective Defiance", 0);
        assertPermanentCount(playerB, "Wall of Omens", 1);        
        assertLife(playerA, 20);
        assertLife(playerB, 20);
    }

}
