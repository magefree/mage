package org.mage.test.cards.single.afc;

import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

import mage.constants.PhaseStep;
import mage.constants.Zone;

public class ProsperTomeBoundTest extends CardTestPlayerBase {
    static final String prosper = "Prosper, Tome-Bound";

    @Test
    // Author: alexander-novo
    // As copies of cards aren't themselves cards, and Prosper specifies that his ability only triggers when a *card* is played from exile,
    // Prosper shouldn't work with cards a la Mizzix's Mastery, which creates copies of cards in exile and then casts them.
    // As of right now, this is true, but I'd guess this is due to the fact that effects like Mizzix's Mastery currently don't cast the copies from exile properly,
    // not because Prosper is actually checking that the spells come from actual cards.
    public void castCopyFromExileTest() {
        String mastery = "Mizzix's Mastery";
        String bolt = "Lightning Bolt";

        // Cast mastery from hand targetting bolt, which will be exiled, copied, and cast. Prosper will see this cast.
        addCard(Zone.GRAVEYARD, playerA, bolt);
        addCard(Zone.HAND, playerA, mastery);
        addCard(Zone.BATTLEFIELD, playerA, prosper);

        // Enough mana for Mizzix's mastery
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);

        // Cast mastery. Choose target for bolt
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, mastery, bolt);
        addTarget(playerA, playerB);

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertLife(playerB, 20 - 3);
        assertExileCount(playerA, bolt, 1);
        assertTokenCount(playerA, "Treasure Token", 0);
    }
}
