/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.filter;

import mage.constants.CardType;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;

/**
 *
 * @author LevelX2
 */
public class StaticFilters {

    public static final FilterPermanent FILTER_PERMANENT_ARTIFACT_OR_CREATURE = new FilterPermanent("artifact or creature");
    public static final FilterControlledPermanent FILTER_CONTROLLED_PERMANENT_ARTIFACT_OR_CREATURE = new FilterControlledPermanent("artifact or creature you control");
    public static final FilterControlledPermanent FILTER_CONTROLLED_PERMANENT_ARTIFACT = new FilterControlledArtifactPermanent();
    public static final FilterCard FILTER_CARD_ARTIFACT_OR_CREATURE = new FilterCard("artifact or creature card");

    static {
        FILTER_PERMANENT_ARTIFACT_OR_CREATURE.add(Predicates.or(
                new CardTypePredicate(CardType.ARTIFACT),
                new CardTypePredicate(CardType.CREATURE)
        ));
        FILTER_CONTROLLED_PERMANENT_ARTIFACT_OR_CREATURE.add(Predicates.or(
                new CardTypePredicate(CardType.ARTIFACT),
                new CardTypePredicate(CardType.CREATURE)
        ));
        FILTER_CARD_ARTIFACT_OR_CREATURE.add(Predicates.or(
                new CardTypePredicate(CardType.ARTIFACT),
                new CardTypePredicate(CardType.CREATURE)
        ));
    }

}
