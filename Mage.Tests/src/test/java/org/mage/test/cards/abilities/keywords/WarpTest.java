package org.mage.test.cards.abilities.keywords;

import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author TheElk801
 */
public class WarpTest extends CardTestPlayerBase {

    private static final String colossus = "Bygone Colossus";

    @Test
    public void testRegular() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 9);
        addCard(Zone.HAND, playerA, colossus);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, colossus);

        waitStackResolved(1, PhaseStep.END_TURN, playerA);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, colossus, 1);
    }

    @Test
    public void testWarpExile() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        addCard(Zone.HAND, playerA, colossus);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, colossus + " with Warp");

        waitStackResolved(1, PhaseStep.END_TURN, playerA);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertExileCount(playerA, colossus, 1);
    }

    @Test
    public void testWarpExileCast() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 9);
        addCard(Zone.HAND, playerA, colossus);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, colossus + " with Warp");

        waitStackResolved(1, PhaseStep.END_TURN, playerA);

        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, colossus);

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, colossus, 1);
    }

    @Test
    public void testWarpExileOptions() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        addCard(Zone.HAND, playerA, colossus);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, colossus + " with Warp");

        waitStackResolved(1, PhaseStep.END_TURN, playerA);

        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, colossus + " with Warp");

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_TURN);
        try {
            execute();
        } catch (Throwable e) {
            Assert.assertEquals(
                    "Should fail to be able to cast " + colossus + " with warp",
                    "Can't find ability to activate command: Cast " + colossus + " with Warp",
                    e.getMessage()
            );
        }
    }

    private static final String bolt = "Plasma Bolt";

    @Test
    public void testVoid() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3 + 1);
        addCard(Zone.HAND, playerA, colossus);
        addCard(Zone.HAND, playerA, bolt);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, colossus + " with Warp");

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, bolt, playerB);
        waitStackResolved(1, PhaseStep.POSTCOMBAT_MAIN, playerA);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, colossus, 1);
        assertLife(playerB, 20 - 3);
    }

    @Test
    public void testNoVoid() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 9 + 1);
        addCard(Zone.HAND, playerA, colossus);
        addCard(Zone.HAND, playerA, bolt);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, colossus);

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, bolt, playerB);
        waitStackResolved(1, PhaseStep.POSTCOMBAT_MAIN, playerA);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, colossus, 1);
        assertLife(playerB, 20 - 2);
    }

    private static final String culler = "Timeline Culler";

    @Test
    public void testTimelineCuller() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp");
        addCard(Zone.GRAVEYARD, playerA, culler);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, culler + " with Warp");

        waitStackResolved(1, PhaseStep.END_TURN, playerA);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertExileCount(playerA, culler, 1);
        assertLife(playerA, 20 - 2);
    }

    private static final String bore = "Full Bore";

    @Test
    public void testFullBoreWithoutWarp() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 9 + 1);
        addCard(Zone.HAND, playerA, colossus);
        addCard(Zone.HAND, playerA, bore);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, colossus);

        castSpell(1, PhaseStep.BEGIN_COMBAT, playerA, bore, colossus);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPowerToughness(playerA, colossus, 9 + 3, 9 + 2);
        assertAbility(playerA, colossus, TrampleAbility.getInstance(), false);
        assertAbility(playerA, colossus, HasteAbility.getInstance(), false);
    }

    @Test
    public void testFullBoreWithWarp() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3 + 1);
        addCard(Zone.HAND, playerA, colossus);
        addCard(Zone.HAND, playerA, bore);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, colossus + " with Warp");

        castSpell(1, PhaseStep.BEGIN_COMBAT, playerA, bore, colossus);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPowerToughness(playerA, colossus, 9 + 3, 9 + 2);
        assertAbility(playerA, colossus, TrampleAbility.getInstance(), true);
        assertAbility(playerA, colossus, HasteAbility.getInstance(), true);
    }
}
