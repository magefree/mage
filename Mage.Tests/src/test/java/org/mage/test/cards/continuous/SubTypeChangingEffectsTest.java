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
package org.mage.test.cards.continuous;

import mage.cards.Card;
import mage.constants.PhaseStep;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.permanent.Permanent;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class SubTypeChangingEffectsTest extends CardTestPlayerBase {

    @Test
    public void testConspiracyGiveType() {
        // As Conspiracy enters the battlefield, choose a creature type.
        // Creature cards you own that aren't on the battlefield, creature spells you control, and creatures you control are the chosen type.
        addCard(Zone.HAND, playerA, "Conspiracy", 1); // Enchantment {3}{B}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);

        addCard(Zone.HAND, playerA, "Silvercoat Lion");
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion");
        addCard(Zone.GRAVEYARD, playerA, "Silvercoat Lion");

        addCard(Zone.HAND, playerB, "Silvercoat Lion");
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion");
        addCard(Zone.GRAVEYARD, playerA, "Silvercoat Lion");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Conspiracy");
        setChoice(playerA, "Orc");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Conspiracy", 1);

        Permanent silvercoatLion = getPermanent("Silvercoat Lion", playerA);
        Assert.assertEquals(false, silvercoatLion.getSubtype(currentGame).contains(SubType.CAT));
        Assert.assertEquals(true, silvercoatLion.getSubtype(currentGame).contains(SubType.ORC));

        silvercoatLion = getPermanent("Silvercoat Lion", playerB);
        Assert.assertEquals(true, silvercoatLion.getSubtype(currentGame).contains(SubType.CAT));
        Assert.assertEquals(false, silvercoatLion.getSubtype(currentGame).contains(SubType.ORC));

        for (Card card : playerA.getLibrary().getCards(currentGame)) {
            if (card.isCreature()) {
                Assert.assertEquals(card.getName() + " should have ORC type", true, card.getSubtype(currentGame).contains(SubType.ORC));
                Assert.assertEquals(card.getName() + " should not have CAT type", false, card.getSubtype(currentGame).contains(SubType.CAT));
            }
        }
        for (Card card : playerB.getLibrary().getCards(currentGame)) {
            if (card.isCreature()) {
                Assert.assertEquals(card.getName() + " should not have ORC type", false, card.getSubtype(currentGame).contains(SubType.ORC));
                Assert.assertEquals(card.getName() + " should have CAT type", true, card.getSubtype(currentGame).contains(SubType.CAT));
            }
        }

        for (Card card : playerA.getHand().getCards(currentGame)) {
            if (card.isCreature()) {
                Assert.assertEquals(card.getName() + " should have ORC type", true, card.getSubtype(currentGame).contains(SubType.ORC));
            }
        }
        for (Card card : playerB.getHand().getCards(currentGame)) {
            if (card.isCreature()) {
                Assert.assertEquals(card.getName() + " should not have ORC type", false, card.getSubtype(currentGame).contains(SubType.ORC));
            }
        }
        for (Card card : playerA.getGraveyard().getCards(currentGame)) {
            if (card.isCreature()) {
                Assert.assertEquals(card.getName() + " should have ORC type", true, card.getSubtype(currentGame).contains(SubType.ORC));
            }

        }
        for (Card card : playerB.getGraveyard().getCards(currentGame)) {
            if (card.isCreature()) {
                Assert.assertEquals(card.getName() + " should not have ORC type", false, card.getSubtype(currentGame).contains(SubType.ORC));

            }
        }

    }

    /**
     * Conspiracy doesn't revert creature types of non-permanent cards when it
     * leaves the battlefield
     */
    @Test
    public void testConspiracyIsRestCorrectly() {
        // As Conspiracy enters the battlefield, choose a creature type.
        // Creature cards you own that aren't on the battlefield, creature spells you control, and creatures you control are the chosen type.
        addCard(Zone.HAND, playerA, "Conspiracy", 1); // Enchantment {3}{B}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);

        addCard(Zone.HAND, playerA, "Silvercoat Lion");
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion");
        addCard(Zone.GRAVEYARD, playerA, "Silvercoat Lion");

        addCard(Zone.HAND, playerB, "Disenchant", 1); // Instant
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 2);

        addCard(Zone.HAND, playerB, "Silvercoat Lion");
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion");
        addCard(Zone.GRAVEYARD, playerA, "Silvercoat Lion");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Conspiracy");
        setChoice(playerA, "Orc");

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Disenchant", "Conspiracy");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Conspiracy", 1);
        assertGraveyardCount(playerB, "Disenchant", 1);

        Permanent silvercoatLion = getPermanent("Silvercoat Lion", playerA);
        Assert.assertEquals(true, silvercoatLion.getSubtype(currentGame).contains(SubType.CAT));
        Assert.assertEquals(false, silvercoatLion.getSubtype(currentGame).contains(SubType.ORC));

        for (Card card : playerA.getLibrary().getCards(currentGame)) {
            if (card.isCreature()) {
                Assert.assertEquals(card.getName() + " should not have ORC type", false, card.getSubtype(currentGame).contains(SubType.ORC));
                Assert.assertEquals(card.getName() + " should have CAT type", true, card.getSubtype(currentGame).contains(SubType.CAT));
            }
        }

        for (Card card : playerA.getHand().getCards(currentGame)) {
            if (card.isCreature()) {
                Assert.assertEquals(card.getName() + " should not have ORC type", false, card.getSubtype(currentGame).contains(SubType.ORC));
                Assert.assertEquals(card.getName() + " should have CAT type", true, card.getSubtype(currentGame).contains(SubType.CAT));
            }
        }

        for (Card card : playerA.getGraveyard().getCards(currentGame)) {
            if (card.isCreature()) {
                Assert.assertEquals(card.getName() + " should not have ORC type", false, card.getSubtype(currentGame).contains(SubType.ORC));
                Assert.assertEquals(card.getName() + " should have CAT type", true, card.getSubtype(currentGame).contains(SubType.CAT));
            }

        }

    }

    @Test
    public void testArcaneAdaptationGiveType() {
        // As Arcane Adaptation enters the battlefield, choose a creature type.
        // Creatures you control are the chosen type in addition to their other types. The same is true for creature spells you control and creature cards you own that aren't on the battlefield.
        addCard(Zone.HAND, playerA, "Arcane Adaptation", 1); // Enchantment {2}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);

        addCard(Zone.HAND, playerA, "Silvercoat Lion");
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion");
        addCard(Zone.GRAVEYARD, playerA, "Silvercoat Lion");

        addCard(Zone.HAND, playerB, "Silvercoat Lion");
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion");
        addCard(Zone.GRAVEYARD, playerA, "Silvercoat Lion");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Arcane Adaptation");
        setChoice(playerA, "Orc");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Arcane Adaptation", 1);

        Permanent silvercoatLion = getPermanent("Silvercoat Lion", playerA);
        Assert.assertEquals(true, silvercoatLion.getSubtype(currentGame).contains(SubType.CAT));
        Assert.assertEquals(true, silvercoatLion.getSubtype(currentGame).contains(SubType.ORC));

        silvercoatLion = getPermanent("Silvercoat Lion", playerB);
        Assert.assertEquals(true, silvercoatLion.getSubtype(currentGame).contains(SubType.CAT));
        Assert.assertEquals(false, silvercoatLion.getSubtype(currentGame).contains(SubType.ORC));

        for (Card card : playerA.getLibrary().getCards(currentGame)) {
            if (card.isCreature()) {
                Assert.assertEquals(card.getName() + " should have ORC type", true, card.getSubtype(currentGame).contains(SubType.ORC));
                Assert.assertEquals(card.getName() + " should have CAT type", true, card.getSubtype(currentGame).contains(SubType.CAT));
            }
        }
        for (Card card : playerB.getLibrary().getCards(currentGame)) {
            if (card.isCreature()) {
                Assert.assertEquals(card.getName() + " should not have ORC type", false, card.getSubtype(currentGame).contains(SubType.ORC));
                Assert.assertEquals(card.getName() + " should have CAT type", true, card.getSubtype(currentGame).contains(SubType.CAT));
            }
        }

        for (Card card : playerA.getHand().getCards(currentGame)) {
            if (card.isCreature()) {
                Assert.assertEquals(card.getName() + " should have ORC type", true, card.getSubtype(currentGame).contains(SubType.ORC));
                Assert.assertEquals(card.getName() + " should have CAT type", true, card.getSubtype(currentGame).contains(SubType.CAT));
            }
        }
        for (Card card : playerB.getHand().getCards(currentGame)) {
            if (card.isCreature()) {
                Assert.assertEquals(card.getName() + " should not have ORC type", false, card.getSubtype(currentGame).contains(SubType.ORC));
                Assert.assertEquals(card.getName() + " should have CAT type", true, card.getSubtype(currentGame).contains(SubType.CAT));
            }
        }
        for (Card card : playerA.getGraveyard().getCards(currentGame)) {
            if (card.isCreature()) {
                Assert.assertEquals(card.getName() + " should have ORC type", true, card.getSubtype(currentGame).contains(SubType.ORC));
                Assert.assertEquals(card.getName() + " should have CAT type", true, card.getSubtype(currentGame).contains(SubType.CAT));
            }

        }
        for (Card card : playerB.getGraveyard().getCards(currentGame)) {
            if (card.isCreature()) {
                Assert.assertEquals(card.getName() + " should not have ORC type", false, card.getSubtype(currentGame).contains(SubType.ORC));
                Assert.assertEquals(card.getName() + " should have CAT type", true, card.getSubtype(currentGame).contains(SubType.CAT));
            }
        }

    }

    /**
     * Arcane Adaptation doesn't revert creature types of non-permanent cards
     * when it leaves the battlefield
     */
    @Test
    public void testArcaneAdaptationIsRestCorrectly() {
        // As Arcane Adaptation enters the battlefield, choose a creature type.
        // Creatures you control are the chosen type in addition to their other types. The same is true for creature spells you control and creature cards you own that aren't on the battlefield.
        addCard(Zone.HAND, playerA, "Arcane Adaptation", 1); // Enchantment {2}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);

        addCard(Zone.HAND, playerA, "Silvercoat Lion");
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion");
        addCard(Zone.GRAVEYARD, playerA, "Silvercoat Lion");

        addCard(Zone.HAND, playerB, "Disenchant", 1); // Instant
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 2);

        addCard(Zone.HAND, playerB, "Silvercoat Lion");
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion");
        addCard(Zone.GRAVEYARD, playerA, "Silvercoat Lion");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Arcane Adaptation");
        setChoice(playerA, "Orc");

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Disenchant", "Arcane Adaptation");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Arcane Adaptation", 1);
        assertGraveyardCount(playerB, "Disenchant", 1);

        Permanent silvercoatLion = getPermanent("Silvercoat Lion", playerA);
        Assert.assertEquals(true, silvercoatLion.getSubtype(currentGame).contains(SubType.CAT));
        Assert.assertEquals(false, silvercoatLion.getSubtype(currentGame).contains(SubType.ORC));

        for (Card card : playerA.getLibrary().getCards(currentGame)) {
            if (card.isCreature()) {
                Assert.assertEquals(card.getName() + " should not have ORC type", false, card.getSubtype(currentGame).contains(SubType.ORC));
                Assert.assertEquals(card.getName() + " should have CAT type", true, card.getSubtype(currentGame).contains(SubType.CAT));
            }
        }

        for (Card card : playerA.getHand().getCards(currentGame)) {
            if (card.isCreature()) {
                Assert.assertEquals(card.getName() + " should not have ORC type", false, card.getSubtype(currentGame).contains(SubType.ORC));
                Assert.assertEquals(card.getName() + " should have CAT type", true, card.getSubtype(currentGame).contains(SubType.CAT));
            }
        }

        for (Card card : playerA.getGraveyard().getCards(currentGame)) {
            if (card.isCreature()) {
                Assert.assertEquals(card.getName() + " should not have ORC type", false, card.getSubtype(currentGame).contains(SubType.ORC));
                Assert.assertEquals(card.getName() + " should have CAT type", true, card.getSubtype(currentGame).contains(SubType.CAT));
            }

        }

    }
}
