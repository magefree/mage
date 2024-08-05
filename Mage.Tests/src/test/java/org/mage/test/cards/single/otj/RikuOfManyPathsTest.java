package org.mage.test.cards.single.otj;

import mage.abilities.keyword.TrampleAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author TheElk801
 */
public class RikuOfManyPathsTest extends CardTestPlayerBase {

    private static final String riku = "Riku of Many Paths";
    private static final String takedown = "Artful Takedown";
    private static final String bear = "Grizzly Bears";
    private static final String lion = "Silvercoat Lion";

    @Test
    public void testModeChoice() {
        addCard(Zone.BATTLEFIELD, playerA, "Underground Sea", 4);
        addCard(Zone.BATTLEFIELD, playerA, riku);
        addCard(Zone.BATTLEFIELD, playerA, bear);
        addCard(Zone.BATTLEFIELD, playerA, lion);
        addCard(Zone.HAND, playerA, takedown);

        setModeChoice(playerA, "1");
        setModeChoice(playerA, "2");
        addTarget(playerA, lion);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, takedown, bear);

        setModeChoice(playerA, "2");
        setModeChoice(playerA, "3");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertTapped(bear, true);
        assertGraveyardCount(playerA, lion, 1);
        assertCounterCount(playerA, riku, CounterType.P1P1, 1);
        assertAbility(playerA, riku, TrampleAbility.getInstance(), true);
        assertPermanentCount(playerA, "Bird Token", 1);
    }
}
