
package mage.cards.c;

import java.util.UUID;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.search.SearchLibraryGraveyardPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.target.common.TargetPlayerOrPlaneswalker;

/**
 *
 * @author TheElk801
 */
public final class ChandrasOutburst extends CardImpl {

    private static final FilterCard filter = new FilterCard("Chandra, Bold Pyromancer");

    static {
        filter.add(new NamePredicate("Chandra, Bold Pyromancer"));
    }

    public ChandrasOutburst(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{R}{R}");

        // Chandra's Outburst deals 4 damage to target player or planeswalker.
        this.getSpellAbility().addEffect(new DamageTargetEffect(4));
        this.getSpellAbility().addTarget(new TargetPlayerOrPlaneswalker());

        // Search your library and/or graveyard for a card named Chandra, Bold Pyromancer, reveal it, and put it into your hand. If you search your library this way, shuffle it.
        this.getSpellAbility().addEffect(new SearchLibraryGraveyardPutInHandEffect(filter));
    }

    private ChandrasOutburst(final ChandrasOutburst card) {
        super(card);
    }

    @Override
    public ChandrasOutburst copy() {
        return new ChandrasOutburst(this);
    }
}
