package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.effects.SearchEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
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
 * @author emerald000
 */
public final class ManipulateFate extends CardImpl {

    public ManipulateFate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{U}");

        // Search your library for three cards, exile them, then shuffle your library.
        this.getSpellAbility().addEffect(new ManipulateFateEffect());
    }

    private ManipulateFate(final ManipulateFate card) {
        super(card);
    }

    @Override
    public ManipulateFate copy() {
        return new ManipulateFate(this);
    }
}

class ManipulateFateEffect extends SearchEffect {

    ManipulateFateEffect() {
        super(new TargetCardInLibrary(3, StaticFilters.FILTER_CARD), Outcome.Benefit);
        staticText = "Search your library for three cards, exile them, "
                + "then shuffle.<br>Draw a card";
    }

    private ManipulateFateEffect(final ManipulateFateEffect effect) {
        super(effect);
    }

    @Override
    public ManipulateFateEffect copy() {
        return new ManipulateFateEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        player.searchLibrary(target, source, game);
        player.moveCards(new CardsImpl(target.getTargets()), Zone.EXILED, source, game);
        player.shuffleLibrary(source, game);
        player.drawCards(1, source, game);
        return true;
    }
}
