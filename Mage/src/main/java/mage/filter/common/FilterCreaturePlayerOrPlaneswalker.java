package mage.filter.common;

import mage.MageObject;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.Predicates;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * If you add predicate to permanentFilter then it will be applied to planeswalker too
 *
 * @author JRHerlehy Created on 4/8/18.
 */
public class FilterCreaturePlayerOrPlaneswalker extends FilterPermanentOrPlayer {

    public FilterCreaturePlayerOrPlaneswalker() {
        this("any target");
    }

    public FilterCreaturePlayerOrPlaneswalker(String name) {
        this(name, (SubType) null);
    }

    public FilterCreaturePlayerOrPlaneswalker(String name, SubType... andCreatureTypes) {
        super(name);
        List<Predicate<MageObject>> allCreaturePredicates = Arrays.stream(andCreatureTypes)
                .filter(Objects::nonNull)
                .map(SubType::getPredicate)
                .collect(Collectors.toList());
        allCreaturePredicates.add(0, CardType.CREATURE.getPredicate());
        Predicate<MageObject> planeswalkerPredicate = CardType.PLANESWALKER.getPredicate();

        this.permanentFilter.add(Predicates.or(
                Predicates.and(allCreaturePredicates),
                planeswalkerPredicate
        ));
    }

    public FilterCreaturePlayerOrPlaneswalker(final FilterCreaturePlayerOrPlaneswalker filter) {
        super(filter);
    }

    @Override
    public FilterCreaturePlayerOrPlaneswalker copy() {
        return new FilterCreaturePlayerOrPlaneswalker(this);
    }
}
