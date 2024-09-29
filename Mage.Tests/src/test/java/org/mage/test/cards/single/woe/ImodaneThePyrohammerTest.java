package org.mage.test.cards.single.woe;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class ImodaneThePyrohammerTest extends CardTestPlayerBase {

    /**
     * Imodane, the Pyrohammer
     * {2}{R}{R}
     * Legendary Creature — Human Knight
     * <p>
     * Whenever an instant or sorcery spell you control that targets only a single creature deals damage to that creature, Imodane deals that much damage to each opponent.
     * <p>
     * 4/4
     */
    private static final String imodane = "Imodane, the Pyrohammer";

    // 2/2
    private static final String bears = "Grizzly Bears";
    // 2/2
    private static final String lion = "Silvercoat Lion";

    @Test
    public void boltTheBear() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, imodane);
        addCard(Zone.BATTLEFIELD, playerB, bears);
        // 3 damage to any target
        addCard(Zone.HAND, playerA, "Lightning Bolt");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");

        castSpell(1, PhaseStep.UPKEEP, playerA, "Lightning Bolt", bears);

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertLife(playerB, 20 - 3);
        assertPermanentCount(playerB, bears, 0);
    }

    @Test
    public void flamesOfTheRazeBoarTheBear() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, imodane);
        addCard(Zone.BATTLEFIELD, playerB, bears);
        addCard(Zone.BATTLEFIELD, playerB, lion, 4);

        // Flames of the Raze-Boar deals 4 damage to target creature an opponent controls.
        // Then Flames of the Raze-Boar deals 2 damage to each other creature that player
        // controls if you control a creature with power 4 or greater.
        addCard(Zone.HAND, playerA, "Flames of the Raze-Boar", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 6);

        castSpell(1, PhaseStep.UPKEEP, playerA, "Flames of the Raze-Boar", bears);

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertLife(playerB, 20 - 4); // Only damage on the bears should be accounted for.
        assertPermanentCount(playerB, bears, 0);
        assertPermanentCount(playerB, lion, 0);
    }

    @Test
    public void pyroclasmDoesNotTriggerImodane() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, imodane);
        addCard(Zone.BATTLEFIELD, playerB, bears);
        addCard(Zone.BATTLEFIELD, playerB, lion, 4);

        // 2 damage to each creature
        addCard(Zone.HAND, playerA, "Pyroclasm", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Pyroclasm");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerB, 20); // No damage from Imodane
        assertPermanentCount(playerB, bears, 0);
        assertPermanentCount(playerB, lion, 0);
    }

    @Test
    public void targetTowThingsDoesNotTriggerImodane() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, imodane);
        addCard(Zone.BATTLEFIELD, playerB, bears);

        // Shower of Sparks deals 1 damage to target creature and 1 damage to target player or planeswalker.
        addCard(Zone.HAND, playerA, "Shower of Sparks", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);

        castSpell(1, PhaseStep.UPKEEP, playerA, "Shower of Sparks");
        addTarget(playerA, bears);
        addTarget(playerA, playerB);

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertLife(playerB, 20 - 1); // No damage from Imodane, but one from Shower of Sparks
        assertPermanentCount(playerB, bears, 1);
        assertDamageReceived(playerB, bears, 1);
    }

    @Test
    public void nonSpellsDoesNotTriggerImodane() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, imodane);
        addCard(Zone.BATTLEFIELD, playerB, bears);

        // Enchantment
        // {2}: Task Mage Assembly deals 1 damage to target creature.
        // Any player may activate this ability but only as a sorcery.
        addCard(Zone.BATTLEFIELD, playerA, "Task Mage Assembly", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{2}: ");
        addTarget(playerA, bears);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerB, 20); // No damage from Imodane
        assertPermanentCount(playerB, bears, 1);
        assertDamageReceived(playerB, bears, 1);
    }
}