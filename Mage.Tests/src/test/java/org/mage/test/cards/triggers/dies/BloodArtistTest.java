package org.mage.test.cards.triggers.dies;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author noxx
 *
 *   Whenever Blood Artist or another creature dies, target player loses 1 life and you gain 1 life.
 */
public class BloodArtistTest extends CardTestPlayerBase {

    /**
     * Tests that whenever Blood Artist goes to graveyard, it would trigger its ability.
     * Tests that after Blood Artist went to graveyard, his ability doesn't work anymore.
     */
    @Test
    public void testDisabledEffectOnChangeZone() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.HAND, playerA, "Lightning Bolt", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Blood Artist", 2);
        addCard(Zone.GRAVEYARD, playerA, "Blood Artist", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Bloodflow Connoisseur", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Blood Artist");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Lightning Bolt", "Bloodflow Connoisseur");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 23);
        assertLife(playerB, 17);
    }

}
