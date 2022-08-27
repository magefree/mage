package org.mage.test.cards.continuous;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.players.Player;
import org.junit.Test;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author LevelX2
 */
public class BoostEnchantedTest extends CardTestPlayerBase {

    @Test
    public void testFirebreathingNormal() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1);
        // {R}: Enchanted creature gets +1/+0 until end of turn.
        addCard(Zone.HAND, playerA, "Firebreathing"); // {R} Enchantment - Aura

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Firebreathing", "Silvercoat Lion");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{R}: Enchanted creature");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Firebreathing", 1);
        assertPowerToughness(playerA, "Silvercoat Lion", 3, 2);
    }

    /**
     * On Ghitu Firebreathing (and probably other similar cards), when you
     * activate the ability to give +1/0 to the enchanted creature and the
     * return Ghitu Firebreathing to your hand, the +1/0 goes away on the
     * creature. If you re-cast Ghitu Firebreathing onto the creature, the boost
     * returns.
     * <p>
     * Gatherer Rulings: 9/25/2006 If you return Ghitu Firebreathing to its
     * owner's hand while the +1/+0 ability is on the stack, that ability will
     * still give the creature that was last enchanted by Ghitu Firebreathing
     * +1/+0.
     */
    @Test
    public void testFirebreathingReturnToHand() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1);
        // {R}: Enchanted creature gets +1/+0 until end of turn.
        addCard(Zone.HAND, playerA, "Firebreathing"); // {R} Enchantment - Aura

        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);
        addCard(Zone.HAND, playerB, "Boomerang"); // {U}{U} Instant

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Firebreathing", "Silvercoat Lion");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Boomerang", "Firebreathing");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{R}: Enchanted creature", TestPlayer.NO_TARGET, "Boomerang");

        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertHandCount(playerA, "Firebreathing", 1);
        assertGraveyardCount(playerB, "Boomerang", 1);
        assertPowerToughness(playerA, "Silvercoat Lion", 3, 2);
    }

    /**
     * If the aura moves between activation and resolution, the new enchanted
     * creature should be boosted, not the old one.
     */
    @Test
    public void testFirebreathingWithAuraGraft() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1);
        // {R}: Enchanted creature gets +1/+0 until end of turn.
        addCard(Zone.HAND, playerA, "Firebreathing"); // {R} Enchantment - Aura

        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Pillarfield Ox", 1);
        // Gain control of target Aura that's attached to a permanent. Attach it to another permanent it can enchant.
        addCard(Zone.HAND, playerB, "Aura Graft"); // {1}{U} Instant

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Firebreathing", "Silvercoat Lion");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, 1);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{R}: Enchanted creature");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Aura Graft", "Firebreathing", "{R}: Enchanted");
        setChoice(playerB, "Pillarfield Ox");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerB, "Aura Graft", 1);
        assertPermanentCount(playerB, "Firebreathing", 1);
        assertPowerToughness(playerA, "Silvercoat Lion", 2, 2);
        assertPowerToughness(playerB, "Pillarfield Ox", 3, 4);
    }
}
