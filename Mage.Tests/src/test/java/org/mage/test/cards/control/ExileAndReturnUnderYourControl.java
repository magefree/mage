package org.mage.test.cards.control;

import mage.constants.EmptyNames;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * Tests the effect: - Exile target creature you control, then return that card
 * to the battlefield under your control
 * <p>
 * This effect grants you permanent control over the returned creature. So you
 * mail steal opponent's creature with "Act of Treason" and then use this effect
 * for permanent control effect.
 *
 * @author noxx
 */
public class ExileAndReturnUnderYourControl extends CardTestPlayerBase {

    @Test
    public void testPermanentControlEffect() {
        addCard(Zone.HAND, playerA, "Cloudshift");
        addCard(Zone.HAND, playerA, "Act of Treason");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);

        addCard(Zone.BATTLEFIELD, playerB, "Elite Vanguard");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Act of Treason", "Elite Vanguard");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Cloudshift", "Elite Vanguard");

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Elite Vanguard", 1);
    }

    @Test
    public void testVillainousWealthExilesCourser() {
        // Villainous Wealth {X}{B}{G}{U}
        // Target opponent exiles the top X cards of their library. You may cast any number
        // of nonland cards with converted mana cost X or less from among them without paying
        // their mana costs.
        addCard(Zone.HAND, playerA, "Villainous Wealth");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);

        // Courser of Kruphix {1}{G}{G}
        // Play with the top card of your library revealed.
        // You may play the top card of your library if it's a land card.
        // Whenever a land enters the battlefield under your control, you gain 1 life.
        addCard(Zone.LIBRARY, playerB, "Courser of Kruphix");
        skipInitShuffling(); // to keep this card on top of library

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Villainous Wealth", playerB);
        setChoice(playerA, "X=3");
        setChoice(playerA, "Yes"); // Courser of Kruphix is the only option, say Yes to cast for free

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Villainous Wealth", 1);
        assertExileCount(playerB, 2);
        assertExileCount("Courser of Kruphix", 0);
        assertPermanentCount(playerA, "Courser of Kruphix", 1);
        Assert.assertTrue("player A should play with top card revealed", playerA.isTopCardRevealed());
        Assert.assertFalse("player B should play NOT with top card revealed", playerB.isTopCardRevealed());
    }

    @Test
    public void testVillainousWealthExilesBoost() {
        // Villainous Wealth {X}{B}{G}{U}
        // Target opponent exiles the top X cards of their library. You may cast any number
        // of nonland cards with converted mana cost X or less from among them without paying
        // their mana costs.
        addCard(Zone.HAND, playerA, "Villainous Wealth");
        addCard(Zone.HAND, playerA, "Master of Pearls");

        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);

        // Secret Plans {G}{U}
        // Face-down creatures you control get +0/+1.
        // Whenever a permanent you control is turned face up, draw a card.
        addCard(Zone.LIBRARY, playerB, "Secret Plans");
        skipInitShuffling(); // to keep this card on top of library

        setStrictChooseMode(true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Master of Pearls");
        setChoice(playerA, true); // cast it face down as 2/2 creature

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Villainous Wealth", playerB);
        setChoice(playerA, "X=3");
        setChoice(playerA, "Yes"); // Cast Secret Plan without paying Only one possible target (Secret Plan)

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Villainous Wealth", 1);
        assertExileCount(playerB, 2);
        assertExileCount("Secret Plans", 0);
        assertPermanentCount(playerA, "Secret Plans", 1);

        assertPermanentCount(playerA, EmptyNames.FACE_DOWN_CREATURE.toString(), 1);
        assertPowerToughness(playerA, EmptyNames.FACE_DOWN_CREATURE.toString(), 2, 3);
    }

    /**
     * My opponent cast Villainous Wealth and took control of my Sylvan Library.
     * On their next turn, when Sylvan Library's trigger resolved, they kept the two
     * extra cards without paying life.
     */
    @Test
    public void testVillainousWealthExilesSylvanLibrary() {
        // Villainous Wealth {X}{B}{G}{U}
        // Target opponent exiles the top X cards of their library. You may cast any number
        // of nonland cards with converted mana cost X or less from among them without paying
        // their mana costs.
        addCard(Zone.HAND, playerA, "Villainous Wealth");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);

        // At the beginning of your draw step, you may draw two additional cards.
        // If you do, choose two cards in your hand drawn this turn.
        // For each of those cards, pay 4 life or put the card on top of your library.
        addCard(Zone.LIBRARY, playerB, "Sylvan Library");
        skipInitShuffling(); // to keep this card on top of library

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Villainous Wealth", playerB);
        setChoice(playerA, "X=3");
        setChoice(playerA, "Yes"); // Sylvan Library is the only option, say Yes to cast for free

        setStopAt(3, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerA, "Villainous Wealth", 1);
        assertExileCount(playerB, 2);
        assertExileCount("Sylvan Library", 0);
        assertPermanentCount(playerA, "Sylvan Library", 1);

        assertHandCount(playerB, 1);
        assertHandCount(playerA, 3);
        assertLife(playerA, 12);
        assertLife(playerB, 20);
    }

    /**
     * I cast a Villainous Wealth in Vintage Cube, and when it came time to cast
     * my opponent's cards (Mox Sapphire, Mox Emerald, Brainstorm, Snapcaster
     * Mage, Fact or Fiction and a Quicken), it rolled back to before I had cast
     * my spell after Quicken resolved.
     * I have the error, but the forums won't let me post them.
     * I did find it was replicatable whenever you try to cast Quicken off a Villainous Wealth.
     */
    @Test
    public void testVillainousWealthAndQuicken() {
        // Villainous Wealth {X}{B}{G}{U}
        // Target opponent exiles the top X cards of their library. You may cast any number
        // of nonland cards with converted mana cost X or less from among them without paying
        // their mana costs.
        addCard(Zone.HAND, playerA, "Villainous Wealth"); // {X}{B}{G}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);

        // At the beginning of your draw step, you may draw two additional cards.
        // If you do, choose two cards in your hand drawn this turn.
        // For each of those cards, pay 4 life or put the card on top of your library.
        addCard(Zone.LIBRARY, playerB, "Mox Emerald");
        // The next sorcery card you cast this turn can be cast as though it had flash.
        // Draw a card.
        addCard(Zone.LIBRARY, playerB, "Quicken"); // Instant - {U}
        addCard(Zone.LIBRARY, playerB, "Mox Sapphire");
        skipInitShuffling(); // to keep this card on top of library

        setStrictChooseMode(true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Villainous Wealth", playerB);
        setChoice(playerA, "X=3");

        setChoice(playerA, "Mox Emerald");
        setChoice(playerA, "Yes");

        setChoice(playerA, "Mox Sapphire");
        setChoice(playerA, "Yes");

        // Quicken is auto-chosen since it's the last of the 3 cards. Only need to say Yes to casting for free.
        setChoice(playerA, "Yes");

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerA, "Villainous Wealth", 1);
        assertExileCount(playerB, 0);
        assertPermanentCount(playerA, "Mox Emerald", 1);
        assertPermanentCount(playerA, "Mox Sapphire", 1);
        assertGraveyardCount(playerB, "Quicken", 1);
    }
}
