
package mage.cards.l;

import java.util.UUID;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.effects.common.search.SearchLibraryGraveyardPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;

/**
 *
 * @author fireshoes
 */
public final class LilianasInfluence extends CardImpl {

    private final static FilterCard filter = new FilterCard("Liliana, Death Wielder");
    private final static FilterCreaturePermanent filterCreatures = new FilterCreaturePermanent("creature you don't control");

    static {
        filter.add(new NamePredicate("Liliana, Death Wielder"));
        filterCreatures.add(new ControllerPredicate(TargetController.NOT_YOU));
    }

    public LilianasInfluence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{B}{B}");

        // Put a -1/-1 counter on each creature you don't control. You may search your library and/or graveyard for a card named Liliana, Death Wielder,
        // reveal it, and put it into your hand. If you search your library this way, shuffle it.
        getSpellAbility().addEffect(new AddCountersAllEffect(CounterType.M1M1.createInstance(1), filterCreatures));
        getSpellAbility().addEffect(new SearchLibraryGraveyardPutInHandEffect(filter));
    }

    public LilianasInfluence(final LilianasInfluence card) {
        super(card);
    }

    @Override
    public LilianasInfluence copy() {
        return new LilianasInfluence(this);
    }
}
