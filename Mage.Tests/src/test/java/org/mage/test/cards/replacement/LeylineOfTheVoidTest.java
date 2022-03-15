
package org.mage.test.cards.replacement;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class LeylineOfTheVoidTest extends CardTestPlayerBase {

    /**
     * Leyline of the Void is on the battlefield, Helm of Obedience is used on
     * an opponent with X=1. Now the Helm states to mill "until a creature card
     * or X cards are put into the graveyard this way". For each of those milled
     * cards, Leyline states to "remove [them] from the game instead". In other
     * words: Leyline + Helm and X of at least 1 exiles the library of one
     * unlucky opponent. Or should, because right now it doesn't. Right now it's
     * Helm's originally intended "X or 1st Creature" amount of cards that after
     * it's effect get exiled.
     */
    @Test
    public void testGrindstoneProgenius() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains");

        // If Leyline of the Void is in your opening hand, you may begin the game with it on the battlefield.
        // If a card would be put into an opponent's graveyard from anywhere, exile it instead.
        addCard(Zone.BATTLEFIELD, playerA, "Leyline of the Void");

        // {X}, {T}: Target opponent puts cards from the top of their library into their graveyard until a creature card or X cards are put into that graveyard this way, whichever comes first. If a creature card is put into that graveyard this way, sacrifice Helm of Obedience and put that card onto the battlefield under your control. X can't be 0.
        addCard(Zone.BATTLEFIELD, playerA, "Helm of Obedience");

        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "{X}, {T}: Target opponent mills", playerB);
        setChoice(playerA, "X=1");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertExileCount(playerB, 71); // All cards go to exile replaced from Leyline of the void
    }

    /**
     * Today i casted Ill-gotten Gains in EDH (with a leyline of the veil in
     * play) and the spell simply discarded both players hands not letting
     * either of us choose cards to get back, this ended up with me losing the
     * game as i was going to combo off some cards in yard.
     */
    @Test
    public void testIllgottenGains() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);

        // If Leyline of the Void is in your opening hand, you may begin the game with it on the battlefield.
        // If a card would be put into an opponent's graveyard from anywhere, exile it instead.
        addCard(Zone.BATTLEFIELD, playerA, "Leyline of the Void");

        // Exile Ill-Gotten Gains.
        // Each player discards their hand,
        // then returns up to three cards from their graveyard to their hand.
        addCard(Zone.HAND, playerA, "Ill-Gotten Gains"); // Sorcery - {2}{B}{B}
        addCard(Zone.HAND, playerA, "Silvercoat Lion", 4);
        addCard(Zone.HAND, playerB, "Silvercoat Lion", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Ill-Gotten Gains");
        setChoice(playerA, "Silvercoat Lion^Silvercoat Lion^Silvercoat Lion");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertExileCount(playerB, 4);
        assertHandCount(playerB, 0);

        assertExileCount(playerA, 1);
        assertHandCount(playerA, 3);
    }
    
    @Test
    public void testMorbidAbility() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        // If Leyline of the Void is in your opening hand, you may begin the game with it on the battlefield.
        // If a card would be put into an opponent's graveyard from anywhere, exile it instead.
        addCard(Zone.BATTLEFIELD, playerA, "Leyline of the Void");
        addCard(Zone.HAND, playerA, "Murder");
         // Morbid — At the beginning of each end step, if a creature died this turn, you may draw a card.
        addCard(Zone.BATTLEFIELD, playerB, "Deathreap Ritual");
        addCard(Zone.BATTLEFIELD, playerB, "Memnite");
                
        castSpell(2, PhaseStep.POSTCOMBAT_MAIN, playerA, "Murder");
        setChoice(playerA, "Memnite");
        setChoice(playerB, true);
        
        setStopAt(2, PhaseStep.END_TURN);
        execute();
        
        assertHandCount(playerB, 1); // card drawn for turn
        assertExileCount(playerB, 1);
        
    }
    
    /*
    "Leyline of the Void's second ability doesn't affect token permanents that would be put into an opponent's graveyard from the battlefield. 
    They'll be put into that graveyard as normal (causing any applicable triggered abilities to trigger), then they'll cease to exist."
    https://gatherer.wizards.com/Pages/Card/Details.aspx?multiverseid=107682
    
    */
    @Test
    public void testMorbidAbilityWithAwakeningZoneTokens() {
        // At the beginning of your upkeep, you may put a 0/1 colorless Eldrazi Spawn creature token onto the battlefield. 
        // It has "Sacrifice this creature: Add mana symbol 1."
        addCard(Zone.BATTLEFIELD, playerA, "Awakening Zone");
        // If Leyline of the Void is in your opening hand, you may begin the game with it on the battlefield.
        // If a card would be put into an opponent's graveyard from anywhere, exile it instead.
        addCard(Zone.BATTLEFIELD, playerA, "Leyline of the Void");
        // Morbid — At the beginning of each end step, if a creature died this turn, you may draw a card.
        addCard(Zone.BATTLEFIELD, playerB, "Deathreap Ritual");
        
        setChoice(playerA, true);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sacrifice");
        setChoice(playerB, true);
        
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        
        assertPermanentCount(playerA, "Eldrazi Spawn Token", 0);        
        assertExileCount(playerB, 0);
        assertHandCount(playerB, 1); 
    }

}
