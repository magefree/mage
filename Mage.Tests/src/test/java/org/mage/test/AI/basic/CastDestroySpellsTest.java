
package org.mage.test.AI.basic;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBaseAI;

/**
 *
 * @author LevelX2
 */
public class CastDestroySpellsTest extends CardTestPlayerBaseAI {

    @Test
    public void testOrzhovCharm() {
        // Choose one -
        // - Return target creature you control and all Auras you control attached to it to their owner's hand;
        // - Destroy target creature and you lose life equal to its toughness;
        // - Return target creature card with converted mana cost 1 or less from your graveyard to the battlefield.
        addCard(Zone.HAND, playerA, "Orzhov Charm"); // {W}{B}

        // {T}: Add {C}.
        // {W/B}, {T}: Add {W}{W}, {W}{B}, or {B}{B}.
        addCard(Zone.BATTLEFIELD, playerA, "Fetid Heath", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);

        // Cycling abilities you activate cost you up to {2} less to activate.
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Orzhov Charm", "Silvercoat Lion");
        setModeChoice(playerA, "2");
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 18);

        assertGraveyardCount(playerA, "Orzhov Charm", 1);
        assertGraveyardCount(playerB, "Silvercoat Lion", 1);
    }

    /**
     * Cast Divine Verdict if the opponent attacks
     */
    @Test
    public void testCastSpellTargingAttacker() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);

        // Destroy target attacking or blocking creature.
        addCard(Zone.HAND, playerA, "Divine Verdict"); // INSTANT {3}{W}

        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion");

        attack(2, playerB, "Silvercoat Lion");

        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 20);

        assertGraveyardCount(playerA, "Divine Verdict", 1);
        assertGraveyardCount(playerB, "Silvercoat Lion", 1);
    }
}
