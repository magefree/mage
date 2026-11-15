package org.mage.test.cards.single.trc;

import mage.abilities.keyword.FlyingAbility;
import mage.constants.CardType;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class USSDefiantMirrorMigrantTest extends CardTestPlayerBase {

    @Test
    public void test_PhaseOut() {
        // When U.S.S. Defiant enters, it and up to one target creature you control phase out.
        addCard(Zone.HAND, playerA, "U.S.S. Defiant, Mirror Migrant"); // {2}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // bear 2/2

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "U.S.S. Defiant, Mirror Migrant");
        addTarget(playerA, "Grizzly Bears"); // phase out with it

        // phased out until the next turn of its controller
        checkPermanentCount("after enters", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "U.S.S. Defiant, Mirror Migrant", 0);
        checkPermanentCount("after enters", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Grizzly Bears", 0);
        checkPermanentCount("opponent turn", 2, PhaseStep.PRECOMBAT_MAIN, playerA, "U.S.S. Defiant, Mirror Migrant", 0);
        checkPermanentCount("opponent turn", 2, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears", 0);

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "U.S.S. Defiant, Mirror Migrant", 1);
        assertPermanentCount(playerA, "Grizzly Bears", 1);
    }

    @Test
    public void test_PhaseOut_NoTarget() {
        // When U.S.S. Defiant enters, it and up to one target creature you control phase out.
        addCard(Zone.HAND, playerA, "U.S.S. Defiant, Mirror Migrant"); // {2}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // bear 2/2

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "U.S.S. Defiant, Mirror Migrant");
        addTarget(playerA, TestPlayer.TARGET_SKIP); // up to one - choose nothing

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "U.S.S. Defiant, Mirror Migrant", 0);
        assertPermanentCount(playerA, "Grizzly Bears", 1);
    }

    @Test
    public void test_Station() {
        // 8+ | Flying (5/5)
        addCard(Zone.BATTLEFIELD, playerA, "U.S.S. Defiant, Mirror Migrant");

        addCounters(1, PhaseStep.UPKEEP, playerA, "U.S.S. Defiant, Mirror Migrant", CounterType.CHARGE, 7);
        checkType("7 counters", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "U.S.S. Defiant, Mirror Migrant", CardType.CREATURE, false);
        checkAbility("7 counters", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "U.S.S. Defiant, Mirror Migrant", FlyingAbility.class, false);

        addCounters(1, PhaseStep.BEGIN_COMBAT, playerA, "U.S.S. Defiant, Mirror Migrant", CounterType.CHARGE, 1);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertType("U.S.S. Defiant, Mirror Migrant", CardType.CREATURE, true);
        assertAbility(playerA, "U.S.S. Defiant, Mirror Migrant", FlyingAbility.getInstance(), true);
        assertPowerToughness(playerA, "U.S.S. Defiant, Mirror Migrant", 5, 5);
    }
}
