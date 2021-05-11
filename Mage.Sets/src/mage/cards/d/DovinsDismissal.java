package mage.cards.d;

import mage.abilities.effects.common.PutOnLibraryTargetEffect;
import mage.abilities.effects.common.search.SearchLibraryGraveyardPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DovinsDismissal extends CardImpl {

    private static final FilterCard filter = new FilterCard("card named Dovin, Architect of Law");
    private static final FilterPermanent filter2 = new FilterCreaturePermanent("tapped creature");

    static {
        filter.add(new NamePredicate("Dovin, Architect of Law"));
        filter2.add(TappedPredicate.TAPPED);
    }

    public DovinsDismissal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{W}{U}");

        // Put up to one target tapped creature on top of its owner's library. You may search your library and/or graveyard for a card named Dovin, Architect of Law, reveal it, and put it into your hand. If you search your library this way, shuffle it.
        this.getSpellAbility().addEffect(new PutOnLibraryTargetEffect(true));
        this.getSpellAbility().addEffect(
                new SearchLibraryGraveyardPutInHandEffect(filter, false, true)
        );
        this.getSpellAbility().addTarget(new TargetPermanent(0, 1, filter2, false));
    }

    private DovinsDismissal(final DovinsDismissal card) {
        super(card);
    }

    @Override
    public DovinsDismissal copy() {
        return new DovinsDismissal(this);
    }
}
