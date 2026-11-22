package org.mage.test.cards.single.sld;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.HexproofAbility;
import mage.abilities.keyword.VigilanceAbility;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class ChaniBladeOfSietchTabrTest extends CardTestPlayerBase {

    // Landfall -- Whenever a land you control enters, put a +1/+1 counter on target creature.
    // If that land is a Desert, that creature gains vigilance, hexproof, and haste until end of turn.
    private static final String CHANI = "Chani, Blade of Sietch Tabr";

    @Test
    public void testDesertLandfallGrantsAbilities() {
        addCard(Zone.BATTLEFIELD, playerA, CHANI);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears");
        addCard(Zone.HAND, playerA, "Desert");

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Desert");
        addTarget(playerA, "Grizzly Bears");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertCounterCount("Grizzly Bears", CounterType.P1P1, 1);
        assertAbility(playerA, "Grizzly Bears", VigilanceAbility.getInstance(), true);
        assertAbility(playerA, "Grizzly Bears", HexproofAbility.getInstance(), true);
        assertAbility(playerA, "Grizzly Bears", HasteAbility.getInstance(), true);
    }

    @Test
    public void testNonDesertLandfallOnlyAddsCounter() {
        addCard(Zone.BATTLEFIELD, playerA, CHANI);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears");
        addCard(Zone.HAND, playerA, "Forest");

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Forest");
        addTarget(playerA, "Grizzly Bears");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertCounterCount("Grizzly Bears", CounterType.P1P1, 1);
        assertAbility(playerA, "Grizzly Bears", VigilanceAbility.getInstance(), false);
        assertAbility(playerA, "Grizzly Bears", HexproofAbility.getInstance(), false);
        assertAbility(playerA, "Grizzly Bears", HasteAbility.getInstance(), false);
    }
}
