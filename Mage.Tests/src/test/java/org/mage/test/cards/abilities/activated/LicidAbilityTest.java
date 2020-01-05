package org.mage.test.cards.abilities.activated;

import mage.abilities.common.LicidAbility;
import mage.abilities.costs.mana.ColoredManaCost;
import mage.abilities.keyword.HasteAbility;
import mage.constants.CardType;
import mage.constants.ColoredManaSymbol;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Ignore;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author emerald0000
 */
public class LicidAbilityTest extends CardTestPlayerBase {

    /**
     * Activate on another creature
     */
    @Test
    public void BasicUsageTest() {
        addCard(Zone.BATTLEFIELD, playerA, "Pillarfield Ox");
        // {R}, {T}: Enraging Licid loses this ability and becomes an Aura enchantment with enchant creature. Attach it to target creature. You may pay {R} to end this effect.
        // Enchanted creature has haste.
        addCard(Zone.BATTLEFIELD, playerA, "Enraging Licid");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");

        activateAbility(1, PhaseStep.UPKEEP, playerA, "{R},", "Pillarfield Ox");
        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);

        execute();

        assertAbility(playerA, "Pillarfield Ox", HasteAbility.getInstance(), true);
        assertAbility(playerA, "Enraging Licid", new LicidAbility(new ColoredManaCost(ColoredManaSymbol.R), new ColoredManaCost(ColoredManaSymbol.R)), false);
        assertType("Enraging Licid", CardType.ENCHANTMENT, true);
        assertType("Enraging Licid", CardType.CREATURE, false);
    }

    /**
     * Use special action to remove the continuous effect
     */
    @Test
    @Ignore("Test player can't activate special actions yet")
    public void SpecialActionTest() {
        addCard(Zone.BATTLEFIELD, playerA, "Pillarfield Ox");
        // {R}, {T}: Enraging Licid loses this ability and becomes an Aura enchantment with enchant creature. Attach it to target creature. You may pay {R} to end this effect.
        // Enchanted creature has haste.
        addCard(Zone.BATTLEFIELD, playerA, "Enraging Licid");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);

        activateAbility(1, PhaseStep.UPKEEP, playerA, "{R},", "Pillarfield Ox");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{R}: End");
        setStopAt(1, PhaseStep.BEGIN_COMBAT);

        execute();

        assertActionsCount(playerA, 0);
        assertAbility(playerA, "Pillarfield Ox", HasteAbility.getInstance(), false);
        assertAbility(playerA, "Enraging Licid", new LicidAbility(new ColoredManaCost(ColoredManaSymbol.R), new ColoredManaCost(ColoredManaSymbol.R)), true);
        assertType("Enraging Licid", CardType.ENCHANTMENT, false);
        assertType("Enraging Licid", CardType.CREATURE, true);
    }

    /**
     * Licid should die if enchanted creature dies
     */
    @Test
    public void EnchantedCreatureDiesTest() {
        addCard(Zone.BATTLEFIELD, playerA, "Pillarfield Ox");
        // {R}, {T}: Enraging Licid loses this ability and becomes an Aura enchantment with enchant creature. Attach it to target creature. You may pay {R} to end this effect.
        // Enchanted creature has haste.
        addCard(Zone.BATTLEFIELD, playerA, "Enraging Licid");
        // Destroy target nonblack creature.
        addCard(Zone.HAND, playerB, "Doom Blade");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 2);

        activateAbility(1, PhaseStep.UPKEEP, playerA, "{R},", "Pillarfield Ox");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Doom Blade", "Pillarfield Ox");
        setStopAt(1, PhaseStep.BEGIN_COMBAT);

        execute();

        assertPermanentCount(playerA, "Enraging Licid", 0);

        assertGraveyardCount(playerA, "Pillarfield Ox", 1);
        assertGraveyardCount(playerA, "Enraging Licid", 1);
    }
}
