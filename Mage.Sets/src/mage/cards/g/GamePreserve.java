package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GamePreserve extends CardImpl {

    public GamePreserve(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}");

        // At the beginning of your upkeep, each player reveals the top card of their library. If all cards revealed this way are creature cards, put those cards onto the battlefield under their owners' control.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new GamePreserveEffect(), TargetController.YOU, false
        ));
    }

    private GamePreserve(final GamePreserve card) {
        super(card);
    }

    @Override
    public GamePreserve copy() {
        return new GamePreserve(this);
    }
}

class GamePreserveEffect extends OneShotEffect {

    public GamePreserveEffect() {
        super(Outcome.Detriment);
        this.staticText = "each player reveals the top card of their library. If all cards revealed this way are creature cards, put those cards onto the battlefield under their owners' control";
    }

    public GamePreserveEffect(final GamePreserveEffect effect) {
        super(effect);
    }

    @Override
    public GamePreserveEffect copy() {
        return new GamePreserveEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Cards cards = new CardsImpl();
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                cards.add(player.getLibrary().getFromTop(game));
            }
        }
        controller.revealCards(source, cards, game);
        if (cards.isEmpty() || cards.count(StaticFilters.FILTER_CARD_NON_CREATURE, game) > 0) {
            return false;
        }
        controller.moveCards(
                cards.getCards(game), Zone.BATTLEFIELD, source, game,
                false, false, true, null
        );
        return true;
    }
}
