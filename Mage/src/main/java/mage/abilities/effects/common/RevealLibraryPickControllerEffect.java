package mage.abilities.effects.common;

import mage.filter.FilterCard;

/**
 * @author awjackson
 */
public class RevealLibraryPickControllerEffect extends LookLibraryAndPickControllerEffect {

    public RevealLibraryPickControllerEffect(int numberOfCards, int numberToPick, FilterCard filter,
                                             PutCards putPickedCards, PutCards putLookedCards) {
        this(numberOfCards, numberToPick, filter, putPickedCards, putLookedCards, true);
    }

    public RevealLibraryPickControllerEffect(int numberOfCards, int numberToPick, FilterCard filter,
                                             PutCards putPickedCards, PutCards putLookedCards, boolean optional) {
        super(numberOfCards, numberToPick, filter, putPickedCards, putLookedCards, optional);
        this.revealCards = true;
        this.revealPickedCards = false;
    }

    public RevealLibraryPickControllerEffect(final LookLibraryAndPickControllerEffect effect) {
        super(effect);
    }

    @Override
    public RevealLibraryPickControllerEffect copy() {
        return new RevealLibraryPickControllerEffect(this);
    }
}
