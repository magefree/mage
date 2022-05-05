package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author nick.myers
 */
public final class ManaSeverance extends CardImpl {

    public ManaSeverance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{U}");

        // Search your library for any number of land cards and remove them from the game.
        // Shuffle your library afterwards.
        this.getSpellAbility().addEffect(new ManaSeveranceEffect());
    }

    private ManaSeverance(final ManaSeverance card) {
        super(card);
    }

    @Override
    public ManaSeverance copy() {
        return new ManaSeverance(this);
    }

}

class ManaSeveranceEffect extends OneShotEffect {

    public ManaSeveranceEffect() {
        super(Outcome.Benefit);
        this.staticText = "search your library for any number of land cards, exile them, then shuffle";
    }

    public ManaSeveranceEffect(final ManaSeveranceEffect effect) {
        super(effect);
    }

    @Override
    public ManaSeveranceEffect copy() {
        return new ManaSeveranceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        TargetCardInLibrary target = new TargetCardInLibrary(
                0, Integer.MAX_VALUE, StaticFilters.FILTER_CARD_LANDS
        );
        player.searchLibrary(target, source, game);
        Cards cards = new CardsImpl(target.getTargets());
        cards.retainZone(Zone.LIBRARY, game);
        player.moveCards(cards, Zone.EXILED, source, game);
        player.shuffleLibrary(source, game);
        return true;
    }
}
