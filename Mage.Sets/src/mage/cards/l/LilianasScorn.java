package mage.cards.l;

import mage.abilities.effects.common.DestroyTargetEffect;
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
public final class LilianasScorn extends CardImpl {

    private static final FilterCard filter = new FilterCard("Liliana, Death Mage");

    static {
        filter.add(new NamePredicate("Liliana, Death Mage"));
    }

    public LilianasScorn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{B}{B}");

        // Destroy target creature. You may search your library and/or graveyard for a card named Liliana, Death Mage, reveal it, and put it into your hand. If you search your library this way, shuffle it.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addEffect(new SearchLibraryGraveyardPutInHandEffect(filter, false, true));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private LilianasScorn(final LilianasScorn card) {
        super(card);
    }

    @Override
    public LilianasScorn copy() {
        return new LilianasScorn(this);
    }
}
