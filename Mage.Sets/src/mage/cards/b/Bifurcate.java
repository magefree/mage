
package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.common.FilterPermanentCard;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author ciaccona007
 */
public final class Bifurcate extends CardImpl {
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("nontoken creatures");

    static {
        filter.add(TokenPredicate.FALSE);
    }

    public Bifurcate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{G}");

        // Search your library for a permanent card with the same name as target nontoken creature and put that card onto the battlefield. Then shuffle your library.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filter));
        this.getSpellAbility().addEffect(new BifurcateEffect());
    }

    private Bifurcate(final Bifurcate card) {
        super(card);
    }

    @Override
    public Bifurcate copy() {
        return new Bifurcate(this);
    }
}

class BifurcateEffect extends OneShotEffect {

    public BifurcateEffect() {
        super(Outcome.Benefit);
        this.staticText = "search your library for a permanent card with the same name as target nontoken creature, put that card onto the battlefield, then shuffle";
    }

    public BifurcateEffect(final BifurcateEffect effect) {
        super(effect);
    }

    @Override
    public BifurcateEffect copy() {
        return new BifurcateEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        FilterCard filter = new FilterPermanentCard();
        filter.add(new NamePredicate(permanent.getName()));
        return new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(filter)).apply(game, source);
    }
}
