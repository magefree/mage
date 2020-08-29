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
        addCard(Zone.GRAVEYARD, playerB, "Silvercoat Lion");

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
        addCard(Zone.GRAVEYARD, playerB, "Silvercoat Lion");

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
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);

        addCard(Zone.HAND, playerA, "Silvercoat Lion");
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion");
        addCard(Zone.GRAVEYARD, playerA, "Silvercoat Lion");

        addCard(Zone.HAND, playerB, "Silvercoat Lion");
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion");
        addCard(Zone.GRAVEYARD, playerB, "Silvercoat Lion");

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
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 8);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);

        // Create a 5/5 green Wurm creature token with trample.
        addCard(Zone.HAND, playerA, "Advent of the Wurm", 1); // Instant {1}{G}{G}{W}
        // Create a 4/4 green Beast creature token.
        // Flashback {2}{G}{G}{G} (You may cast this card from your graveyard for its flashback cost. Then exile it.)
        addCard(Zone.HAND, playerA, "Beast Attack", 1); // Instant {2}{G}{G}{G}

        addCard(Zone.HAND, playerA, "Silvercoat Lion");
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion");
        addCard(Zone.GRAVEYARD, playerA, "Silvercoat Lion");

        addCard(Zone.HAND, playerB, "Disenchant", 1); // Instant
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 2);

        addCard(Zone.HAND, playerB, "Silvercoat Lion");
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion");
        addCard(Zone.GRAVEYARD, playerB, "Silvercoat Lion");

        castSpell(1, PhaseStep.UPKEEP, playerA, "Advent of the Wurm");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Arcane Adaptation");
        setChoice(playerA, "Orc");

        castSpell(1, PhaseStep.BEGIN_COMBAT, playerA, "Beast Attack");

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Disenchant", "Arcane Adaptation");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Arcane Adaptation", 1);
        assertGraveyardCount(playerA, "Advent of the Wurm", 1);
        assertGraveyardCount(playerA, "Beast Attack", 1);
        assertGraveyardCount(playerB, "Disenchant", 1);

        Permanent silvercoatLion = getPermanent("Silvercoat Lion", playerA);
        Assert.assertEquals(true, silvercoatLion.getSubtype(currentGame).contains(SubType.CAT));
        Assert.assertEquals(false, silvercoatLion.getSubtype(currentGame).contains(SubType.ORC));

        Permanent beast = getPermanent("Beast", playerA);
        Assert.assertEquals(true, beast.getSubtype(currentGame).contains(SubType.BEAST));
        Assert.assertEquals(false, beast.getSubtype(currentGame).contains(SubType.ORC));

        Permanent wurm = getPermanent("Wurm", playerA);
        Assert.assertEquals(true, wurm.getSubtype(currentGame).contains(SubType.WURM));
        Assert.assertEquals(false, wurm.getSubtype(currentGame).contains(SubType.ORC));

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
