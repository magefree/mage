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
public class SacredGroundTest extends CardTestPlayerBase {

    /**
     * Sacred Ground {1}{W} Enchantment Whenever a spell or ability an opponent
     * controls causes a land to be put into your graveyard from the
     * battlefield, return that card to the battlefield.
     *
     *
     * Destroyed land returns to battlefield
     */
    @Test
    public void testSpellDestroyLandReturnsToBattlefield() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        // Destroy target land. If that land was nonbasic, Molten Rain deals 2 damage to the land's controller.
        addCard(Zone.HAND, playerA, "Molten Rain");

        // Kabira Crossroads Land
        // Kabira Crossroads enters the battlefield tapped.
        // When Kabira Crossroads enters the battlefield, you gain 2 life.
        // {T}: Add {W}to your mana pool.
        addCard(Zone.HAND, playerB, "Kabira Crossroads");
        addCard(Zone.BATTLEFIELD, playerB, "Sacred Ground");
        addCard(Zone.BATTLEFIELD, playerB, "Plains");
        playLand(2, PhaseStep.POSTCOMBAT_MAIN, playerB, "Kabira Crossroads");

        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Molten Rain", "Kabira Crossroads");

        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Molten Rain", 1);
        assertPermanentCount(playerB, "Kabira Crossroads", 1);

        assertLife(playerA, 20);
        assertLife(playerB, 22); // + 2 * 2 life from  Kabira Crossroads - 2 Life from Molten Rain
    }

    /**
     * Sacred Ground {1}{W} Enchantment Whenever a spell or ability an opponent
     * controls causes a land to be put into your graveyard from the
     * battlefield, return that card to the battlefield.
     *
     *
     * Destroyed land returns to battlefield
     */
    @Test
    public void testSpellSacrificeLandReturnsToBattlefield() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        // Each player loses 1 life, discards a card, sacrifices a creature, then sacrifices a land.
        addCard(Zone.HAND, playerA, "Smallpox");

        // Kabira Crossroads Land
        // Kabira Crossroads enters the battlefield tapped.
        // When Kabira Crossroads enters the battlefield, you gain 2 life.
        // {T}: Add {W}to your mana pool.
        addCard(Zone.HAND, playerB, "Kabira Crossroads");
        addCard(Zone.BATTLEFIELD, playerB, "Sacred Ground");
        playLand(2, PhaseStep.POSTCOMBAT_MAIN, playerB, "Kabira Crossroads");

        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Smallpox");

        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Smallpox", 1);
        assertPermanentCount(playerB, "Kabira Crossroads", 1);

        assertLife(playerA, 19);
        assertLife(playerB, 23);
    }

    /**
     * Sacred Ground {1}{W} Enchantment Whenever a spell or ability an opponent
     * controls causes a land to be put into your graveyard from the
     * battlefield, return that card to the battlefield.
     *
     *
     * Destroyed land returns to battlefield
     */
    @Test
    public void testTriggeredAbilityReturnedToBattlefield() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 7);
        // When Faultgrinder enters the battlefield, destroy target land.
        addCard(Zone.HAND, playerA, "Faultgrinder");

        // Kabira Crossroads Land
        // Kabira Crossroads enters the battlefield tapped.
        // When Kabira Crossroads enters the battlefield, you gain 2 life.
        // {T}: Add {W}to your mana pool.
        addCard(Zone.HAND, playerB, "Kabira Crossroads");
        addCard(Zone.BATTLEFIELD, playerB, "Sacred Ground");
        playLand(2, PhaseStep.POSTCOMBAT_MAIN, playerB, "Kabira Crossroads");

        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Faultgrinder");

        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerB, "Kabira Crossroads", 1);

        assertLife(playerA, 20);
        assertLife(playerB, 24); // + 2 * 2 life from  Kabira Crossroads
    }

    /**
     * I was playing against Sacred Ground. I Molten Rained oponents land and
     * responded Sacred Ground trigger by exiling it with Surgical Extraction.
     * Then after that resolved, Sacred Ground ability put the land from exile
     * onto the battfield! Fix this, please
     */
    @Test
    public void testWithSurgicalExtraction() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);

        // Destroy target land.
        // If that land was nonbasic, Molten Rain deals 2 damage to the land's controller.
        addCard(Zone.HAND, playerA, "Molten Rain");// Instant {1}{R}{R}
        // Choose target card in a graveyard other than a basic land card. Search its owner's graveyard,
        // hand, and library for any number of cards with the same name as that card and exile them.
        // Then that player shuffles his or her library.
        addCard(Zone.HAND, playerA, "Surgical Extraction"); // Instant {B/P}

        addCard(Zone.BATTLEFIELD, playerB, "Caves of Koilos", 1);
        /**
         * Whenever a spell or ability an opponent controls causes a land to be
         * put into your graveyard from the battlefield, return that card to the
         * battlefield.
         */
        addCard(Zone.BATTLEFIELD, playerB, "Sacred Ground");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Molten Rain", "Caves of Koilos");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Surgical Extraction", "Caves of Koilos");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Molten Rain", 1);
        assertGraveyardCount(playerA, "Surgical Extraction", 1);
        assertExileCount("Caves of Koilos", 1);

        assertLife(playerA, 18);
        assertLife(playerB, 18);
    }

}
