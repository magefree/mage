package org.mage.test.cards.copy;
import mage.constants.CardType;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentToken;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author LevelX2
 */
public class ProgenitorMimicTest extends CardTestPlayerBase {

    /**
     * Tests triggers working on both sides after Clone coming onto battlefield
     */
    @Test
    public void testCloneTriggered() {
        addCard(Zone.BATTLEFIELD, playerA, "Runeclaw Bear", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);

        addCard(Zone.HAND, playerB, "Progenitor Mimic");
        addCard(Zone.BATTLEFIELD, playerB, "Island", 3);
        addCard(Zone.BATTLEFIELD, playerB, "Forest", 3);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Progenitor Mimic");

        setStopAt(4, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertPermanentCount(playerA, "Runeclaw Bear", 1);
        assertPermanentCount(playerB, "Runeclaw Bear", 2);

        int tokens  = 0;
        int nonTokens = 0;
        for (Permanent permanent : currentGame.getBattlefield().getAllPermanents()) {
            if (permanent.getControllerId().equals(playerB.getId())) {
                if (permanent.getCardType().contains(CardType.CREATURE)) {
                    if (permanent instanceof PermanentToken) {
                       tokens++;
                    } else {
                       nonTokens++;
                    }

                }
            }
        }

        Assert.assertEquals("Only one non token permanent ",1, nonTokens);
        Assert.assertEquals("Only one token permanent ",1, tokens);
    }    
    
    /**
     * If you have Progenitor Mimic copy a creature it gets all of the abilities plus "At the beginning of upkeep
     * if this creature isn't a token, put a token that's a copy of this creature".
     * Up to this point everything works correctly.
     * 
     * If you then summon another mimic and have it be a copy of the first mimic it should have "At the beginning of
     * upkeep if this creature isn't a token, put a token that's a copy of this creature" two times. The second mimic
     * would then make two copies and the first mimic would make one copy every turn. Right now the second mimc only
     * makes one copy per turn.
     * 
     * 706.9a Some copy effects cause the copy to gain an ability as part of the copying process. This ability becomes
     * part of the copiable values for the copy, along with any other abilities that were copied. 
     * Example: Quirion Elves enters the battlefield and an Unstable Shapeshifter copies it. The copiable values of the
     * Shapeshifter now match those of the Elves, except that the Shapeshifter also has the ability “Whenever a creature
     * enters the battlefield, Unstable Shapeshifter becomes a copy of that creature and gains this ability.” Then a Clone
     * enters the battlefield as a copy of the Unstable Shapeshifter. The Clone copies the new copiable values of the 
     * Shapeshifter, including the ability that the Shapeshifter gave itself when it copied the Elves.

     */
    @Test
    public void testTwoMimic() {
        addCard(Zone.BATTLEFIELD, playerA, "Runeclaw Bear", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        // Return target permanent you control to its owner's hand. You gain 4 life.
        addCard(Zone.HAND, playerA, "Narrow Escape");

        // You may have Progenitor Mimic enter the battlefield as a copy of any creature on the battlefield except 
        // it gains "At the beginning of your upkeep, if this creature isn't a token, put a token onto the battlefield
        // that's a copy of this creature."
        addCard(Zone.HAND, playerB, "Progenitor Mimic", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Island", 3);
        addCard(Zone.BATTLEFIELD, playerB, "Forest", 3);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Progenitor Mimic");
        setChoice(playerB, "Runeclaw Bear");
        
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Narrow Escape", "Runeclaw Bear");
        
        // Begin of upkeep 1 token added
        castSpell(4, PhaseStep.PRECOMBAT_MAIN, playerB, "Progenitor Mimic");
        setChoice(playerB, "Runeclaw Bear");

        // Begin of upkeep 3 tokens added
        setStopAt(6, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 24);
        assertLife(playerB, 20);

        assertGraveyardCount(playerA, "Narrow Escape", 1);
        assertPermanentCount(playerA, "Runeclaw Bear", 0);
        assertHandCount(playerA, "Runeclaw Bear", 1);
        
        assertPermanentCount(playerB, "Runeclaw Bear", 6);

        int tokens  = 0;
        int nonTokens = 0;
        for (Permanent permanent : currentGame.getBattlefield().getAllPermanents()) {
            if (permanent.getControllerId().equals(playerB.getId())) {
                if (permanent.getCardType().contains(CardType.CREATURE)) {
                    if (permanent instanceof PermanentToken) {
                       tokens++;
                    } else {
                       nonTokens++;
                    }

                }
            }
        }

        Assert.assertEquals("Two non token permanents ",2, nonTokens);
        Assert.assertEquals("Four token permanents",4, tokens);
    }    
        
    
}
