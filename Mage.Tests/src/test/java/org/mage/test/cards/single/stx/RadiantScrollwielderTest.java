package org.mage.test.cards.single.stx;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author TheElk801
 */
public class RadiantScrollwielderTest extends CardTestPlayerBase {

    private static final String wielder = "Radiant Scrollwielder";
    private static final String bolt = "Lightning Bolt";

    @Test
    public void testExileCastExile() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.BATTLEFIELD, playerA, wielder);
        addCard(Zone.GRAVEYARD, playerA, bolt);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, bolt, playerB);

//        setStrictChooseMode(true); currently doesn't work as computer player doesn't allow random targeting
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20 + 3);
        assertLife(playerB, 20 - 3);
        assertExileCount(playerA, bolt, 1);
    }
}
