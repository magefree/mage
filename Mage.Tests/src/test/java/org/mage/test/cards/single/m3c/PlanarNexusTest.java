package org.mage.test.cards.single.m3c;

import mage.abilities.keyword.TrampleAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author TheElk801
 */
public class PlanarNexusTest extends CardTestPlayerBase {

    private static final String nexus = "Planar Nexus";
    private static final String cloudpost = "Cloudpost";
    private static final String glimmerpost = "Glimmerpost";
    private static final String golem = "Stone Golem";

    @Test
    public void testLocus() {
        addCard(Zone.BATTLEFIELD, playerA, nexus);
        addCard(Zone.BATTLEFIELD, playerA, cloudpost);
        addCard(Zone.HAND, playerA, glimmerpost);
        addCard(Zone.HAND, playerA, golem);

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, glimmerpost);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 20 + 3);

        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, golem);
        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, golem, 1);
    }

    private static final String tower = "Urza's Tower";
    private static final String sentinel = "Gilded Sentinel";

    @Test
    public void testTronLand() {
        addCard(Zone.BATTLEFIELD, playerA, nexus);
        addCard(Zone.BATTLEFIELD, playerA, tower);
        addCard(Zone.HAND, playerA, sentinel);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, sentinel);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, sentinel, 1);
    }

    private static final String seas = "Spreading Seas";

    @Test
    public void testSpreadingSeas() {
        addCard(Zone.BATTLEFIELD, playerA, "Island");
        addCard(Zone.BATTLEFIELD, playerA, nexus);
        addCard(Zone.HAND, playerA, seas);
        addCard(Zone.HAND, playerA, glimmerpost);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, seas, nexus);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, glimmerpost);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 20 + 1);
    }

    private static final String vine = "Gatecreeper Vine";

    @Test
    public void testGateSearch() {
        addCard(Zone.LIBRARY, playerA, nexus);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.HAND, playerA, vine);

        setChoice(playerA, true);
        addTarget(playerA, nexus);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, vine);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertHandCount(playerA, nexus, 1);
    }

    private static final String naga = "Sidewinder Naga";

    @Test
    public void testDesertGraveyard() {
        addCard(Zone.GRAVEYARD, playerA, nexus);
        addCard(Zone.BATTLEFIELD, playerA, naga);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPowerToughness(playerA, naga, 3 + 1, 2);
        assertAbility(playerA, naga, TrampleAbility.getInstance(), true);
    }
}
