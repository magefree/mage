package org.mage.test.cards.single.tsp;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author xenohedron
 */
public class NetherTraitorTest extends CardTestPlayerBase {

    private static final String nt = "Nether Traitor";
    /* Haste; shadow
    Whenever another creature is put into your graveyard from the battlefield, you may pay {B}.
    If you do, return this card from your graveyard to the battlefield.
     */
    private static final String gb = "Goblin Bombardment"; // Sacrifice a creature: 1 damage to any target
    private static final String wg = "Warpath Ghoul"; // 3/2

    @Test
    public void testNetherTraitorBattlefieldGhoulDies() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp");
        addCard(Zone.BATTLEFIELD, playerA, nt);
        addCard(Zone.BATTLEFIELD, playerA, gb);
        addCard(Zone.BATTLEFIELD, playerA, wg);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sacrifice");
        setChoice(playerA, wg); // to sac
        addTarget(playerA, playerB); // 1 damage

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 19);
        assertGraveyardCount(playerA, wg, 1);
        assertPermanentCount(playerA, nt, 1);
        assertTapped("Swamp", false);
    }

    @Test
    public void testNetherTraitorBattlefieldAndDies() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp");
        addCard(Zone.BATTLEFIELD, playerA, nt);
        addCard(Zone.BATTLEFIELD, playerA, gb);
        addCard(Zone.BATTLEFIELD, playerA, wg);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sacrifice");
        setChoice(playerA, nt); // to sac
        addTarget(playerA, playerB); // 1 damage

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 19);
        assertGraveyardCount(playerA, nt, 1);
        assertPermanentCount(playerA, wg, 1);
        assertTapped("Swamp", false);
    }

    @Test
    public void testNetherTraitorGraveyardGhoulDies() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp");
        addCard(Zone.GRAVEYARD, playerA, nt);
        addCard(Zone.BATTLEFIELD, playerA, gb);
        addCard(Zone.BATTLEFIELD, playerA, wg);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sacrifice");
        setChoice(playerA, wg); // to sac
        addTarget(playerA, playerB); // 1 damage
        setChoice(playerA, true); // yes to pay B and return

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 19);
        assertGraveyardCount(playerA, wg, 1);
        assertPermanentCount(playerA, nt, 1);
        assertTapped("Swamp", true);
    }

    /* Ruling:
    If Nether Traitor and another creature are put into your graveyard at the same time,
    Nether Traitor's ability won't trigger.
    This is because it must be in your graveyard before the creature dies in order for its ability
    that returns it to the battlefield to trigger.
     */

    @Test
    public void testNetherTraitorGhoulBothDie() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp");
        addCard(Zone.BATTLEFIELD, playerA, nt);
        addCard(Zone.BATTLEFIELD, playerA, "Tooth and Claw");
        // Sacrifice two creatures: Create a 3/1 red Beast creature token named Carnivore.
        addCard(Zone.BATTLEFIELD, playerA, wg);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sacrifice two");
        setChoice(playerA, wg + "^" + nt); // to sac

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertPermanentCount(playerA, "Carnivore", 1);
        assertGraveyardCount(playerA, wg, 1);
        assertGraveyardCount(playerA, nt, 1);
        assertTapped("Swamp", false);
    }

}
