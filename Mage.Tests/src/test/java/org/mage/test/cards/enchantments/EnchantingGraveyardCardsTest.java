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

import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.game.permanent.Permanent;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class EnchantingGraveyardCardsTest extends CardTestPlayerBase {

    static final String LIGHTNING_BOLT = "Lightning Bolt";
    static final String SPELLWEAVER_VOLUTE = "Spellweaver Volute";

    /**
     * Test that a card in the graveyard can be enchanted
     */
    @Test
    public void testSpellwaeverVoluteNormal() {
        // Enchant instant card in a graveyard
        // Whenever you cast a sorcery spell, copy the enchanted instant card. You may cast the copy without paying its mana cost.
        // If you do, exile the enchanted card and attach Spellweaver Volute to another instant card in a graveyard.
        addCard(Zone.HAND, playerA, SPELLWEAVER_VOLUTE, 1); // Enchantment Aura {3}{U}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);

        // Lightning Bolt deals 3 damage to target creature or player.
        addCard(Zone.GRAVEYARD, playerB, "Lightning Bolt", 1); // Instant  {R}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, SPELLWEAVER_VOLUTE, LIGHTNING_BOLT);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertHandCount(playerA, 0);
        assertPermanentCount(playerA, SPELLWEAVER_VOLUTE, 1);
        assertGraveyardCount(playerB, LIGHTNING_BOLT, 1);

        Permanent spellweaver = getPermanent(SPELLWEAVER_VOLUTE);
        Card attachedToCard = null;
        if (spellweaver != null) {
            attachedToCard = playerB.getGraveyard().get(spellweaver.getAttachedTo(), currentGame);
        }
        Assert.assertTrue(SPELLWEAVER_VOLUTE + " has to be attached to Lightning Bolt in graveyard", attachedToCard != null && attachedToCard.getName().equals(LIGHTNING_BOLT));
    }

    /**
     * Test that a card in the graveyard can be enchanted and the enchanted card
     * switches to a new one
     */
    @Test
    public void testSpellwaeverVoluteAndSorcery() {

        // Enchant instant card in a graveyard
        // Whenever you cast a sorcery spell, copy the enchanted instant card. You may cast the copy without paying its mana cost.
        // If you do, exile the enchanted card and attach Spellweaver Volute to another instant card in a graveyard.
        addCard(Zone.HAND, playerA, SPELLWEAVER_VOLUTE, 1); // Enchantment Aura {3}{U}{U}
        addCard(Zone.HAND, playerA, "Cloak of Feathers"); // Sorcery {U}
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 6);
        addCard(Zone.GRAVEYARD, playerA, "Aerial Volley", 1); // Instant  {G}

        // Lightning Bolt deals 3 damage to target creature or player.
        addCard(Zone.GRAVEYARD, playerB, "Lightning Bolt", 1); // Instant  {R}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, SPELLWEAVER_VOLUTE, LIGHTNING_BOLT);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cloak of Feathers", "Silvercoat Lion");
        setChoice(playerA, "Yes"); // play the L. Bold
        addTarget(playerA, playerB); // Add Target for the L. Bold

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 17);

        assertHandCount(playerA, 1);
        assertGraveyardCount(playerA, "Cloak of Feathers", 1);
        assertPermanentCount(playerA, SPELLWEAVER_VOLUTE, 1);
        assertGraveyardCount(playerA, "Aerial Volley", 1);
        assertExileCount(playerB, LIGHTNING_BOLT, 1);
        assertAbility(playerA, "Silvercoat Lion", FlyingAbility.getInstance(), true);
        Permanent spellweaver = getPermanent(SPELLWEAVER_VOLUTE);
        Card attachedToCard = null;
        if (spellweaver != null) {
            attachedToCard = playerA.getGraveyard().get(spellweaver.getAttachedTo(), currentGame);
        }
        Assert.assertTrue(SPELLWEAVER_VOLUTE + " has to be attached to Aerial Volley in graveyard", attachedToCard != null && attachedToCard.getName().equals("Aerial Volley"));

        assertHandCount(playerA, 1);

    }

    /**
     * Test that a card in the graveyard can be enchanted and the enchanted card
     * switches to a new one
     */
    @Test
    public void testSpellwaeverVoluteAndSorceryWithoutNewTarget() {

        // Enchant instant card in a graveyard
        // Whenever you cast a sorcery spell, copy the enchanted instant card. You may cast the copy without paying its mana cost.
        // If you do, exile the enchanted card and attach Spellweaver Volute to another instant card in a graveyard.
        addCard(Zone.HAND, playerA, SPELLWEAVER_VOLUTE, 1); // Enchantment Aura {3}{U}{U}
        addCard(Zone.HAND, playerA, "Cloak of Feathers"); // Sorcery {U}
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 6);

        // Lightning Bolt deals 3 damage to target creature or player.
        addCard(Zone.GRAVEYARD, playerB, "Lightning Bolt", 1); // Instant  {R}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, SPELLWEAVER_VOLUTE, LIGHTNING_BOLT);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cloak of Feathers", "Silvercoat Lion");
        setChoice(playerA, "Yes"); // play the L. Bold
        addTarget(playerA, playerB); // Add Target for the L. Bold

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 17);

        assertHandCount(playerA, 1);
        assertGraveyardCount(playerA, "Cloak of Feathers", 1);
        assertPermanentCount(playerA, SPELLWEAVER_VOLUTE, 0);
        assertGraveyardCount(playerA, SPELLWEAVER_VOLUTE, 1);
        assertExileCount(playerB, LIGHTNING_BOLT, 1);
        assertAbility(playerA, "Silvercoat Lion", FlyingAbility.getInstance(), true);

        assertGraveyardCount(playerA, SPELLWEAVER_VOLUTE, 1);

        for (Card card : currentGame.getExile().getAllCards(currentGame)) {
            if (card.getName().equals(LIGHTNING_BOLT)) {
                Assert.assertTrue(LIGHTNING_BOLT + " may not have any attachments", card.getAttachments().isEmpty());

            }
        }
        assertHandCount(playerA, 1);

    }

    /**
     * Test that a card in the graveyard can be enchanted and if the Enchantment
     * returns to hand, the enchanting ends
     */
    @Test
    public void testSpellwaeverVoluteAndReturnToHand() {

        // Enchant instant card in a graveyard
        // Whenever you cast a sorcery spell, copy the enchanted instant card. You may cast the copy without paying its mana cost.
        // If you do, exile the enchanted card and attach Spellweaver Volute to another instant card in a graveyard.
        addCard(Zone.HAND, playerA, SPELLWEAVER_VOLUTE, 1); // Enchantment Aura {3}{U}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);

        // Lightning Bolt deals 3 damage to target creature or player.
        addCard(Zone.GRAVEYARD, playerB, "Lightning Bolt", 1); // Instant  {R}

        // Return target permanent to its owner's hand.
        addCard(Zone.HAND, playerB, "Boomerang", 1); // Instant  {U}{U}
        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, SPELLWEAVER_VOLUTE, LIGHTNING_BOLT);

        castSpell(1, PhaseStep.BEGIN_COMBAT, playerB, "Boomerang", SPELLWEAVER_VOLUTE);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertHandCount(playerA, SPELLWEAVER_VOLUTE, 1);
        assertGraveyardCount(playerB, "Boomerang", 1);
        assertPermanentCount(playerA, SPELLWEAVER_VOLUTE, 0);

        for (Card card : playerB.getGraveyard().getCards(currentGame)) {
            if (card.getName().equals(LIGHTNING_BOLT)) {
                Assert.assertTrue(LIGHTNING_BOLT + " may not have any attachments", card.getAttachments().isEmpty());

            }
        }
        assertHandCount(playerA, 1);

    }
}
