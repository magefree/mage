
package org.mage.test.cards.continuous;

import mage.abilities.AbilitiesImpl;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.CardType;
import mage.constants.PhaseStep;
import mage.constants.SubType;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author JayDi85
 */
public class BecomesCreatureAttachedTest extends CardTestPlayerBase {

    // Dryad Arbor -- green creature land

    @Test
    public void test_CreatureLandWithColor() {
        addCard(Zone.BATTLEFIELD, playerA, "Dryad Arbor", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Dryad Arbor", 1);
        assertPowerToughness(playerA, "Dryad Arbor", 1, 1);
        // land
        assertColor(playerA, "Forest", "WUBGR", false);
        // dryad
        assertColor(playerA, "Dryad Arbor", "G", true);
        assertColor(playerA, "Dryad Arbor", "WUBR", false);
    }

    @Test
    public void test_AttachToLandWithColorReplace() {
        // Enchanted land is a 2/2 blue Elemental creature with flying. It's still a land.
        addCard(Zone.HAND, playerA, "Wind Zendikon", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);

        addCard(Zone.BATTLEFIELD, playerA, "Dryad Arbor", 1);

        // attach to forest and check color changing
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Wind Zendikon", "Dryad Arbor");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Dryad Arbor", 1);
        assertPowerToughness(playerA, "Dryad Arbor", 2, 2);
        assertType("Dryad Arbor", CardType.CREATURE, true);
        assertType("Dryad Arbor", CardType.LAND, true);
        assertAbilities(playerA, "Dryad Arbor", new AbilitiesImpl<>(FlyingAbility.getInstance()));
        assertColor(playerA, "Dryad Arbor", "U", true);
        assertColor(playerA, "Dryad Arbor", "WBGR", false);
    }

    @Test
    public void test_AttachToLandWithColorAdd() {
        // Enchanted land is a 2/2 blue Elemental creature with flying. It's still a land.
        addCard(Zone.HAND, playerA, "Deep Freeze", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);

        addCard(Zone.BATTLEFIELD, playerA, "Dryad Arbor", 1);

        // attach to forest and check color changing
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Deep Freeze", "Dryad Arbor");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Dryad Arbor", 1);
        assertPowerToughness(playerA, "Dryad Arbor", 0, 4);
        assertType("Dryad Arbor", CardType.CREATURE, true);
        assertType("Dryad Arbor", CardType.LAND, true);
        assertType("Dryad Arbor", CardType.LAND, SubType.WALL);
        assertAbilities(playerA, "Dryad Arbor", new AbilitiesImpl<>(DefenderAbility.getInstance()));
        assertColor(playerA, "Dryad Arbor", "UG", true);
        assertColor(playerA, "Dryad Arbor", "WBR", false);
    }
}
