package org.mage.test.cards.abilities.keywords;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class OfferingTest extends CardTestPlayerBase {

    private static final String nezumiPatron = "Patron of the Nezumi";


    @Test
    public void testOfferRatDecreaseCC() {

        String kurosTaken = "Kuro's Taken";

        addCard(Zone.HAND, playerA, nezumiPatron, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 8);
        addCard(Zone.BATTLEFIELD, playerA, kurosTaken);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, nezumiPatron);
        setChoice(playerA, true);
        addTarget(playerA, kurosTaken);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerA, kurosTaken, 1);
        assertPermanentCount(playerA, nezumiPatron, 1);
        assertTappedCount("Swamp", true, 5); // {5}{B}{B} - {1}{B} = {4}{B} = 5 swamps tapped
    }

    @Test
    public void testDontOfferRatNotDecreaseCC() {

        String kurosTaken = "Kuro's Taken";

        addCard(Zone.HAND, playerA, nezumiPatron, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 8);
        addCard(Zone.BATTLEFIELD, playerA, kurosTaken);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, nezumiPatron);
        setChoice(playerA, false);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, kurosTaken, 1);
        assertPermanentCount(playerA, nezumiPatron, 1);
        assertTappedCount("Swamp", true, 7); // {5}{B}{B} - {1}{B} = {4}{B} = 7 swamps tapped              
    }
    
    @Test
    public void testCastWithMinimalMana() {
        setStrictChooseMode(true);
        
        // Goblin offering (You may cast this card any time you could cast an instant by sacrificing a Goblin and paying the difference in mana costs between this and the sacrificed Goblin. Mana cost includes color.)
        // Whenever Patron of the Akki attacks, creatures you control get +2/+0 until end of turn.        
        String patron = "Patron of the Akki"; // Creature {4}{R}{R} (5/5)

        addCard(Zone.HAND, playerA, patron, 1);
        
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Akki Drillmaster"); // Creature Goblin {2}{R}  (2/2)

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, patron);                
        setChoice(playerA, true);
        addTarget(playerA, "Akki Drillmaster");
        
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, patron, 1);
        
        assertGraveyardCount(playerA, "Akki Drillmaster", 1);

    }    
    
    
    @Test
    public void testCastWithBorosRecruit() {
        setStrictChooseMode(true);
        
        // Goblin offering (You may cast this card any time you could cast an instant by sacrificing a Goblin and paying the difference in mana costs between this and the sacrificed Goblin. Mana cost includes color.)
        // Whenever Patron of the Akki attacks, creatures you control get +2/+0 until end of turn.        
        String patron = "Patron of the Akki"; // Creature {4}{R}{R} (5/5)

        addCard(Zone.HAND, playerA, patron, 1);
        
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
        
        // First strike
        addCard(Zone.BATTLEFIELD, playerA, "Boros Recruit"); // Creature Goblin {R/W}  (1/1)

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, patron);                
        setChoice(playerA, true);
        addTarget(playerA, "Boros Recruit");
        
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, patron, 1);
        
        assertGraveyardCount(playerA, "Boros Recruit", 1);

    }    
    
    @Test
    public void testCastWithMultipleOptions() {
        setStrictChooseMode(true);
        
        // Goblin offering (You may cast this card any time you could cast an instant by sacrificing a Goblin and paying the difference in mana costs between this and the sacrificed Goblin. Mana cost includes color.)
        // Whenever Patron of the Akki attacks, creatures you control get +2/+0 until end of turn.        
        String patron = "Patron of the Akki"; // Creature {4}{R}{R} (5/5)

        addCard(Zone.HAND, playerA, patron, 1);
        
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        
        // First strike
        addCard(Zone.BATTLEFIELD, playerA, "Boros Recruit"); // Creature Goblin {R/W}  (1/1)
        addCard(Zone.BATTLEFIELD, playerA, "Akki Drillmaster"); // Creature Goblin {2}{R}  (2/2)
        addCard(Zone.BATTLEFIELD, playerA, "Boggart Ram-Gang"); // Creature Goblin Warrior {R/G}{R/G}{R/G}  (3/3)
        
        

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, patron);                
        setChoice(playerA, true);
        addTarget(playerA, "Boggart Ram-Gang");
        
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, patron, 1);
        
        assertGraveyardCount(playerA, "Boggart Ram-Gang", 1);

    }    

}
