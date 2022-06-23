package org.mage.test.cards.abilities.oneshot.damage;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.game.GameException;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * {@link mage.cards.s.SatyrFiredancer Satyr Firedancer}
 * {1}{R}
 * Enchantment Creature — Satyr
 * Whenever an instant or sorcery spell you control deals damage to an opponent,
 * Satyr Firedancer deals that much damage to target creature that player controls.
 *
 * @author LevelX2
 */
public class SatyrFiredancerTest extends CardTestPlayerBase {

    @Test
    public void testDamageFromInstantToPlayer() {
        addCard(Zone.BATTLEFIELD, playerA, "Satyr Firedancer");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.HAND, playerA, "Lightning Bolt");

        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        addTarget(playerA, "Silvercoat Lion");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 17);

        assertGraveyardCount(playerA, "Lightning Bolt", 1);
        assertGraveyardCount(playerB, "Silvercoat Lion", 1);
    }

    /**
     * Check that Satyr won't trigger from combat damage.
     */
    @Test
    public void testDamageFromAttackWontTrigger() {
        addCard(Zone.BATTLEFIELD, playerA, "Satyr Firedancer");
        addCard(Zone.BATTLEFIELD, playerA, "Pillarfield Ox", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion");

        setStrictChooseMode(true);

        attack(1, playerA, "Pillarfield Ox");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 18);
    }

    /**
     * Check that Satyr doesn't trigger from an ability.
     */
    @Test
    public void testDamageFromAbility() {
        addCard(Zone.BATTLEFIELD, playerA, "Satyr Firedancer");

        // {T}: Prodigal Pyromancer deals 1 damage to any target.
        addCard(Zone.BATTLEFIELD, playerA, "Prodigal Pyromancer", 1);

        setStrictChooseMode(true);

        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: {this} deals", playerB);

        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 19);
    }

    @Test
    public void testPriceOfProgressMultiplayer() throws GameException {
        playerC = createPlayer(currentGame, "PlayerC");
        addCard(Zone.BATTLEFIELD, playerA, "Satyr Firedancer", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        // Price of Progress deals damage to each player equal to twice the number of nonbasic lands that player controls.
        addCard(Zone.HAND, playerA, "Price of Progress", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Taiga", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Swab Goblin", 1);
        addCard(Zone.BATTLEFIELD, playerC, "Savannah", 1);
        addCard(Zone.BATTLEFIELD, playerC, "Grizzly Bears", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Price of Progress");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        execute();

        assertPermanentCount(playerB, "Swab Goblin", 0);
        assertGraveyardCount(playerB, "Swab Goblin", 1);

        assertPermanentCount(playerC, "Grizzly Bears", 0);
        assertGraveyardCount(playerC, "Grizzly Bears", 1);
    }

    @Test
    public void testMultipleInstanceOfDamage() {
        addCard(Zone.BATTLEFIELD, playerA, "Satyr Firedancer", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        addCard(Zone.HAND, playerA, "Fiery Confluence", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Taiga", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Bear Cub", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Forest Bear", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Runeclaw Bear", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Fiery Confluence");
        setModeChoice(playerA, "2");
        setModeChoice(playerA, "2");
        setModeChoice(playerA, "2");

        addTarget(playerA, "Grizzly Bears");
        addTarget(playerA, "Bear Cub");
        addTarget(playerA, "Forest Bear");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        execute();

        assertPermanentCount(playerB, "Grizzly Bears", 0);
        assertPermanentCount(playerB, "Bear Cub", 0);
        assertPermanentCount(playerB, "Forest Bear", 0);
        assertPermanentCount(playerB, "Runeclaw Bear", 1);

        assertGraveyardCount(playerB, "Grizzly Bears", 1);
        assertGraveyardCount(playerB, "Bear Cub", 1);
        assertGraveyardCount(playerB, "Forest Bear", 1);
        assertGraveyardCount(playerB, "Runeclaw Bear", 0);
    }
}
