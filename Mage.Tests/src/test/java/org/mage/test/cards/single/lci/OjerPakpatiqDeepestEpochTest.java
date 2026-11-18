package org.mage.test.cards.single.lci;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author adams-shaun
 */
public class OjerPakpatiqDeepestEpochTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.o.OjerPakpatiqDeepestEpoch Ojer Pakpatiq, Deepest Epoch} {2}{U}{U}
     * Legendary Creature - God 4/3
     * When Ojer Pakpatiq dies, return it to the battlefield tapped and transformed under its owner's control
     * with three time counters on it.
     * Temple of Cyclical Time
     * {T}: Add {U}. Remove a time counter from this land.
     */
    private static final String ojer = "Ojer Pakpatiq, Deepest Epoch";
    private static final String temple = "Temple of Cyclical Time";

    @Test
    public void test_ReturnsWithThreeTimeCounters() {
        addCard(Zone.BATTLEFIELD, playerA, ojer);
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 3);
        addCard(Zone.HAND, playerB, "Murder");

        castSpell(1, PhaseStep.BEGIN_COMBAT, playerB, "Murder", ojer);

        setStopAt(1, PhaseStep.END_COMBAT);
        setStrictChooseMode(true);
        execute();

        assertPermanentCount(playerA, temple, 1);
        assertTapped(temple, true);
        assertCounterCount(playerA, temple, CounterType.TIME, 3);
    }

    @Test
    public void test_TransformingCardReturnedWithCounter() {
        // Return target creature card from your graveyard to the battlefield with a lifelink counter on it.
        addCard(Zone.HAND, playerA, "Unbreakable Bond");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);
        addCard(Zone.GRAVEYARD, playerA, ojer);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Unbreakable Bond");
        addTarget(playerA, ojer);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        setStrictChooseMode(true);
        execute();

        assertCounterCount(playerA, ojer, CounterType.LIFELINK, 1);
    }

    @Test
    public void test_ModalCardReturnedWithCounter() {
        addCard(Zone.HAND, playerA, "Unbreakable Bond");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);
        addCard(Zone.GRAVEYARD, playerA, "Jennifer Walters");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Unbreakable Bond");
        addTarget(playerA, "Jennifer Walters");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        setStrictChooseMode(true);
        execute();

        assertCounterCount(playerA, "Jennifer Walters", CounterType.LIFELINK, 1);
    }
}
