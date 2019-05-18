package mage.cards.j;

import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.effects.common.search.SearchLibraryGraveyardPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class JacesRuse extends CardImpl {

    private static final FilterCard filter = new FilterCard("Jace, Arcane Strategist");

    static {
        filter.add(new NamePredicate("Jace, Arcane Strategist"));
    }

    public JacesRuse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{U}{U}");

        // Return up to two target creatures to their owner's hand. You may search your library and/or graveyard for a card named Jace, Arcane Strategist, reveal it, and put it into your hand. If you search your library this way, shuffle it.
        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect());
        this.getSpellAbility().addEffect(new SearchLibraryGraveyardPutInHandEffect(filter, false, true));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0, 2));
    }

    private JacesRuse(final JacesRuse card) {
        super(card);
    }

    @Override
    public JacesRuse copy() {
        return new JacesRuse(this);
    }
}
