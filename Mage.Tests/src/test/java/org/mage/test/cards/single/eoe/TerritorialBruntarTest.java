package org.mage.test.cards.single.eoe;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class TerritorialBruntarTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.t.TerritorialBruntar Territorial Bruntar} {4}{R}{R}
     * Creature — Beast
     * Reach
     * Landfall — Whenever a land you control enters, exile cards from the top of your library until you exile a nonland card. You may cast that card this turn.
     * 6/6
     */
    private static final String bruntar = "Territorial Bruntar";

    @Test
    public void test_Simple() {
        addCard(Zone.BATTLEFIELD, playerA, bruntar);
        addCard(Zone.HAND, playerA, "Mountain", 1);
        skipInitShuffling();
        addCard(Zone.LIBRARY, playerA, "Lightning Bolt");
        addCard(Zone.LIBRARY, playerA, "Forest", 8);

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Mountain");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        setStrictChooseMode(true);
        execute();

        assertTappedCount("Mountain", true, 1);
        assertExileCount(playerA, "Forest", 8);
        assertLife(playerB, 20 - 3);
    }
}
