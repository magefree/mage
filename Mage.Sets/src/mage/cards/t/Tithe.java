
package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.common.FilterLandPermanent;
import mage.game.Game;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetOpponent;

/**
 *
 * @author emerald000
 */
public final class Tithe extends CardImpl {

    public Tithe(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{W}");

        // Search your library for a Plains card. If target opponent controls more lands than you, you may search your library for an additional Plains card. Reveal those cards and put them into your hand. Then shuffle your library.
        this.getSpellAbility().addEffect(new TitheEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());
    }

    private Tithe(final Tithe card) {
        super(card);
    }

    @Override
    public Tithe copy() {
        return new Tithe(this);
    }
}

class TitheEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("Plains");
    static {
        filter.add(SubType.PLAINS.getPredicate());
    }

    TitheEffect() {
        super(Outcome.Benefit);
        this.staticText = "Search your library for a Plains card. If target opponent controls more lands than you, you may search your library for an additional Plains card. Reveal those cards, put them into your hand, then shuffle";
    }

    private TitheEffect(final TitheEffect effect) {
        super(effect);
    }

    @Override
    public TitheEffect copy() {
        return new TitheEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int numYourLands = game.getBattlefield().countAll(new FilterLandPermanent(), source.getControllerId(), game);
        int numOpponentLands = game.getBattlefield().countAll(new FilterLandPermanent(), this.getTargetPointer().getFirst(game, source), game);
        new SearchLibraryPutInHandEffect(new TargetCardInLibrary(0, (numOpponentLands > numYourLands ? 2 : 1), filter), true).apply(game, source);
        return true;
    }
}
