package mage.cards.d;

import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Deathsprout extends CardImpl {

    public Deathsprout(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{B}{B}{G}");

        // Destroy target creature. Search your library for a basic land card, put it onto the battlefield tapped, then shuffle your library.
        this.getSpellAbility().addEffect(new DestroyTargetEffect().setText("Destroy target creature"));
        this.getSpellAbility().addEffect(new SearchLibraryPutInPlayEffect(
                new TargetCardInLibrary(StaticFilters.FILTER_CARD_BASIC_LAND), true
        ));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private Deathsprout(final Deathsprout card) {
        super(card);
    }

    @Override
    public Deathsprout copy() {
        return new Deathsprout(this);
    }
}
