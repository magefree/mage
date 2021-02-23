package org.mage.test.cards.single.khm;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author TheElk801
 */
public class DraugrNecromancerTest extends CardTestPlayerBase {

    private static final String necromancer = "Draugr Necromancer";
    private static final String bolt = "Lightning Bolt";
    private static final String bear = "Grizzly Bears";

    @Test
    public void testExilesWithCounter() {
        addCard(Zone.BATTLEFIELD, playerA, necromancer);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.HAND, playerA, bolt);
        addCard(Zone.BATTLEFIELD, playerB, bear);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, bolt, bear);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertExileCount(playerB, bear, 1);
        assertCounterOnExiledCardCount(bear, CounterType.ICE, 1);
    }

    @Test
    public void testCastFromExile() {
        addCard(Zone.BATTLEFIELD, playerA, necromancer);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Zone.HAND, playerA, bolt);
        addCard(Zone.BATTLEFIELD, playerB, bear);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, bolt, bear);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, bear);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertExileCount(playerB, bear, 0);
        assertPermanentCount(playerA, bear, 1);
    }

    @Test
    public void testCastFromExileWithSnow() {
        addCard(Zone.BATTLEFIELD, playerA, necromancer);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Snow-Covered Swamp");
        addCard(Zone.HAND, playerA, bolt);
        addCard(Zone.BATTLEFIELD, playerB, bear);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, bolt, bear);

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, bear);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        assertAllCommandsUsed();

        assertExileCount(playerB, bear, 0);
        assertPermanentCount(playerA, bear, 1);
    }

    @Test
    public void testCastFromExileWithoutSnow() {
        addCard(Zone.BATTLEFIELD, playerA, necromancer);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp");
        addCard(Zone.HAND, playerA, bolt);
        addCard(Zone.BATTLEFIELD, playerB, bear);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, bolt, bear);

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, bear);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertExileCount(playerB, bear, 1);
        assertCounterOnExiledCardCount(bear, CounterType.ICE, 1);
        assertPermanentCount(playerA, bear, 0);
    }
}
