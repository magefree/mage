package org.mage.test.cards.single.dis;

import mage.abilities.keyword.VigilanceAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class OccularHaloTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.o.OcularHalo Ocular Halo} {3}{U}
     * Enchantment — Aura
     * Enchant creature
     * Enchanted creature has “{T}: Draw a card.”
     * {W}: Enchanted creature gains vigilance until end of turn.
     */
    private static final String halo = "Ocular Halo";

    // bug: the activated ability did not give vigilance
    @Test
    public void test_Vigilance() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, halo);
        addCard(Zone.BATTLEFIELD, playerA, "Elite Vanguard");
        addCard(Zone.BATTLEFIELD, playerA, "Tundra", 5);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, halo, "Elite Vanguard");
        activateAbility(1, PhaseStep.BEGIN_COMBAT, playerA, "{W}");

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertAbility(playerA, "Elite Vanguard", VigilanceAbility.getInstance(), true);
    }
}
