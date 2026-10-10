package org.mage.test.cards.continuous;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.continuous.BecomesCreatureTargetEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.HexproofAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.abilities.token.TreasureAbility;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.PhaseStep;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.permanent.token.custom.CreatureToken;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetLandPermanent;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * 105.3
 * Effects may change an object’s color or give a color to a colorless object. If an effect gives an
 * object a new color, the new color replaces all previous colors the object had (unless the effect
 * said the object became that color “in addition” to its other colors). Effects may also make a
 * colored object become colorless.
 * <p>
 * 205.1a
 * Some effects set an object’s card type. In most such cases, the new card type(s) replaces any
 * existing card types. However, an object with either the instant or sorcery card type retains that
 * type. Counters, stickers, effects, and damage marked on the object remain with it, even if they
 * are meaningless to the new card type. Similarly, when an effect sets one or more of an object’s
 * subtypes, the new subtype(s) replaces any existing subtypes from the appropriate set (creature
 * types, land types, artifact types, enchantment types, planeswalker types, or spell types). If an
 * object’s card type is removed, the subtypes correlated with that card type will remain if they are
 * also the subtypes of a card type the object currently has; otherwise, they are also removed for
 * the entire time the object’s card type is removed. Removing an object’s subtype doesn’t affect
 * its card types at all.
 * <p>
 * 205.1b
 * Some effects change an object’s card type, supertype, or subtype but specify that the object
 * retains a prior card type, supertype, or subtype. In such cases, all the object’s prior card
 * types, supertypes, and subtypes are retained. This rule applies to effects that use phrases such
 * as “in addition to its other types” or that state that something is “still a [type, supertype, or
 * subtype].” Some effects state that an object becomes an “artifact creature”; these effects also
 * allow the object to retain all of its prior card types and subtypes. Some effects state that an
 * object becomes a “[creature type or types] artifact creature”; these effects also allow the
 * object to retain all of its prior card types and subtypes other than creature types, but replace
 * any existing creature types.
 *
 * @author JayDi85
 */
public class BecomesCreatureTargetEffectTest extends CardTestPlayerBase {

    @Test
    public void test_Become_SecretIdentity_1() {
        // change stats

        // Conceal -- Until end of turn, target creature you control becomes a Citizen with base power and toughness 1/1 and gains hexproof
        addCard(Zone.HAND, playerA, "Secret Identity"); // {U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // bear 2/2

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Secret Identity", "Grizzly Bears");
        setModeChoice(playerA, "1"); // choose Conceal mode

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        // keep
        assertColor(playerA, "Grizzly Bears", "G", true);
        // gain
        assertPowerToughness(playerA, "Grizzly Bears", 1, 1);
        assertAbility(playerA, "Grizzly Bears", HexproofAbility.getInstance(), true);
        assertSubtype("Grizzly Bears", SubType.CITIZEN);
        // lost
        assertNotSubtype("Grizzly Bears", SubType.BEAR);
    }

    @Test
    public void test_Become_SecretIdentity_2() {
        // change stats

        // Reveal -- Until end of turn, target creature you control becomes a Hero with base power and toughness 3/4 and gains flying and vigilance
        addCard(Zone.HAND, playerA, "Secret Identity"); // {U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // bear 2/2

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Secret Identity", "Grizzly Bears");
        setModeChoice(playerA, "2"); // choose Reveal mode

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        // keep
        assertColor(playerA, "Grizzly Bears", "G", true);
        // gain
        assertPowerToughness(playerA, "Grizzly Bears", 3, 4);
        assertAbility(playerA, "Grizzly Bears", FlyingAbility.getInstance(), true);
        assertAbility(playerA, "Grizzly Bears", VigilanceAbility.getInstance(), true);
        assertSubtype("Grizzly Bears", SubType.HERO);
        // lost
        assertNotSubtype("Grizzly Bears", SubType.BEAR);
    }

    @Test
    public void test_Become_Omnibian() {
        // change stats

        // This effect changes only the power, toughness, and creature type of the targeted
        // creature. It does not cause that creature to lose any abilities.
        // (2013-04-15)

        // {T}: Target creature becomes a Frog with base power and toughness 3/3 until end of turn.
        addCard(Zone.BATTLEFIELD, playerA, "Omnibian");
        //
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // bear 2/2

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Target creature becomes", "Grizzly Bears");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        // keep
        assertColor(playerA, "Grizzly Bears", "G", true);
        // gain
        assertPowerToughness(playerA, "Grizzly Bears", 3, 3);
        assertSubtype("Grizzly Bears", SubType.FROG);
        // lost
        assertNotSubtype("Grizzly Bears", SubType.BEAR);
    }

    @Test
    public void test_BecomeAndLoseAbilities_TurnToFrog() {
        // lost abilities + change stats

        // The creature will lose all other colors and creature types, but it will retain any other
        // card types (such as artifact) or supertypes (such as legendary) it may have.
        // (2014-07-18)

        // Until end of turn, target creature loses all abilities and becomes a blue Frog with base power and toughness 1/1
        addCard(Zone.HAND, playerA, "Turn to Frog"); // {1}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Lizard Blades"); // red artifact creature, Equipment Lizard 1/1, double strike

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Turn to Frog", "Lizard Blades");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        // keep
        assertType("Lizard Blades", CardType.ARTIFACT, true);
        assertSubtype("Lizard Blades", SubType.EQUIPMENT);
        // gain
        assertPowerToughness(playerA, "Lizard Blades", 1, 1);
        assertColor(playerA, "Lizard Blades", "U", true);
        assertSubtype("Lizard Blades", SubType.FROG);
        // lost
        assertAbility(playerA, "Lizard Blades", DoubleStrikeAbility.getInstance(), false);
        assertColor(playerA, "Lizard Blades", "R", false);
        assertNotSubtype("Lizard Blades", SubType.LIZARD);
    }

    @Test
    public void test_BecomeAndLoseAbilities_TheCurseOfFenric_1() {
        // lost abilities + change stats

        // II -- Target nontoken creature becomes a 6/6 legendary Horror creature named Fenric and loses all abilities.
        addCard(Zone.BATTLEFIELD, playerA, "The Curse of Fenric");
        //
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears"); // bear 2/2

        addTarget(playerA, TestPlayer.TARGET_SKIP); // chapter I: destroy no creature of playerA
        addTarget(playerA, TestPlayer.TARGET_SKIP); // chapter I: destroy no creature of playerB
        addTarget(playerA, "Grizzly Bears"); // chapter II

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        // keep
        assertColor(playerB, "Fenric", "G", true);
        // gain
        assertPermanentCount(playerB, "Fenric", 1);
        assertPowerToughness(playerB, "Fenric", 6, 6);
        Assert.assertTrue("must be legendary", getPermanent("Fenric").isLegendary(currentGame));
        assertType("Fenric", CardType.CREATURE, SubType.HORROR);
        // lost
        assertPermanentCount(playerB, "Grizzly Bears", 0);
        assertNotSubtype("Fenric", SubType.BEAR);
    }

    @Test
    public void test_BecomeAndLoseAbilities_TheCurseOfFenric_2() {
        // lost abilities + lost card types + change stats

        // II -- Target nontoken creature becomes a 6/6 legendary Horror creature named Fenric and loses all abilities.
        addCard(Zone.BATTLEFIELD, playerA, "The Curse of Fenric");
        //
        addCard(Zone.BATTLEFIELD, playerB, "Lizard Blades"); // red artifact creature, Equipment Lizard 1/1, double strike

        addTarget(playerA, TestPlayer.TARGET_SKIP); // chapter I: destroy no creature of playerA
        addTarget(playerA, TestPlayer.TARGET_SKIP); // chapter I: destroy no creature of playerB
        addTarget(playerA, "Lizard Blades"); // chapter II

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        // keep
        assertColor(playerB, "Fenric", "R", true);
        // gain
        assertPermanentCount(playerB, "Fenric", 1);
        assertPowerToughness(playerB, "Fenric", 6, 6);
        Assert.assertTrue("must be legendary", getPermanent("Fenric").isLegendary(currentGame));
        assertType("Fenric", CardType.CREATURE, SubType.HORROR);
        // lost
        assertPermanentCount(playerB, "Lizard Blades", 0);
        assertAbility(playerB, "Fenric", DoubleStrikeAbility.getInstance(), false);
        assertNotSubtype("Fenric", SubType.LIZARD);
        assertNotType("Fenric", CardType.ARTIFACT);
        assertNotSubtype("Fenric", SubType.EQUIPMENT);
    }

    @Test
    public void test_BecomeAndLoseAbilities_LizardConnorssCurse() {
        // lost abilities + lost card types + change stats

        // When Lizard, Connors's Curse enters, up to one other target creature loses all abilities and becomes a green Lizard creature with base power and toughness 4/4.
        addCard(Zone.HAND, playerA, "Lizard, Connors's Curse"); // {2}{G}{G}
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);
        //
        addCard(Zone.BATTLEFIELD, playerB, "Ornithopter"); // colorless artifact creature, Thopter 0/2, flying

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lizard, Connors's Curse");
        addTarget(playerA, "Ornithopter");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        // gain
        assertPowerToughness(playerB, "Ornithopter", 4, 4);
        assertColor(playerB, "Ornithopter", "G", true);
        assertType("Ornithopter", CardType.CREATURE, SubType.LIZARD);
        // lost
        assertNotType("Ornithopter", CardType.ARTIFACT);
        assertNotSubtype("Ornithopter", SubType.THOPTER);
        assertAbility(playerB, "Ornithopter", FlyingAbility.getInstance(), false);
    }

    @Test
    public void test_BecomeAndLoseAbilities_MercurialTransformation_1() {
        // lost abilities + lost card types + change stats

        // You choose Frog or Octopus as Mercurial Transformation resolves, not when you cast it.
        // (2021-04-16)

        // Until end of turn, target nonland permanent loses all abilities and becomes your choice of a blue Frog creature with base power and toughness 1/1 or a blue Octopus creature with base power and toughness 4/4.
        addCard(Zone.HAND, playerA, "Mercurial Transformation"); // {1}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        //
        addCard(Zone.BATTLEFIELD, playerB, "Ornithopter"); // colorless artifact creature, Thopter 0/2, flying

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Mercurial Transformation", "Ornithopter");
        setChoice(playerA, true); // Frog

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        // gain
        assertPowerToughness(playerB, "Ornithopter", 1, 1);
        assertColor(playerB, "Ornithopter", "U", true);
        assertType("Ornithopter", CardType.CREATURE, SubType.FROG);
        // lost
        assertNotType("Ornithopter", CardType.ARTIFACT);
        assertNotSubtype("Ornithopter", SubType.THOPTER);
        assertAbility(playerB, "Ornithopter", FlyingAbility.getInstance(), false);
    }

    @Test
    public void test_BecomeAndLoseAbilities_MercurialTransformation_2() {
        // lost abilities + lost card types + change stats

        // You choose Frog or Octopus as Mercurial Transformation resolves, not when you cast it.
        // (2021-04-16)

        // Until end of turn, target nonland permanent loses all abilities and becomes your choice of a blue Frog creature with base power and toughness 1/1 or a blue Octopus creature with base power and toughness 4/4.
        addCard(Zone.HAND, playerA, "Mercurial Transformation"); // {1}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        //
        addCard(Zone.BATTLEFIELD, playerB, "Ajani Goldmane"); // white planeswalker, Ajani

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Mercurial Transformation", "Ajani Goldmane");
        setChoice(playerA, false); // Octopus

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        // gain
        assertPowerToughness(playerB, "Ajani Goldmane", 4, 4);
        assertColor(playerB, "Ajani Goldmane", "U", true);
        assertType("Ajani Goldmane", CardType.CREATURE, SubType.OCTOPUS);
        // lost
        assertColor(playerB, "Ajani Goldmane", "W", false);
        assertNotType("Ajani Goldmane", CardType.PLANESWALKER);
        assertNotSubtype("Ajani Goldmane", SubType.AJANI);
    }

    @Test
    public void test_BecomeAndLoseAbilities_HuntedByTheFamily() {
        // lost abilities + lost card types + change stats

        // Choose up to four target creatures you don't control. For each of them, that creature's controller faces a villainous choice -- That creature becomes a 1/1 white Human creature and loses all abilities, or you create a token that's a copy of it.
        addCard(Zone.HAND, playerA, "Hunted by The Family"); // {5}{U}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 7);
        //
        addCard(Zone.BATTLEFIELD, playerB, "Ornithopter"); // colorless artifact creature, Thopter 0/2, flying

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Hunted by The Family", "Ornithopter");
        setChoice(playerB, true); // becomes a Human

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        // gain
        assertPowerToughness(playerB, "Ornithopter", 1, 1);
        assertColor(playerB, "Ornithopter", "W", true);
        assertType("Ornithopter", CardType.CREATURE, SubType.HUMAN);
        // lost
        assertNotType("Ornithopter", CardType.ARTIFACT);
        assertNotSubtype("Ornithopter", SubType.THOPTER);
        assertAbility(playerB, "Ornithopter", FlyingAbility.getInstance(), false);
    }

    @Test
    public void test_Become_ScaleUp_1() {
        // change stats

        // Until end of turn, target creature you control becomes a green Wurm with base power and toughness 6/4
        addCard(Zone.HAND, playerA, "Scale Up"); // {G}
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // bear 2/2

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Scale Up", "Grizzly Bears");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        // gain
        assertPowerToughness(playerA, "Grizzly Bears", 6, 4);
        assertColor(playerA, "Grizzly Bears", "G", true);
        assertSubtype("Grizzly Bears", SubType.WURM);
        // lost
        assertNotSubtype("Grizzly Bears", SubType.BEAR);
    }

    @Test
    public void test_Become_ScaleUp_2() {
        // change stats

        // The affected creatures don't lose any abilities when they become Wurms.
        // (2019-06-14)

        // Until end of turn, target creature you control becomes a green Wurm with base power and toughness 6/4
        addCard(Zone.HAND, playerA, "Scale Up"); // {G}
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Lizard Blades"); // red artifact creature, Equipment Lizard 1/1, double strike

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Scale Up", "Lizard Blades");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        // keep
        assertType("Lizard Blades", CardType.ARTIFACT, true);
        assertAbility(playerA, "Lizard Blades", DoubleStrikeAbility.getInstance(), true);
        assertSubtype("Lizard Blades", SubType.EQUIPMENT);
        // gain
        assertPowerToughness(playerA, "Lizard Blades", 6, 4);
        assertColor(playerA, "Lizard Blades", "G", true);
        assertSubtype("Lizard Blades", SubType.WURM);
        // lost
        assertColor(playerA, "Lizard Blades", "R", false);
        assertNotSubtype("Lizard Blades", SubType.LIZARD);
    }

    @Test
    public void test_BecomeAndLoseAbilitiesAndCardTypes_VraskaBetrayalsSting_1() {
        // lost abilities + lost card types

        // The target of Vraska's second loyalty ability will lose any other subtypes and card
        // types it previously had and will be only a Treasure artifact. It will retain any
        // supertypes it had.
        // (2023-02-04)

        // −2: Target creature becomes a Treasure artifact with "{T}, Sacrifice this artifact: Add one mana of any color" and loses all other card types and abilities
        addCard(Zone.BATTLEFIELD, playerA, "Vraska, Betrayal's Sting");
        //
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears"); // bear 2/2

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "-2:", "Grizzly Bears");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        // keep
        assertColor(playerB, "Grizzly Bears", "G", true);
        // gain
        assertType("Grizzly Bears", CardType.ARTIFACT, true);
        assertSubtype("Grizzly Bears", SubType.TREASURE);
        assertAbility(playerB, "Grizzly Bears", new TreasureAbility(false), true);
        // lost
        assertNotType("Grizzly Bears", CardType.CREATURE);
        assertNotSubtype("Grizzly Bears", SubType.BEAR);
    }

    @Test
    public void test_BecomeAndLoseAbilitiesAndCardTypes_VraskaBetrayalsSting_2() {
        // lost abilities + lost card types

        // The target of Vraska's second loyalty ability will lose any other subtypes and card
        // types it previously had and will be only a Treasure artifact. It will retain any
        // supertypes it had.
        // (2023-02-04)

        // −2: Target creature becomes a Treasure artifact with "{T}, Sacrifice this artifact: Add one mana of any color" and loses all other card types and abilities
        addCard(Zone.BATTLEFIELD, playerA, "Vraska, Betrayal's Sting");
        //
        addCard(Zone.BATTLEFIELD, playerB, "Lizard Blades"); // red artifact creature, Equipment Lizard 1/1, double strike

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "-2:", "Lizard Blades");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        // keep
        assertType("Lizard Blades", CardType.ARTIFACT, true);
        assertColor(playerB, "Lizard Blades", "R", true);
        // gain
        assertSubtype("Lizard Blades", SubType.TREASURE);
        assertAbility(playerB, "Lizard Blades", new TreasureAbility(false), true);
        // lost
        assertNotType("Lizard Blades", CardType.CREATURE);
        assertAbility(playerB, "Lizard Blades", DoubleStrikeAbility.getInstance(), false);
        assertNotSubtype("Lizard Blades", SubType.LIZARD);
        assertNotSubtype("Lizard Blades", SubType.EQUIPMENT);
    }

    @Test
    public void test_BecomeAndLoseAbilities_KitesailLarcenist() {
        // lost abilities + lost card types

        // The targets of Kitesail Larcenist's enters-the-battlefield ability will lose any other
        // subtypes and card types they previously had and will be only Treasure artifacts as long
        // as Kitesail Larcenist remains on the battlefield. They will retain any supertypes they
        // had.
        // (2023-11-10)

        // When this creature enters, for each player, choose up to one other target artifact or creature that player controls. For as long as this creature remains on the battlefield, the chosen permanents become Treasure artifacts with "{T}, Sacrifice this artifact: Add one mana of any color" and lose all other abilities.
        addCard(Zone.HAND, playerA, "Kitesail Larcenist"); // {2}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        //
        addCard(Zone.BATTLEFIELD, playerB, "Lizard Blades"); // red artifact creature, Equipment Lizard 1/1, double strike

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Kitesail Larcenist");
        addTarget(playerA, TestPlayer.TARGET_SKIP); // choose nothing of playerA
        addTarget(playerA, "Lizard Blades"); // choose the permanent of playerB

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        // keep
        assertType("Lizard Blades", CardType.ARTIFACT, true);
        assertColor(playerB, "Lizard Blades", "R", true);
        // gain
        assertSubtype("Lizard Blades", SubType.TREASURE);
        assertAbility(playerB, "Lizard Blades", new TreasureAbility(false), true);
        // lost
        assertNotType("Lizard Blades", CardType.CREATURE);
        assertAbility(playerB, "Lizard Blades", DoubleStrikeAbility.getInstance(), false);
        assertNotSubtype("Lizard Blades", SubType.LIZARD);
        assertNotSubtype("Lizard Blades", SubType.EQUIPMENT);
    }

    @Test
    public void test_Become_MetamorphicBlast() {
        // change stats

        // The effect of Metamorphic Blast's first mode causes the target creature to lose its
        // other colors and creature types. It doesn't lose any of its abilities.
        // (2024-04-12)

        // Spree
        // + {1} -- Until end of turn, target creature becomes a white Rabbit with base power and toughness 0/1
        addCard(Zone.HAND, playerA, "Metamorphic Blast"); // {U} + {1}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1 + 1);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // bear 2/2

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Metamorphic Blast", "Grizzly Bears");
        setModeChoice(playerA, "1"); // choose Rabbit mode
        setModeChoice(playerA, TestPlayer.MODE_SKIP); // no more modes

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        // gain
        assertPowerToughness(playerA, "Grizzly Bears", 0, 1);
        assertColor(playerA, "Grizzly Bears", "W", true);
        assertSubtype("Grizzly Bears", SubType.RABBIT);
        // lost
        assertColor(playerA, "Grizzly Bears", "G", false);
        assertNotSubtype("Grizzly Bears", SubType.BEAR);
    }

    @Test
    public void test_BecomeArtifactCreature_LivingBrain_1() {
        // change stats + keep other card types

        // At the beginning of combat on your turn, target non-Equipment artifact you control becomes an artifact creature with base power and toughness 3/3 until end of turn. Untap it.
        addCard(Zone.BATTLEFIELD, playerA, "Living Brain, Mechanical Marvel");
        //
        addCard(Zone.HAND, playerA, "Thraben Inspector"); // {W}, ETB: investigate
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Thraben Inspector");

        addTarget(playerA, "Clue Token"); // target for Living Brain's combat trigger

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        // keep
        assertType("Clue Token", CardType.ARTIFACT, true);
        assertSubtype("Clue Token", SubType.CLUE);
        // gain
        assertType("Clue Token", CardType.CREATURE, true);
        assertPowerToughness(playerA, "Clue Token", 3, 3);
    }

    @Test
    public void test_BecomeArtifactCreature_LivingBrain_2() {
        // keep other card types

        // At the beginning of combat on your turn, target non-Equipment artifact you control becomes an artifact creature with base power and toughness 3/3 until end of turn. Untap it.
        addCard(Zone.BATTLEFIELD, playerA, "Living Brain, Mechanical Marvel"); // legendary artifact creature, Robot Villain 3/3

        addTarget(playerA, "Living Brain, Mechanical Marvel"); // target itself for the combat trigger

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        // keep
        assertType("Living Brain, Mechanical Marvel", CardType.ARTIFACT, true);
        assertType("Living Brain, Mechanical Marvel", CardType.CREATURE, SubType.ROBOT);
        assertSubtype("Living Brain, Mechanical Marvel", SubType.VILLAIN);
        Assert.assertTrue("must be legendary", getPermanent("Living Brain, Mechanical Marvel").isLegendary(currentGame));
        assertPowerToughness(playerA, "Living Brain, Mechanical Marvel", 3, 3);
    }

    @Test
    public void test_BecomeArtifactCreature_MindTransferProtocol() {
        // change stats + keep other card types

        // Until end of turn, target artifact or creature becomes an artifact creature with base power and toughness 4/5.
        addCard(Zone.HAND, playerA, "Mind Transfer Protocol"); // {2}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Boon Satyr"); // green enchantment creature, Satyr 4/2

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Mind Transfer Protocol", "Boon Satyr");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        // keep
        assertType("Boon Satyr", CardType.ENCHANTMENT, true);
        assertType("Boon Satyr", CardType.CREATURE, SubType.SATYR);
        assertColor(playerA, "Boon Satyr", "G", true);
        // gain
        assertType("Boon Satyr", CardType.ARTIFACT, true);
        assertPowerToughness(playerA, "Boon Satyr", 4, 5);
    }

    @Test
    public void test_BecomeArtifactCreature_TheMechanistAerialArtisan_1() {
        // change stats + keep other card types + replace creature types

        // If the artifact token was already a creature, its base power and toughness will become
        // 3/1. This overwrites any previous effects that set the creature's base power and
        // toughness to specific values. Any power- or toughness-setting effects that start to
        // apply after The Mechanist, Aerial Artisan's ability resolves will overwrite this effect.
        // (2025-10-02)

        // The Mechanist, Aerial Artisan's activated ability doesn't remove any abilities the
        // target artifact token has. The artifact token also retains any types, subtypes, and
        // supertypes other than creature types.
        // (2025-10-02)

        // {T}: Until end of turn, target artifact token you control becomes a 3/1 Construct artifact creature with flying.
        addCard(Zone.BATTLEFIELD, playerA, "The Mechanist, Aerial Artisan");
        //
        // When this creature enters, create a 1/1 colorless Thopter artifact creature token with flying.
        addCard(Zone.HAND, playerA, "Thopter Engineer"); // {2}{R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Thopter Engineer");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Until end of turn", "Thopter Token");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        // keep
        assertType("Thopter Token", CardType.ARTIFACT, true);
        assertAbility(playerA, "Thopter Token", FlyingAbility.getInstance(), true);
        // gain
        assertPowerToughness(playerA, "Thopter Token", 3, 1);
        assertType("Thopter Token", CardType.CREATURE, SubType.CONSTRUCT);
        // lost
        assertNotSubtype("Thopter Token", SubType.THOPTER);
    }

    @Test
    public void test_BecomeArtifactCreature_TheMechanistAerialArtisan_2() {
        // change stats + keep other card types

        // The Mechanist, Aerial Artisan's activated ability doesn't remove any abilities the
        // target artifact token has. The artifact token also retains any types, subtypes, and
        // supertypes other than creature types.
        // (2025-10-02)

        // {T}: Until end of turn, target artifact token you control becomes a 3/1 Construct artifact creature with flying.
        addCard(Zone.BATTLEFIELD, playerA, "The Mechanist, Aerial Artisan");
        //
        addCard(Zone.HAND, playerA, "Thraben Inspector"); // {W}, ETB: investigate
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Thraben Inspector");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Until end of turn", "Clue Token");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        // keep
        assertType("Clue Token", CardType.ARTIFACT, SubType.CLUE);
        // gain
        assertPowerToughness(playerA, "Clue Token", 3, 1);
        assertType("Clue Token", CardType.CREATURE, SubType.CONSTRUCT);
        assertAbility(playerA, "Clue Token", FlyingAbility.getInstance(), true);
    }

    @Test
    public void test_BecomeArtifactCreature_LifecraftAwakening_1() {
        // change stats + keep other card types

        // Put X +1/+1 counters on target artifact you control. If it isn't a creature or Vehicle, it becomes a 0/0 Construct artifact creature.
        addCard(Zone.HAND, playerA, "Lifecraft Awakening"); // {X}{G}
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Mind Stone"); // colorless artifact, {T}: Add {C}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lifecraft Awakening", "Mind Stone");
        setChoice(playerA, "X=2");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        // keep
        assertType("Mind Stone", CardType.ARTIFACT, true);
        // gain
        assertCounterCount(playerA, "Mind Stone", CounterType.P1P1, 2);
        assertPowerToughness(playerA, "Mind Stone", 2, 2);
        assertType("Mind Stone", CardType.CREATURE, SubType.CONSTRUCT);
    }

    @Test
    public void test_BecomeArtifactCreature_LifecraftAwakening_2() {
        // nothing to become - it's a creature already

        // Put X +1/+1 counters on target artifact you control. If it isn't a creature or Vehicle, it becomes a 0/0 Construct artifact creature.
        addCard(Zone.HAND, playerA, "Lifecraft Awakening"); // {X}{G}
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Ornithopter"); // colorless artifact creature, Thopter 0/2, flying

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lifecraft Awakening", "Ornithopter");
        setChoice(playerA, "X=2");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        // keep
        assertType("Ornithopter", CardType.ARTIFACT, true);
        assertType("Ornithopter", CardType.CREATURE, SubType.THOPTER);
        // gain
        assertCounterCount(playerA, "Ornithopter", CounterType.P1P1, 2);
        assertPowerToughness(playerA, "Ornithopter", 2, 4);
        // lost
        assertNotSubtype("Ornithopter", SubType.CONSTRUCT);
    }

    @Test
    public void test_BecomeArtifactCreature_WelcomeTo() {
        // change stats + keep other card types

        // I -- For each opponent, up to one target noncreature artifact they control becomes a 0/4 Wall artifact creature with defender for as long as you control this Saga.
        addCard(Zone.BATTLEFIELD, playerA, "Welcome to . . .");
        //
        addCard(Zone.BATTLEFIELD, playerB, "Mind Stone"); // colorless artifact, {T}: Add {C}

        addTarget(playerA, "Mind Stone"); // chapter I

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        // keep
        assertType("Mind Stone", CardType.ARTIFACT, true);
        // gain
        assertPowerToughness(playerB, "Mind Stone", 0, 4);
        assertType("Mind Stone", CardType.CREATURE, SubType.WALL);
        assertAbility(playerB, "Mind Stone", DefenderAbility.getInstance(), true);
    }

    @Test
    public void test_Become_CustomAllCreatureTypes() {
        // change stats + all creature types

        // {0}: Until end of turn, target creature becomes a 4/4 creature with all creature types.
        Ability ability = new SimpleActivatedAbility(new BecomesCreatureTargetEffect(
                new CreatureToken(4, 4).withAllCreatureTypes(true), false, false, Duration.EndOfTurn
        ).setText("Until end of turn, target creature becomes a 4/4 creature with all creature types"), new GenericManaCost(0));
        ability.addTarget(new TargetCreaturePermanent());
        addCustomCardWithAbility("animate", playerA, ability);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // bear 2/2

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{0}: Until end of turn", "Grizzly Bears");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        // keep
        assertColor(playerA, "Grizzly Bears", "G", true);
        // gain
        assertPowerToughness(playerA, "Grizzly Bears", 4, 4);
        Assert.assertTrue("must have all creature types", getPermanent("Grizzly Bears").isAllCreatureTypes(currentGame));
        assertSubtype("Grizzly Bears", SubType.ELF);
        assertSubtype("Grizzly Bears", SubType.GOBLIN);
    }

    @Test
    public void test_Become_CustomLandType_1() {
        // change stats + lost card types

        // {0}: Until end of turn, target land becomes a 2/2 Forest creature.
        Ability ability = new SimpleActivatedAbility(new BecomesCreatureTargetEffect(
                new CreatureToken(2, 2, "", SubType.FOREST), false, false, Duration.EndOfTurn
        ).withLoseOtherCardTypes(true).setText("Until end of turn, target land becomes a 2/2 Forest creature"), new GenericManaCost(0));
        ability.addTarget(new TargetLandPermanent());
        addCustomCardWithAbility("animate", playerA, ability);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Island"); // basic land, Island

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{0}: Until end of turn", "Island");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        // gain
        assertPowerToughness(playerA, "Island", 2, 2);
        assertType("Island", CardType.CREATURE, true);
        // lost
        assertNotType("Island", CardType.LAND);
        assertNotSubtype("Island", SubType.ISLAND);
        assertNotSubtype("Island", SubType.FOREST); // 205.3d: no land type without land card type
    }

    @Test
    public void test_Become_CustomLandType_2() {
        // change stats + lost card types

        // {0}: Until end of turn, target land becomes a 2/2 Forest creature.
        Ability ability = new SimpleActivatedAbility(new BecomesCreatureTargetEffect(
                new CreatureToken(2, 2, "", SubType.FOREST), false, false, Duration.EndOfTurn
        ).withLoseOtherCardTypes(true).setText("Until end of turn, target land becomes a 2/2 Forest creature"), new GenericManaCost(0));
        ability.addTarget(new TargetLandPermanent());
        addCustomCardWithAbility("animate", playerA, ability);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Azorius Guildgate"); // nonbasic land, Gate

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{0}: Until end of turn", "Azorius Guildgate");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        // gain
        assertPowerToughness(playerA, "Azorius Guildgate", 2, 2);
        assertType("Azorius Guildgate", CardType.CREATURE, true);
        // lost
        assertNotType("Azorius Guildgate", CardType.LAND);
        assertNotSubtype("Azorius Guildgate", SubType.GATE);
        assertNotSubtype("Azorius Guildgate", SubType.FOREST); // 205.3d: no land type without land card type
    }

    @Test
    public void test_Become_SparkshaperVisionary() {
        // change stats + lost card types

        // Once Sparkshaper Visionary's ability has resolved, each of the chosen planeswalkers 
        // are no longer planeswalkers for the rest of the turn. They don't lose any loyalty 
        // counters or abilities, and you can still activate their loyalty abilities if you 
        // haven't done so yet this turn. They don't lose loyalty if they're dealt damage 
        // while they're not planeswalkers.
        // (2023-07-28)

        // At the beginning of combat on your turn, choose any number of target planeswalkers you control. Until end of turn, they become 3/3 blue Bird creatures with flying, hexproof, and "Whenever this creature deals combat damage to a player, scry 1."
        addCard(Zone.BATTLEFIELD, playerA, "Sparkshaper Visionary");
        //
        addCard(Zone.BATTLEFIELD, playerA, "Ajani Goldmane"); // white planeswalker, Ajani, loyalty 4, +1: You gain 2 life

        addTarget(playerA, "Ajani Goldmane"); // target for Sparkshaper's combat trigger

        // loyalty abilities can still be activated
        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "+1:");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        // keep
        assertCounterCount(playerA, "Ajani Goldmane", CounterType.LOYALTY, 4 + 1);
        assertLife(playerA, 20 + 2);
        // gain
        assertType("Ajani Goldmane", CardType.CREATURE, true);
        assertPowerToughness(playerA, "Ajani Goldmane", 3, 3);
        assertColor(playerA, "Ajani Goldmane", "U", true);
        assertAbility(playerA, "Ajani Goldmane", FlyingAbility.getInstance(), true);
        assertAbility(playerA, "Ajani Goldmane", HexproofAbility.getInstance(), true);
        assertSubtype("Ajani Goldmane", SubType.BIRD);
        // lost
        assertColor(playerA, "Ajani Goldmane", "W", false);
        assertNotType("Ajani Goldmane", CardType.PLANESWALKER);
        assertNotSubtype("Ajani Goldmane", SubType.AJANI);
    }

    @Test
    public void test_Become_SerpentineAmbush_1() {
        // change stats

        // Until end of turn, target creature becomes a blue Serpent with base power and toughness 5/5
        addCard(Zone.HAND, playerA, "Serpentine Ambush"); // {1}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // bear 2/2

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Serpentine Ambush", "Grizzly Bears");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        // gain
        assertPowerToughness(playerA, "Grizzly Bears", 5, 5);
        assertColor(playerA, "Grizzly Bears", "U", true);
        assertSubtype("Grizzly Bears", SubType.SERPENT);
        // lost
        assertColor(playerA, "Grizzly Bears", "G", false);
        assertNotSubtype("Grizzly Bears", SubType.BEAR);
    }

    @Test
    public void test_Become_SerpentineAmbush_2() {
        // change stats

        // Until end of turn, target creature becomes a blue Serpent with base power and toughness 5/5
        addCard(Zone.HAND, playerA, "Serpentine Ambush"); // {1}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Lizard Blades"); // red artifact creature, Equipment Lizard 1/1, double strike

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Serpentine Ambush", "Lizard Blades");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        // keep
        assertType("Lizard Blades", CardType.ARTIFACT, true);
        assertAbility(playerA, "Lizard Blades", DoubleStrikeAbility.getInstance(), true);
        assertSubtype("Lizard Blades", SubType.EQUIPMENT);
        // gain
        assertPowerToughness(playerA, "Lizard Blades", 5, 5);
        assertColor(playerA, "Lizard Blades", "U", true);
        assertSubtype("Lizard Blades", SubType.SERPENT);
        // lost
        assertColor(playerA, "Lizard Blades", "R", false);
        assertNotSubtype("Lizard Blades", SubType.LIZARD);
    }

    @Test
    public void test_BecomeAndStillLand_AwakeningOfVituGhazi() {
        // change stats + still a land

        // The target land loses the name it had, and its name is just Vitu-Ghazi. It keeps any
        // types and abilities it had.
        // (2019-05-03)

        // Put nine +1/+1 counters on target land you control. It becomes a legendary 0/0 Elemental creature with haste named Vitu-Ghazi. It's still a land.
        addCard(Zone.HAND, playerA, "Awakening of Vitu-Ghazi"); // {3}{G}{G}
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 5);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Awakening of Vitu-Ghazi", "Mountain");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        // keep
        assertType("Vitu-Ghazi", CardType.LAND, SubType.MOUNTAIN);
        // gain
        assertPermanentCount(playerA, "Vitu-Ghazi", 1);
        assertPowerToughness(playerA, "Vitu-Ghazi", 0 + 9, 0 + 9);
        assertAbility(playerA, "Vitu-Ghazi", HasteAbility.getInstance(), true);
        Assert.assertTrue("must be legendary", getPermanent("Vitu-Ghazi").isLegendary(currentGame));
        assertType("Vitu-Ghazi", CardType.CREATURE, SubType.ELEMENTAL);
        // lost
        assertPermanentCount(playerA, "Mountain", 0);
    }

    @Test
    public void test_BecomeAndStillLand_JolraelVoiceOfZhalfir() {
        // change stats + still a land

        // Use the number of cards in your hand as Jolrael's first ability resolves to determine
        // the value of X. Once the ability resolves, that value is locked in. The power and
        // toughness of the land creature won't change if the number of cards in your hand changes
        // later in the turn.
        // (2023-05-12)

        // At the beginning of combat on your turn, up to one target land you control becomes an X/X green and blue Bird creature with flying and haste until end of turn, where X is the number of cards in your hand. It's still a land.
        addCard(Zone.BATTLEFIELD, playerA, "Jolrael, Voice of Zhalfir");
        addCard(Zone.HAND, playerA, "Grizzly Bears", 2); // X = 2
        //
        addCard(Zone.BATTLEFIELD, playerA, "Forest");

        addTarget(playerA, "Forest");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        // keep
        assertType("Forest", CardType.LAND, SubType.FOREST);
        // gain
        assertPowerToughness(playerA, "Forest", 2, 2);
        assertType("Forest", CardType.CREATURE, SubType.BIRD);
        assertColor(playerA, "Forest", "G", true);
        assertColor(playerA, "Forest", "U", true);
        assertAbility(playerA, "Forest", FlyingAbility.getInstance(), true);
        assertAbility(playerA, "Forest", HasteAbility.getInstance(), true);
    }

    @Test
    public void test_BecomeInAddition_DoesMachines() {
        // change stats + keep other types

        // The artifact retains any types, subtypes, or supertypes it has.
        // (2026-01-27)

        // {1}{U}: Level 2
        // When this Class becomes level 2, return up to two target artifact cards from your graveyard to your hand.
        // {4}{U}: Level 3
        // At the beginning of combat on your turn, put three +1/+1 counters on target artifact you control. If it isn't a creature, it becomes a 0/0 Robot creature in addition to its other types.
        addCard(Zone.BATTLEFIELD, playerA, "Does Machines");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 7);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Mind Stone"); // colorless artifact, {T}: Add {C}

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}{U}: Level 2");
        addTarget(playerA, TestPlayer.TARGET_SKIP); // level 2 trigger: return nothing
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{4}{U}: Level 3");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        addTarget(playerA, "Mind Stone"); // level 3 trigger

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        // keep
        assertType("Mind Stone", CardType.ARTIFACT, true);
        // gain
        assertCounterCount(playerA, "Mind Stone", CounterType.P1P1, 3);
        assertPowerToughness(playerA, "Mind Stone", 3, 3);
        assertType("Mind Stone", CardType.CREATURE, SubType.ROBOT);
    }

    @Test
    public void test_BecomeInAddition_PizzaFace() {
        // change stats + keep other types

        // The target permanent retains any types, subtypes, or supertypes it has.
        // (2026-01-27)

        // Disappear -- At the beginning of your end step, if a permanent left the battlefield under your control this turn, put three +1/+1 counters on up to one other target artifact or creature. If it isn't a creature, it becomes a 0/0 Mutant creature in addition to its other types.
        addCard(Zone.BATTLEFIELD, playerA, "Pizza Face, Gastromancer");
        //
        addCard(Zone.HAND, playerA, "Shock"); // {R}, 2 damage
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // bear 2/2
        //
        addCard(Zone.BATTLEFIELD, playerA, "Mind Stone"); // colorless artifact, {T}: Add {C}

        // permanent left the battlefield
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Shock", "Grizzly Bears");

        addTarget(playerA, "Mind Stone"); // end step trigger

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.UPKEEP);
        execute();

        // keep
        assertType("Mind Stone", CardType.ARTIFACT, true);
        // gain
        assertCounterCount(playerA, "Mind Stone", CounterType.P1P1, 3);
        assertPowerToughness(playerA, "Mind Stone", 3, 3);
        assertType("Mind Stone", CardType.CREATURE, SubType.MUTANT);
    }

    @Test
    public void test_BecomeInAddition_SageOfTheMaze() {
        // change stats + keep other types

        // The value of X is calculated only once, as Sage of the Maze's second ability resolves.
        // (2024-06-07)

        // {T}: Until end of turn, target land you control becomes an X/X Citizen creature with haste in addition to its other types, where X is twice the number of Gates you control. Activate only as a sorcery.
        addCard(Zone.BATTLEFIELD, playerA, "Sage of the Maze");
        addCard(Zone.BATTLEFIELD, playerA, "Azorius Guildgate"); // X = 2
        //
        addCard(Zone.BATTLEFIELD, playerA, "Forest");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Until end of turn", "Forest");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        // keep
        assertType("Forest", CardType.LAND, SubType.FOREST);
        // gain
        assertPowerToughness(playerA, "Forest", 2, 2);
        assertType("Forest", CardType.CREATURE, SubType.CITIZEN);
        assertAbility(playerA, "Forest", HasteAbility.getInstance(), true);
    }

    @Test
    public void test_BecomeInAddition_PhantasmalForm() {
        // change stats + keep other colors and types

        // Until end of turn, up to two target creatures each have base power and toughness 3/3, gain flying, and become blue Illusions in addition to their other colors and types.
        addCard(Zone.HAND, playerA, "Phantasmal Form"); // {2}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // bear 2/2

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Phantasmal Form", "Grizzly Bears");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        // keep
        assertColor(playerA, "Grizzly Bears", "G", true);
        assertSubtype("Grizzly Bears", SubType.BEAR);
        // gain
        assertPowerToughness(playerA, "Grizzly Bears", 3, 3);
        assertAbility(playerA, "Grizzly Bears", FlyingAbility.getInstance(), true);
        assertColor(playerA, "Grizzly Bears", "U", true);
        assertSubtype("Grizzly Bears", SubType.ILLUSION);
    }
}