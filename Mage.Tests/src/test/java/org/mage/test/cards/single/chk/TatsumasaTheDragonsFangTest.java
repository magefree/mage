package org.mage.test.cards.single.chk;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author TheElk801
 */
public class TatsumasaTheDragonsFangTest extends CardTestPlayerBase {

    private static final String tatsumasa = "Tatsumasa, the Dragon's Fang";
    private static final String murder = "Murder";

    @Test
    public void testTatsumasa() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 9);
        addCard(Zone.BATTLEFIELD, playerA, tatsumasa);
        addCard(Zone.HAND, playerA, murder);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{6}");

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, murder, "Dragon Spirit");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerA, tatsumasa, 1);
        assertGraveyardCount(playerA, murder, 1);
    }
}
