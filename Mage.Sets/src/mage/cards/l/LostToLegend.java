package mage.cards.l;

import mage.abilities.effects.common.PutIntoLibraryNFromTopTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.mageobject.HistoricPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LostToLegend extends CardImpl {

    private static final FilterPermanent filter = new FilterNonlandPermanent("nonland historic permanent");

    static {
        filter.add(HistoricPredicate.instance);
    }

    public LostToLegend(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{W}{W}");

        // Put target nonland historic permanent into its owner's library fourth from the top.
        this.getSpellAbility().addEffect(new PutIntoLibraryNFromTopTargetEffect(4));
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
    }

    private LostToLegend(final LostToLegend card) {
        super(card);
    }

    @Override
    public LostToLegend copy() {
        return new LostToLegend(this);
    }
}
