package org.mage.test.cards.single.lgn;

import mage.abilities.keyword.HasteAbility;
import mage.constants.PhaseStep;
import mage.constants.SubType;
import mage.constants.Zone;
import org.junit.Ignore;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author TheElk801
 */
public class MistformUltimusTest extends CardTestPlayerBase {

    private static final String ultimus = "Mistform Ultimus";
    private static final String chieftain = "Goblin Chieftain";
    private static final String gametrail = "Game-Trail Changeling";
    private static final String inversion = "Nameless Inversion";

    @Test
    public void testMistformUltimus() {
        addCard(Zone.BATTLEFIELD, playerA, ultimus);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertSubtype(ultimus, SubType.GOBLIN);
    }

    @Test
    public void testGoblinChieftain() {
        addCard(Zone.BATTLEFIELD, playerA, ultimus);
        addCard(Zone.BATTLEFIELD, playerA, chieftain);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPowerToughness(playerA, ultimus, 4, 4);
        assertAbility(playerA, ultimus, HasteAbility.getInstance(), true);
    }
}
