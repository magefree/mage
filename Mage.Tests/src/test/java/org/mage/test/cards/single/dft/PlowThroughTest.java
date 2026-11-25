package org.mage.test.cards.single.dft;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * Plow Through, mode 1: "Target creature you control fights target creature an opponent controls."
 * <p>
 * CR 701.14a: when two creatures fight each other, each deals damage equal to its power to the other.
 * CR 702.15b: damage dealt by a source with lifelink causes its controller to gain that much life.
 * So a lifelink fighter gains its whole power, not only the part that is lethal to the other creature.
 * <p>
 * The excess-damage helper (Permanent.damageWithExcess) deals only min(lethal, damage) because its
 * "excess damage is dealt to that creature's controller instead" callers (Flame Spill and the like)
 * must not deal the excess twice; a fight must not borrow that truncation.
 */
public class PlowThroughTest extends CardTestPlayerBase {

    private static final String plow = "Plow Through";
    private static final String nighthawk = "Vampire Nighthawk"; // 2/3 flying, deathtouch, lifelink
    private static final String crawWurm = "Craw Wurm"; // 6/4
    private static final String baneslayer = "Baneslayer Angel"; // 5/5 flying, first strike, lifelink
    private static final String elves = "Llanowar Elves"; // 1/1
    private static final String spill = "Flame Spill"; // 4 damage to target creature, excess to its controller instead
    private static final String bears = "Grizzly Bears"; // 2/2
    private static final String reckoner = "Boros Reckoner"; // 3/3, whenever dealt damage, deals that much damage to any target

    @Test
    public void testLifelinkDeathtouchFightGainsWholePower() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        addCard(Zone.BATTLEFIELD, playerA, nighthawk);
        addCard(Zone.BATTLEFIELD, playerB, crawWurm);
        addCard(Zone.HAND, playerA, plow);

        setModeChoice(playerA, "1");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, plow, nighthawk + "^" + crawWurm);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        // the 6/4 Craw Wurm deals 6 to the Nighthawk (dies); the Nighthawk deals 2 with deathtouch (dies) and lifelink
        assertGraveyardCount(playerA, nighthawk, 1);
        assertGraveyardCount(playerB, crawWurm, 1);
        assertLife(playerA, 20 + 2);
        assertLife(playerB, 20);
    }

    @Test
    public void testLifelinkFightGainsWholePowerNotToughness() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        addCard(Zone.BATTLEFIELD, playerA, baneslayer);
        addCard(Zone.BATTLEFIELD, playerB, elves);
        addCard(Zone.HAND, playerA, plow);

        setModeChoice(playerA, "1");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, plow, baneslayer + "^" + elves);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        // Baneslayer deals 5 (not just the 1 that is lethal to the Elves) and gains 5
        assertGraveyardCount(playerB, elves, 1);
        assertPermanentCount(playerA, baneslayer, 1);
        assertDamageReceived(playerA, baneslayer, 1);
        assertLife(playerA, 20 + 5);
        assertLife(playerB, 20);
    }

    /**
     * Control, must pass before and after the fight fix: Flame Spill's "excess damage is dealt to that
     * creature's controller instead" still sends only the excess to the controller, and the creature
     * is still dealt only its lethal part. Boros Reckoner reflects the damage it is dealt, so a
     * damage-dealt count of 4 instead of 3 would show.
     */
    @Test
    public void testFlameSpillExcessInsteadStillTruncated() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        addCard(Zone.HAND, playerA, spill);
        addCard(Zone.BATTLEFIELD, playerB, reckoner);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, spill, reckoner);
        addTarget(playerB, playerA); // the Reckoner's reflected damage

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        // 4 damage at a 3/3: 3 to the Reckoner (it reflects 3 to playerA), the excess 1 to playerB
        assertGraveyardCount(playerB, reckoner, 1);
        assertLife(playerB, 20 - 1);
        assertLife(playerA, 20 - 3);
    }

    /** Control: the plain Flame Spill case from ExcessDamageTest, a 2/2 takes 2 and its controller 2. */
    @Test
    public void testFlameSpillOnBears() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        addCard(Zone.HAND, playerA, spill);
        addCard(Zone.BATTLEFIELD, playerB, bears);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, spill, bears);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerB, bears, 1);
        assertLife(playerB, 20 - 2);
        assertLife(playerA, 20);
    }
}
