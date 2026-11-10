package org.mage.test.cards.single.mdd;

import mage.constants.CardType;
import mage.constants.PhaseStep;
import mage.constants.SubType;
import mage.constants.Zone;
import org.junit.Ignore;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class TempleOfKhonshuTest extends CardTestPlayerBase {

    @Test
    public void test_EntersTapped_NoDimension() {
        // This land enters tapped unless you control a Dimension.
        addCard(Zone.HAND, playerA, "Temple of Khonshu");

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Temple of Khonshu");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertTapped("Temple of Khonshu", true);
    }

    @Test
    public void test_EntersUntapped_YouControlDimension() {
        // This land enters tapped unless you control a Dimension.
        addCard(Zone.HAND, playerA, "Temple of Khonshu");
        //
        // no real Dimension cards yet
        addCustomCardWithAbility("Dimension Land", playerA, null, null, CardType.LAND, "", Zone.BATTLEFIELD, SubType.DIMENSION);

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Temple of Khonshu");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertTapped("Temple of Khonshu", false);
    }

    @Test
    public void test_EntersTapped_OpponentControlsDimension() {
        // This land enters tapped unless you control a Dimension.
        addCard(Zone.HAND, playerA, "Temple of Khonshu");
        //
        // no real Dimension cards yet
        addCustomCardWithAbility("Dimension Land", playerB, null, null, CardType.LAND, "", Zone.BATTLEFIELD, SubType.DIMENSION);

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Temple of Khonshu");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertTapped("Temple of Khonshu", true);
    }

    @Test
    public void test_EntersUntapped_EveryNonbasicLandType() {
        // This land enters tapped unless you control a Dimension.
        addCard(Zone.HAND, playerA, "Temple of Khonshu");
        //
        addCard(Zone.BATTLEFIELD, playerA, "Planar Nexus"); // This land is every nonbasic land type.

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Temple of Khonshu");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        // TODO: Dimension is not revealed yet and implemented as a nonbasic land type, so Planar Nexus is a Dimension;
        //  when the rules come out or this check fails (Dimension became another type, e.g. an enchantment type),
        //  delete this test and enable test_EntersTapped_EveryNonbasicLandType below
        assertTapped("Temple of Khonshu", false);
    }

    @Test
    @Ignore // TODO: enable it instead test_EntersUntapped_EveryNonbasicLandType if Dimension is not a land type
    public void test_EntersTapped_EveryNonbasicLandType() {
        // This land enters tapped unless you control a Dimension.
        addCard(Zone.HAND, playerA, "Temple of Khonshu");
        //
        addCard(Zone.BATTLEFIELD, playerA, "Planar Nexus"); // This land is every nonbasic land type.

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Temple of Khonshu");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertTapped("Temple of Khonshu", true);
    }
}
