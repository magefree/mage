
package org.mage.test.cards.abilities.keywords;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author LevelX2
 */
public class DiscardTest extends CardTestPlayerBase {

    /*
     * If Rest in Peace is in play, every card going to the graveyard goes to exile instead.
     * If a card is discarded while Rest in Peace is on the battlefield, abilities that function
     * when a card is discarded (such as madness) still work, even though that card never reaches
     * a graveyard.
     */
    @Test
    public void testRestInPeaceAndCycle() {

        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        addCard(Zone.HAND, playerA, "Tranquil Thicket");
        addCard(Zone.BATTLEFIELD, playerB, "Rest in Peace", 1);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cycling {G}"); //cycling ability
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertExileCount("Tranquil Thicket", 1); //exiled by Rest in Peace
        assertHandCount(playerA, "Tranquil Thicket", 0); //should be exiled
        assertHandCount(playerA, 1); // the card drawn by Cycling
    }

    @Test
    public void AmnesiaTest() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 20);
        addCard(Zone.HAND, playerA, "Shock");
        addCard(Zone.HAND, playerA, "Shock");
        addCard(Zone.HAND, playerA, "Amnesia");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Amnesia", playerA);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();
        assertHandCount(playerA, 0);
    }

    /**
     * With Bazaar of Baghdad, if you use it when you have no cards in hand, you
     * draw 2, it asks for you to discard 3, but you can't. So the game can't
     * progress and you lose on time.
     */
    @Test
    public void testBazaarOfBaghdad() {
        // {T}: Draw two cards, then discard three cards.
        addCard(Zone.BATTLEFIELD, playerA, "Bazaar of Baghdad", 1);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Draw two cards, then discard three cards");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertHandCount(playerA, 0);
        assertGraveyardCount(playerA, 2);

    }

    /**
     * "When using the enchantment "Liliana's Caress" in a vs. human match, it
     * seems to count cards that read "you choose a card, enemy discards them"
     * as the choosing playing making the discard. This means that any spell
     * that lets you pick what your opponent discards doesn't trigger the Caress
     * like it should."
     */
    @Test
    public void testLilianasCaress() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);
        // Whenever an opponent discards a card, that player loses 2 life.
        addCard(Zone.HAND, playerA, "Liliana's Caress", 1); // ENCHANTMENT {1}{B}
        // Target opponent reveals their hand. You choose a card from it. That player discards that card.
        addCard(Zone.HAND, playerA, "Coercion", 1); // SORCERY {2}{B}

        addCard(Zone.HAND, playerB, "Island", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Liliana's Caress", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Coercion", playerB);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Liliana's Caress", 1);

        assertGraveyardCount(playerA, "Coercion", 1);
        assertGraveyardCount(playerB, "Island", 1);
        assertHandCount(playerB, "Island", 1);

        assertLife(playerA, 20);
        assertLife(playerB, 18);
    }

    @Test
    public void testCabalTherapyAfterMathCard(){
        addCard(Zone.HAND, playerA, "Cabal Therapy", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 10);
        addCard(Zone.HAND, playerB, "Driven // Despair");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cabal Therapy", playerB);

        setChoice(playerA, "Driven");
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertHandCount(playerB, "Driven // Despair", 0);
    }
    
    
    /**
     * Test a discard after selecting the cards from another player    
     */
    @Test
    public void GruesomeDiscoveryTest(){
        // Target player discards two cards.
        // Morbid - If a creature died this turn, instead that player reveals their hand, 
        // you choose two cards from it, then that player discards those cards.        
        addCard(Zone.HAND, playerA, "Gruesome Discovery", 1); // Sorcery {2}{B}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);
        
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1);
        
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion", 1);
        addCard(Zone.HAND, playerB, "Silvercoat Lion");
        addCard(Zone.HAND, playerB, "Aluren");
        addCard(Zone.HAND, playerB, "Contagion");

        attack(3, playerA, "Silvercoat Lion");
        block(3, playerB, "Silvercoat Lion", "Silvercoat Lion");
        castSpell(3, PhaseStep.POSTCOMBAT_MAIN, playerA, "Gruesome Discovery", playerB);

        setChoice(playerA, "Aluren^Contagion");
        
        // addTarget(playerA, "Aluren");
        // addTarget(playerA, "Contagion");
        
        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Silvercoat Lion", 1);
        assertGraveyardCount(playerB, "Silvercoat Lion", 1);
        
        assertGraveyardCount(playerA, "Gruesome Discovery", 1);
        
        assertGraveyardCount(playerB, "Aluren", 1);
        assertGraveyardCount(playerB, "Contagion", 1);
        assertGraveyardCount(playerB, 3);
    }  
}
