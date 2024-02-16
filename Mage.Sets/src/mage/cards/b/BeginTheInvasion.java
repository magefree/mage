package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.target.common.TargetCardWithDifferentNameInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BeginTheInvasion extends CardImpl {

    public BeginTheInvasion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{W}{U}{B}{R}{G}");

        // Search your library for up to X battle cards with different names, put them onto the battlefield, then shuffle.
        this.getSpellAbility().addEffect(new BeginTheInvasionEffect());
    }

    private BeginTheInvasion(final BeginTheInvasion card) {
        super(card);
    }

    @Override
    public BeginTheInvasion copy() {
        return new BeginTheInvasion(this);
    }
}

class BeginTheInvasionEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("battle cards with different names");

    static {
        filter.add(CardType.BATTLE.getPredicate());
    }

    BeginTheInvasionEffect() {
        super(Outcome.Benefit);
        staticText = "search your library for up to X battle cards with different names, " +
                "put them onto the battlefield, then shuffle";
    }

    private BeginTheInvasionEffect(final BeginTheInvasionEffect effect) {
        super(effect);
    }

    @Override
    public BeginTheInvasionEffect copy() {
        return new BeginTheInvasionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int xValue = source.getManaCostsToPay().getX();
        return new SearchLibraryPutInPlayEffect(
                new TargetCardWithDifferentNameInLibrary(0, xValue, filter), false
        ).apply(game, source);
    }
}
