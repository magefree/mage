package org.mage.test.cards.single.m3c;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class PyrogoyfTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.p.Pyrogoyf} {3}{R}
     * Creature — Lhurgoyf
     * Pyrogoyf's power is equal to the number of card types among cards in all graveyards and its toughness is equal to that number plus 1.
     * Whenever Pyrogoyf or another Lhurgoyf creature enters the battlefield under your control, that creature deals damage equal to its power to any target.
     * * / 1+*
     */
    private static final String pyrogoyf = "Pyrogoyf";

    @Test
    public void test_Trigger_Simple() {
        setStrictChooseMode(true);

        addCard(Zone.GRAVEYARD, playerB, "Bitterblossom"); // Tribal Enchantment
        addCard(Zone.GRAVEYARD, playerA, "Swamp"); // Land

        addCard(Zone.HAND, playerA, pyrogoyf, 1);
        addCard(Zone.HAND, playerA, "Tarmogoyf", 1);
        addCard(Zone.HAND, playerA, "Memnite", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Taiga", 6);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, pyrogoyf, true);
        addTarget(playerA, playerB); // trigger target, will deal 3
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Tarmogoyf", true);
        addTarget(playerA, pyrogoyf); // trigger target, will deal 3
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Memnite");
        // Not a lhurgoyf, no trigger

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertDamageReceived(playerA, pyrogoyf, 3);
        assertLife(playerB, 20 - 3);
    }

    @Test
    public void test_Trigger_FetchInResponse() {
        setStrictChooseMode(true);

        addCard(Zone.GRAVEYARD, playerB, "Bitterblossom"); // Tribal Enchantment

        addCard(Zone.HAND, playerA, pyrogoyf, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Taiga", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Evolving Wilds");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, pyrogoyf);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA, true);
        addTarget(playerA, playerB); // trigger target, will deal 3
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}, Sacrifice");
        addTarget(playerA, "Mountain");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Evolving Wilds", 1);
        assertLife(playerB, 20 - 3);
    }

    @Test
    public void test_Trigger_Doomblade() {
        setStrictChooseMode(true);

        addCard(Zone.GRAVEYARD, playerB, "Bitterblossom"); // Tribal Enchantment
        addCard(Zone.GRAVEYARD, playerA, "Swamp"); // Land

        addCard(Zone.HAND, playerA, pyrogoyf, 1);
        addCard(Zone.HAND, playerA, "Doom Blade", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Badlands", 6);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, pyrogoyf);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA, true);
        addTarget(playerA, playerB); // trigger target, will deal 3
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Doom Blade", pyrogoyf);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, pyrogoyf, 1);
        assertLife(playerB, 20 - 3); // LKI has 3 power for Pyrogoyf.
    }

    @Test
    public void test_Trigger_Cloudshift() {
        setStrictChooseMode(true);

        addCard(Zone.GRAVEYARD, playerB, "Bitterblossom"); // Tribal Enchantment
        addCard(Zone.GRAVEYARD, playerA, "Swamp"); // Land

        addCard(Zone.HAND, playerA, pyrogoyf, 1);
        addCard(Zone.HAND, playerA, "Cloudshift"); // Instant {W} Exile target creature you control, then return that card to the battlefield under your control.
        addCard(Zone.BATTLEFIELD, playerA, "Plateau", 5);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, pyrogoyf);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA, true);
        addTarget(playerA, playerB); // trigger target, will deal 3
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cloudshift", pyrogoyf);
        addTarget(playerA, playerB); // trigger target, will deal 4

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, pyrogoyf, 1);
        assertLife(playerB, 20 - 3 - 4); // LKI has 3 power for first trigger.
    }
}
