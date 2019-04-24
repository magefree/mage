
package mage.cards.t;

import java.util.UUID;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.search.SearchLibraryGraveyardPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class TezzeretsBetrayal extends CardImpl {

    private final static FilterCard filter = new FilterCard("Tezzeret, Master of Metal");

    static {
        filter.add(new NamePredicate("Tezzeret, Master of Metal"));
    }

    public TezzeretsBetrayal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{U}{B}");

        // Destroy target creature.
        getSpellAbility().addEffect(new DestroyTargetEffect());
        getSpellAbility().addTarget(new TargetCreaturePermanent());

        // You may search your library and/or graveyard for a card named Tezzeret, Master of Metal, reveal it, and put it into your hand.
        // If you search your library this way, shuffle it.
        getSpellAbility().addEffect(new SearchLibraryGraveyardPutInHandEffect(filter));
    }

    public TezzeretsBetrayal(final TezzeretsBetrayal card) {
        super(card);
    }

    @Override
    public TezzeretsBetrayal copy() {
        return new TezzeretsBetrayal(this);
    }
}
