/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.filter;

import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.*;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.filter.predicate.permanent.AttackingPredicate;
import mage.filter.predicate.permanent.TokenPredicate;

/**
 *
 * @author LevelX2
 */
public final class StaticFilters {


    public static final FilterSpiritOrArcaneCard SPIRIT_OR_ARCANE_CARD = new FilterSpiritOrArcaneCard();

    public static final FilterArtifactCard FILTER_CARD_ARTIFACT = new FilterArtifactCard();
    public static final FilterNonlandCard FILTER_CARD_NON_LAND = new FilterNonlandCard();
    public static final FilterNonlandCard FILTER_CARD_A_NON_LAND = new FilterNonlandCard("a nonland card");
    public static final FilterCard FILTER_CARD_ARTIFACT_OR_CREATURE = new FilterCard("artifact or creature card");

    public static final FilterCreaturePermanent FILTER_ARTIFACT_CREATURE_PERMANENT = new FilterArtifactCreaturePermanent();
    public static final FilterPermanent FILTER_PERMANENT_ARTIFACT_OR_CREATURE = new FilterPermanent("artifact or creature");
    public static final FilterPermanent FILTER_PERMANENT_ARTIFACT_CREATURE_OR_ENCHANTMENT = new FilterPermanent("artifact, creature, or enchantment");
    public static final FilterPermanent FILTER_PERMANENT_ARTIFACT_CREATURE_ENCHANTMENT_OR_LAND = new FilterPermanent("artifact, creature, enchantment, or land");
    public static final FilterControlledPermanent FILTER_CONTROLLED_PERMANENT_ARTIFACT = new FilterControlledArtifactPermanent();
    public static final FilterControlledPermanent FILTER_CONTROLLED_PERMANENT_ARTIFACT_OR_CREATURE = new FilterControlledPermanent("artifact or creature you control");

    public static final FilterControlledPermanent FILTER_CONTROLLED_A_CREATURE = new FilterControlledCreaturePermanent("a creature you control");
    public static final FilterControlledCreaturePermanent FILTER_CONTROLLED_ANOTHER_CREATURE = new FilterControlledCreaturePermanent("another creature");
    public static final FilterControlledPermanent FILTER_CONTROLLED_PERMANENT_NON_LAND = new FilterControlledPermanent("nonland permanent");

    public static final FilterCreaturePermanent FILTER_PERMANENT_CREATURE = new FilterCreaturePermanent();
    public static final FilterCreaturePermanent FILTER_PERMANENT_A_CREATURE = new FilterCreaturePermanent("a creature");
    public static final FilterCreaturePermanent FILTER_PERMANENT_CREATURES = new FilterCreaturePermanent("creatures");
    public static final FilterCreaturePermanent FILTER_PERMANENT_CREATURE_GOBLINS = new FilterCreaturePermanent(SubType.GOBLIN, "Goblin creatures");
    public static final FilterCreaturePermanent FILTER_PERMANENT_CREATURE_SLIVERS = new FilterCreaturePermanent(SubType.SLIVER, "Sliver creatures");

    public static final FilterPermanent FILTER_PERMANENT_NON_LAND = new FilterNonlandPermanent();

    public static final FilterCreatureSpell FILTER_SPELL_A_CREATURE = new FilterCreatureSpell("a creature spell");
    public static final FilterSpell FILTER_SPELL_NON_CREATURE
            = (FilterSpell) new FilterSpell("noncreature spell").add(Predicates.not(new CardTypePredicate(CardType.CREATURE)));

    public static final FilterPermanent FILTER_CREATURE_TOKENS = new FilterCreaturePermanent("creature tokens");

    public static final FilterPermanent FILTER_ATTACKING_CREATURES = new FilterCreaturePermanent("attacking creatures");

    static {
        FILTER_CONTROLLED_PERMANENT_NON_LAND.add(
                Predicates.not(new CardTypePredicate(CardType.LAND)));
        FILTER_CREATURE_TOKENS.add(new TokenPredicate());

        FILTER_ATTACKING_CREATURES.add(new AttackingPredicate());

        FILTER_PERMANENT_ARTIFACT_OR_CREATURE.add(Predicates.or(
                new CardTypePredicate(CardType.ARTIFACT),
                new CardTypePredicate(CardType.CREATURE)
        ));
        FILTER_PERMANENT_ARTIFACT_CREATURE_OR_ENCHANTMENT.add(Predicates.or(
                new CardTypePredicate(CardType.ARTIFACT),
                new CardTypePredicate(CardType.CREATURE),
                new CardTypePredicate(CardType.ENCHANTMENT)
        ));
        FILTER_PERMANENT_ARTIFACT_CREATURE_ENCHANTMENT_OR_LAND.add(Predicates.or(
                new CardTypePredicate(CardType.ARTIFACT),
                new CardTypePredicate(CardType.CREATURE),
                new CardTypePredicate(CardType.ENCHANTMENT),
                new CardTypePredicate(CardType.LAND)
        ));
        FILTER_CONTROLLED_PERMANENT_ARTIFACT_OR_CREATURE.add(Predicates.or(
                new CardTypePredicate(CardType.ARTIFACT),
                new CardTypePredicate(CardType.CREATURE)
        ));
        FILTER_CONTROLLED_ANOTHER_CREATURE.add(new AnotherPredicate());

        FILTER_CARD_ARTIFACT_OR_CREATURE.add(Predicates.or(
                new CardTypePredicate(CardType.ARTIFACT),
                new CardTypePredicate(CardType.CREATURE)
        ));
    }

}
