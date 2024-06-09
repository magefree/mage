
package org.mage.test.cards.abilities.keywords;

import mage.cards.Card;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author BetaSteward
 */
public class HauntTest extends CardTestPlayerBase {

    /**
     * Blind Hunter - 2WB
     * Creature — Bat
     * 2/2
     * Flying
     * Haunt (When this creature dies, exile it haunting target creature.)
     * When Blind Hunter enters the battlefield or the creature it haunts dies, target player loses 2 life and you gain 2 life.
     *
     */
    
    // test that Haunting and Haunted by rules are added to cards
    @Test
    public void testAddHaunt() {
        
        addCard(Zone.BATTLEFIELD, playerA, "Blind Hunter", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Goblin Roughrider");
        addCard(Zone.HAND, playerA, "Lightning Bolt", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Blind Hunter");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Lightning Bolt", 1);
        assertExileCount("Blind Hunter", 1);
        
        boolean found = false;
        for (Card card : currentGame.getExile().getAllCards(currentGame)) {
            if (card.getName().equals("Blind Hunter")) {
                for (String rule : card.getRules(currentGame)) {
                    if (rule.startsWith("Haunting") &&  rule.contains("Goblin Roughrider")) {
                        found = true;
                        break;
                    }
                }
            }
        }
        Assert.assertTrue("Couldn't find Haunting rule text displayed for the card", found);

        found = false;
        for (Card card : currentGame.getBattlefield().getAllActivePermanents()) {
            if (card.getName().equals("Goblin Roughrider")) {
                for (String rule : card.getRules(currentGame)) {
                    if (rule.startsWith("Haunted by") && rule.contains("Blind Hunter")) {
                        found = true;
                        break;
                    }
                }
            }
        }
        Assert.assertTrue("Couldn't find Haunted by rule text displayed for the card", found);
        
    }

    // test that Haunted by rule is removed from cards (it is only added to permanent)
    @Test
    public void testRemoveHaunt() {
        
        addCard(Zone.BATTLEFIELD, playerA, "Blind Hunter", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Goblin Roughrider");
        addCard(Zone.HAND, playerA, "Lightning Bolt", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Blind Hunter");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Lightning Bolt", "Goblin Roughrider");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Lightning Bolt", 2);
        assertExileCount("Blind Hunter", 1);
        assertGraveyardCount(playerA, "Goblin Roughrider", 1);
        assertLife(playerA, 22);

        boolean found = false;
        for (Card card : currentGame.getPlayer(playerA.getId()).getGraveyard().getCards(currentGame)) {
            if (card.getName().equals("Goblin Roughrider")) {
                for (String rule : card.getRules(currentGame)) {
                    if (rule.startsWith("Haunted by") && rule.contains("Blind Hunter")) {
                        found = true;
                        break;
                    }
                }
            }
        }
        Assert.assertFalse("Found Haunted by rule text displayed for the card", found);
        
    }

    @Test
    public void testHauntToken() {
        addCard(Zone.BATTLEFIELD, playerA, "Blind Hunter");
        addCard(Zone.HAND, playerA, "Satyr's Cunning");
        addCard(Zone.HAND, playerA, "Lightning Bolt", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Satyr's Cunning");
        castSpell(1, PhaseStep.BEGIN_COMBAT, playerA, "Lightning Bolt", "Blind Hunter");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Lightning Bolt", "Satyr Token");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Lightning Bolt", 2);
        assertExileCount("Blind Hunter", 1);
        assertLife(playerA, 22);
    }
    
}
