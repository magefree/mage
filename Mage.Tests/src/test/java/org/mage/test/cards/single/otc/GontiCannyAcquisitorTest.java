package org.mage.test.cards.single.otc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class GontiCannyAcquisitorTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.g.GontiCannyAcquisitor Gonti, Canny Acquisitor} {2}{B}{G}{U}
     * Legendary Creature — Aetherborn Rogue
     * Spells you cast but don’t own cost {1} less to cast.
     * Whenever one or more creatures you control deal combat damage to a player, look at the top card of that player’s library, then exile it face down. You may play that card for as long as it remains exiled, and mana of any type can be spent to cast that spell.
     * 5/5
     */
    private static final String gonti = "Gonti, Canny Acquisitor";

    @Test
    public void test_Simple() {
        setStrictChooseMode(true);
        skipInitShuffling();

        addCard(Zone.BATTLEFIELD, playerA, gonti);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.LIBRARY, playerB, "Grizzly Bears", 1); // {1}{G}

        attack(1, playerA, gonti, playerB);

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Grizzly Bears");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Grizzly Bears", 1);
        assertTapped("Mountain", true);
    }
}
