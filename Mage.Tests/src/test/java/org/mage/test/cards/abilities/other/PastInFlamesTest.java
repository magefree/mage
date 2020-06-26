package org.mage.test.cards.abilities.other;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author BetaSteward
 */
public class PastInFlamesTest extends CardTestPlayerBase {

    /**
     * Past in Flames
     * Sorcery, 3R (4)
     * Each instant and sorcery card in your graveyard gains flashback until end
     * of turn. The flashback cost is equal to its mana cost.
     * Flashback {4}{R} (You may cast this card from your graveyard for its
     * flashback cost. Then exile it.)
     */

    @Test
    public void testSpellsGainFlashback() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
        addCard(Zone.HAND, playerA, "Past in Flames");
        addCard(Zone.GRAVEYARD, playerA, "Lightning Bolt");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Past in Flames");
        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Flashback");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 17);
        assertLife(playerA, 20);

        assertExileCount("Lightning Bolt", 1);
        assertGraveyardCount(playerA, "Lightning Bolt", 0);
    }

}
