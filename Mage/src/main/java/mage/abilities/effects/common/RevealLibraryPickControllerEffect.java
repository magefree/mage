package mage.abilities.effects.common;

import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.constants.PutCards;
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
        this(StaticValue.get(numberOfCards), numberToPick, filter, putPickedCards, putLookedCards, optional);
    }

    public RevealLibraryPickControllerEffect(DynamicValue numberOfCards, int numberToPick, FilterCard filter,
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
