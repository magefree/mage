package org.mage.test.cards.rules;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * "Whenever ... activates an ability" for activated mana abilities: they are activated abilities too, but they have no
 * stack object, so "that isn't a mana ability" listeners must ignore them and others must see them
 * <p>
 * 605.3
 * Activating an activated mana ability follows the rules for activating any other activated ability (see rule
 * 602.2), with the following exceptions:
 * <p>
 * 605.3b
 * An activated mana ability doesn't go on the stack, so it can't be targeted, countered, or otherwise responded to.
 * Rather, it resolves immediately after it is activated. (See rule 405.6c.)
 *
 * @author JayDi85
 */
public class ManaAbilityActivationTriggersTest extends CardTestPlayerBase {

    @Test
    public void test_AvalancheOfSector7_OpponentArtifactManaAbility_Damage() {
        // Whenever an opponent activates an ability of an artifact they control, Avalanche of Sector 7 deals 1 damage
        // to that player.
        addCard(Zone.BATTLEFIELD, playerA, "Avalanche of Sector 7");
        //
        addCard(Zone.BATTLEFIELD, playerB, "Sol Ring"); // {T}: Add {C}{C}.

        activateManaAbility(2, PhaseStep.PRECOMBAT_MAIN, playerB, "{T}: Add {C}{C}");

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerB, 20 - 1);
    }

    @Test
    public void test_TezzeretBetrayerOfFlesh_ManaAbilityIsFirst_NoDiscountForNext() {
        // The first activated ability of an artifact you activate each turn costs {2} less to activate.
        // Notably, this does not exclude mana abilities.
        // (2022-02-18)
        addCard(Zone.BATTLEFIELD, playerA, "Tezzeret, Betrayer of Flesh");
        addCard(Zone.BATTLEFIELD, playerA, "Sol Ring"); // {T}: Add {C}{C}.
        addCard(Zone.BATTLEFIELD, playerA, "Mind Stone"); // {1}, {T}, Sacrifice this artifact: Draw a card.
        addCard(Zone.HAND, playerA, "Millstone"); // {2}

        // first artifact ability - mana
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {C}{C}");
        // second artifact ability - full cost {1} from the pool, {C} left
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}, {T}, Sacrifice");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkHandCardCount("draw", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Millstone", 1);
        checkPlayableAbility("only {C} left", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Millstone", false);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertHandCount(playerA, 1 + 1);
    }

    @Test
    public void test_TezzeretBetrayerOfFlesh_SacrificedArtifactIsFirst_NoDiscountForNext() {
        // The first activated ability of an artifact you activate each turn costs {2} less to activate.
        addCard(Zone.BATTLEFIELD, playerA, "Tezzeret, Betrayer of Flesh");
        addCard(Zone.BATTLEFIELD, playerA, "Mind Stone"); // {1}, {T}, Sacrifice this artifact: Draw a card.
        addCard(Zone.BATTLEFIELD, playerA, "Millstone"); // {2}, {T}: Target player mills two cards.

        // first artifact ability - for free, the artifact is sacrificed as a cost
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}, {T}, Sacrifice");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPlayableAbility("no discount, no mana", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "{2}, {T}: Target player mills", false);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertHandCount(playerA, 1);
    }

    @Test
    public void test_TezzeretBetrayerOfFlesh_ManaAbilityWhilePaying_DiscountKept() {
        // If you begin to activate an ability that costs mana before activating any mana abilities you need to pay
        // for it, that ability will be the one to get Tezzeret's discount. The total cost for that ability becomes
        // locked in, and then you can activate mana abilities to pay for it.
        // (2022-02-18)
        addCard(Zone.BATTLEFIELD, playerA, "Tezzeret, Betrayer of Flesh");
        addCard(Zone.BATTLEFIELD, playerA, "Executioner's Capsule"); // {1}{B}, {T}, Sacrifice this artifact: Destroy target nonblack creature.
        addCard(Zone.BATTLEFIELD, playerA, "Charcoal Diamond"); // {T}: Add {B}.
        //
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears");

        // cost {B} after the discount, paid by the Diamond's mana ability
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}{B}, {T}, Sacrifice", "Grizzly Bears");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerB, "Grizzly Bears", 1);
    }

    @Test
    public void test_ElrondMoonReader_CreatureManaAbilityWithoutTap_Draw() {
        // Whenever you activate an ability of a creature, draw a card. This ability triggers only once each turn.
        // An activated ability of a creature that's also a mana ability (such as "{T}: Add {G}") can cause Elrond's
        // first ability to trigger.
        // (2026-06-29)
        addCard(Zone.BATTLEFIELD, playerA, "Elrond, Moon-Reader");
        addCard(Zone.BATTLEFIELD, playerA, "Blood Pet"); // Sacrifice this creature: Add {B}.

        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sacrifice");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertHandCount(playerA, 1);
    }

    @Test
    public void test_CeaselessSearblades_ElementalManaAbilities_BoostForEach() {
        // Whenever you activate an ability of an Elemental, this creature gets +1/+0 until end of turn.
        addCard(Zone.BATTLEFIELD, playerA, "Ceaseless Searblades"); // 2/4
        addCard(Zone.BATTLEFIELD, playerA, "Catalyst Elemental"); // Sacrifice this creature: Add {R}{R}.
        addCard(Zone.BATTLEFIELD, playerA, "Smokebraider"); // {T}: Add two mana in any combination of colors.

        // without {T}
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sacrifice");
        // with {T} - must trigger once only
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add two mana");
        setChoiceAmount(playerA, 0, 0, 0, 2, 0); // R x2

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPowerToughness(playerA, "Ceaseless Searblades", 2 + 1 + 1, 4);
    }

    @Test
    public void test_HauntingWind_ArtifactManaAbilities_DamageForEach() {
        // Whenever an artifact becomes tapped or a player activates an artifact's ability without {T} in its
        // activation cost, this enchantment deals 1 damage to that artifact's controller.
        addCard(Zone.BATTLEFIELD, playerA, "Haunting Wind");
        //
        addCard(Zone.BATTLEFIELD, playerB, "Lion's Eye Diamond"); // Discard your hand, Sacrifice this artifact: Add three mana of any one color.
        addCard(Zone.BATTLEFIELD, playerB, "Sol Ring"); // {T}: Add {C}{C}.

        // without {T}
        activateManaAbility(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Discard your hand");
        setChoice(playerB, "Red");
        // with {T} - becomes tapped only, one damage
        activateManaAbility(2, PhaseStep.PRECOMBAT_MAIN, playerB, "{T}: Add {C}{C}");

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerB, 20 - 1 - 1);
    }

    @Test
    public void test_Powerleech_OpponentArtifactManaAbilities_LifeForEach() {
        // Whenever an artifact an opponent controls becomes tapped or an opponent activates an artifact's ability
        // without {T} in its activation cost, you gain 1 life.
        addCard(Zone.BATTLEFIELD, playerA, "Powerleech");
        //
        addCard(Zone.BATTLEFIELD, playerB, "Lion's Eye Diamond"); // Discard your hand, Sacrifice this artifact: Add three mana of any one color.
        addCard(Zone.BATTLEFIELD, playerB, "Sol Ring"); // {T}: Add {C}{C}.

        // without {T}
        activateManaAbility(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Discard your hand");
        setChoice(playerB, "Red");
        // with {T} - becomes tapped only, one life
        activateManaAbility(2, PhaseStep.PRECOMBAT_MAIN, playerB, "{T}: Add {C}{C}");

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 20 + 1 + 1);
    }

    @Test
    public void test_ArtifactPossession_AbilityWithoutTap_Damage() {
        // Whenever enchanted artifact becomes tapped or a player activates an ability of enchanted artifact without
        // {T} in its activation cost, this Aura deals 2 damage to that artifact's controller.
        addCard(Zone.HAND, playerA, "Artifact Possession"); // {2}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        //
        addCard(Zone.BATTLEFIELD, playerB, "Bottle Gnomes"); // Sacrifice this creature: You gain 3 life.

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Artifact Possession", "Bottle Gnomes");

        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Sacrifice");

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerB, 20 + 3 - 2);
    }

    @Test
    public void test_ArtifactPossession_ManaAbilityWithoutTap_Damage() {
        // Whenever enchanted artifact becomes tapped or a player activates an ability of enchanted artifact without
        // {T} in its activation cost, this Aura deals 2 damage to that artifact's controller.
        addCard(Zone.HAND, playerA, "Artifact Possession"); // {2}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        //
        addCard(Zone.BATTLEFIELD, playerB, "Lion's Eye Diamond"); // Discard your hand, Sacrifice this artifact: Add three mana of any one color.

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Artifact Possession", "Lion's Eye Diamond");

        activateManaAbility(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Discard your hand");
        setChoice(playerB, "Red");

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerB, 20 - 2);
    }

    @Test
    public void test_ArtifactPossession_ManaAbilityWithTap_DamageOnce() {
        // Whenever enchanted artifact becomes tapped or a player activates an ability of enchanted artifact without
        // {T} in its activation cost, this Aura deals 2 damage to that artifact's controller.
        addCard(Zone.HAND, playerA, "Artifact Possession"); // {2}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        //
        addCard(Zone.BATTLEFIELD, playerB, "Sol Ring"); // {T}: Add {C}{C}.

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Artifact Possession", "Sol Ring");

        // with {T} - becomes tapped only
        activateManaAbility(2, PhaseStep.PRECOMBAT_MAIN, playerB, "{T}: Add {C}{C}");

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerB, 20 - 2);
    }

    @Test
    public void test_VaziKeenNegotiator_TreasureManaSpentOnManaAbility_Trigger() {
        // Whenever an opponent casts a spell or activates an ability, if mana from a Treasure was spent to cast it or
        // activate it, put a +1/+1 counter on target creature, then draw a card.
        addCard(Zone.BATTLEFIELD, playerA, "Vazi, Keen Negotiator");
        //
        addCard(Zone.HAND, playerB, "Strike It Rich"); // {R}, create a Treasure token
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Dimir Signet"); // {1}, {T}: Add {U}{B}.

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Strike It Rich");
        waitStackResolved(2, PhaseStep.PRECOMBAT_MAIN);
        // no mana spent on it - no trigger
        activateManaAbility(2, PhaseStep.PRECOMBAT_MAIN, playerB, "{T}, Sacrifice");
        setChoice(playerB, "Black");
        // Treasure mana spent on it - trigger
        activateManaAbility(2, PhaseStep.PRECOMBAT_MAIN, playerB, "{1}, {T}: Add {U}{B}");
        addTarget(playerA, "Vazi, Keen Negotiator");

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertCounterCount(playerA, "Vazi, Keen Negotiator", CounterType.P1P1, 1);
        assertHandCount(playerA, 1);
    }

    @Test
    public void test_NotManaAbilityTriggers_OpponentManaAbilities_NoTriggers() {
        addCard(Zone.HAND, playerA, "Imprison"); // {B}, Aura
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        // Whenever a player activates an ability that isn't a mana ability, this creature deals 1 damage to that player.
        addCard(Zone.BATTLEFIELD, playerA, "Burning-Tree Shaman");
        // Whenever an opponent activates an ability of an artifact, creature, or land on the battlefield, if it isn't
        // a mana ability, this creature deals 2 damage to that player.
        addCard(Zone.BATTLEFIELD, playerA, "Harsh Mentor");
        // Whenever an opponent activates an ability of an artifact, creature, or land that isn't a mana ability, this
        // creature deals 1 damage to that player.
        addCard(Zone.BATTLEFIELD, playerA, "Immolation Shaman");
        // Whenever an opponent activates an ability that isn't a mana ability, this creature deals 1 damage to that player.
        addCard(Zone.BATTLEFIELD, playerA, "Flamescroll Celebrant");
        // Whenever an opponent activates an ability of a creature or land that isn't a mana ability, you may draw a card.
        addCard(Zone.BATTLEFIELD, playerA, "Runic Armasaur");
        // Whenever an opponent activates an ability of a permanent that isn't a mana ability, you create a 1/1 white
        // Zombie creature token. This ability triggers only once each turn.
        addCard(Zone.BATTLEFIELD, playerA, "Wizened Mentor");
        //
        addCard(Zone.BATTLEFIELD, playerB, "Llanowar Elves"); // {T}: Add {G}.
        addCard(Zone.BATTLEFIELD, playerB, "Sol Ring"); // {T}: Add {C}{C}.
        addCard(Zone.BATTLEFIELD, playerB, "Mana Confluence"); // {T}, Pay 1 life: Add one mana of any color.

        // Whenever a player activates an ability of enchanted creature with {T} in its activation cost that isn't a
        // mana ability, you may pay {1}. If you do, counter that ability. If you don't, destroy this Aura.
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Imprison", "Llanowar Elves");

        activateManaAbility(2, PhaseStep.PRECOMBAT_MAIN, playerB, "{T}: Add {G}"); // creature
        activateManaAbility(2, PhaseStep.PRECOMBAT_MAIN, playerB, "{T}: Add {C}{C}"); // artifact
        activateManaAbility(2, PhaseStep.PRECOMBAT_MAIN, playerB, "{T}, Pay 1 life"); // land
        setChoice(playerB, "Red");
        checkStackSize("no triggers", 2, PhaseStep.PRECOMBAT_MAIN, playerB, 0);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerB, 20 - 1);
        assertPermanentCount(playerA, "Zombie Token", 0);
        assertAttachedTo(playerB, "Imprison", "Llanowar Elves", true);
        assertTapped("Llanowar Elves", true);
        assertHandCount(playerA, 0);
    }

    @Test
    public void test_NotManaAbilityTriggers_OwnManaAbilities_NoTriggers() {
        // Whenever you activate an ability of an artifact or creature that isn't a mana ability, this creature gets
        // +1/+1 until end of turn.
        addCard(Zone.BATTLEFIELD, playerA, "Crackdown Construct"); // 2/2
        // Whenever you activate an ability of an artifact or creature that isn't a mana ability, if one or more
        // permanents were sacrificed to activate it, you may copy that ability.
        addCard(Zone.BATTLEFIELD, playerA, "Ashnod the Uncaring");
        // Whenever you activate an ability that isn't a mana ability, if life was paid to activate it, you may pay
        // that much life again. If you do, copy that ability.
        addCard(Zone.BATTLEFIELD, playerA, "Verrak, Warped Sengir");
        // Whenever you activate a ninjutsu ability, look at the top three cards of your library.
        addCard(Zone.BATTLEFIELD, playerA, "Satoru Umezawa");
        // Whenever you activate an eternalize or embalm ability, draw a card.
        addCard(Zone.BATTLEFIELD, playerA, "Vizier of the Anointed");
        // Whenever a spell or ability is put onto the stack, if it has a single target, reselect its target at random.
        addCard(Zone.BATTLEFIELD, playerA, "Grip of Chaos");
        // Whenever you activate an ability, if it isn't a mana ability, you may pay {2}. If you do, copy that ability.
        addCard(Zone.BATTLEFIELD, playerA, "Rings of Brighthearth");
        // Whenever you activate an ability of an artifact, if it isn't a mana ability, you may pay {R}. If you do,
        // copy that ability.
        addCard(Zone.BATTLEFIELD, playerA, "Kurkesh, Onakke Ancient");
        //
        addCard(Zone.BATTLEFIELD, playerA, "Blood Pet"); // Sacrifice this creature: Add {B}.
        addCard(Zone.BATTLEFIELD, playerA, "Sol Ring"); // {T}: Add {C}{C}.
        addCard(Zone.BATTLEFIELD, playerA, "Mana Confluence"); // {T}, Pay 1 life: Add one mana of any color.

        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sacrifice");
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {C}{C}");
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}, Pay 1 life");
        setChoice(playerA, "Red");
        checkStackSize("no triggers", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 0);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPowerToughness(playerA, "Crackdown Construct", 2, 2);
        assertLife(playerA, 20 - 1);
        assertHandCount(playerA, 0);
    }

    @Test
    public void test_RingsOfBrighthearth_NonManaAbility_Copy() {
        // Whenever you activate an ability, if it isn't a mana ability, you may pay {2}. If you do, copy that ability.
        addCard(Zone.BATTLEFIELD, playerA, "Rings of Brighthearth");
        addCard(Zone.BATTLEFIELD, playerA, "Bottle Gnomes"); // Sacrifice this creature: You gain 3 life.
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sacrifice");
        setChoice(playerA, true); // pay {2}

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 20 + 3 + 3);
    }

    @Test
    public void test_KurkeshOnakkeAncient_ArtifactAbility_Copy() {
        // Whenever you activate an ability of an artifact, if it isn't a mana ability, you may pay {R}. If you do,
        // copy that ability.
        addCard(Zone.BATTLEFIELD, playerA, "Kurkesh, Onakke Ancient");
        addCard(Zone.BATTLEFIELD, playerA, "Bottle Gnomes"); // Sacrifice this creature: You gain 3 life.
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sacrifice");
        setChoice(playerA, true); // pay {R}

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 20 + 3 + 3);
    }

    @Test
    public void test_KurkeshOnakkeAncient_NonArtifactAbility_NoTrigger() {
        // Whenever you activate an ability of an artifact, if it isn't a mana ability, you may pay {R}. If you do,
        // copy that ability.
        addCard(Zone.BATTLEFIELD, playerA, "Kurkesh, Onakke Ancient");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Prodigal Pyromancer"); // {T}: This creature deals 1 damage to any target.

        // no pay dialog
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: {this} deals", playerB);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerB, 20 - 1);
    }

    @Test
    public void test_BattlemagesBracers_EquippedCreatureAbility_Copy() {
        // Whenever an ability of equipped creature is activated, if it isn't a mana ability, you may pay {1}. If you
        // do, copy that ability.
        addCard(Zone.BATTLEFIELD, playerA, "Battlemage's Bracers"); // Equip {2}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2 + 1);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Prodigal Pyromancer"); // {T}: This creature deals 1 damage to any target.

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip {2}", "Prodigal Pyromancer");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: {this} deals", playerB);
        setChoice(playerA, true); // pay {1}
        setChoice(playerA, false); // no new target for copy

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerB, 20 - 1 - 1);
    }

    @Test
    public void test_IllusionistsBracers_EquippedCreatureAbility_Copy() {
        // Whenever an ability of equipped creature is activated, if it isn't a mana ability, copy that ability.
        addCard(Zone.BATTLEFIELD, playerA, "Illusionist's Bracers"); // Equip {3}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Prodigal Pyromancer"); // {T}: This creature deals 1 damage to any target.

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip {3}", "Prodigal Pyromancer");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: {this} deals", playerB);
        setChoice(playerA, false); // no new target for copy

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerB, 20 - 1 - 1);
    }

    @Test
    public void test_IllusionistsBracers_NotEquippedCreatureAbility_NoCopy() {
        // Whenever an ability of equipped creature is activated, if it isn't a mana ability, copy that ability.
        addCard(Zone.BATTLEFIELD, playerA, "Illusionist's Bracers"); // Equip {3}
        //
        addCard(Zone.BATTLEFIELD, playerA, "Prodigal Pyromancer"); // {T}: This creature deals 1 damage to any target.

        // no copy dialog
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: {this} deals", playerB);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerB, 20 - 1);
    }

    @Test
    public void test_IllusionistsBracers_EquippedCreatureSacrificedAsCost_NoCopy() {
        // Whenever an ability of equipped creature is activated, if it isn't a mana ability, copy that ability.
        // If the cost of an activated ability requires Illusionist's Bracers or the equipped creature to be
        // sacrificed, the ability won't be copied. At the time the ability is considered activated (after all costs
        // are paid), Illusionist's Bracers is no longer equipped to that creature.
        // (2013-01-24)
        addCard(Zone.BATTLEFIELD, playerA, "Illusionist's Bracers"); // Equip {3}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Bottle Gnomes"); // Sacrifice this creature: You gain 3 life.

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip {3}", "Bottle Gnomes");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sacrifice");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 20 + 3);
    }

    @Test
    public void test_IllusionistsBracers_EquippedCreatureManaAbility_NoCopy() {
        // Whenever an ability of equipped creature is activated, if it isn't a mana ability, copy that ability.
        addCard(Zone.BATTLEFIELD, playerA, "Illusionist's Bracers"); // Equip {3}
        addCard(Zone.BATTLEFIELD, playerA, "Llanowar Elves"); // {T}: Add {G}.
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip {3}", "Llanowar Elves");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {G}");
        checkStackSize("no copy trigger", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 0);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertTapped("Llanowar Elves", true);
    }

    @Test
    public void test_UnboundFlourishing_ManaAbilityWithX_TriggersWithoutCopy() {
        // Whenever you cast an instant or sorcery spell or activate an ability, if that spell's mana cost or that
        // ability's activation cost contains {X}, copy that spell or ability.
        // Mana abilities don't use the stack, so the trigger has nothing to copy:
        // https://www.cranial-insertion.com/article/4160
        addCard(Zone.BATTLEFIELD, playerA, "Unbound Flourishing");
        addCard(Zone.BATTLEFIELD, playerA, "Wizard's Rockets"); // {X}, {T}, Sacrifice this artifact: Add X mana in any combination of colors.
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);

        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{X}, {T}, Sacrifice");
        setChoice(playerA, "X=1");
        setChoice(playerA, "Red");
        setChoice(playerA, "Whenever you cast an instant or sorcery spell"); // trigger order: Unbound, Rockets draw
        checkStackObject("trigger", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Whenever you cast an instant or sorcery spell", 1);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertHandCount(playerA, 1); // Rockets draw
    }

    @Test
    public void test_MagusLuceaKane_ManaAbilityWithX_UsesUpDelayedTrigger() {
        // When you next cast a spell with {X} in its mana cost or activate an ability with {X} in its activation
        // cost this turn, copy that spell or ability.
        // A mana ability with {X} triggers it too (nothing to copy), so the next {X} spell is not copied.
        addCard(Zone.BATTLEFIELD, playerA, "Magus Lucea Kane"); // {T}: Add {C}{C} + delayed trigger
        addCard(Zone.BATTLEFIELD, playerA, "Wizard's Rockets"); // {X}, {T}, Sacrifice this artifact: Add X mana in any combination of colors.
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.HAND, playerA, "Blaze"); // {X}{R}, X damage to any target

        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "<i>Psychic Stimulus</i>");
        // X paid by {C}{C}
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{X}, {T}, Sacrifice");
        setChoice(playerA, "X=2");
        setChoiceAmount(playerA, 0, 0, 0, 2, 0); // R x2
        setChoice(playerA, "When you next cast a spell"); // trigger order: Magus, Rockets draw
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        // {R}{R} + Mountain
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Blaze", playerB);
        setChoice(playerA, "X=2");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertLife(playerB, 20 - 2);
    }

    @Test
    public void test_CyclingTriggers_ManaPaidWhileCycling_OneTriggerEach() {
        // Whenever you cycle another card, this creature deals 1 damage to each opponent.
        addCard(Zone.BATTLEFIELD, playerA, "Drannith Stinger");
        // Whenever a player cycles a card, this creature gets +2/+2 until end of turn.
        addCard(Zone.BATTLEFIELD, playerA, "Stoic Champion"); // 2/2
        // Whenever you cycle another card for the first time each turn, create a 1/1 white Human Soldier creature token.
        addCard(Zone.BATTLEFIELD, playerA, "Valiant Rescuer");
        // Whenever you cycle this card or cycle another card while this enchantment is on the battlefield, you may
        // exile target creature. If you do, return that card to the battlefield under its owner's control at the
        // beginning of the next end step.
        addCard(Zone.BATTLEFIELD, playerA, "Astral Drift");
        addCard(Zone.HAND, playerA, "Drifting Meadow"); // cycling {2}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        //
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears");

        // cost paid by two mana abilities while the cycling ability is on the stack
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cycling");
        // trigger order (exactly 4 triggers)
        setChoice(playerA, "Whenever a player cycles a card");
        setChoice(playerA, "Whenever you cycle another card, {this} deals");
        setChoice(playerA, "Whenever you cycle another card for the first time");
        setChoice(playerA, true); // Astral Drift: exile
        addTarget(playerA, "Grizzly Bears");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkExileCount("exiled", 1, PhaseStep.PRECOMBAT_MAIN, playerB, "Grizzly Bears", 1);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertLife(playerB, 20 - 1);
        assertPowerToughness(playerA, "Stoic Champion", 2 + 2, 2 + 2);
        assertPermanentCount(playerA, "Human Soldier Token", 1);
    }
}
