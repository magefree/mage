package mage.filter.common;

import mage.constants.CardType;
import mage.filter.predicate.Predicates;

/**
 * If you add predicate to permanentFilter then it will be applied to planeswalker too
 *
 * @author JRHerlehy Created on 4/8/18.
 */
public class FilterCreaturePlayerOrPlaneswalker extends FilterPermanentOrPlayer {

    public FilterCreaturePlayerOrPlaneswalker() {
        this("creature, player, or planeswalker");
    }

    public FilterCreaturePlayerOrPlaneswalker(String name) {
        super(name);
        this.permanentFilter.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                CardType.PLANESWALKER.getPredicate()
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
