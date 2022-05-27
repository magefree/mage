package org.mage.test.cards.control;

import mage.abilities.keyword.FlyingAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.game.permanent.Permanent;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author noxx
 */
public class ExchangeControlTest extends CardTestPlayerBase {

    /**
     * Tests switching controls for two creatures on different sides
     */
    @Test
    public void testSimpleExchange() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);
        addCard(Zone.HAND, playerA, "Switcheroo");

        addCard(Zone.BATTLEFIELD, playerA, "Llanowar Elves");
        addCard(Zone.BATTLEFIELD, playerB, "Elite Vanguard");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Switcheroo", "Llanowar Elves^Elite Vanguard");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        // check creatures changes their controllers
        assertPermanentCount(playerA, "Elite Vanguard", 1);
        assertPermanentCount(playerB, "Llanowar Elves", 1);
    }

    /**
     * Tests switching control for two creature on one side (both creatures are
     * under the same player's control)
     *
     * Also tests "7/1/2012: You don't have to control either target."
     */
    @Test
    public void testOneSideExchange() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);
        addCard(Zone.HAND, playerA, "Switcheroo");

        addCard(Zone.BATTLEFIELD, playerB, "Llanowar Elves");
        addCard(Zone.BATTLEFIELD, playerB, "Elite Vanguard");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Switcheroo", "Llanowar Elves^Elite Vanguard");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        // check spell was cast
        assertGraveyardCount(playerA, "Switcheroo", 1);

        // check nothing happened
        assertPermanentCount(playerB, "Elite Vanguard", 1);
        assertPermanentCount(playerB, "Llanowar Elves", 1);
    }

    /**
     * Tests: 7/1/2012: If one of the target creatures is an illegal target when
     * Switcheroo resolves, the exchange won't happen.
     *
     * Targets opponent's creature
     */
    @Test
    public void testOneTargetBecomesIllegal() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
        addCard(Zone.HAND, playerA, "Switcheroo");
        addCard(Zone.HAND, playerA, "Lightning Bolt");

        addCard(Zone.BATTLEFIELD, playerA, "Llanowar Elves");
        addCard(Zone.BATTLEFIELD, playerB, "Elite Vanguard");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Switcheroo", "Llanowar Elves^Elite Vanguard");
        // cast in response
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Elite Vanguard");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerB, "Elite Vanguard", 1);

        // check nothing happened
        assertPermanentCount(playerA, "Llanowar Elves", 1);
    }

    /**
     * Tests: 7/1/2012: If one of the target creatures is an illegal target when
     * Switcheroo resolves, the exchange won't happen.
     *
     * Targets its own creature.
     */
    @Test
    public void testOneTargetBecomesIllegal2() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
        addCard(Zone.HAND, playerA, "Switcheroo");
        addCard(Zone.HAND, playerA, "Lightning Bolt");

        addCard(Zone.BATTLEFIELD, playerA, "Llanowar Elves");
        addCard(Zone.BATTLEFIELD, playerB, "Elite Vanguard");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Switcheroo", "Llanowar Elves^Elite Vanguard");
        // cast in response
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Llanowar Elves");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Llanowar Elves", 1);

        // check nothing happened
        assertPermanentCount(playerB, "Elite Vanguard", 1);
    }

    /**
     * First gain control by Act of Treason. Then exchange control with other
     * opponent's creature.
     *
     * Finally second creature should stay under ours control permanently.
     */
    @Test
    public void testInteractionWithOtherChangeControlEffect() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
        addCard(Zone.HAND, playerA, "Switcheroo");
        addCard(Zone.HAND, playerA, "Act of Treason");

        // both creatures on opponent's side
        addCard(Zone.BATTLEFIELD, playerB, "Llanowar Elves");
        addCard(Zone.BATTLEFIELD, playerB, "Elite Vanguard");

        // get control
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Act of Treason", "Elite Vanguard");

        // attack
        attack(1, playerA, "Elite Vanguard");

        // exchange control after combat
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Switcheroo", "Llanowar Elves^Elite Vanguard");

        // check the control effect still works on second turn
        setStopAt(2, PhaseStep.END_TURN);
        execute();

        // now it is our creature for ages
        assertPermanentCount(playerA, "Llanowar Elves", 1);
        // this one is still on opponent's side
        assertPermanentCount(playerB, "Elite Vanguard", 1);
    }

    /**
     * Tests switching controls will affect restriction effect
     */
    @Test
    public void testRestrictionEffect() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);
        addCard(Zone.HAND, playerA, "Switcheroo");

        addCard(Zone.BATTLEFIELD, playerA, "War Falcon");
        addCard(Zone.BATTLEFIELD, playerA, "Elite Vanguard");

        addCard(Zone.BATTLEFIELD, playerB, "Llanowar Elves");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Switcheroo", "War Falcon^Llanowar Elves");

        attack(2, playerB, "War Falcon");

        setStopAt(2, PhaseStep.END_TURN);

        try {
            execute();
            assertAllCommandsUsed();
            Assert.fail("must throw exception on execute");
        } catch (Throwable e) {
            if (!e.getMessage().contains("Player PlayerB must have 0 actions but found 1")) {
                Assert.fail("Needed error about PlayerB having too many actions, but got:\n" + e.getMessage());
            }
        }

        // check creatures changes their controllers
        assertPermanentCount(playerA, "Llanowar Elves", 1);
        assertPermanentCount(playerB, "War Falcon", 1);

        // War Falcon can't attack
        assertLife(playerA, 20);
    }

    /**
     * An control exchanged creature gets an copy effect from an creature with
     * an activated ability to the by exchange controlled creature. Check that
     * the activated ability is controlled by the new controller of the copy
     * target.
     */
    @Test
    public void testExchangeAnCopyEffect() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 6);
        // Gilded Drake {1}{U} Creature - Drake
        // Flying
        // When Gilded Drake enters the battlefield, exchange control of Gilded Drake and up to one target
        // creature an opponent controls. If you don't make an exchange, sacrifice Gilded Drake. This ability
        // can't be countered except by spells and abilities. (This effect lasts indefinitely.)
        addCard(Zone.HAND, playerA, "Gilded Drake");
        // Polymorphous Rush {2}{U} - Instant
        // Strive — Polymorphous Rush costs {1}{U} more to cast for each target beyond the first.
        // Choose a creature on the battlefield. Any number of target creatures you control each become a copy of that creature until end of turn.
        addCard(Zone.HAND, playerA, "Polymorphous Rush");
        // {U}: Manta Riders gains flying until end of turn.
        addCard(Zone.BATTLEFIELD, playerB, "Manta Riders");
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion");

        // exchange control between Gilded Drake and Silvercoat Lion
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Gilded Drake");
        addTarget(playerA, "Silvercoat Lion");
        // Let your Silvercoat Lion now be a copy of the Manta Riders
        castSpell(1, PhaseStep.BEGIN_COMBAT, playerA, "Polymorphous Rush", "Silvercoat Lion");
        addTarget(playerA, "Manta Riders");

        // now use the activated ability to make the "Silvercoat Lions" (that became Mana Riders) flying
        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "{U}: {this} gains flying until end of turn.");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        // check creatures change their controllers
        assertPermanentCount(playerB, "Gilded Drake", 1);
        assertGraveyardCount(playerA, "Polymorphous Rush", 1);

        assertPermanentCount(playerB, "Silvercoat Lion", 0);
        assertPermanentCount(playerA, "Silvercoat Lion", 0);

        assertPermanentCount(playerB, "Manta Riders", 1);
        assertPermanentCount(playerA, "Manta Riders", 1);

        Permanent controlledMantas = getPermanent("Manta Riders", playerA.getId());
        Assert.assertTrue("Manta Riders should have flying ability", controlledMantas.getAbilities().contains(FlyingAbility.getInstance()));

    }

    /**
     * Gilded Drake doesn't get sacrificed if the creature its ability targets
     * is invalid when it enters the battlefield
     */
    @Test
    public void testDrakeSacrificedIfNoExchangeHappens() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 6);
        // Gilded Drake {1}{U} Creature - Drake
        // Flying
        // When Gilded Drake enters the battlefield, exchange control of Gilded Drake and up to one target
        // creature an opponent controls. If you don't make an exchange, sacrifice Gilded Drake. This ability
        // can't be countered except by spells and abilities. (This effect lasts indefinitely.)
        addCard(Zone.HAND, playerA, "Gilded Drake");

        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion");
        addCard(Zone.BATTLEFIELD, playerB, "Mountain");
        addCard(Zone.HAND, playerB, "Lightning Bolt");

        // exchange control between Gilded Drake and Silvercoat Lion
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Gilded Drake");
        addTarget(playerA, "Silvercoat Lion");

        // Destroy Silvercoat Lion before the exchange resolves so the Drake has to be sacrificed
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Lightning Bolt", "Silvercoat Lion", "When");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerB, "Lightning Bolt", 1);
        assertGraveyardCount(playerB, "Silvercoat Lion", 1);
        assertGraveyardCount(playerA, "Gilded Drake", 1);

    }
}
