package org.mage.test.cards.facedown;

import mage.cards.Card;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.game.permanent.Permanent;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author BetaSteward
 */
public class SummonersEggTest extends CardTestPlayerBase {

    /**
     * Summoner's Egg
     * Artifact Creature — Construct 0/4, 4 (4)
     * Imprint — When Summoner's Egg enters the battlefield, you may exile a 
     * card from your hand face down.
     * When Summoner's Egg dies, turn the exiled card face up. If it's a creature 
     * card, put it onto the battlefield under your control.
     * 
     */
    
    // test that cards imprinted using Summoner's Egg are face down
    @Test
    public void testSummonersEggImprint() {
        addCard(Zone.HAND, playerA, "Summoner's Egg");
        addCard(Zone.HAND, playerA, "Maritime Guard");
        addCard(Zone.HAND, playerA, "Goblin Roughrider");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Summoner's Egg");
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertHandCount(playerA, 1);
        assertHandCount(playerA, "Maritime Guard", 1);
        assertHandCount(playerA, "Goblin Roughrider", 0);

        assertExileCount("Goblin Roughrider", 1);
        for (Card card :currentGame.getExile().getAllCards(currentGame)){
            if (card.getName().equals("Goblin Roughrider")) {
                Assert.assertTrue("Exiled card is not face down", card.isFaceDown(currentGame));
            }
        }

    }

    // test that creature cards imprinted using Summoner's Egg are put in play face up
    @Test
    public void testSummonersEggDies() {
        addCard(Zone.HAND, playerA, "Summoner's Egg");
        addCard(Zone.HAND, playerA, "Maritime Guard");
        addCard(Zone.HAND, playerA, "Goblin Roughrider");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        addCard(Zone.HAND, playerB, "Char");
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 3);
        
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Summoner's Egg");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Char", "Summoner's Egg");

        setStopAt(1, PhaseStep.END_TURN);
        execute();
        
        assertHandCount(playerA, 1);
        assertHandCount(playerA, "Maritime Guard", 1);
        assertHandCount(playerA, "Goblin Roughrider", 0);
        
        assertGraveyardCount(playerA, "Summoner's Egg", 1);

        assertExileCount("Goblin Roughrider", 0);
        assertPermanentCount(playerA, "Goblin Roughrider", 1);
        for (Permanent p :currentGame.getBattlefield().getAllActivePermanents()){
            if (p.getName().equals("Goblin Roughrider")) {
                Assert.assertTrue("Permanent is not face up", !p.isFaceDown(currentGame));
            }
        }

    }

    // test that non-creature cards imprinted using Summoner's Egg are left in exile face up
    @Test
    public void testSummonersEggDies2() {
        addCard(Zone.HAND, playerA, "Summoner's Egg");
        addCard(Zone.HAND, playerA, "Forest", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        addCard(Zone.HAND, playerB, "Char");
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 3);
        
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Summoner's Egg");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Char", "Summoner's Egg");

        setStopAt(1, PhaseStep.END_TURN);
        execute();
        
        assertHandCount(playerA, 1);
        assertHandCount(playerA, "Forest", 1);
        
        assertGraveyardCount(playerA, "Summoner's Egg", 1);

        assertExileCount("Forest", 1);
        for (Card card :currentGame.getExile().getAllCards(currentGame)){
            if (card.getName().equals("Forest")) {
                Assert.assertTrue("Exiled card is not face up", !card.isFaceDown(currentGame));
            }
        }

    }
    
}
