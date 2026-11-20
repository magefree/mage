package org.mage.test.cards.single.msh;

import mage.abilities.keyword.LifelinkAbility;
import mage.constants.CardType;
import mage.constants.PhaseStep;
import mage.constants.SubType;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * 400.7
 * An object that moves from one zone to another becomes a new object with no memory of, or relation to, its previous
 * existence.
 * <p>
 * 707.9b
 * Some copy effects modify a characteristic as part of the copying process. The final set of values for that
 * characteristic becomes part of the copiable values of the copy.
 *
 * @author JayDi85
 */
public class TaskmasterMercenaryMimicTest extends CardTestPlayerBase {

    /**
     * Taskmaster, Mercenary Mimic {2}{U}{B}
     * Legendary Creature - Human Mercenary Villain 3/5
     * Photographic Reflexes - At the beginning of your first main phase, until your next turn, Taskmaster becomes a copy
     * of up to one target creature on the battlefield or creature card in a graveyard, except his name is Taskmaster,
     * Mercenary Mimic and he's a legendary Human Mercenary Villain creature.
     */
    private static final String taskmaster = "Taskmaster, Mercenary Mimic";

    @Test
    public void test_CopyCreatureOnBattlefield() {
        addCard(Zone.BATTLEFIELD, playerA, taskmaster);
        //
        addCard(Zone.BATTLEFIELD, playerB, "Vampire Nighthawk"); // Vampire Shaman 2/3, flying, deathtouch, lifelink

        addTarget(playerA, "Vampire Nighthawk"); // creature to copy

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPowerToughness(playerA, taskmaster, 2, 3);
        assertAbility(playerA, taskmaster, LifelinkAbility.getInstance(), true);
        assertSubtype(taskmaster, SubType.HUMAN);
        assertSubtype(taskmaster, SubType.MERCENARY);
        assertSubtype(taskmaster, SubType.VILLAIN);
        assertNotSubtype(taskmaster, SubType.VAMPIRE);
    }

    @Test
    public void test_CopyCreatureCardInGraveyard() {
        addCard(Zone.BATTLEFIELD, playerA, taskmaster);
        //
        addCard(Zone.GRAVEYARD, playerB, "Vampire Nighthawk"); // Vampire Shaman 2/3, flying, deathtouch, lifelink

        addTarget(playerA, "Vampire Nighthawk"); // creature card to copy

        // until your next turn - still a copy on the opponent's turn
        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertPowerToughness(playerA, taskmaster, 2, 3);
        assertAbility(playerA, taskmaster, LifelinkAbility.getInstance(), true);
        assertSubtype(taskmaster, SubType.HUMAN);
        assertNotSubtype(taskmaster, SubType.VAMPIRE);
        assertGraveyardCount(playerB, "Vampire Nighthawk", 1); // the card stays in the graveyard
    }

    @Test
    public void test_CopyEndsOnYourNextTurn() {
        addCard(Zone.BATTLEFIELD, playerA, taskmaster);
        //
        addCard(Zone.BATTLEFIELD, playerB, "Vampire Nighthawk"); // Vampire Shaman 2/3, flying, deathtouch, lifelink

        addTarget(playerA, "Vampire Nighthawk"); // turn 1: creature to copy
        checkPT("copy on opponent's turn", 2, PhaseStep.END_TURN, playerA, taskmaster, 2, 3);
        addTarget(playerA, TestPlayer.TARGET_SKIP); // turn 3: new trigger, copy nothing

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertPowerToughness(playerA, taskmaster, 3, 5);
        assertAbility(playerA, taskmaster, LifelinkAbility.getInstance(), false);
    }

    @Test
    public void test_CopyArtifactCreatureCard_OnlyCreatureTypes() {
        addCard(Zone.BATTLEFIELD, playerA, taskmaster);
        //
        addCard(Zone.GRAVEYARD, playerB, "Lizard Blades"); // Artifact Creature - Equipment Lizard 1/1, double strike

        addTarget(playerA, "Lizard Blades"); // creature card to copy

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        // analog card with the same copy exception, Benthic Anomaly:
        // The token is an Eldrazi creature instead of its other types and subtypes and is colorless instead of
        // retaining any of its colors. It retains any supertypes it had, such as legendary or snow.
        // (2024-06-07)
        assertPowerToughness(playerA, taskmaster, 1, 1);
        assertType(taskmaster, CardType.CREATURE, true);
        assertType(taskmaster, CardType.ARTIFACT, false);
        assertNotSubtype(taskmaster, SubType.EQUIPMENT);
        assertNotSubtype(taskmaster, SubType.LIZARD);
        assertSubtype(taskmaster, SubType.VILLAIN);
    }

    @Test
    public void test_CopyDoubleFacedCardInGraveyard_TDFC() {
        addCard(Zone.BATTLEFIELD, playerA, taskmaster);
        //
        addCard(Zone.GRAVEYARD, playerB, "Delver of Secrets"); // Human Wizard 1/1, transforms to Insectile Aberration

        addTarget(playerA, "Delver of Secrets"); // creature card to copy

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPowerToughness(playerA, taskmaster, 1, 1);
    }

    @Test
    public void test_CopyDoubleFacedCardInGraveyard_MDFC() {
        addCard(Zone.BATTLEFIELD, playerA, taskmaster);
        //
        addCard(Zone.GRAVEYARD, playerB, "Kazandu Mammoth"); // Elephant 3/3 // Kazandu Valley

        addTarget(playerA, "Kazandu Mammoth"); // creature card to copy

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPowerToughness(playerA, taskmaster, 3, 3);
    }

    @Test
    public void test_UpToOne_CopyNothing() {
        addCard(Zone.BATTLEFIELD, playerA, taskmaster);
        //
        addCard(Zone.BATTLEFIELD, playerB, "Vampire Nighthawk"); // Vampire Shaman 2/3, flying, deathtouch, lifelink

        addTarget(playerA, TestPlayer.TARGET_SKIP); // copy nothing

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPowerToughness(playerA, taskmaster, 3, 5);
    }

    @Test
    public void test_BlinkInResponse_CreatureOnBattlefield_NoCopy() {
        addCard(Zone.BATTLEFIELD, playerA, taskmaster);
        //
        addCard(Zone.HAND, playerA, "Cloudshift"); // {W}, exile target creature you control, then return it
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        //
        addCard(Zone.BATTLEFIELD, playerB, "Vampire Nighthawk"); // Vampire Shaman 2/3, flying, deathtouch, lifelink

        // the returned Taskmaster is a new object (400.7), the old trigger can't make it a copy
        addTarget(playerA, "Vampire Nighthawk"); // creature to copy
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cloudshift", taskmaster, "becomes a copy");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Cloudshift", 1);
        assertPowerToughness(playerA, taskmaster, 3, 5);
    }

    @Test
    public void test_BlinkInResponse_CreatureCardInGraveyard_NoCopy() {
        addCard(Zone.BATTLEFIELD, playerA, taskmaster);
        //
        addCard(Zone.HAND, playerA, "Cloudshift"); // {W}, exile target creature you control, then return it
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        //
        addCard(Zone.GRAVEYARD, playerB, "Vampire Nighthawk"); // Vampire Shaman 2/3, flying, deathtouch, lifelink

        // the returned Taskmaster is a new object (400.7), the old trigger can't make it a copy
        addTarget(playerA, "Vampire Nighthawk"); // creature card to copy
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cloudshift", taskmaster, "becomes a copy");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Cloudshift", 1);
        assertPowerToughness(playerA, taskmaster, 3, 5);
    }
}
