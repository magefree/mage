package mage.cards.g;

import mage.abilities.Mode;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
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
public final class GoForth extends CardImpl {

    public GoForth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{G}");

        // Choose one--
        // * Search your library for a basic land card, reveal it, put it into your hand, then shuffle.
        this.getSpellAbility().addEffect(new SearchLibraryPutInHandEffect(
                new TargetCardInLibrary(StaticFilters.FILTER_CARD_BASIC_LAND), true
        ));

        // * Target creature gets +2/+2 until end of turn.
        this.getSpellAbility().addMode(new Mode(new BoostTargetEffect(2, 2))
                .addTarget(new TargetCreaturePermanent()));
    }

    private GoForth(final GoForth card) {
        super(card);
    }

    @Override
    public GoForth copy() {
        return new GoForth(this);
    }
}
