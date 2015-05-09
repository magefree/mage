package org.mage.test.cards.control;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * Tests the effect:
 *   - Exile target creature you control, then return that card to the battlefield under your control
 *
 * This effect grants you permanent control over the returned creature.
 * So you mail steal opponent's creature with "Act of Treason" and then use this effect for permanent control effect.
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
        // Target opponent exiles the top X cards of his or her library. You may cast any number
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
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Courser of Kruphix");

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
        // Target opponent exiles the top X cards of his or her library. You may cast any number
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

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Master of Pearls");
        setChoice(playerA, "Yes"); // cast it face down as 2/2 creature

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Villainous Wealth", playerB);
        setChoice(playerA, "X=3");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Secret Plans");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Villainous Wealth", 1);
        assertExileCount(playerB, 2);
        assertExileCount("Secret Plans", 0);
        assertPermanentCount(playerA, "Secret Plans", 1);
        
        assertPermanentCount(playerA, "", 1);
        assertPowerToughness(playerA, "", 2, 3);        
    }    

}
