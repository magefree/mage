package mage.cards.r;

import java.util.UUID;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.effects.common.search.SearchLibraryGraveyardPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public final class RalsDispersal extends CardImpl {

    private static final FilterCard filter = new FilterCard("Ral, Caller of Storms");

    static {
        filter.add(new NamePredicate("Ral, Caller of Storms"));
    }

    public RalsDispersal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{U}{U}");

        // Return target creature to its owner's hand. You may search you library and/or graveyard for a card named Ral, Caller of Storms, reveal it, and put it in to your hand. If you search your library this way, shuffle it.
        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect());
        this.getSpellAbility().addEffect(
                new SearchLibraryGraveyardPutInHandEffect(filter, false, true)
        );
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private RalsDispersal(final RalsDispersal card) {
        super(card);
    }

    @Override
    public RalsDispersal copy() {
        return new RalsDispersal(this);
    }
}
