package mage.cards.h;

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

import java.util.UUID;

/**
 * @author L_J
 */
public final class HonorTheFallen extends CardImpl {

    public HonorTheFallen(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");

        // Exile all creature cards from all graveyards. You gain 1 life for each card exiled this way.
        this.getSpellAbility().addEffect(new HonorTheFallenEffect());
    }

    private HonorTheFallen(final HonorTheFallen card) {
        super(card);
    }

    @Override
    public HonorTheFallen copy() {
        return new HonorTheFallen(this);
    }
}

class HonorTheFallenEffect extends OneShotEffect {

    HonorTheFallenEffect() {
        super(Outcome.Detriment);
        staticText = "Exile all creature cards from all graveyards. You gain 1 life for each card exiled this way";
    }

    private HonorTheFallenEffect(final HonorTheFallenEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Cards cards = new CardsImpl();
        for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player == null) {
                continue;
            }
            cards.addAllCards(player.getGraveyard().getCards(StaticFilters.FILTER_CARD_CREATURE, game));
        }
        controller.moveCards(cards, Zone.EXILED, source, game);
        int count = cards
                .stream()
                .map(game.getState()::getZone)
                .map(Zone.EXILED::equals)
                .mapToInt(x -> x ? 1 : 0)
                .sum();
        controller.gainLife(count, game, source);
        return true;
    }

    @Override
    public HonorTheFallenEffect copy() {
        return new HonorTheFallenEffect(this);
    }
}
