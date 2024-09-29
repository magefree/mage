package org.mage.test.cards.single.mh2;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class PersistTest extends CardTestPlayerBase {

    // Persist {1}{B}
    // Sorcery
    // Return target nonlegendary creature card from your graveyard to the battlefield with a -1/-1 counter on it.
    private static final String persist = "Persist";
    // Cathedral Sanctifier {W}
    // Creature — Human Cleric
    //
    // When Cathedral Sanctifier enters the battlefield, you gain 3 life.
    // 1/1
    private static final String sanctifier = "Cathedral Sanctifier";

    @Test
    public void test_Persist_Card() {
        addCard(Zone.GRAVEYARD, playerA, sanctifier, 1);
        addCard(Zone.BATTLEFIELD, playerA, "swamp", 2);
        addCard(Zone.HAND, playerA, persist);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, persist, sanctifier);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, sanctifier, 0);
        assertGraveyardCount(playerA, sanctifier, 1);
        assertLife(playerA, 20 + 3);
    }
}
