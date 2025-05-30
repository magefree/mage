package org.mage.test.cards.single.m13;

import mage.abilities.keyword.HexproofAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class RingOfEvosIsleTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.r.RingOfEvosIsle Ring of Evos Isle} {2}
     * Artifact — Equipment
     * {2}: Equipped creature gains hexproof until end of turn. (It can’t be the target of spells or abilities your opponents control.)
     * At the beginning of your upkeep, put a +1/+1 counter on equipped creature if it’s blue.
     * Equip {1} ({1}: Attach to target creature you control. Equip only as a sorcery.)
     */
    private static final String ring = "Ring of Evos Isle";

    // bug: the activated ability did not give hexproof
    @Test
    public void test_Hexproof() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Ring of Evos Isle");
        addCard(Zone.BATTLEFIELD, playerA, "Elite Vanguard");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip", "Elite Vanguard");
        activateAbility(1, PhaseStep.BEGIN_COMBAT, playerA, "{2}");

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertAbility(playerA, "Elite Vanguard", HexproofAbility.getInstance(), true);
    }
}
