package org.mage.test.cards.cost.modification;

import mage.cards.Card;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

import java.util.UUID;

/**
 * {@link mage.cards.a.ArcaneMelee Arcane Melee}
 * {4}{U}
 * Enchantment
 *
 * Instant and sorcery spells cost {2} less to cast.
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
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Arcane Melee", 1);
        addCard(Zone.HAND, playerA, "Flow of Ideas", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Flow of Ideas");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        // by default players don't draw 7 cards at startup in tests (it can be changed through command though)
        // 6 Islands => draw 6 cards
        assertHandCount(playerA, 4);
    }


    /**
     * Shouldn't have any affect while being in the hand.
     */
    @Test
    public void testInHand() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        addCard(Zone.HAND, playerA, "Arcane Melee", 1);
        addCard(Zone.HAND, playerA, "Flow of Ideas", 1);

        checkPlayableAbility("doesn't work on creature", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Flow", false);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        // by default players don't draw 7 cards at startup in tests (it can be changed through command though)
        // 2 cards: 1 Flow of Ideas (not enough mana to cast) + 1 Arcane Melee
        assertHandCount(playerA, 2);
    }

    /**
     * Test cumulative effect of cost reduction effects.
     */
    @Test
    public void testMultiArcaneMelee() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Arcane Melee", 3);
        addCard(Zone.HAND, playerA, "Flow of Ideas", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Flow of Ideas");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        // by default players don't draw 7 cards at startup in tests (it can be changed through command though)
        // 1 card: Flow of Ideas should be cast and one card should be drawn
        assertHandCount(playerA, 1);
        assertGraveyardCount(playerA, "Flow of Ideas", 1);
    }

    /**
     * Should not affect creature card.
     */
    @Test
    public void testNonInstantAndSorcery() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Arcane Melee", 1);
        addCard(Zone.HAND, playerA, "Merfolk Looter", 1);

        checkPlayableAbility("doesn't work on creature", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Merfolk", false);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
    }
}
