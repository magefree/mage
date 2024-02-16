package org.mage.test.cards.single.emn;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * {@link mage.cards.s.SpellQueller Spell Queller}
 * {1}{W}{U}
 * Spirit
 * Flash Flying
 *
 * When Spell Queller enters the battlefield, exile target spell with converted mana cost 4 or less.
 * When Spell Queller leaves the battlefield, the exiled card's owner may cast that card without paying its mana cost.
 * 2/3
 *
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public class SpellQuellerTest extends CardTestPlayerBase {
    
    @Test
    public void testExileSpellCMCFour() {
        addCard(Zone.HAND, playerB, "Spell Queller");
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);
        
        addCard(Zone.HAND, playerA, "Languish");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Languish");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Spell Queller");
        // Languish is auto-chosen since only possible target
                
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        
        assertPermanentCount(playerB, "Spell Queller", 1);
        assertExileCount("Languish", 1);
    }
    
    @Test
    public void testAttemptExileSpellCMCFive() {
        addCard(Zone.HAND, playerB, "Spell Queller");
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);
        
        addCard(Zone.HAND, playerA, "Battle Sliver"); // {4}{R} 3/3 Sliver - Sliver creatures you control get +2/+0
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Battle Sliver");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Spell Queller");
        // Battle Sliver is auto-chosen since only possible target
                
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        
        assertPermanentCount(playerB, "Spell Queller", 1);
        assertPermanentCount(playerA, "Battle Sliver", 1);
        assertExileCount(playerA, 0);
    }
    
    @Test
    public void testExileSpellAndDiesAllowsFreeCast() {
        addCard(Zone.HAND, playerB, "Spell Queller");
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);
        
        addCard(Zone.HAND, playerA, "Divination"); // {2}{U} sorcery - draw 2 cards
        addCard(Zone.HAND, playerA, "Murder"); // {1}{B}{B} instant - destroy target creature
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Divination");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Spell Queller");
        // Divination is autochosen since only possible target
        
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Murder");
        // Spell Queller is auto-chosen since only possible target
        setChoice(playerA, "Yes"); // elect to cast exiled card (divination) for free
        
        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();
        
        assertGraveyardCount(playerB, "Spell Queller", 1);
        assertGraveyardCount(playerA, "Murder", 1);
        assertGraveyardCount(playerA, "Divination", 1);
        assertExileCount("Divination", 0);
        assertHandCount(playerA, 3); // card drawn on draw step + 2 from divination
    }
    
    /**
     * Reported bug:
     *      "...Spell Queller exiled my Nissa, Vastwood Seeker.
     *      Next turn they processed Nissa with Wasteland Strangler and killed my Tireless Tracker.
     *      I then cast Quarantine Field, targeting Spell Queller and Wasteland Strangler.
     *      That's when the error message occurred. (fatal exception)"
     */
    @Test
    public void testExiledSpellProcessedThenQuellerDies() {
        addCard(Zone.HAND, playerB, "Spell Queller");
        
        // {2}{B} 3/2 Eldrazi (devoid)
        // When Wasteland Strangler enters the battlefield, you may put a card an opponent owns from exile into that player's graveyard. 
        // If you do, target creature gets -3/-3 until end of turn.        
        addCard(Zone.HAND, playerB, "Wasteland Strangler");
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 1);
        
        addCard(Zone.HAND, playerA, "Centaur Courser"); // {2}{G} 3/3
        addCard(Zone.HAND, playerA, "Wrath of God"); // {2}{W}{W} sorcery - Destroy all creatures, no regen
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Sylvan Advocate"); // {1}{G} 2/3 vigilance

        setStrictChooseMode(true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Centaur Courser");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Spell Queller");
        addTarget(playerB, "Centaur Courser"); // exiles courser

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Wasteland Strangler");
        setChoice(playerB, true);
        addTarget(playerB, "Centaur Courser"); // put courser from exile into grave from ETB ability
        addTarget(playerB, "Sylvan Advocate"); // gives -3/-3 to Advocate 
        
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Wrath of God"); // kill queller and strangler
        
        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();
        
        assertGraveyardCount(playerA, "Wrath of God", 1);
        assertGraveyardCount(playerA, "Centaur Courser", 1); // in grave from processing
        assertGraveyardCount(playerA, "Sylvan Advocate", 1);
        assertGraveyardCount(playerB, "Spell Queller", 1);
        assertGraveyardCount(playerB, "Wasteland Strangler", 1);
        assertExileCount(playerA, 0);
    }
}
