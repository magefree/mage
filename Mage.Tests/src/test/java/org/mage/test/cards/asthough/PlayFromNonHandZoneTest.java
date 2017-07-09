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
package org.mage.test.cards.asthough;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author LevelX2
 */
public class PlayFromNonHandZoneTest extends CardTestPlayerBase {

    @Test
    public void testWorldheartPhoenixNormal() {
        // Creature - Phoenix {3}{R}
        // Flying
        // You may cast Worldheart Phoenix from your graveyard by paying {W}{U}{B}{R}{G} rather than paying its mana cost.
        // If you do, it enters the battlefield with two +1/+1 counters on it.
        addCard(Zone.HAND, playerA, "Worldheart Phoenix");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Worldheart Phoenix"); // can only be cast by {W}{U}{B}{R}{G}

        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertPowerToughness(playerA, "Worldheart Phoenix", 2, 2);

    }

    @Test
    public void testWorldheartPhoenixNoMana() {
        // Creature - Phoenix {3}{R}
        // Flying
        // You may cast Worldheart Phoenix from your graveyard by paying {W}{U}{B}{R}{G} rather than paying its mana cost.
        // If you do, it enters the battlefield with two +1/+1 counters on it.
        addCard(Zone.GRAVEYARD, playerA, "Worldheart Phoenix");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Worldheart Phoenix"); // can only be cast by {W}{U}{B}{R}{G}

        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertPermanentCount(playerA, "Worldheart Phoenix", 0);

    }

    @Test
    public void testWorldheartPhoenix() {
        // Creature - Phoenix {3}{R}
        // Flying
        // You may cast Worldheart Phoenix from your graveyard by paying {W}{U}{B}{R}{G} rather than paying its mana cost.
        // If you do, it enters the battlefield with two +1/+1 counters on it.
        addCard(Zone.GRAVEYARD, playerA, "Worldheart Phoenix");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Worldheart Phoenix"); // can only be cast by {W}{U}{B}{R}{G}

        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertPermanentCount(playerA, "Worldheart Phoenix", 1);

    }

    @Test
    public void testNarsetEnlightenedMaster() {
        // First strike
        // Hexproof
        // Whenever Narset, Enlightented Master attacks, exile the top four cards of your library. Until end of turn, you may cast noncreature cards exiled with Narset this turn without paying their mana costs.
        addCard(Zone.BATTLEFIELD, playerB, "Narset, Enlightened Master", 1);

        skipInitShuffling();
        addCard(Zone.LIBRARY, playerB, "Silvercoat Lion");
        addCard(Zone.LIBRARY, playerB, "Abzan Banner");
        // Ferocious - If you control a creature with power 4 or greater, you may cast Dragon Grip as though it had flash. (You may cast it any time you could cast an instant.)
        // Enchant creature
        // Enchanted creature gets +2/+0 and has first strike.
        addCard(Zone.LIBRARY, playerB, "Dragon Grip");
        // You gain 2 life for each creature you control.
        addCard(Zone.LIBRARY, playerB, "Peach Garden Oath");
        addCard(Zone.LIBRARY, playerB, "Plains");

        attack(2, playerB, "Narset, Enlightened Master");

        castSpell(2, PhaseStep.POSTCOMBAT_MAIN, playerB, "Silvercoat Lion"); // can't be cast from exile
        castSpell(2, PhaseStep.POSTCOMBAT_MAIN, playerB, "Abzan Banner"); // can be cast from exile
        castSpell(2, PhaseStep.POSTCOMBAT_MAIN, playerB, "Dragon Grip", "Narset, Enlightened Master"); // can be cast from exile
        castSpell(2, PhaseStep.POSTCOMBAT_MAIN, playerB, "Peach Garden Oath"); // can be cast from exile

        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertExileCount("Silvercoat Lion", 1);
        assertPermanentCount(playerB, "Abzan Banner", 1);
        assertPermanentCount(playerB, "Dragon Grip", 1);
        assertGraveyardCount(playerB, "Peach Garden Oath", 1);

        assertPowerToughness(playerB, "Narset, Enlightened Master", 5, 2);

        assertHandCount(playerB, "Plains", 1);
        assertLife(playerA, 17);
        assertLife(playerB, 22);

    }

    @Test
    public void testNarsetEnlightenedMasterAdditionalCost() {
        // First strike
        // Hexproof
        // Whenever Narset, Enlightented Master attacks, exile the top four cards of your library. Until end of turn, you may cast noncreature cards exiled with Narset this turn without paying their mana costs.
        skipInitShuffling();

        addCard(Zone.BATTLEFIELD, playerB, "Narset, Enlightened Master", 1);
        addCard(Zone.HAND, playerB, "Swamp");
        addCard(Zone.LIBRARY, playerB, "Plains");
        addCard(Zone.LIBRARY, playerB, "Plains");
        addCard(Zone.LIBRARY, playerB, "Plains");
        addCard(Zone.LIBRARY, playerB, "Cathartic Reunion");
        addCard(Zone.LIBRARY, playerB, "Forest");

        attack(2, playerB, "Narset, Enlightened Master");

        castSpell(2, PhaseStep.POSTCOMBAT_MAIN, playerB, "Cathartic Reunion");
        setChoice(playerB, "Swamp^Forest");
        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertHandCount(playerB, 3);
        assertGraveyardCount(playerB, "Forest", 1);
        assertGraveyardCount(playerB, "Swamp", 1);
        assertGraveyardCount(playerB, "Cathartic Reunion", 1);
        assertGraveyardCount(playerB, 3);
        assertExileCount(playerB, "Plains", 3);
        assertExileCount(playerB, 3);


    }

}
