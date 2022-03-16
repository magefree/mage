package mage.cards.d;

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
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class DenyingWind extends CardImpl {

    public DenyingWind(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{7}{U}{U}");

        // Search target player's library for up to seven cards and exile them. Then that player shuffles their library.
        getSpellAbility().addEffect(new DenyingWindEffect());
        getSpellAbility().addTarget(new TargetPlayer());
    }

    private DenyingWind(final DenyingWind card) {
        super(card);
    }

    @Override
    public DenyingWind copy() {
        return new DenyingWind(this);
    }
}

class DenyingWindEffect extends OneShotEffect {

    public DenyingWindEffect() {
        super(Outcome.Neutral);
        staticText = "search target player's library for up to seven cards and exile them. Then that player shuffles";
    }

    public DenyingWindEffect(final DenyingWindEffect effect) {
        super(effect);
    }

    @Override
    public DenyingWindEffect copy() {
        return new DenyingWindEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getFirstTarget());
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null || player == null) {
            return false;
        }
        TargetCardInLibrary target = new TargetCardInLibrary(7, StaticFilters.FILTER_CARD);
        controller.searchLibrary(target, source, game, player.getId());
        Cards cards = new CardsImpl();
        target.getTargets()
                .stream()
                .map(uuid -> player.getLibrary().getCard(uuid, game))
                .forEach(cards::add);
        controller.moveCards(cards, Zone.EXILED, source, game);
        player.shuffleLibrary(source, game);
        return true;
    }
}
