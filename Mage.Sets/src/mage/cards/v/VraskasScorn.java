
package mage.cards.v;

import java.util.UUID;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.search.SearchLibraryGraveyardPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.target.common.TargetOpponent;

/**
 *
 * @author LevelX2
 */
public final class VraskasScorn extends CardImpl {

    private static final FilterCard filter = new FilterCard("Vraska, Scheming Gorgon");

    static {
        filter.add(new NamePredicate(filter.getMessage()));
    }

    public VraskasScorn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}{B}");

        // Target opponent loses 4 life.
        this.getSpellAbility().addEffect(new LoseLifeTargetEffect(4));
        this.getSpellAbility().addTarget(new TargetOpponent());

        // You may search your library and/or graveyard for a card named Vraska, Scheming Gorgon, reveal it, and put it into your hand. If you search your library this way, shuffle it.
        this.getSpellAbility().addEffect(new SearchLibraryGraveyardPutInHandEffect(filter)
                .setText("You may search your library and/or graveyard for a card named Vraska, Scheming Gorgon, reveal it, and put it into your hand. If you search your library this way, shuffle"));
    }

    private VraskasScorn(final VraskasScorn card) {
        super(card);
    }

    @Override
    public VraskasScorn copy() {
        return new VraskasScorn(this);
    }
}
