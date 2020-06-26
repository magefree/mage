package mage.filter;

import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.common.*;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.MulticoloredPredicate;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.filter.predicate.permanent.AttackingPredicate;
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

    public static final FilterSpiritOrArcaneCard SPIRIT_OR_ARCANE_CARD = new FilterSpiritOrArcaneCard();

    static {
        SPIRIT_OR_ARCANE_CARD.setLockedFilter(true);
    }

    public static final FilterEnchantmentPermanent FILTER_ENCHANTMENT_PERMANENT = new FilterEnchantmentPermanent();

    static {
        FILTER_ENCHANTMENT_PERMANENT.setLockedFilter(true);
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

    public static final FilterPermanent FILTER_PERMANENTS = new FilterPermanent("permanents");

    static {
        FILTER_PERMANENTS.setLockedFilter(true);
    }

    public static final FilterArtifactPermanent FILTER_PERMANENT_ARTIFACT = new FilterArtifactPermanent("artifact");

    static {
        FILTER_PERMANENT_ARTIFACT.setLockedFilter(true);
    }

    public static final FilterArtifactPermanent FILTER_PERMANENT_ARTIFACT_AN = new FilterArtifactPermanent("an artifact");

    static {
        FILTER_PERMANENT_ARTIFACT_AN.setLockedFilter(true);
    }

    public static final FilterArtifactOrEnchantmentPermanent FILTER_PERMANENT_ARTIFACT_OR_ENCHANTMENT = new FilterArtifactOrEnchantmentPermanent();

    static {
        FILTER_PERMANENT_ARTIFACT_OR_ENCHANTMENT.setLockedFilter(true);
    }

    public static final FilterCreaturePermanent FILTER_ARTIFACT_CREATURE_PERMANENT = new FilterArtifactCreaturePermanent();

    static {
        FILTER_ARTIFACT_CREATURE_PERMANENT.setLockedFilter(true);
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

    public static final FilterControlledPermanent FILTER_CONTROLLED_PERMANENT_ARTIFACT = new FilterControlledArtifactPermanent();

    static {
        FILTER_CONTROLLED_PERMANENT_ARTIFACT.setLockedFilter(true);
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

    public static final FilterControlledPermanent FILTER_CONTROLLED_PERMANENT_LAND = new FilterControlledLandPermanent();

    static {
        FILTER_CONTROLLED_PERMANENT_LAND.setLockedFilter(true);
    }

    public static final FilterControlledPermanent FILTER_CONTROLLED_PERMANENT_LANDS = new FilterControlledLandPermanent("lands you control");

    static {
        FILTER_CONTROLLED_PERMANENT_LANDS.setLockedFilter(true);
    }

    public static final FilterPermanent FILTER_OPPONENTS_PERMANENT = new FilterPermanent("permanent an opponent controls");

    static {
        FILTER_OPPONENTS_PERMANENT.add(TargetController.OPPONENT.getControllerPredicate());
        FILTER_OPPONENTS_PERMANENT.setLockedFilter(true);
    }

    public static final FilterCreaturePermanent FILTER_OPPONENTS_PERMANENT_CREATURE = new FilterCreaturePermanent("creature an opponent controls");

    static {
        FILTER_OPPONENTS_PERMANENT_CREATURE.add(TargetController.OPPONENT.getControllerPredicate());
        FILTER_OPPONENTS_PERMANENT_CREATURE.setLockedFilter(true);
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

    public static final FilterCreaturePermanent FILTER_CREATURE_YOU_DONT_CONTROL = new FilterCreaturePermanent("creature you don't control");

    static {
        FILTER_CREATURE_YOU_DONT_CONTROL.add(TargetController.NOT_YOU.getControllerPredicate());
        FILTER_CREATURE_YOU_DONT_CONTROL.setLockedFilter(true);
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

    public static final FilterCreaturePermanent FILTER_PERMANENT_CREATURE_A = new FilterCreaturePermanent("a creature");

    static {
        FILTER_PERMANENT_CREATURE_A.setLockedFilter(true);
    }

    public static final FilterPermanent FILTER_PERMANENT_CREATURE_OR_PLANESWALKER_A = new FilterPermanent("a creature or planeswalker");

    static {
        FILTER_PERMANENT_CREATURE_OR_PLANESWALKER_A.add(
                Predicates.or(CardType.CREATURE.getPredicate(), CardType.PLANESWALKER.getPredicate()));
        FILTER_PERMANENT_CREATURE_OR_PLANESWALKER_A.setLockedFilter(true);
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

    public static final FilterCreaturePermanent FILTER_PERMANENT_CREATURES_CONTROLLED = new FilterCreaturePermanent("creatures you control");

    static {
        FILTER_PERMANENT_CREATURES_CONTROLLED.add(TargetController.YOU.getControllerPredicate());
        FILTER_PERMANENT_CREATURES_CONTROLLED.setLockedFilter(true);
    }

    public static final FilterCreaturePermanent FILTER_PERMANENT_CREATURE_GOBLINS = new FilterCreaturePermanent(SubType.GOBLIN, "Goblin creatures");

    static {
        FILTER_PERMANENT_CREATURE_GOBLINS.setLockedFilter(true);
    }

    public static final FilterCreaturePermanent FILTER_PERMANENT_CREATURE_SLIVERS = new FilterCreaturePermanent(SubType.SLIVER, "all Sliver creatures");

    static {
        FILTER_PERMANENT_CREATURE_SLIVERS.setLockedFilter(true);
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

    public static final FilterStackObject FILTER_SPELL_OR_ABILITY_OPPONENTS = new FilterStackObject("spell or ability and opponent controls");

    static {
        FILTER_SPELL_OR_ABILITY_OPPONENTS.add(TargetController.OPPONENT.getControllerPredicate());
        FILTER_SPELL_OR_ABILITY_OPPONENTS.setLockedFilter(true);
    }

    public static final FilterStackObject FILTER_SPELL_OR_ABILITY = new FilterStackObject();

    static {
        FILTER_SPELL_OR_ABILITY.setLockedFilter(true);
    }

    public static final FilterCreatureSpell FILTER_SPELL_A_CREATURE = new FilterCreatureSpell("a creature spell");

    static {
        FILTER_SPELL_A_CREATURE.setLockedFilter(true);
    }

    public static final FilterCreatureSpell FILTER_SPELL_CREATURE = new FilterCreatureSpell("creature spell");

    static {
        FILTER_SPELL_CREATURE.setLockedFilter(true);
    }

    public static final FilterSpell FILTER_SPELL_NON_CREATURE = (FilterSpell) new FilterSpell("noncreature spell").add(Predicates.not(CardType.CREATURE.getPredicate()));

    static {
        FILTER_SPELL_NON_CREATURE.setLockedFilter(true);
    }

    public static final FilterSpell FILTER_SPELL_A_NON_CREATURE = (FilterSpell) new FilterSpell("a noncreature spell").add(Predicates.not(CardType.CREATURE.getPredicate()));

    static {
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

    public static final FilterCreaturePermanent FILTER_CREATURE_TOKENS = new FilterCreaturePermanent("creature tokens");

    static {
        FILTER_CREATURE_TOKENS.add(TokenPredicate.instance);
        FILTER_CREATURE_TOKENS.setLockedFilter(true);
    }

    public static final FilterCreaturePermanent FILTER_ATTACKING_CREATURES = new FilterCreaturePermanent("attacking creatures");

    static {
        FILTER_ATTACKING_CREATURES.add(AttackingPredicate.instance);
        FILTER_ATTACKING_CREATURES.setLockedFilter(true);
    }

    public static final FilterPermanent FILTER_PERMANENT_AURA = new FilterPermanent();

    static {
        FILTER_PERMANENT_AURA.add(CardType.ENCHANTMENT.getPredicate());
        FILTER_PERMANENT_AURA.add(SubType.AURA.getPredicate());
        FILTER_PERMANENT_AURA.setLockedFilter(true);
    }

    public static final FilterPermanent FILTER_PERMANENT_EQUIPMENT = new FilterEquipmentPermanent();

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

    public static final FilterPlayer FILTER_PLAYER_CONTROLLER = new FilterPlayer("you");

    static {
        FILTER_PLAYER_CONTROLLER.add(TargetController.YOU.getPlayerPredicate());
        FILTER_PLAYER_CONTROLLER.setLockedFilter(true);
    }
}
