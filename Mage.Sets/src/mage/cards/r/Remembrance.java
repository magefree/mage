package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.predicate.mageobject.SharesNamePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Remembrance extends CardImpl {

    public Remembrance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{W}");

        // Whenever a nontoken creature you control dies, you may search your library for a card with the same name as that creature, reveal it, and put it into your hand. If you do, shuffle your library.
        this.addAbility(new DiesCreatureTriggeredAbility(
                new RemembranceEffect(), true, StaticFilters.FILTER_CONTROLLED_CREATURE_NON_TOKEN
        ));
    }

    private Remembrance(final Remembrance card) {
        super(card);
    }

    @Override
    public Remembrance copy() {
        return new Remembrance(this);
    }
}

class RemembranceEffect extends OneShotEffect {

    RemembranceEffect() {
        super(Outcome.Benefit);
        staticText = "search your library for a card with the same name as that creature, " +
                "reveal it, and put it into your hand. If you do, shuffle your library";
    }

    private RemembranceEffect(final RemembranceEffect effect) {
        super(effect);
    }

    @Override
    public RemembranceEffect copy() {
        return new RemembranceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = (Permanent) getValue("creatureDied");
        if (player == null || permanent == null) {
            return false;
        }
        FilterCard filter = new FilterCard("card with the same name");
        filter.add(new SharesNamePredicate(permanent));
        return new SearchLibraryPutInHandEffect(new TargetCardInLibrary(filter), true).apply(game, source);
    }
}
