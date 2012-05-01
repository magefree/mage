package org.mage.test.cards.cost.modification;

import mage.Constants;
import mage.cards.Card;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

import java.util.UUID;

/**
 * Arcane Melee:
 *   Enchantment
 *   Instant and sorcery spells cost {2} less to cast.
 *
 * @author noxx
 */
public class ArcaneMeleeTest extends CardTestPlayerBase {

    /**
     * While on battlefield, "Arcane Melee" should reduce cost.
     * So one Island would be enough to cast "Divination" that can be checked by playerA's card count
     */
    @Test
    public void testOnBattlefield() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Island", 4);
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Arcane Melee", 1);
        addCard(Constants.Zone.HAND, playerA, "Flow of Ideas", 1);

        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Flow of Ideas");

        setStopAt(1, Constants.PhaseStep.BEGIN_COMBAT);
        execute();
        
        assertLife(playerA, 20);
        assertLife(playerB, 20);
        // by default players don't draw 7 cards at startup in tests (it can be changed through command though)
        // 6 Islands => draw 6 cards
        assertHandCount(playerA, 4);
    }


    /**
     * "Arcane Melee" shouldn't cause any affect while being in hand
     */
    @Test
    public void testInHand() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Island", 4);
        addCard(Constants.Zone.HAND, playerA, "Arcane Melee", 1);
        addCard(Constants.Zone.HAND, playerA, "Flow of Ideas", 1);

        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Flow of Ideas");

        setStopAt(1, Constants.PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        // by default players don't draw 7 cards at startup in tests (it can be changed through command though)
        // 2 cards: 1 Flow of Ideas (not enough mana to cast) + 1 Arcane Melee
        assertHandCount(playerA, 2);
    }

    /**
     * Test cumulative effect of cost reduction effects
     */
    @Test
    public void testMultiArcaneMelee() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Island", 1);
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Arcane Melee", 3);
        addCard(Constants.Zone.HAND, playerA, "Flow of Ideas", 1);

        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Flow of Ideas");

        setStopAt(1, Constants.PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        // by default players don't draw 7 cards at startup in tests (it can be changed through command though)
        // 1 card: Flow of Ideas should be cast and one card should be drawn
        assertHandCount(playerA, 1);

        // check there is 'Flow of Ideas' in graveyard
        boolean found = false;
        for (UUID cardId : playerA.getGraveyard()) {
            Card card = currentGame.getCard(cardId);
            if (card.getName().equals("Flow of Ideas")) {
                found = true;
                break;
            }
        }
        Assert.assertTrue("Flow of Ideas wasn't found in graveyard, means it wasn't cast", found);
    }

    /**
     * Tests that "Arcane Melee" doesn't affect creature card
     */
    @Test
    public void testNonInstantAndSorcery() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Island", 1);
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Arcane Melee", 1);
        addCard(Constants.Zone.HAND, playerA, "Merfolk Looter", 1);

        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Merfolk Looter");

        setStopAt(1, Constants.PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        // by default players don't draw 7 cards at startup in tests (it can be changed through command though)
        // 1 card: Merfolk Looter (Arcane Melee doesn't affect creatures' costs)
        assertHandCount(playerA, 1);
    }
}
