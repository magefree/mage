package org.mage.test.rollback;

import mage.Mana;
import mage.abilities.mana.SimpleManaAbility;
import mage.constants.CardType;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * A failed or canceled mana ability while paying rolls the game back. Everything the spell or ability does after
 * that rollback must still reach the object on the stack.
 */
public class PaymentRollbackTest extends CardTestPlayerBase {

    // The auto-payer tries this before Sol Ring for {1}; its cost fails and rolls the game back, then Sol Ring pays.
    // If the auto-payer ever picks Sol Ring first, the tests using this still pass but no longer test a rollback.
    private void addCanceledSource() {
        addCustomCardWithAbility("canceled source", playerA, new SimpleManaAbility(Zone.BATTLEFIELD,
                Mana.ColorlessMana(2), new CanceledCost()), null, CardType.LAND, "", Zone.BATTLEFIELD);
    }

    @Test
    public void test_Spell_CostPaidAfterRollback() {
        // As an additional cost to cast this spell, sacrifice a creature.
        // Fling deals damage equal to the sacrificed creature's power to any target.
        addCard(Zone.HAND, playerA, "Fling"); // {1}{R}
        addCard(Zone.BATTLEFIELD, playerA, "Hill Giant"); // 3/3
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.BATTLEFIELD, playerA, "Sol Ring");
        addCanceledSource();

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Fling", playerB);
        setChoice(playerA, "Hill Giant"); // sacrifice for cost

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Hill Giant", 1);
        assertTapped("Sol Ring", true);
        assertLife(playerB, 20 - 3);
    }

    @Test
    public void test_ActivatedAbility_CostPaidAfterRollback() {
        // {1}, Sacrifice another creature: You gain life equal to the sacrificed creature's toughness.
        addCard(Zone.BATTLEFIELD, playerA, "Ayli, Eternal Pilgrim");
        addCard(Zone.BATTLEFIELD, playerA, "Hill Giant"); // 3/3
        addCard(Zone.BATTLEFIELD, playerA, "Sol Ring");
        addCanceledSource();

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}, Sacrifice another creature");
        setChoice(playerA, "Hill Giant"); // sacrifice for cost

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Hill Giant", 1);
        assertTapped("Sol Ring", true);
        assertLife(playerA, 20 + 3);
    }

    @Test
    public void test_FusedSpell_TargetsChosenAfterRollback() {
        // Wear {1}{R}: Destroy target artifact.
        // Tear {W}: Destroy target enchantment.
        // Fuse
        addCard(Zone.HAND, playerA, "Wear // Tear");
        addCard(Zone.BATTLEFIELD, playerA, "Plateau", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Sol Ring");
        addCanceledSource();
        addCard(Zone.BATTLEFIELD, playerB, "Juggernaut"); // artifact
        addCard(Zone.BATTLEFIELD, playerB, "Absolute Grace"); // enchantment

        // both halves choose their targets after the fused cost is paid
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "fused Wear // Tear");
        addTarget(playerA, "Juggernaut");
        addTarget(playerA, "Absolute Grace");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertTapped("Sol Ring", true);
        assertGraveyardCount(playerB, "Juggernaut", 1);
        assertGraveyardCount(playerB, "Absolute Grace", 1);
    }

    @Test
    public void test_ConvokeAfterRollback_ManaAbilitiesUsableOnceCast() {
        // Searing Spear deals 3 damage to any target.
        addCard(Zone.HAND, playerA, "Searing Spear"); // {1}{R}, instant
        //
        // {T}: The next spell you cast this turn has convoke.
        addCard(Zone.BATTLEFIELD, playerA, "Wand of the Worldsoul");
        addCard(Zone.BATTLEFIELD, playerA, "Runeclaw Bear"); // 2/2, for convoke
        addCard(Zone.BATTLEFIELD, playerA, "Rakdos Signet"); // {1}, {T}: Add {B}{R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        //
        addCard(Zone.HAND, playerB, "Lightning Bolt");
        addCard(Zone.BATTLEFIELD, playerB, "Mountain");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: The next spell you cast this turn");
        // playerA only: playerB's Bolt below waits for Searing Spear on the stack
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        // R from Mountain, then Rakdos Signet is tried for {1} when nothing is left to pay its own {1}, then convoke pays
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Searing Spear", playerB);
        addTarget(playerA, "Runeclaw Bear"); // convoke for {1}
        // convoke stops mana abilities only while that spell is being paid for
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Lightning Bolt", "Runeclaw Bear", "Searing Spear");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertTapped("Rakdos Signet", false);
        assertGraveyardCount(playerA, "Runeclaw Bear", 1);
        assertLife(playerB, 20 - 3);
    }
}
