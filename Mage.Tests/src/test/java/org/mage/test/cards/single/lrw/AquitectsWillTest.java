package org.mage.test.cards.single.lrw;

import mage.constants.PhaseStep;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author TheElk801
 */
public class AquitectsWillTest extends CardTestPlayerBase {

    private static final String mountain = "Mountain";
    private static final String will = "Aquitect's Will";
    private static final String recall = "Ancestral Recall";
    private static final String hexmage = "Vampire Hexmage";

    @Test
    public void testProduceBlueDuringCast() {
        addCard(Zone.BATTLEFIELD, playerA, "Island");
        addCard(Zone.BATTLEFIELD, playerA, mountain);
        addCard(Zone.HAND, playerA, will);
        addCard(Zone.HAND, playerA, recall);

        setStrictChooseMode(true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, will, mountain);

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, recall, playerA);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertHandCount(playerA, 3);
        assertGraveyardCount(playerA, will, 1);
        assertGraveyardCount(playerA, recall, 1);
        assertCounterCount(playerA, mountain, CounterType.FLOOD, 1);
        assertSubtype(mountain, SubType.MOUNTAIN);
        assertSubtype(mountain, SubType.ISLAND);
    }

    @Test
    public void testProduceBlueOutsideCast() {
        addCard(Zone.BATTLEFIELD, playerA, "Island");
        addCard(Zone.BATTLEFIELD, playerA, mountain);
        addCard(Zone.HAND, playerA, will);
        addCard(Zone.HAND, playerA, recall);

        setStrictChooseMode(true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, will, mountain);

        activateManaAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "{T}: Add {U}");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, recall, playerA);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertHandCount(playerA, 3);
        assertGraveyardCount(playerA, will, 1);
        assertGraveyardCount(playerA, recall, 1);
        assertCounterCount(playerA, mountain, CounterType.FLOOD, 1);
        assertSubtype(mountain, SubType.MOUNTAIN);
        assertSubtype(mountain, SubType.ISLAND);
    }

    @Test
    public void testEffectTiedToCounter() {
        addCard(Zone.BATTLEFIELD, playerA, "Island");
        addCard(Zone.BATTLEFIELD, playerA, mountain);
        addCard(Zone.BATTLEFIELD, playerA, hexmage);
        addCard(Zone.HAND, playerA, will);

        setStrictChooseMode(true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, will, mountain);

        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Sacrifice", mountain);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, will, 1);
        assertGraveyardCount(playerA, hexmage, 1);
        assertCounterCount(playerA, mountain, CounterType.FLOOD, 0);
        assertSubtype(mountain, SubType.MOUNTAIN);
        assertNotSubtype(mountain, SubType.ISLAND);
    }
}
