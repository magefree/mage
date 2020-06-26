package mage.cards.l;

import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.effects.common.search.SearchLibraryGraveyardPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.predicate.mageobject.NamePredicate;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class LilianasInfluence extends CardImpl {

    private static final FilterCard filter = new FilterCard("Liliana, Death Wielder");

    static {
        filter.add(new NamePredicate("Liliana, Death Wielder"));
    }

    public LilianasInfluence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{B}{B}");

        // Put a -1/-1 counter on each creature you don't control. You may search your library and/or graveyard for a card named Liliana, Death Wielder,
        // reveal it, and put it into your hand. If you search your library this way, shuffle it.
        getSpellAbility().addEffect(new AddCountersAllEffect(CounterType.M1M1.createInstance(1), StaticFilters.FILTER_CREATURE_YOU_DONT_CONTROL));
        getSpellAbility().addEffect(new SearchLibraryGraveyardPutInHandEffect(filter));
    }

    private LilianasInfluence(final LilianasInfluence card) {
        super(card);
    }

    @Override
    public LilianasInfluence copy() {
        return new LilianasInfluence(this);
    }
}
