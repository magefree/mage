package org.mage.test.cards.single.mh3;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class EmrakulTheWorldAnewTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.e.EmrakulTheWorldAnew Emrakul, the World Anew} {12}
     * Legendary Creature — Eldrazi
     * When you cast this spell, gain control of all creatures target player controls.
     * Flying, protection from spells and from permanents that were cast this turn
     * When Emrakul, the World Anew leaves the battlefield, sacrifice all creatures you control.
     * Madness—Pay six {C}.
     * 12/12
     */
    private static final String emrakul = "Emrakul, the World Anew";

    @Test
    public void test_Madness_And_Triggers() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, emrakul, 1);
        addCard(Zone.HAND, playerA, "One with Nothing"); // Discard your hand
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Wastes", 6);

        addCard(Zone.HAND, playerB, "Diabolic Edict"); // Target player sacrifices a creature.
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Memnite", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "One with Nothing", true);
        setChoice(playerA, true); // Yes to madness trigger
        addTarget(playerA, playerB); // for cast trigger

        checkPermanentCount("Has gained control of Memnites", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Memnite", 2);

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Diabolic Edict", playerA);
        setChoice(playerA, emrakul); // choose to sacrifice emrakul, will trigger leave effect

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, 2); // One with Nothing + Emrakul.
        assertGraveyardCount(playerB, "Memnite", 2);
    }

    @Test
    public void test_Protection_Spells() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerB, emrakul);

        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);

        addCard(Zone.HAND, playerA, "Murder");
        addCard(Zone.HAND, playerA, "Pyroclasm");

        checkPlayableAbility("Protected from Murder", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Murder", false);
        checkPlayableAbility("Pyroclasm can be cast", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Pyroclasm", true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Pyroclasm");
        // Cast, but deals no damage to Emrakul

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertDamageReceived(playerB, emrakul, 0); // protection from spell damage
    }

    @Test
    public void test_Protection_Pacifism() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerB, emrakul);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.HAND, playerA, "Pacifism");

        // TODO: Investigate. Pacifism should not be castable at all. Probably more an Aura bug than an Emrakul bug
        checkPlayableAbility("Protected from Pacifism", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Pacifism", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Pacifism", emrakul);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        try {
            execute();
            Assert.fail("should have failed on casting Pacifism");
        } catch (AssertionError error) {
            Assert.assertEquals("Can't find ability to activate command: Cast Pacifism$target=Emrakul, the World Anew", error.getMessage());
        }
    }

    @Test
    public void test_Protection_CastThisTurn() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerB, emrakul);

        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        addCard(Zone.HAND, playerA, "Chandra, Torch of Defiance"); // −3: Chandra, Torch of Defiance deals 4 damage to target creature.

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Chandra, Torch of Defiance", true);

        checkPlayableAbility("Protected from Chandra the turn it was cast", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "-3", false);
        checkPlayableAbility("No longer protected the turn after", 3, PhaseStep.PRECOMBAT_MAIN, playerA, "-3", true);

        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "-3", emrakul);

        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertDamageReceived(playerB, emrakul, 4);
    }

    @Test
    public void test_NoProtection_IfNotCast() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerB, emrakul);

        addCard(Zone.BATTLEFIELD, playerA, "Badlands", 5);
        addCard(Zone.HAND, playerA, "Flametongue Kavu"); // When Flametongue Kavu enters the battlefield, it deals 4 damage to target creature.
        addCard(Zone.HAND, playerA, "Reanimate");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Flametongue Kavu", true);
        addTarget(playerA, "Flametongue Kavu"); // only legal target.

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Reanimate", "Flametongue Kavu");
        addTarget(playerA, emrakul); // since Kavu enters from graveyard, it is valid to target emrakul

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertDamageReceived(playerB, emrakul, 4);
    }

    @Test
    public void test_CanBeBlockedByTokens() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerB, emrakul);

        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Zone.HAND, playerA, "Midnight Haunting"); // Create two 1/1 white Spirit creature tokens with flying.

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA, "Midnight Haunting");
        attack(2, playerB, emrakul, playerA);
        block(2, playerA, "Spirit Token", emrakul);

        setStopAt(2, PhaseStep.END_COMBAT);
        execute();

        assertDamageReceived(playerB, emrakul, 1);
        assertLife(playerA, 20);
    }
}
