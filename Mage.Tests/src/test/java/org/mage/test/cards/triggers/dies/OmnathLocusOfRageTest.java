
package org.mage.test.cards.triggers.dies;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class OmnathLocusOfRageTest extends CardTestPlayerBase {

    /**
     * The new Omnath's ability doesn't trigger when he dies, although it
     * explicitely states in the card's text that it should.
     */
    @Test
    public void testDiesTriggeredAbility() {
        // <i>Landfall</i> &mdash; Whenever a land enters the battlefield under your control, put a 5/5 red and green Elemental creature token onto the battlefield.
        // Whenever Omnath, Locus of Rage or another Elemental you control dies, Omnath deals 3 damage to any target.
        addCard(Zone.BATTLEFIELD, playerA, "Omnath, Locus of Rage", 1);

        // Target player sacrifices a creature.
        addCard(Zone.HAND, playerB, "Diabolic Edict", 1); // {1}{B}
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Diabolic Edict", playerA);
        addTarget(playerA, playerB);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerB, "Diabolic Edict", 1);
        assertGraveyardCount(playerA, "Omnath, Locus of Rage", 1);

        assertLife(playerA, 20);
        assertLife(playerB, 17);

    }

    @Test
    public void testDiesTriggeredAbilityOnlyIfPresent() {
        // <i>Landfall</i> &mdash; Whenever a land enters the battlefield under your control, put a 5/5 red and green Elemental creature token onto the battlefield.
        // Whenever Omnath, Locus of Rage or another Elemental you control dies, Omnath deals 3 damage to any target.
        addCard(Zone.BATTLEFIELD, playerA, "Omnath, Locus of Rage", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Lightning Elemental", 1); // 4/1 Elemental - Haste

        // Blastfire Bolt deals 5 damage to target creature. Destroy all Equipment attached to that creature.
        addCard(Zone.HAND, playerB, "Blastfire Bolt", 1); // {5}{R}
        addCard(Zone.HAND, playerB, "Lightning Bolt", 1); // {R}
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 7);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Lightning Bolt", "Lightning Elemental"); // Dying Lightning Elemental does no longer trigger ability of Omnath
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Blastfire Bolt", "Omnath, Locus of Rage", "Lightning Bolt");
        addTarget(playerA, playerB);
        addTarget(playerA, playerB);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerB, "Lightning Bolt", 1);
        assertGraveyardCount(playerB, "Blastfire Bolt", 1);
        assertGraveyardCount(playerA, "Omnath, Locus of Rage", 1);
        assertGraveyardCount(playerA, "Lightning Elemental", 1);

        assertLife(playerA, 20);
        assertLife(playerB, 17);

    }
}
