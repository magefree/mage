package org.mage.test.cards.abilities.keywords;

import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.constants.CardType;
import mage.constants.PhaseStep;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author TheElk801
 */
public class StationTest extends CardTestPlayerBase {

    private static final String sawship = "Galvanizing Sawship";

    private void checkSpacecraft(int counters) {
        boolean isLeveled = counters >= 3;
        assertCounterCount(playerA, sawship, CounterType.CHARGE, counters);
        assertAbility(playerA, sawship, HasteAbility.getInstance(), isLeveled);
        assertAbility(playerA, sawship, FlyingAbility.getInstance(), isLeveled);
        assertType(sawship, CardType.ARTIFACT, SubType.SPACECRAFT);
        assertType(sawship, CardType.CREATURE, isLeveled);
        if (isLeveled) {
            assertPowerToughness(playerA, sawship, 6, 5);
        }
    }

    @Test
    public void testNoStation() {
        addCard(Zone.BATTLEFIELD, playerA, sawship);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        checkSpacecraft(0);
    }

    private static final String devils = "Riot Devils";

    @Test
    public void testStationInsufficient() {
        addCard(Zone.BATTLEFIELD, playerA, sawship);
        addCard(Zone.BATTLEFIELD, playerA, devils);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Station");
        setChoice(playerA, devils);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertTapped(devils, true);
        checkSpacecraft(2);
    }

    private static final String giant = "Hill Giant";

    @Test
    public void testStationSufficient() {
        addCard(Zone.BATTLEFIELD, playerA, sawship);
        addCard(Zone.BATTLEFIELD, playerA, giant);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Station");
        setChoice(playerA, giant);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertTapped(giant, true);
        checkSpacecraft(3);
    }

    private static final String warden = "Tapestry Warden";

    @Test
    public void testTapestryWarden() {
        addCard(Zone.BATTLEFIELD, playerA, sawship);
        addCard(Zone.BATTLEFIELD, playerA, warden);
        addCard(Zone.BATTLEFIELD, playerA, devils);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Station");
        setChoice(playerA, devils);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertTapped(devils, true);
        checkSpacecraft(3);
    }
}
