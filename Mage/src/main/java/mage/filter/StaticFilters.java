package mage.filter;

import mage.ObjectColor;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.common.*;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.*;
import mage.filter.predicate.other.AnotherTargetPredicate;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.filter.predicate.permanent.TokenPredicate;

/**
 * A class that holds Filter objects that may not be modified without copying
 * before. This prevents the creation of thousands of filter objects.
 * <p>
 * Because the filters are used application wide they may not be modified.
 * NEVER!!!!! But it's possible, so be careful!
 *
 * @author LevelX2
 */
public final class StaticFilters {

    public static final FilterSpiritOrArcaneCard FILTER_SPIRIT_OR_ARCANE_CARD = new FilterSpiritOrArcaneCard();

    static {
        FILTER_SPIRIT_OR_ARCANE_CARD.setLockedFilter(true);
    }

    public static final FilterCard FILTER_CARD = new FilterCard("card");

    static {
        FILTER_CARD.setLockedFilter(true);
    }


    public static final FilterCard FILTER_CARD_A = new FilterCard("a card");

    static {
        FILTER_CARD_A.setLockedFilter(true);
    }

    public static final FilterCard FILTER_CARD_CARDS = new FilterCard("cards");

    static {
        FILTER_CARD_CARDS.setLockedFilter(true);
    }

    public static final FilterCard FILTER_CARD_ENTCHANTMENT = new FilterEnchantmentCard();

    static {
        FILTER_CARD_ENTCHANTMENT.setLockedFilter(true);
    }

    public static final FilterArtifactCard FILTER_CARD_ARTIFACT = new FilterArtifactCard();

    static {
        FILTER_CARD_ARTIFACT.setLockedFilter(true);
    }

    public static final FilterArtifactCard FILTER_CARD_ARTIFACTS = new FilterArtifactCard("artifact cards");

    static {
        FILTER_CARD_ARTIFACTS.setLockedFilter(true);
    }

    public static final FilterArtifactCard FILTER_CARD_ARTIFACT_AN = new FilterArtifactCard("an artifact card");

    static {
        FILTER_CARD_ARTIFACT_AN.setLockedFilter(true);
    }

    public static final FilterCreatureCard FILTER_CARD_CREATURE = new FilterCreatureCard();

    static {
        FILTER_CARD_CREATURE.setLockedFilter(true);
    }

    public static final FilterCreatureCard FILTER_CARD_CREATURES = new FilterCreatureCard("creature cards");

    static {
        FILTER_CARD_CREATURES.setLockedFilter(true);
    }

    public static final FilterCreatureCard FILTER_CARD_CREATURE_A = new FilterCreatureCard("a creature card");

    static {
        FILTER_CARD_CREATURE_A.setLockedFilter(true);
    }

    public static final FilterCreatureCard FILTER_CARD_CREATURE_YOUR_HAND = new FilterCreatureCard("a creature card from your hand");

    static {
        FILTER_CARD_CREATURE_YOUR_HAND.setLockedFilter(true);
    }

    public static final FilterCreatureCard FILTER_CARD_CREATURE_YOUR_GRAVEYARD = new FilterCreatureCard("creature card from your graveyard");

    static {
        FILTER_CARD_CREATURE_YOUR_GRAVEYARD.setLockedFilter(true);
    }

    public static final FilterCreatureCard FILTER_CARD_CREATURES_YOUR_GRAVEYARD = new FilterCreatureCard("creature cards from your graveyard");

    static {
        FILTER_CARD_CREATURES_YOUR_GRAVEYARD.setLockedFilter(true);
    }

    public static final FilterCard FILTER_CARD_FROM_YOUR_GRAVEYARD = new FilterCard("card from your graveyard");

    static {
        FILTER_CARD_FROM_YOUR_GRAVEYARD.setLockedFilter(true);
    }

    public static final FilterCard FILTER_CARDS_FROM_YOUR_GRAVEYARD = new FilterCard("cards from your graveyard");

    static {
        FILTER_CARDS_FROM_YOUR_GRAVEYARD.setLockedFilter(true);
    }

    public static final FilterCard FILTER_CARD_INSTANT_OR_SORCERY_FROM_YOUR_GRAVEYARD = new FilterInstantOrSorceryCard("instant or sorcery card from your graveyard");

    static {
        FILTER_CARD_INSTANT_OR_SORCERY_FROM_YOUR_GRAVEYARD.setLockedFilter(true);
    }

    public static final FilterCard FILTER_CARD_ARTIFACT_FROM_YOUR_GRAVEYARD = new FilterArtifactCard("artifact card from your graveyard");

    static {
        FILTER_CARD_ARTIFACT_FROM_YOUR_GRAVEYARD.setLockedFilter(true);
    }

    public static final FilterNoncreatureCard FILTER_CARD_NON_CREATURE = new FilterNoncreatureCard();

    static {
        FILTER_CARD_NON_CREATURE.setLockedFilter(true);
    }

    public static final FilterNoncreatureCard FILTER_CARD_A_NON_CREATURE = new FilterNoncreatureCard("a noncreature card");

    static {
        FILTER_CARD_A_NON_CREATURE.setLockedFilter(true);
    }

    public static final FilterNoncreatureCard FILTER_CARDS_NON_CREATURE = new FilterNoncreatureCard("noncreature cards");

    static {
        FILTER_CARDS_NON_CREATURE.setLockedFilter(true);
    }

    public static final FilterLandCard FILTER_CARD_LAND = new FilterLandCard();

    static {
        FILTER_CARD_LAND.setLockedFilter(true);
    }

    public static final FilterLandCard FILTER_CARD_LANDS = new FilterLandCard("land cards");

    static {
        FILTER_CARD_LANDS.setLockedFilter(true);
    }

    public static final FilterLandCard FILTER_CARD_LAND_A = new FilterLandCard("a land card");

    static {
        FILTER_CARD_LAND_A.setLockedFilter(true);
    }

    public static final FilterBasicLandCard FILTER_CARD_BASIC_LAND = new FilterBasicLandCard();

    static {
        FILTER_CARD_BASIC_LAND.setLockedFilter(true);
    }

    public static final FilterBasicLandCard FILTER_CARD_BASIC_LANDS = new FilterBasicLandCard("basic land cards");

    static {
        FILTER_CARD_BASIC_LANDS.setLockedFilter(true);
    }

    public static final FilterBasicLandCard FILTER_CARD_BASIC_LAND_A = new FilterBasicLandCard("a basic land card");

    static {
        FILTER_CARD_BASIC_LAND_A.setLockedFilter(true);
    }

    public static final FilterNonlandCard FILTER_CARD_NON_LAND = new FilterNonlandCard();

    static {
        FILTER_CARD_NON_LAND.setLockedFilter(true);
    }

    public static final FilterNonlandCard FILTER_CARD_A_NON_LAND = new FilterNonlandCard("a nonland card");

    static {
        FILTER_CARD_A_NON_LAND.setLockedFilter(true);
    }

    public static final FilterNonlandCard FILTER_CARDS_NON_LAND = new FilterNonlandCard("nonland cards");

    static {
        FILTER_CARDS_NON_LAND.setLockedFilter(true);
    }

    public static final FilterInstantOrSorceryCard FILTER_CARD_INSTANT_OR_SORCERY = new FilterInstantOrSorceryCard();

    static {
        FILTER_CARD_INSTANT_OR_SORCERY.setLockedFilter(true);
    }

    public static final FilterInstantOrSorceryCard FILTER_CARD_INSTANT_AND_SORCERY = new FilterInstantOrSorceryCard("instant and sorcery card");

    static {
        FILTER_CARD_INSTANT_AND_SORCERY.setLockedFilter(true);
    }

    public static final FilterPermanentCard FILTER_CARD_PERMANENT = new FilterPermanentCard("permanent card");

    static {
        FILTER_CARD_PERMANENT.setLockedFilter(true);
    }

    public static final FilterPermanent FILTER_PERMANENT = new FilterPermanent();

    static {
        FILTER_PERMANENT.setLockedFilter(true);
    }

    public static final FilterPermanent FILTER_PERMANENT_A = new FilterPermanent("a permanent");

    static {
        FILTER_PERMANENT_A.setLockedFilter(true);
    }

    public static final FilterPermanent FILTER_PERMANENTS = new FilterPermanent("permanents");

    static {
        FILTER_PERMANENTS.setLockedFilter(true);
    }

    public static final FilterEnchantmentPermanent FILTER_PERMANENT_ENCHANTMENT = new FilterEnchantmentPermanent();

    static {
        FILTER_PERMANENT_ENCHANTMENT.setLockedFilter(true);
    }


    public static final FilterEnchantmentPermanent FILTER_PERMANENT_ENCHANTMENTS = new FilterEnchantmentPermanent("enchantments");

    static {
        FILTER_PERMANENT_ENCHANTMENTS.setLockedFilter(true);
    }

    public static final FilterArtifactPermanent FILTER_PERMANENT_ARTIFACT = new FilterArtifactPermanent("artifact");

    static {
        FILTER_PERMANENT_ARTIFACT.setLockedFilter(true);
    }

    public static final FilterArtifactPermanent FILTER_PERMANENT_ARTIFACTS = new FilterArtifactPermanent("artifacts");

    static {
        FILTER_PERMANENT_ARTIFACTS.setLockedFilter(true);
    }

    public static final FilterArtifactPermanent FILTER_PERMANENT_ARTIFACT_AN = new FilterArtifactPermanent("an artifact");

    static {
        FILTER_PERMANENT_ARTIFACT_AN.setLockedFilter(true);
    }

    public static final FilterArtifactOrEnchantmentPermanent FILTER_PERMANENT_ARTIFACT_OR_ENCHANTMENT = new FilterArtifactOrEnchantmentPermanent();

    static {
        FILTER_PERMANENT_ARTIFACT_OR_ENCHANTMENT.setLockedFilter(true);
    }


    public static final FilterControlledPermanent FILTER_PERMANENT_CONTROLLED_ARTIFACT_OR_ENCHANTMENT = new FilterControlledPermanent("artifact or enchantment you control");

    static {
        FILTER_PERMANENT_CONTROLLED_ARTIFACT_OR_ENCHANTMENT.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.ENCHANTMENT.getPredicate()
        ));
        FILTER_PERMANENT_CONTROLLED_ARTIFACT_OR_ENCHANTMENT.setLockedFilter(true);
    }

    public static final FilterCreaturePermanent FILTER_PERMANENT_ARTIFACT_CREATURE = new FilterArtifactCreaturePermanent();

    static {
        FILTER_PERMANENT_ARTIFACT_CREATURE.setLockedFilter(true);
    }

    public static final FilterCreaturePermanent FILTER_PERMANENTS_ARTIFACT_CREATURE = new FilterArtifactCreaturePermanent("artifact creatures");

    static {
        FILTER_PERMANENTS_ARTIFACT_CREATURE.setLockedFilter(true);
    }

    public static final FilterControlledArtifactPermanent FILTER_ARTIFACTS_NON_CREATURE = new FilterControlledArtifactPermanent("Noncreature artifacts");

    static {
        FILTER_ARTIFACTS_NON_CREATURE.add(Predicates.not(CardType.CREATURE.getPredicate()));
        FILTER_ARTIFACTS_NON_CREATURE.setLockedFilter(true);
    }

    public static final FilterPermanent FILTER_PERMANENT_ARTIFACT_OR_CREATURE = new FilterPermanent("artifact or creature");

    static {
        FILTER_PERMANENT_ARTIFACT_OR_CREATURE.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.CREATURE.getPredicate()
        ));
        FILTER_PERMANENT_ARTIFACT_OR_CREATURE.setLockedFilter(true);
    }

    public static final FilterPermanent FILTER_PERMANENT_ARTIFACT_CREATURE_OR_ENCHANTMENT = new FilterPermanent("artifact, creature, or enchantment");

    static {
        FILTER_PERMANENT_ARTIFACT_CREATURE_OR_ENCHANTMENT.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.CREATURE.getPredicate(),
                CardType.ENCHANTMENT.getPredicate()
        ));
        FILTER_PERMANENT_ARTIFACT_CREATURE_OR_ENCHANTMENT.setLockedFilter(true);
    }

    public static final FilterPermanent FILTER_PERMANENT_ARTIFACT_CREATURE_ENCHANTMENT_OR_LAND = new FilterPermanent("artifact, creature, enchantment, or land");

    static {
        FILTER_PERMANENT_ARTIFACT_CREATURE_ENCHANTMENT_OR_LAND.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.CREATURE.getPredicate(),
                CardType.ENCHANTMENT.getPredicate(),
                CardType.LAND.getPredicate()
        ));
        FILTER_PERMANENT_ARTIFACT_CREATURE_ENCHANTMENT_OR_LAND.setLockedFilter(true);
    }

    public static final FilterControlledPermanent FILTER_CONTROLLED_PERMANENT = new FilterControlledPermanent();

    static {
        FILTER_CONTROLLED_PERMANENT.setLockedFilter(true);
    }

    public static final FilterControlledPermanent FILTER_CONTROLLED_A_PERMANENT = new FilterControlledPermanent("a permanent you control");

    static {
        FILTER_CONTROLLED_A_PERMANENT.setLockedFilter(true);
    }

    public static final FilterControlledPermanent FILTER_CONTROLLED_PERMANENTS = new FilterControlledPermanent("permanents you control");

    static {
        FILTER_CONTROLLED_PERMANENTS.setLockedFilter(true);
    }

    public static final FilterControlledPermanent FILTER_CONTROLLED_PERMANENT_SHORT_TEXT = new FilterControlledPermanent("permanent");

    static {
        FILTER_CONTROLLED_PERMANENT_SHORT_TEXT.setLockedFilter(true);
    }

    public static final FilterControlledPermanent FILTER_CONTROLLED_PERMANENT_ARTIFACT = new FilterControlledArtifactPermanent();

    static {
        FILTER_CONTROLLED_PERMANENT_ARTIFACT.setLockedFilter(true);
    }

    public static final FilterControlledPermanent FILTER_CONTROLLED_PERMANENT_ARTIFACTS = new FilterControlledArtifactPermanent("artifacts you control");

    static {
        FILTER_CONTROLLED_PERMANENT_ARTIFACTS.setLockedFilter(true);
    }

    public static final FilterControlledPermanent FILTER_CONTROLLED_PERMANENT_ARTIFACT_AN = new FilterControlledArtifactPermanent("an artifact");

    static {
        FILTER_CONTROLLED_PERMANENT_ARTIFACT_AN.setLockedFilter(true);
    }

    public static final FilterControlledPermanent FILTER_CONTROLLED_PERMANENT_ARTIFACT_OR_CREATURE = new FilterControlledPermanent("artifact or creature you control");

    static {
        FILTER_CONTROLLED_PERMANENT_ARTIFACT_OR_CREATURE.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.CREATURE.getPredicate()
        ));
        FILTER_CONTROLLED_PERMANENT_ARTIFACT_OR_CREATURE.setLockedFilter(true);
    }

    public static final FilterControlledPermanent FILTER_CONTROLLED_PERMANENT_ENCHANTMENT = new FilterControlledEnchantmentPermanent();

    static {
        FILTER_CONTROLLED_PERMANENT_ENCHANTMENT.setLockedFilter(true);
    }

    public static final FilterControlledPermanent FILTER_CONTROLLED_PERMANENT_LAND = new FilterControlledLandPermanent();

    static {
        FILTER_CONTROLLED_PERMANENT_LAND.setLockedFilter(true);
    }

    public static final FilterControlledPermanent FILTER_CONTROLLED_PERMANENT_A_LAND = new FilterControlledLandPermanent("a land you control");

    static {
        FILTER_CONTROLLED_PERMANENT_A_LAND.setLockedFilter(true);
    }

    public static final FilterControlledPermanent FILTER_CONTROLLED_PERMANENT_LANDS = new FilterControlledLandPermanent("lands you control");

    static {
        FILTER_CONTROLLED_PERMANENT_LANDS.setLockedFilter(true);
    }

    public static final FilterControlledPermanent FILTER_CONTROLLED_PERMANENT_PLANESWALKER = new FilterControlledPlaneswalkerPermanent("planeswalker you control");

    static {
        FILTER_CONTROLLED_PERMANENT_PLANESWALKER.setLockedFilter(true);
    }

    public static final FilterPermanent FILTER_OPPONENTS_PERMANENT = new FilterPermanent("permanent an opponent controls");

    static {
        FILTER_OPPONENTS_PERMANENT.add(TargetController.OPPONENT.getControllerPredicate());
        FILTER_OPPONENTS_PERMANENT.setLockedFilter(true);
    }

    public static final FilterCreaturePermanent FILTER_OPPONENTS_PERMANENT_CREATURE = new FilterOpponentsCreaturePermanent();

    static {
        FILTER_OPPONENTS_PERMANENT_CREATURE.setLockedFilter(true);
    }

    public static final FilterCreaturePermanent FILTER_OPPONENTS_PERMANENT_CREATURES = new FilterOpponentsCreaturePermanent("creatures your opponents control");

    static {
        FILTER_OPPONENTS_PERMANENT_CREATURES.setLockedFilter(true);
    }

    public static final FilterCreaturePermanent FILTER_OPPONENTS_PERMANENT_A_CREATURE = new FilterCreaturePermanent("a creature an opponent controls");

    static {
        FILTER_OPPONENTS_PERMANENT_A_CREATURE.add(TargetController.OPPONENT.getControllerPredicate());
        FILTER_OPPONENTS_PERMANENT_A_CREATURE.setLockedFilter(true);
    }

    public static final FilterPermanent FILTER_OPPONENTS_PERMANENT_ARTIFACT = new FilterPermanent("artifact an opponent controls");

    static {
        FILTER_OPPONENTS_PERMANENT_ARTIFACT.add(TargetController.OPPONENT.getControllerPredicate());
        FILTER_OPPONENTS_PERMANENT_ARTIFACT.add(CardType.ARTIFACT.getPredicate());
        FILTER_OPPONENTS_PERMANENT_ARTIFACT.setLockedFilter(true);
    }

    public static final FilterPermanent FILTER_OPPONENTS_PERMANENT_ARTIFACT_OR_CREATURE = new FilterPermanent("artifact or creature an opponent controls");

    static {
        FILTER_OPPONENTS_PERMANENT_ARTIFACT_OR_CREATURE.add(TargetController.OPPONENT.getControllerPredicate());
        FILTER_OPPONENTS_PERMANENT_ARTIFACT_OR_CREATURE.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.CREATURE.getPredicate()
        ));
        FILTER_OPPONENTS_PERMANENT_ARTIFACT_OR_CREATURE.setLockedFilter(true);
    }

    public static final FilterCreaturePermanent FILTER_ANOTHER_CREATURE_TARGET_2 = new FilterCreaturePermanent("another target creature");

    static {
        FILTER_ANOTHER_CREATURE_TARGET_2.add(new AnotherTargetPredicate(2));
        FILTER_ANOTHER_CREATURE_TARGET_2.setLockedFilter(true);
    }

    public static final FilterCreaturePermanent FILTER_CREATURE_YOU_DONT_CONTROL = new FilterCreaturePermanent("creature you don't control");

    static {
        FILTER_CREATURE_YOU_DONT_CONTROL.add(TargetController.NOT_YOU.getControllerPredicate());
        FILTER_CREATURE_YOU_DONT_CONTROL.setLockedFilter(true);
    }

    public static final FilterCreaturePermanent FILTER_CREATURES_YOU_DONT_CONTROL = new FilterCreaturePermanent("creatures you don't control");

    static {
        FILTER_CREATURES_YOU_DONT_CONTROL.add(TargetController.NOT_YOU.getControllerPredicate());
        FILTER_CREATURES_YOU_DONT_CONTROL.setLockedFilter(true);
    }

    public static final FilterControlledCreaturePermanent FILTER_CONTROLLED_CREATURE = new FilterControlledCreaturePermanent();

    static {
        FILTER_CONTROLLED_CREATURE.setLockedFilter(true);
    }

    // Used for sacrifice targets that don't need the "you control" text
    public static final FilterControlledCreaturePermanent FILTER_CONTROLLED_CREATURE_SHORT_TEXT = new FilterControlledCreaturePermanent("a creature");

    static {
        FILTER_CONTROLLED_CREATURE_SHORT_TEXT.setLockedFilter(true);
    }

    public static final FilterControlledCreaturePermanent FILTER_CONTROLLED_CREATURES = new FilterControlledCreaturePermanent("creatures you control");

    static {
        FILTER_CONTROLLED_CREATURES.setLockedFilter(true);
    }

    public static final FilterControlledCreaturePermanent FILTER_CONTROLLED_A_CREATURE = new FilterControlledCreaturePermanent("a creature you control");

    static {
        FILTER_CONTROLLED_A_CREATURE.setLockedFilter(true);
    }

    public static final FilterControlledCreaturePermanent FILTER_CONTROLLED_ANOTHER_CREATURE = new FilterControlledCreaturePermanent("another creature");

    static {
        FILTER_CONTROLLED_ANOTHER_CREATURE.add(AnotherPredicate.instance);
        FILTER_CONTROLLED_ANOTHER_CREATURE.setLockedFilter(true);
    }

    public static final FilterControlledCreaturePermanent FILTER_CONTROLLED_UNTAPPED_CREATURES = new FilterControlledCreaturePermanent("untapped creatures you control");

    static {
        FILTER_CONTROLLED_UNTAPPED_CREATURES.add(TappedPredicate.UNTAPPED);
        FILTER_CONTROLLED_UNTAPPED_CREATURES.setLockedFilter(true);
    }

    public static final FilterControlledPermanent FILTER_CONTROLLED_PERMANENT_NON_LAND = new FilterControlledPermanent("nonland permanent");

    static {
        FILTER_CONTROLLED_PERMANENT_NON_LAND.add(
                Predicates.not(CardType.LAND.getPredicate())
        );
        FILTER_CONTROLLED_PERMANENT_NON_LAND.setLockedFilter(true);
    }

    public static final FilterLandPermanent FILTER_LAND = new FilterLandPermanent();

    static {
        FILTER_LAND.setLockedFilter(true);
    }

    public static final FilterLandPermanent FILTER_LAND_A = new FilterLandPermanent("a land");

    static {
        FILTER_LAND_A.setLockedFilter(true);
    }

    public static final FilterLandPermanent FILTER_LANDS = new FilterLandPermanent("lands");

    static {
        FILTER_LANDS.setLockedFilter(true);
    }

    public static final FilterLandPermanent FILTER_LANDS_NONBASIC = FilterLandPermanent.nonbasicLands();

    static {
        FILTER_LANDS_NONBASIC.setLockedFilter(true);
    }

    // Used for sacrifice targets that don't need the "you control" text
    public static final FilterControlledLandPermanent FILTER_CONTROLLED_LAND_SHORT_TEXT = new FilterControlledLandPermanent("a land");

    static {
        FILTER_CONTROLLED_LAND_SHORT_TEXT.setLockedFilter(true);
    }

    public static final FilterCreaturePermanent FILTER_PERMANENT_CREATURE = new FilterCreaturePermanent();

    static {
        FILTER_PERMANENT_CREATURE.setLockedFilter(true);
    }

    public static final FilterPermanent FILTER_PERMANENT_CREATURE_OR_PLANESWALKER_A = new FilterPermanent("a creature or planeswalker");

    static {
        FILTER_PERMANENT_CREATURE_OR_PLANESWALKER_A.add(
                Predicates.or(
                        CardType.CREATURE.getPredicate(),
                        CardType.PLANESWALKER.getPredicate()
                ));
        FILTER_PERMANENT_CREATURE_OR_PLANESWALKER_A.setLockedFilter(true);
    }

    public static final FilterPermanent FILTER_PERMANENT_CREATURE_OR_PLANESWALKER = new FilterPermanent("creature or planeswalker");

    static {
        FILTER_PERMANENT_CREATURE_OR_PLANESWALKER.add(
                Predicates.or(
                        CardType.CREATURE.getPredicate(),
                        CardType.PLANESWALKER.getPredicate()
                ));
        FILTER_PERMANENT_CREATURE_OR_PLANESWALKER.setLockedFilter(true);
    }

    public static final FilterCreaturePermanent FILTER_PERMANENT_A_CREATURE = new FilterCreaturePermanent("a creature");

    static {
        FILTER_PERMANENT_A_CREATURE.setLockedFilter(true);
    }

    public static final FilterCreaturePermanent FILTER_PERMANENT_CREATURE_CONTROLLED = new FilterCreaturePermanent("creature you control");

    static {
        FILTER_PERMANENT_CREATURE_CONTROLLED.add(TargetController.YOU.getControllerPredicate());
        FILTER_PERMANENT_CREATURE_CONTROLLED.setLockedFilter(true);
    }

    public static final FilterCreaturePermanent FILTER_PERMANENT_CREATURES = new FilterCreaturePermanent("creatures");

    static {
        FILTER_PERMANENT_CREATURES.setLockedFilter(true);
    }

    public static final FilterCreaturePermanent FILTER_PERMANENT_ALL_CREATURES = new FilterCreaturePermanent("all creatures");

    static {
        FILTER_PERMANENT_ALL_CREATURES.setLockedFilter(true);
    }

    public static final FilterCreaturePermanent FILTER_PERMANENT_CREATURES_CONTROLLED = new FilterCreaturePermanent("creatures you control");

    static {
        FILTER_PERMANENT_CREATURES_CONTROLLED.add(TargetController.YOU.getControllerPredicate());
        FILTER_PERMANENT_CREATURES_CONTROLLED.setLockedFilter(true);
    }

    public static final FilterCreaturePermanent FILTER_CREATURES_OWNED_COMMANDER = new FilterCreaturePermanent("commander creatures you own");

    static {
        FILTER_CREATURES_OWNED_COMMANDER.add(TargetController.YOU.getOwnerPredicate());
        FILTER_CREATURES_OWNED_COMMANDER.add(CommanderPredicate.instance);
        FILTER_CREATURES_OWNED_COMMANDER.setLockedFilter(true);
    }

    public static final FilterCreaturePermanent FILTER_PERMANENT_CREATURE_NON_BLACK = new FilterCreaturePermanent("nonblack creature");

    static {
        FILTER_PERMANENT_CREATURE_NON_BLACK.add(Predicates.not(new ColorPredicate(ObjectColor.BLACK)));
        FILTER_PERMANENT_CREATURE_NON_BLACK.setLockedFilter(true);
    }

    public static final FilterCreaturePermanent FILTER_PERMANENT_CREATURES_NON_BLACK = new FilterCreaturePermanent("nonblack creatures");

    static {
        FILTER_PERMANENT_CREATURES_NON_BLACK.add(Predicates.not(new ColorPredicate(ObjectColor.BLACK)));
        FILTER_PERMANENT_CREATURES_NON_BLACK.setLockedFilter(true);
    }

    public static final FilterCreaturePermanent FILTER_PERMANENT_CREATURE_GOBLINS = new FilterCreaturePermanent(SubType.GOBLIN, "Goblin creatures");

    static {
        FILTER_PERMANENT_CREATURE_GOBLINS.setLockedFilter(true);
    }

    public static final FilterCreaturePermanent FILTER_PERMANENT_SLIVERS = new FilterCreaturePermanent(SubType.SLIVER, "Sliver creatures");

    static {
        FILTER_PERMANENT_SLIVERS.setLockedFilter(true);
    }

    public static final FilterCreaturePermanent FILTER_PERMANENT_ALL_SLIVERS = new FilterCreaturePermanent(SubType.SLIVER, "all Sliver creatures");

    static {
        FILTER_PERMANENT_ALL_SLIVERS.setLockedFilter(true);
    }

    public static final FilterControlledPermanent FILTER_CONTROLLED_SAMURAI_OR_WARRIOR = new FilterControlledPermanent("a Samurai or Warrior you control");

    static {
        FILTER_CONTROLLED_SAMURAI_OR_WARRIOR.add(Predicates.or(
                SubType.SAMURAI.getPredicate(),
                SubType.WARRIOR.getPredicate()
        ));
        FILTER_CONTROLLED_SAMURAI_OR_WARRIOR.setLockedFilter(true);
    }

    public static final FilterPlaneswalkerPermanent FILTER_PERMANENT_PLANESWALKER = new FilterPlaneswalkerPermanent();

    static {
        FILTER_PERMANENT_PLANESWALKER.setLockedFilter(true);
    }

    public static final FilterPlaneswalkerPermanent FILTER_PERMANENT_PLANESWALKERS = new FilterPlaneswalkerPermanent("planeswalkers");

    static {
        FILTER_PERMANENT_PLANESWALKERS.setLockedFilter(true);
    }

    public static final FilterNonlandPermanent FILTER_PERMANENT_NON_LAND = new FilterNonlandPermanent();

    static {
        FILTER_PERMANENT_NON_LAND.setLockedFilter(true);
    }

    public static final FilterNonlandPermanent FILTER_PERMANENTS_NON_LAND = new FilterNonlandPermanent("nonland permanents");

    static {
        FILTER_PERMANENTS_NON_LAND.setLockedFilter(true);
    }

    public static final FilterStackObject FILTER_SPELL_OR_ABILITY_OPPONENTS = new FilterStackObject("a spell or ability an opponent controls");

    static {
        FILTER_SPELL_OR_ABILITY_OPPONENTS.add(TargetController.OPPONENT.getControllerPredicate());
        FILTER_SPELL_OR_ABILITY_OPPONENTS.setLockedFilter(true);
    }

    public static final FilterStackObject FILTER_SPELL_OR_ABILITY = new FilterStackObject();

    static {
        FILTER_SPELL_OR_ABILITY.setLockedFilter(true);
    }

    public static final FilterStackObject FILTER_SPELL_OR_ABILITY_A = new FilterStackObject("a spell or ability");

    static {
        FILTER_SPELL_OR_ABILITY_A.setLockedFilter(true);
    }

    public static final FilterCreatureSpell FILTER_SPELL_A_CREATURE = new FilterCreatureSpell("a creature spell");

    static {
        FILTER_SPELL_A_CREATURE.setLockedFilter(true);
    }

    public static final FilterCreatureSpell FILTER_SPELL_CREATURE = new FilterCreatureSpell("creature spell");

    static {
        FILTER_SPELL_CREATURE.setLockedFilter(true);
    }

    public static final FilterSpell FILTER_SPELL_NON_CREATURE = (FilterSpell) new FilterSpell("noncreature spell");

    static {
        FILTER_SPELL_NON_CREATURE.add(Predicates.not(CardType.CREATURE.getPredicate()));
        FILTER_SPELL_NON_CREATURE.setLockedFilter(true);
    }

    public static final FilterSpell FILTER_SPELLS_NON_CREATURE = (FilterSpell) new FilterSpell("noncreature spells");

    static {
        FILTER_SPELLS_NON_CREATURE.add(Predicates.not(CardType.CREATURE.getPredicate()));
        FILTER_SPELLS_NON_CREATURE.setLockedFilter(true);
    }

    public static final FilterSpell FILTER_SPELL_A_NON_CREATURE = (FilterSpell) new FilterSpell("a noncreature spell");

    static {
        FILTER_SPELL_A_NON_CREATURE.add(Predicates.not(CardType.CREATURE.getPredicate()));
        FILTER_SPELL_A_NON_CREATURE.setLockedFilter(true);
    }

    public static final FilterSpell FILTER_SPELL = new FilterSpell();

    static {
        FILTER_SPELL.setLockedFilter(true);
    }

    public static final FilterSpell FILTER_SPELL_A = new FilterSpell("a spell");

    static {
        FILTER_SPELL_A.setLockedFilter(true);
    }

    public static final FilterSpell FILTER_SPELL_A_MULTICOLORED = new FilterSpell("a multicolored spell");

    static {
        FILTER_SPELL_A_MULTICOLORED.add(MulticoloredPredicate.instance);
        FILTER_SPELL_A_MULTICOLORED.setLockedFilter(true);
    }

    public static final FilterSpell FILTER_SPELL_AN_INSTANT_OR_SORCERY = new FilterSpell("an instant or sorcery spell");

    static {
        FILTER_SPELL_AN_INSTANT_OR_SORCERY.add(Predicates.or(
                CardType.INSTANT.getPredicate(),
                CardType.SORCERY.getPredicate()
        ));
        FILTER_SPELL_AN_INSTANT_OR_SORCERY.setLockedFilter(true);
    }

    public static final FilterSpell FILTER_SPELL_INSTANT_OR_SORCERY = new FilterSpell("instant or sorcery spell");

    static {
        FILTER_SPELL_INSTANT_OR_SORCERY.add(Predicates.or(
                CardType.INSTANT.getPredicate(),
                CardType.SORCERY.getPredicate()
        ));
        FILTER_SPELL_INSTANT_OR_SORCERY.setLockedFilter(true);
    }

    public static final FilterSpell FILTER_SPELLS_INSTANT_OR_SORCERY = new FilterSpell("instant or sorcery spells");

    static {
        FILTER_SPELLS_INSTANT_OR_SORCERY.add(Predicates.or(
                CardType.INSTANT.getPredicate(),
                CardType.SORCERY.getPredicate()
        ));
        FILTER_SPELLS_INSTANT_OR_SORCERY.setLockedFilter(true);
    }

    public static final FilterSpell FILTER_SPELL_INSTANT_SORCERY_WIZARD = new FilterSpell("an instant, sorcery, or Wizard spell");

    static {
        FILTER_SPELL_INSTANT_SORCERY_WIZARD.add(Predicates.or(
                CardType.INSTANT.getPredicate(),
                CardType.SORCERY.getPredicate(),
                SubType.WIZARD.getPredicate()
        ));
        FILTER_SPELL_INSTANT_SORCERY_WIZARD.setLockedFilter(true);
    }

    public static final FilterSpell FILTER_SPELL_AN_ENCHANTMENT = new FilterSpell("an enchantment spell");

    static {
        FILTER_SPELL_AN_ENCHANTMENT.add(CardType.ENCHANTMENT.getPredicate());
        FILTER_SPELL_AN_ENCHANTMENT.setLockedFilter(true);
    }

    public static final FilterSpell FILTER_SPELL_KICKED_A = new FilterSpell("a kicked spell");

    static {
        FILTER_SPELL_KICKED_A.add(KickedSpellPredicate.instance);
        FILTER_SPELL_KICKED_A.setLockedFilter(true);
    }

    public static final FilterCreaturePermanent FILTER_CREATURE_TOKEN = new FilterCreaturePermanent("creature token");

    static {
        FILTER_CREATURE_TOKEN.add(TokenPredicate.TRUE);
        FILTER_CREATURE_TOKEN.setLockedFilter(true);
    }

    public static final FilterControlledCreaturePermanent FILTER_A_CONTROLLED_CREATURE_P1P1 = new FilterControlledCreaturePermanent("a creature you control with a +1/+1 counter on it");

    static {
        FILTER_A_CONTROLLED_CREATURE_P1P1.add(CounterType.P1P1.getPredicate());
        FILTER_A_CONTROLLED_CREATURE_P1P1.setLockedFilter(true);
    }

    public static final FilterControlledCreaturePermanent FILTER_CONTROLLED_CREATURE_P1P1 = new FilterControlledCreaturePermanent("creature you control with a +1/+1 counter on it");

    static {
        FILTER_CONTROLLED_CREATURE_P1P1.add(CounterType.P1P1.getPredicate());
        FILTER_CONTROLLED_CREATURE_P1P1.setLockedFilter(true);
    }

    public static final FilterControlledCreaturePermanent FILTER_EACH_CONTROLLED_CREATURE_P1P1 = new FilterControlledCreaturePermanent("each creature you control with a +1/+1 counter on it");

    static {
        FILTER_EACH_CONTROLLED_CREATURE_P1P1.add(CounterType.P1P1.getPredicate());
        FILTER_EACH_CONTROLLED_CREATURE_P1P1.setLockedFilter(true);
    }

    public static final FilterControlledCreaturePermanent FILTER_OTHER_CONTROLLED_CREATURE_P1P1 = new FilterControlledCreaturePermanent("other creature you control with a +1/+1 counter on it");

    static {
        FILTER_OTHER_CONTROLLED_CREATURE_P1P1.add(CounterType.P1P1.getPredicate());
        FILTER_OTHER_CONTROLLED_CREATURE_P1P1.add(AnotherPredicate.instance);
        FILTER_OTHER_CONTROLLED_CREATURE_P1P1.setLockedFilter(true);
    }

    public static final FilterCreaturePermanent FILTER_A_CREATURE_P1P1 = new FilterCreaturePermanent("a creature with a +1/+1 counter on it");

    static {
        FILTER_A_CREATURE_P1P1.add(CounterType.P1P1.getPredicate());
        FILTER_A_CREATURE_P1P1.setLockedFilter(true);
    }

    public static final FilterCreaturePermanent FILTER_CREATURE_P1P1 = new FilterCreaturePermanent("creature with a +1/+1 counter on it");

    static {
        FILTER_CREATURE_P1P1.add(CounterType.P1P1.getPredicate());
        FILTER_CREATURE_P1P1.setLockedFilter(true);
    }

    public static final FilterCreaturePermanent FILTER_CREATURE_TOKENS = new FilterCreaturePermanent("creature tokens");

    static {
        FILTER_CREATURE_TOKENS.add(TokenPredicate.TRUE);
        FILTER_CREATURE_TOKENS.setLockedFilter(true);
    }

    public static final FilterAttackingCreature FILTER_ATTACKING_CREATURE = new FilterAttackingCreature();

    static {
        FILTER_ATTACKING_CREATURE.setLockedFilter(true);
    }

    public static final FilterAttackingCreature FILTER_ATTACKING_CREATURES = new FilterAttackingCreature("attacking creatures");

    static {
        FILTER_ATTACKING_CREATURES.setLockedFilter(true);
    }

    public static final FilterAttackingOrBlockingCreature FILTER_ATTACKING_OR_BLOCKING_CREATURE = new FilterAttackingOrBlockingCreature();

    static {
        FILTER_ATTACKING_OR_BLOCKING_CREATURE.setLockedFilter(true);
    }

    public static final FilterAttackingOrBlockingCreature FILTER_ATTACKING_OR_BLOCKING_CREATURES = new FilterAttackingOrBlockingCreature("attacking or blocking creatures");

    static {
        FILTER_ATTACKING_OR_BLOCKING_CREATURES.setLockedFilter(true);
    }

    public static final FilterBlockingCreature FILTER_BLOCKING_CREATURES = new FilterBlockingCreature("blocking creatures");

    static {
        FILTER_BLOCKING_CREATURES.setLockedFilter(true);
    }

    public static final FilterPermanent FILTER_PERMANENT_AURA = new FilterPermanent();

    static {
        FILTER_PERMANENT_AURA.add(CardType.ENCHANTMENT.getPredicate());
        FILTER_PERMANENT_AURA.add(SubType.AURA.getPredicate());
        FILTER_PERMANENT_AURA.setLockedFilter(true);
    }

    public static final FilterPermanent FILTER_PERMANENT_EQUIPMENT = new FilterEquipmentPermanent();

    static {
        FILTER_PERMANENT_EQUIPMENT.setLockedFilter(true);
    }

    public static final FilterPermanent FILTER_PERMANENT_FORTIFICATION = new FilterPermanent();

    static {
        FILTER_PERMANENT_FORTIFICATION.add(CardType.ARTIFACT.getPredicate());
        FILTER_PERMANENT_FORTIFICATION.add(SubType.FORTIFICATION.getPredicate());
        FILTER_PERMANENT_FORTIFICATION.setLockedFilter(true);
    }

    public static final FilterPermanent FILTER_PERMANENT_LEGENDARY = new FilterPermanent();

    static {
        FILTER_PERMANENT_LEGENDARY.add(SuperType.LEGENDARY.getPredicate());
        FILTER_PERMANENT_LEGENDARY.setLockedFilter(true);
    }

    public static final FilterCard FILTER_CARD_ARTIFACT_OR_CREATURE = new FilterCard("artifact or creature card");

    static {
        FILTER_CARD_ARTIFACT_OR_CREATURE.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.CREATURE.getPredicate()
        ));
        FILTER_CARD_ARTIFACT_OR_CREATURE.setLockedFilter(true);
    }

    public static final FilterCard FILTER_CARD_CREATURE_OR_LAND = new FilterCard("creature or land card");

    static {
        FILTER_CARD_CREATURE_OR_LAND.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                CardType.LAND.getPredicate()
        ));
        FILTER_CARD_CREATURE_OR_LAND.setLockedFilter(true);
    }

    public static final FilterPlayer FILTER_PLAYER_CONTROLLER = new FilterPlayer("you");

    static {
        FILTER_PLAYER_CONTROLLER.add(TargetController.YOU.getPlayerPredicate());
        FILTER_PLAYER_CONTROLLER.setLockedFilter(true);
    }
}
