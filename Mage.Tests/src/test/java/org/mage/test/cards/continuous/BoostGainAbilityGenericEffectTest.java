package org.mage.test.cards.continuous;

import mage.abilities.keyword.*;
import mage.constants.EmptyNames;
import mage.constants.PhaseStep;
import mage.constants.Planes;
import mage.constants.Zone;
import mage.game.command.emblems.DomriRadeEmblem;
import mage.game.command.emblems.ElspethSunsChampionEmblem;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * Cards converted to BoostGainAbilityGenericEffect, which applies a boost in layer 7 and grants
 * abilities in layer 6 from a single effect. One case per target pointer shape, plus the cases
 * that decide *which* objects an effect reaches and *what* it grants them.
 *
 * @author notgreat, Claude Opus 5
 */
public class BoostGainAbilityGenericEffectTest extends CardTestPlayerBase {

    /**
     * Target creature gets +1/+0 and gains first strike until end of turn.
     */
    @Test
    public void testTargetCreature() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.HAND, playerA, "Kindled Fury");
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Kindled Fury", "Silvercoat Lion");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPowerToughness(playerA, "Silvercoat Lion", 3, 2);
        assertAbility(playerA, "Silvercoat Lion", FirstStrikeAbility.getInstance(), true);
    }

    /**
     * Both halves must expire together when the duration ends.
     */
    @Test
    public void testBothHalvesEndWithDuration() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.HAND, playerA, "Kindled Fury");
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Kindled Fury", "Silvercoat Lion");

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPowerToughness(playerA, "Silvercoat Lion", 2, 2);
        assertAbility(playerA, "Silvercoat Lion", FirstStrikeAbility.getInstance(), false);
    }

    /**
     * Enchanted creature gets +2/+0 and has trample.
     */
    @Test
    public void testEnchanted() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        addCard(Zone.HAND, playerA, "Rancor");
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Rancor", "Silvercoat Lion");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPowerToughness(playerA, "Silvercoat Lion", 4, 2);
        assertAbility(playerA, "Silvercoat Lion", TrampleAbility.getInstance(), true);
    }

    /**
     * Equipped creature gets +3/+0 and has trample and lifelink -- two abilities from one effect.
     */
    @Test
    public void testEquippedWithTwoAbilities() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Loxodon Warhammer");
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip", "Silvercoat Lion");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPowerToughness(playerA, "Silvercoat Lion", 5, 2);
        assertAbility(playerA, "Silvercoat Lion", TrampleAbility.getInstance(), true);
        assertAbility(playerA, "Silvercoat Lion", LifelinkAbility.getInstance(), true);
    }

    /**
     * Creatures you control get +3/+3 and gain trample until end of turn, and the opponent's do not.
     */
    @Test
    public void testCreaturesYouControl() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 5);
        addCard(Zone.HAND, playerA, "Overrun");
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion");
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Overrun");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPowerToughness(playerA, "Silvercoat Lion", 5, 5);
        assertAbility(playerA, "Silvercoat Lion", TrampleAbility.getInstance(), true);

        assertPowerToughness(playerB, "Silvercoat Lion", 2, 2);
        assertAbility(playerB, "Silvercoat Lion", TrampleAbility.getInstance(), false);
    }

    /**
     * Other Elf creatures get +1/+1 and have forestwalk -- the lord must not boost itself.
     */
    @Test
    public void testLordExcludesItself() {
        addCard(Zone.BATTLEFIELD, playerA, "Elvish Champion");
        addCard(Zone.BATTLEFIELD, playerA, "Llanowar Elves");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPowerToughness(playerA, "Llanowar Elves", 2, 2);
        assertAbility(playerA, "Llanowar Elves", new ForestwalkAbility(), true);

        assertPowerToughness(playerA, "Elvish Champion", 2, 2);
        assertAbility(playerA, "Elvish Champion", new ForestwalkAbility(), false);
    }

    /**
     * Craterhoof Behemoth prints its abilities before the boost and uses a dynamic value:
     * creatures you control gain trample and get +X/+X, where X is the number of creatures you control.
     */
    @Test
    public void testAbilitiesBeforeDynamicBoost() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 8);
        addCard(Zone.HAND, playerA, "Craterhoof Behemoth");
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Craterhoof Behemoth");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        // two creatures on the battlefield once the Behemoth resolves, so +2/+2 each
        assertPowerToughness(playerA, "Silvercoat Lion", 4, 4);
        assertAbility(playerA, "Silvercoat Lion", TrampleAbility.getInstance(), true);
        assertPowerToughness(playerA, "Craterhoof Behemoth", 7, 7);
        assertAbility(playerA, "Craterhoof Behemoth", HasteAbility.getInstance(), true);
    }

    // ------------------------------------------------------------------ which objects are reached

    /**
     * Briar Shield: "Sacrifice Briar Shield: Enchanted creature gets +3/+3 until end of turn."
     * The Aura is gone by the time the ability resolves, so the attachment lives only in LKI.
     */
    @Test
    public void testAttachedSourceSacrificedAsACost() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion"); // 2/2
        addCard(Zone.HAND, playerA, "Briar Shield");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Briar Shield", "Silvercoat Lion", true);
        checkPT("aura attached", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Silvercoat Lion", 3, 3);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sacrifice {this}");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Briar Shield", 1);
        // base 2/2 plus the +3/+3; the static +1/+1 leaves with the Aura
        assertPowerToughness(playerA, "Silvercoat Lion", 5, 5);
    }

    /**
     * Mutual Destruction: "This spell has flash as long as you control a permanent with flash."
     * The grant lands on the card itself, which is never a permanent.
     */
    @Test
    public void testGrantToSourceCard() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Ambush Viper");     // permanent with flash
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion");  // additional cost
        addCard(Zone.HAND, playerA, "Mutual Destruction");
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears");

        // playerB's turn -- only possible if the sorcery has flash
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA, "Mutual Destruction", "Grizzly Bears");
        setChoice(playerA, "Silvercoat Lion"); // sacrifice

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Mutual Destruction", 1);
        assertGraveyardCount(playerB, "Grizzly Bears", 1);
    }

    /**
     * Emblem Elspeth: "Creatures you control get +2/+2 and have flying."
     * Duration.EndOfGame is not a locked-in set, so a creature cast later gets both halves.
     */
    @Test
    public void testEmblemReachesLaterCreature() {
        addEmblem(playerA, new ElspethSunsChampionEmblem());
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // already out
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.HAND, playerA, "Silvercoat Lion");      // enters later

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Silvercoat Lion");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPowerToughness(playerA, "Grizzly Bears", 4, 4);
        assertAbility(playerA, "Grizzly Bears", FlyingAbility.getInstance(), true);
        assertPowerToughness(playerA, "Silvercoat Lion", 4, 4);
        assertAbility(playerA, "Silvercoat Lion", FlyingAbility.getInstance(), true);
    }

    /**
     * Emblem Domri: "Creatures you control have double strike, trample, hexproof, and haste."
     * A grant-only emblem, where nothing but the ability half can show the bug.
     */
    @Test
    public void testGrantOnlyEmblemReachesLaterCreature() {
        addEmblem(playerA, new DomriRadeEmblem());
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.HAND, playerA, "Grizzly Bears");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertAbility(playerA, "Grizzly Bears", HasteAbility.getInstance(), true);
        assertAbility(playerA, "Grizzly Bears", TrampleAbility.getInstance(), true);
    }

    /**
     * Plane - Bant: "All creatures have exalted." A static ability keeps a dynamic set (611.2c)
     * whatever its duration.
     */
    @Test
    public void testStaticAbilityKeepsDynamicSet() {
        addPlane(playerA, Planes.PLANE_BANT);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // already out
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.HAND, playerA, "Silvercoat Lion");      // enters later

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Silvercoat Lion");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertAbility(playerA, "Grizzly Bears", new ExaltedAbility(), true);
        assertAbility(playerA, "Silvercoat Lion", new ExaltedAbility(), true);
    }

    /**
     * Bile Blight: "Target creature and all other creatures with the same name as that creature get
     * -3/-3 until end of turn." A face down creature has no name and so shares one with nothing,
     * which is why its filter has to match the target by identity as well as by name.
     */
    @Test
    public void testNamelessTargetIsStillReached() {
        // spare Swamps, so the two morphs' generic {3} cannot strand the {B}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 10);
        addCard(Zone.HAND, playerA, "Pine Walker", 2); // face down for {3} each
        addCard(Zone.HAND, playerA, "Bile Blight");
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Pine Walker using Morph", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Pine Walker using Morph", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Bile Blight",
                EmptyNames.FACE_DOWN_CREATURE.getTestCommand());

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        // whichever one was targeted died and went to the graveyard face up -- the other shares no
        // name with it, so exactly one of the two is left
        assertGraveyardCount(playerA, "Pine Walker", 1);
        assertPermanentCount(playerA, EmptyNames.FACE_DOWN_CREATURE.getTestCommand(), 1);
        assertPowerToughness(playerB, "Grizzly Bears", 2, 2);
    }

    // ------------------------------------------------------------------ what gets granted

    /**
     * Grothama, All-Devouring: other creatures gain "Whenever this creature attacks, you may have
     * it fight Grothama, All-Devouring." The granted ability can only be built at apply time.
     */
    @Test
    public void testGrantedAbilityBuiltFromRuntimeState() {
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears");
        addCard(Zone.BATTLEFIELD, playerB, "Grothama, All-Devouring"); // 10/8

        attack(1, playerA, "Grizzly Bears");
        setChoice(playerA, true); // "you may have it fight"

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        // the fight happens on the attack trigger, so the Bears never deal combat damage
        assertGraveyardCount(playerA, "Grizzly Bears", 1);
        assertLife(playerB, 20);
    }

    /**
     * Thran Weaponry: "{2}, {T}: All creatures get +2/+2 for as long as Thran Weaponry remains
     * tapped." 611.2b -- once the condition breaks the effect is over and does not come back, but
     * while it holds the boost outlives the turn it was made in.
     */
    @Test
    public void testForAsLongAsEndsForGood() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 9); // echo {4} + {2} + {1} + {2}
        addCard(Zone.BATTLEFIELD, playerA, "Thran Weaponry");
        addCard(Zone.BATTLEFIELD, playerA, "Voltaic Key");
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears");

        setChoice(playerA, true); // pay the {4} echo cost to keep Thran Weaponry around

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{2}, {T}: All creatures get +2/+2");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPT("boosted while tapped", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears", 4, 4);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}, {T}: Untap target artifact", "Thran Weaponry");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPT("boost gone once untapped", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears", 2, 2);

        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "{2}, {T}: All creatures get +2/+2");
        checkPT("second activation", 1, PhaseStep.END_TURN, playerA, "Grizzly Bears", 2 + 2, 2 + 2);

        // Thran Weaponry is still tapped through the opponent's turn, so the boost is too -- it ends
        // with the condition, not with the turn it was made in
        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertTapped("Thran Weaponry", true);
        assertPowerToughness(playerA, "Grizzly Bears", 2 + 2, 2 + 2); // the second activation, not both
    }

    /**
     * Knight of Dawn: "{W}{W}: Knight of Dawn gains protection from the color of your choice until
     * end of turn." The granted ability must never be seen carrying an empty (match-everything)
     * filter, which would strip legal Auras as a state-based action.
     */
    @Test
    public void testGrantedProtectionNeverMatchesEverything() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Knight of Dawn");
        addCard(Zone.HAND, playerA, "Unholy Strength"); // black Aura, +2/+1

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Unholy Strength", "Knight of Dawn", true);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{W}{W}: ");
        setChoice(playerA, "Blue");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Unholy Strength", 1);
        assertPowerToughness(playerA, "Knight of Dawn", 4, 3);
    }

    // ------------------------------------------------------------------ layer interactions

    /**
     * The two halves live in different layers, so a later effect can take one and leave the other.
     * Turn to Frog strips abilities in layer 6 and sets base power and toughness in 7b; Overrun's
     * grant is stripped with everything else, while its boost still applies on top of the new base.
     */
    @Test
    public void testLaterEffectStripsTheGrantButNotTheBoost() {
        addCard(Zone.BATTLEFIELD, playerA, "Tropical Island", 7);
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion"); // 2/2
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears");   // 2/2
        addCard(Zone.HAND, playerA, "Overrun");                // +3/+3 and trample
        addCard(Zone.HAND, playerA, "Turn to Frog");           // base 1/1, loses all abilities

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Overrun");
        castSpell(1, PhaseStep.BEGIN_COMBAT, playerA, "Turn to Frog", "Silvercoat Lion");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.DECLARE_ATTACKERS);
        execute();

        // 1/1 set in 7b, then +3/+3 in 7c -- the boost survives its own effect losing the ability half
        assertPowerToughness(playerA, "Silvercoat Lion", 4, 4);
        assertAbility(playerA, "Silvercoat Lion", TrampleAbility.getInstance(), false);

        assertPowerToughness(playerA, "Grizzly Bears", 5, 5);
        assertAbility(playerA, "Grizzly Bears", TrampleAbility.getInstance(), true);
    }

    /**
     * The same pair in the other order. Layer 6 goes by timestamp, so the grant now lands after the
     * strip and survives it -- while layer 7 is unchanged, since 7b still precedes 7c either way.
     */
    @Test
    public void testGrantAfterTheStripSurvives() {
        addCard(Zone.BATTLEFIELD, playerA, "Tropical Island", 7);
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion"); // 2/2
        addCard(Zone.HAND, playerA, "Overrun");
        addCard(Zone.HAND, playerA, "Turn to Frog");

        castSpell(1, PhaseStep.UPKEEP, playerA, "Turn to Frog", "Silvercoat Lion");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Overrun");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPowerToughness(playerA, "Silvercoat Lion", 4, 4);
        assertAbility(playerA, "Silvercoat Lion", TrampleAbility.getInstance(), true);
    }
}
