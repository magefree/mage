package mage.cards.b;

import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.effects.common.search.SearchLibraryGraveyardPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author htrajan
 */
public final class BasrisAegis extends CardImpl {

    private static final FilterCard filter = new FilterCard("Basri, Devoted Paladin");

    static {
        filter.add(new NamePredicate("Basri, Devoted Paladin"));
    }

    public BasrisAegis(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{W}{W}");

        // Put a +1/+1 counter on each of up to two target creatures. You may search your library and/or graveyard for a card named Basri, Devoted Paladin, reveal it, and put it into your hand. If you search your library this way, shuffle it.
        this.getSpellAbility().addEffect(new AddCountersTargetEffect(
                CounterType.P1P1.createInstance()
        ).setText("Put a +1/+1 counter on each of up to two target creatures"));
        this.getSpellAbility().addEffect(new SearchLibraryGraveyardPutInHandEffect(filter, false, true));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0, 2));
    }

    private BasrisAegis(final BasrisAegis card) {
        super(card);
    }

    @Override
    public BasrisAegis copy() {
        return new BasrisAegis(this);
    }
}
