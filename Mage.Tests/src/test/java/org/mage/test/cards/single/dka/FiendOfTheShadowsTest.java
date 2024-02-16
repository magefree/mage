package org.mage.test.cards.single.dka;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * {@link mage.cards.f.FiendOfTheShadows Fiend of the Shadows}
 * {3}{B}{B}
 * Creature — Vampire Wizard
 * Flying
 * Whenever Fiend of the Shadows deals combat damage to a player, that player exiles a card from their hand. You may play that card for as long as it remains exiled.
 * Sacrifice a Human: Regenerate Fiend of the Shadows.
 * 3/3
 *
 * Also tests regenerate and tests that permanents with protection can be sacrificed
 *
 * @author BetaSteward
 */
public class FiendOfTheShadowsTest extends CardTestPlayerBase {

    @Test
    public void testCard() {
        addCard(Zone.BATTLEFIELD, playerA, "White Knight");
        addCard(Zone.BATTLEFIELD, playerA, "Fiend of the Shadows");
        addCard(Zone.BATTLEFIELD, playerB, "Mountain");
        addCard(Zone.HAND, playerB, "Lightning Bolt");

        setStrictChooseMode(true);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sacrifice a Human: Regenerate {this}.");
        setChoice(playerA, "White Knight");

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Lightning Bolt", "Fiend of the Shadows");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertPermanentCount(playerA, "White Knight", 0);
        assertGraveyardCount(playerB, "Lightning Bolt", 1);
        assertPermanentCount(playerA, "Fiend of the Shadows", 1);
    }

    @Test
    public void testCardExile1() {
        addCard(Zone.BATTLEFIELD, playerA, "Fiend of the Shadows");
        removeAllCardsFromHand(playerB);
        addCard(Zone.HAND, playerB, "Swamp");

        attack(1, playerA, "Fiend of the Shadows");
        setChoice(playerB, "Swamp");
        playLand(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Swamp");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 17);
        assertPermanentCount(playerA, "Fiend of the Shadows", 1);
        assertPermanentCount(playerA, "Swamp", 1);
        assertHandCount(playerB, 0);
    }

    @Test
    public void testCardExile2() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.BATTLEFIELD, playerA, "Fiend of the Shadows");
        removeAllCardsFromHand(playerB);
        addCard(Zone.HAND, playerB, "Lightning Bolt");

        attack(1, playerA, "Fiend of the Shadows");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Lightning Bolt", playerB);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 14);
        assertPermanentCount(playerA, "Fiend of the Shadows", 1);
        assertGraveyardCount(playerA, "Lightning Bolt", 0);
        assertHandCount(playerB, 0);
        assertGraveyardCount(playerB, "Lightning Bolt", 1);
    }
}
