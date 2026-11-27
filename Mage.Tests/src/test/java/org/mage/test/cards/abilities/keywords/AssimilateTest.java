package org.mage.test.cards.abilities.keywords;

import mage.constants.CardType;
import mage.constants.PhaseStep;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * 205.1b
 * Some effects change an object’s card type, supertype, or subtype but specify that the object
 * retains a prior card type, supertype, or subtype. In such cases, all the object’s prior card
 * types, supertypes, and subtypes are retained. This rule applies to effects that use phrases such
 * as “in addition to its other types” or that state that something is “still a [type, supertype,
 * or subtype].” Some effects state that an object becomes an “artifact creature”; these effects
 * also allow the object to retain all of its prior card types and subtypes. Some effects state
 * that an object becomes a “[creature type or types] artifact creature”; these effects also allow
 * the object to retain all of its prior card types and subtypes other than creature types, but
 * replace any existing creature types.
 * <p>
 * 701.72a
 * To assimilate a permanent, gain control of it and put a +1/+1 counter on it. It becomes a Borg
 * artifact creature.
 * <p>
 * 701.72b
 * To assimilate a permanent card that’s in a zone other than the battlefield, put it onto the
 * battlefield under your control with a +1/+1 counter on it. It’s a Borg artifact creature.
 * <p>
 * 701.72c
 * An object may have an ability printed on it that assimilates another object and another ability
 * that refers to permanents “assimilated with [this object].” These abilities are linked; the
 * second refers only to permanents that have been assimilated due to the first. See rule 607,
 * “Linked Abilities.”
 *
 * @author JayDi85
 */
public class AssimilateTest extends CardTestPlayerBase {

    @Test
    public void test_AssimilateCreatureCard_EntersAsBorgArtifactCreatureWithCounter() {
        // Artifact creatures you control get +2/+0.
        // When Borg Queen enters, assimilate target creature card from an opponent's graveyard.
        addCard(Zone.HAND, playerA, "Borg Queen, Perfection Manifest"); // {4}{B}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 6);
        // Whenever an artifact you control enters, Reckless Fireweaver deals 1 damage to each opponent.
        addCard(Zone.BATTLEFIELD, playerA, "Reckless Fireweaver"); // 1/3
        //
        addCard(Zone.GRAVEYARD, playerB, "Grizzly Bears"); // 2/2 Bear

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Borg Queen, Perfection Manifest");
        setChoice(playerA, "When {this} enters"); // order Borg Queen ETB and Reckless Fireweaver trigger
        addTarget(playerA, "Grizzly Bears"); // target creature card in opponent's graveyard

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Grizzly Bears", 1);
        assertGraveyardCount(playerB, "Grizzly Bears", 0);
        // gain
        assertType("Grizzly Bears", CardType.ARTIFACT, true);
        assertType("Grizzly Bears", CardType.CREATURE, true);
        assertSubtype("Grizzly Bears", SubType.BORG);
        assertCounterCount(playerA, "Grizzly Bears", CounterType.P1P1, 1);
        assertPowerToughness(playerA, "Grizzly Bears", 2 + 1 + 2, 2 + 1); // +1/+1 counter and +2/+0 static boost
        // lost
        assertNotSubtype("Grizzly Bears", SubType.BEAR);
        // Reckless Fireweaver triggers for Borg Queen entering and for Grizzly Bears entering as an artifact
        assertLife(playerB, 20 - 1 - 1);
    }

    @Test
    public void test_AssimilateOpponentQueen_AssimilatesYourCreatureCard() {
        // Artifact creatures you control get +2/+0.
        // When Borg Queen enters, assimilate target creature card from an opponent's graveyard.
        addCard(Zone.HAND, playerB, "Borg Queen, Perfection Manifest"); // {4}{B}{B}
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 6);
        //
        addCard(Zone.GRAVEYARD, playerA, "Grizzly Bears"); // 2/2 Bear

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Borg Queen, Perfection Manifest");
        addTarget(playerB, "Grizzly Bears"); // target creature card in playerA's graveyard

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerB, "Grizzly Bears", 1);
        assertPermanentCount(playerA, "Grizzly Bears", 0);
        // gain
        assertType("Grizzly Bears", CardType.ARTIFACT, true);
        assertType("Grizzly Bears", CardType.CREATURE, true);
        assertSubtype("Grizzly Bears", SubType.BORG);
        assertCounterCount(playerB, "Grizzly Bears", CounterType.P1P1, 1);
        assertPowerToughness(playerB, "Grizzly Bears", 2 + 1 + 2, 2 + 1);
        // lost
        assertNotSubtype("Grizzly Bears", SubType.BEAR);
    }

    @Test
    public void test_AssimilateLandCreature_KeepsLandAndForest_DryadArbor() {
        // Artifact creatures you control get +2/+0.
        // When Borg Queen enters, assimilate target creature card from an opponent's graveyard.
        addCard(Zone.HAND, playerA, "Borg Queen, Perfection Manifest"); // {4}{B}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 6);
        //
        addCard(Zone.GRAVEYARD, playerB, "Dryad Arbor"); // Land Creature - Forest Dryad 1/1

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Borg Queen, Perfection Manifest");
        addTarget(playerA, "Dryad Arbor"); // target creature card to assimilate

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Dryad Arbor", 1);
        // keep
        assertType("Dryad Arbor", CardType.LAND, true);
        assertSubtype("Dryad Arbor", SubType.FOREST);
        // gain
        assertType("Dryad Arbor", CardType.ARTIFACT, true);
        assertType("Dryad Arbor", CardType.CREATURE, true);
        assertSubtype("Dryad Arbor", SubType.BORG);
        assertCounterCount(playerA, "Dryad Arbor", CounterType.P1P1, 1);
        assertPowerToughness(playerA, "Dryad Arbor", 1 + 1 + 2, 1 + 1);
        // lost
        assertNotSubtype("Dryad Arbor", SubType.DRYAD);
    }

    @Test
    public void test_AssimilateEnchantmentCreature_KeepsEnchantmentAndShrine_GoShintaiOfLifesOrigin() {
        // Artifact creatures you control get +2/+0.
        // When Borg Queen enters, assimilate target creature card from an opponent's graveyard.
        addCard(Zone.HAND, playerA, "Borg Queen, Perfection Manifest"); // {4}{B}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 6);
        //
        addCard(Zone.GRAVEYARD, playerB, "Go-Shintai of Life's Origin"); // Legendary Enchantment Creature - Shrine 3/4

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Borg Queen, Perfection Manifest");
        addTarget(playerA, "Go-Shintai of Life's Origin"); // target creature card to assimilate

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Go-Shintai of Life's Origin", 1);
        // keep
        Assert.assertTrue("must be legendary", getPermanent("Go-Shintai of Life's Origin").isLegendary(currentGame));
        assertType("Go-Shintai of Life's Origin", CardType.ENCHANTMENT, true);
        assertSubtype("Go-Shintai of Life's Origin", SubType.SHRINE);
        // gain
        assertType("Go-Shintai of Life's Origin", CardType.ARTIFACT, true);
        assertType("Go-Shintai of Life's Origin", CardType.CREATURE, true);
        assertSubtype("Go-Shintai of Life's Origin", SubType.BORG);
        assertCounterCount(playerA, "Go-Shintai of Life's Origin", CounterType.P1P1, 1);
        assertPowerToughness(playerA, "Go-Shintai of Life's Origin", 3 + 1 + 2, 4 + 1);
    }

    @Test
    public void test_AssimilateArtifactCreature_KeepsEquipment_LizardBlades() {
        // Artifact creatures you control get +2/+0.
        // When Borg Queen enters, assimilate target creature card from an opponent's graveyard.
        addCard(Zone.HAND, playerA, "Borg Queen, Perfection Manifest"); // {4}{B}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 6);
        //
        addCard(Zone.GRAVEYARD, playerB, "Lizard Blades"); // Artifact Creature - Equipment Lizard 1/1

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Borg Queen, Perfection Manifest");
        addTarget(playerA, "Lizard Blades"); // target creature card to assimilate

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Lizard Blades", 1);
        // keep
        assertType("Lizard Blades", CardType.ARTIFACT, true);
        assertSubtype("Lizard Blades", SubType.EQUIPMENT);
        // gain
        assertType("Lizard Blades", CardType.CREATURE, true);
        assertSubtype("Lizard Blades", SubType.BORG);
        assertCounterCount(playerA, "Lizard Blades", CounterType.P1P1, 1);
        assertPowerToughness(playerA, "Lizard Blades", 1 + 1 + 2, 1 + 1);
        // lost
        assertNotSubtype("Lizard Blades", SubType.LIZARD);
    }

    @Test
    public void test_AssimilatePlaneswalkerCreature_KeepsPlaneswalkerAndSubtype_GristTheHungerTide() {
        // Artifact creatures you control get +2/+0.
        // When Borg Queen enters, assimilate target creature card from an opponent's graveyard.
        addCard(Zone.HAND, playerA, "Borg Queen, Perfection Manifest"); // {4}{B}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 6);
        //
        // As long as Grist, the Hunger Tide isn't on the battlefield, it's a 1/1 Insect creature in addition to its other types.
        addCard(Zone.GRAVEYARD, playerB, "Grist, the Hunger Tide"); // Legendary Planeswalker - Grist, loyalty 3

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Borg Queen, Perfection Manifest");
        addTarget(playerA, "Grist, the Hunger Tide"); // legal creature card in graveyard

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Grist, the Hunger Tide", 1);
        // keep
        Assert.assertTrue("must be legendary", getPermanent("Grist, the Hunger Tide").isLegendary(currentGame));
        assertType("Grist, the Hunger Tide", CardType.PLANESWALKER, true);
        assertSubtype("Grist, the Hunger Tide", SubType.GRIST);
        assertCounterCount(playerA, "Grist, the Hunger Tide", CounterType.LOYALTY, 3);
        // gain
        assertType("Grist, the Hunger Tide", CardType.ARTIFACT, true);
        assertType("Grist, the Hunger Tide", CardType.CREATURE, true);
        assertSubtype("Grist, the Hunger Tide", SubType.BORG);
        assertCounterCount(playerA, "Grist, the Hunger Tide", CounterType.P1P1, 1);
        assertPowerToughness(playerA, "Grist, the Hunger Tide", 0 + 1 + 2, 0 + 1);
        // lost
        assertNotSubtype("Grist, the Hunger Tide", SubType.INSECT);
    }

    @Test
    public void test_AssimilateChangeling_LosesAllOtherCreatureTypes_ChangelingOutcast() {
        // Artifact creatures you control get +2/+0.
        // When Borg Queen enters, assimilate target creature card from an opponent's graveyard.
        addCard(Zone.HAND, playerA, "Borg Queen, Perfection Manifest"); // {4}{B}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 6);
        //
        // Changeling (This card is every creature type.)
        addCard(Zone.GRAVEYARD, playerB, "Changeling Outcast"); // Creature - Shapeshifter 1/1

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Borg Queen, Perfection Manifest");
        addTarget(playerA, "Changeling Outcast"); // target changeling card to assimilate

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Changeling Outcast", 1);
        // gain
        assertType("Changeling Outcast", CardType.ARTIFACT, true);
        assertType("Changeling Outcast", CardType.CREATURE, true);
        assertSubtype("Changeling Outcast", SubType.BORG);
        assertCounterCount(playerA, "Changeling Outcast", CounterType.P1P1, 1);
        assertPowerToughness(playerA, "Changeling Outcast", 1 + 1 + 2, 1 + 1);
        // lost
        assertNotSubtype("Changeling Outcast", SubType.SHAPESHIFTER);
        assertNotSubtype("Changeling Outcast", SubType.BEAR);
    }

    @Test
    public void test_AssimilateTransformingDoubleFacedCreature_DelverOfSecrets() {
        // Artifact creatures you control get +2/+0.
        // When Borg Queen enters, assimilate target creature card from an opponent's graveyard.
        addCard(Zone.HAND, playerA, "Borg Queen, Perfection Manifest"); // {4}{B}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 6);
        //
        addCard(Zone.GRAVEYARD, playerB, "Delver of Secrets"); // TDFC Creature - Human Wizard 1/1

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Borg Queen, Perfection Manifest");
        addTarget(playerA, "Delver of Secrets"); // target TDFC creature card

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Delver of Secrets", 1);
        // gain
        assertType("Delver of Secrets", CardType.ARTIFACT, true);
        assertType("Delver of Secrets", CardType.CREATURE, true);
        assertSubtype("Delver of Secrets", SubType.BORG);
        assertCounterCount(playerA, "Delver of Secrets", CounterType.P1P1, 1);
        assertPowerToughness(playerA, "Delver of Secrets", 1 + 1 + 2, 1 + 1);
        // lost
        assertNotSubtype("Delver of Secrets", SubType.HUMAN);
        assertNotSubtype("Delver of Secrets", SubType.WIZARD);
    }

    @Test
    public void test_AssimilateModalDoubleFacedCreature_KazanduMammoth() {
        // Artifact creatures you control get +2/+0.
        // When Borg Queen enters, assimilate target creature card from an opponent's graveyard.
        addCard(Zone.HAND, playerA, "Borg Queen, Perfection Manifest"); // {4}{B}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 6);
        //
        addCard(Zone.GRAVEYARD, playerB, "Kazandu Mammoth"); // MDFC Creature - Elephant 3/3 // Land

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Borg Queen, Perfection Manifest");
        addTarget(playerA, "Kazandu Mammoth"); // target MDFC creature card

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Kazandu Mammoth", 1);
        // gain
        assertType("Kazandu Mammoth", CardType.ARTIFACT, true);
        assertType("Kazandu Mammoth", CardType.CREATURE, true);
        assertSubtype("Kazandu Mammoth", SubType.BORG);
        assertCounterCount(playerA, "Kazandu Mammoth", CounterType.P1P1, 1);
        assertPowerToughness(playerA, "Kazandu Mammoth", 3 + 1 + 2, 3 + 1);
        // lost
        assertNotSubtype("Kazandu Mammoth", SubType.ELEPHANT);
    }

    @Test
    public void test_AssimilatePersistsAcrossTurnsAndAfterTokenSourceLeavesBattlefield() {
        // When Borg Queen enters, assimilate target creature card from an opponent's graveyard.
        addCard(Zone.BATTLEFIELD, playerA, "Borg Queen, Perfection Manifest"); // Legendary 1/4
        // Create a token that's a copy of target creature you control.
        addCard(Zone.HAND, playerA, "Cackling Counterpart"); // {1}{U}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        // Destroy target creature.
        addCard(Zone.HAND, playerA, "Murder"); // {1}{B}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        //
        addCard(Zone.GRAVEYARD, playerB, "Grizzly Bears"); // 2/2 Bear

        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {U}", 3); // reserve Swamps for Murder
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cackling Counterpart", "Borg Queen, Perfection Manifest");
        setChoice(playerA, "Borg Queen, Perfection Manifest"); // legend rule: keep one copy
        addTarget(playerA, "Grizzly Bears"); // token copy's ETB trigger
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // Destroy the remaining Borg Queen so the token copy is guaranteed to have ceased to exist
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Murder", "Borg Queen, Perfection Manifest");

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Borg Queen, Perfection Manifest", 0);
        assertPermanentCount(playerA, "Grizzly Bears", 1);
        // gain (must remain on next turn even after token source ceased to exist)
        assertType("Grizzly Bears", CardType.ARTIFACT, true);
        assertType("Grizzly Bears", CardType.CREATURE, true);
        assertSubtype("Grizzly Bears", SubType.BORG);
        assertCounterCount(playerA, "Grizzly Bears", CounterType.P1P1, 1);
        assertPowerToughness(playerA, "Grizzly Bears", 2 + 1, 2 + 1);
        // lost
        assertNotSubtype("Grizzly Bears", SubType.BEAR);
    }

    @Test
    public void test_BlinkAssimilatedCreature_BecomesNewObjectWithoutTypesOrCounter() {
        // Artifact creatures you control get +2/+0.
        // When Borg Queen enters, assimilate target creature card from an opponent's graveyard.
        addCard(Zone.HAND, playerA, "Borg Queen, Perfection Manifest"); // {4}{B}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 6);
        // Exile target creature you control, then return that card to the battlefield under your control.
        addCard(Zone.HAND, playerA, "Cloudshift"); // {W}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        //
        addCard(Zone.GRAVEYARD, playerB, "Grizzly Bears"); // 2/2 Bear

        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {B}", 6); // reserve Plains for Cloudshift
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Borg Queen, Perfection Manifest");
        addTarget(playerA, "Grizzly Bears"); // target creature card to assimilate
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cloudshift", "Grizzly Bears"); // blink into a new object

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Grizzly Bears", 1);
        assertType("Grizzly Bears", CardType.ARTIFACT, false);
        assertSubtype("Grizzly Bears", SubType.BEAR);
        assertNotSubtype("Grizzly Bears", SubType.BORG);
        assertCounterCount(playerA, "Grizzly Bears", CounterType.P1P1, 0);
        assertPowerToughness(playerA, "Grizzly Bears", 2, 2);
    }

    @Test
    public void test_TargetLeavesGraveyardBeforeResolve_NotAssimilated() {
        // When Borg Queen enters, assimilate target creature card from an opponent's graveyard.
        addCard(Zone.HAND, playerA, "Borg Queen, Perfection Manifest"); // {4}{B}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 6);
        // Return target creature or land card from a graveyard to its owner's hand. You gain 6 life.
        addCard(Zone.HAND, playerA, "Pulse of Murasa"); // {2}{G}
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);
        //
        addCard(Zone.GRAVEYARD, playerB, "Hill Giant"); // 3/3 Giant

        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {B}", 6); // reserve Forests for Pulse of Murasa
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Borg Queen, Perfection Manifest");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, true); // resolve spell so ETB trigger is on the stack
        addTarget(playerA, "Hill Giant"); // choose trigger target
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Pulse of Murasa", "Hill Giant"); // remove target in response

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertHandCount(playerB, "Hill Giant", 1);
        assertPermanentCount(playerA, "Hill Giant", 0);
        assertPermanentCount(playerB, "Hill Giant", 0);
    }

    @Test
    public void test_PreventedFromEntering_LaterCastGetsNoCounter() {
        // When Borg Queen enters, assimilate target creature card from an opponent's graveyard.
        addCard(Zone.HAND, playerA, "Borg Queen, Perfection Manifest"); // {4}{B}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 6);
        // Creature cards in graveyards and libraries can't enter the battlefield.
        addCard(Zone.BATTLEFIELD, playerA, "Grafdigger's Cage");
        //
        addCard(Zone.GRAVEYARD, playerB, "Grizzly Bears"); // {1}{G} 2/2 Bear
        // Destroy target artifact or enchantment.
        addCard(Zone.HAND, playerB, "Disenchant"); // {1}{W}
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 2);
        // Return target creature card from your graveyard to your hand.
        addCard(Zone.HAND, playerB, "Raise Dead"); // {B}
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Forest", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Borg Queen, Perfection Manifest");
        addTarget(playerA, "Grizzly Bears"); // blocked by Grafdigger's Cage

        activateManaAbility(2, PhaseStep.PRECOMBAT_MAIN, playerB, "{T}: Add {W}", 2); // reserve Swamp and Forests
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Disenchant", "Grafdigger's Cage");
        waitStackResolved(2, PhaseStep.PRECOMBAT_MAIN);

        activateManaAbility(2, PhaseStep.PRECOMBAT_MAIN, playerB, "{T}: Add {B}", 1); // reserve Forests for Grizzly Bears
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Raise Dead", "Grizzly Bears");
        waitStackResolved(2, PhaseStep.PRECOMBAT_MAIN);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Grizzly Bears");

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerB, "Grizzly Bears", 1);
        assertType("Grizzly Bears", CardType.ARTIFACT, false);
        assertSubtype("Grizzly Bears", SubType.BEAR);
        assertNotSubtype("Grizzly Bears", SubType.BORG);
        assertCounterCount(playerB, "Grizzly Bears", CounterType.P1P1, 0);
        assertPowerToughness(playerB, "Grizzly Bears", 2, 2);
    }

    @Test
    public void test_AssimilatePermanent_LandCreatureKeepsLandAndForest_DryadArbor() {
        // I — Assimilate target nonartifact creature an opponent controls.
        addCard(Zone.HAND, playerA, "The Best of Both Worlds"); // {4}{W}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        //
        addCard(Zone.BATTLEFIELD, playerB, "Dryad Arbor"); // Land Creature - Forest Dryad 1/1

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "The Best of Both Worlds");
        addTarget(playerA, "Dryad Arbor");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Dryad Arbor", 1);
        assertPermanentCount(playerB, "Dryad Arbor", 0);
        // keep
        assertType("Dryad Arbor", CardType.LAND, true);
        assertSubtype("Dryad Arbor", SubType.FOREST);
        // gain
        assertType("Dryad Arbor", CardType.ARTIFACT, true);
        assertType("Dryad Arbor", CardType.CREATURE, true);
        assertSubtype("Dryad Arbor", SubType.BORG);
        assertCounterCount(playerA, "Dryad Arbor", CounterType.P1P1, 1);
        assertPowerToughness(playerA, "Dryad Arbor", 1 + 1, 1 + 1);
        // lost
        assertNotSubtype("Dryad Arbor", SubType.DRYAD);
    }

    @Test
    public void test_AssimilatePermanent_GainsControlBeforeCounter_HardenedScales() {
        // I — Assimilate target nonartifact creature an opponent controls.
        addCard(Zone.HAND, playerA, "The Best of Both Worlds"); // {4}{W}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        // If one or more +1/+1 counters would be put on a creature you control, that many plus one +1/+1 counters are put on it instead.
        addCard(Zone.BATTLEFIELD, playerA, "Hardened Scales");
        //
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears"); // 2/2 Bear

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "The Best of Both Worlds");
        addTarget(playerA, "Grizzly Bears");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        // Control changes before the +1/+1 counter is put on it, so playerA's Hardened Scales applies
        assertPermanentCount(playerA, "Grizzly Bears", 1);
        assertCounterCount(playerA, "Grizzly Bears", CounterType.P1P1, 1 + 1);
        assertPowerToughness(playerA, "Grizzly Bears", 2 + 2, 2 + 2);
    }

    @Test
    public void test_AssimilatePermanent_ZeroToughnessWithOpponentsAnthem_SurvivesDueToCounter() {
        // I — Assimilate target nonartifact creature an opponent controls.
        addCard(Zone.HAND, playerA, "The Best of Both Worlds"); // {4}{W}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        //
        // Creatures you control get +1/+1.
        addCard(Zone.BATTLEFIELD, playerB, "Glorious Anthem");
        addCard(Zone.BATTLEFIELD, playerB, "Force of Savagery"); // 8/0 Elemental (8/1 with Glorious Anthem)

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "The Best of Both Worlds");
        addTarget(playerA, "Force of Savagery");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        // State-based actions are not checked mid-resolution; Force of Savagery receives its +1/+1 counter and survives as 9/1
        assertPermanentCount(playerA, "Force of Savagery", 1);
        assertType("Force of Savagery", CardType.ARTIFACT, true);
        assertSubtype("Force of Savagery", SubType.BORG);
        assertNotSubtype("Force of Savagery", SubType.ELEMENTAL);
        assertCounterCount(playerA, "Force of Savagery", CounterType.P1P1, 1);
        assertPowerToughness(playerA, "Force of Savagery", 8 + 1, 0 + 1);
    }

    @Test
    public void test_AssimilatePermanent_PersistsAfterTokenSourceLeavesBattlefield() {
        // I — Assimilate target nonartifact creature an opponent controls.
        addCard(Zone.HAND, playerA, "The Best of Both Worlds"); // {4}{W}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        // Create a token that's a copy of target permanent.
        addCard(Zone.HAND, playerA, "Mythos of Illuna"); // {2}{U}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        //
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears"); // 2/2 Bear
        addCard(Zone.BATTLEFIELD, playerB, "Hill Giant"); // 3/3 Giant
        addCustomEffect_TargetDestroy(playerB);

        // Cast original Saga on turn 1 -> Chapter I assimilates Hill Giant
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {W}", 3); // pay The Best of Both Worlds
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {U}", 3);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "The Best of Both Worlds");
        addTarget(playerA, "Hill Giant");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        // Create a token copy of The Best of Both Worlds whose Chapter I assimilates Grizzly Bears
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {U}", 4); // pay Mythos of Illuna
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Mythos of Illuna", "The Best of Both Worlds");
        addTarget(playerA, "Grizzly Bears");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        // Destroy both copies on turn 2 so the token source ceases to exist
        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerB, "target destroy", "The Best of Both Worlds");
        waitStackResolved(2, PhaseStep.PRECOMBAT_MAIN);
        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerB, "target destroy", "The Best of Both Worlds");

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "The Best of Both Worlds", 0);
        assertPermanentCount(playerA, "Grizzly Bears", 1);
        assertPermanentCount(playerB, "Grizzly Bears", 0);
        assertType("Grizzly Bears", CardType.ARTIFACT, true);
        assertSubtype("Grizzly Bears", SubType.BORG);
        assertNotSubtype("Grizzly Bears", SubType.BEAR);
        assertCounterCount(playerA, "Grizzly Bears", CounterType.P1P1, 1);
        assertPowerToughness(playerA, "Grizzly Bears", 2 + 1, 2 + 1);
    }
}
