package mage.cards.c;

import java.util.HashSet;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
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
import mage.util.ManaUtil;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import mage.cards.Card;

/**
 * @author LevelX2
 */
public final class CollectiveVoyage extends CardImpl {

    public CollectiveVoyage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{G}");

        // Join forces - Starting with you, each player may pay any amount of mana. Each player searches their library for up to X basic land cards, where X is the total amount of mana paid this way, puts them onto the battlefield tapped, then shuffles their library.
        this.getSpellAbility().addEffect(new CollectiveVoyageEffect());
    }

    private CollectiveVoyage(final CollectiveVoyage card) {
        super(card);
    }

    @Override
    public CollectiveVoyage copy() {
        return new CollectiveVoyage(this);
    }
}

class CollectiveVoyageEffect extends OneShotEffect {

    public CollectiveVoyageEffect() {
        super(Outcome.Detriment);
        this.staticText = "<i>Join forces</i> &mdash; Starting with you, each player may pay any amount of mana. Each player searches their library for up to X basic land cards, where X is the total amount of mana paid this way, puts them onto the battlefield tapped, then shuffles";
    }

    public CollectiveVoyageEffect(final CollectiveVoyageEffect effect) {
        super(effect);
    }

    @Override
    public CollectiveVoyageEffect copy() {
        return new CollectiveVoyageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Set<Card> toBattlefield = new HashSet<>();
        if (controller != null) {
            int xSum = 0;
            xSum += ManaUtil.playerPaysXGenericMana(false, "Collective Voyage", controller, source, game);
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                if (!Objects.equals(playerId, controller.getId())) {
                    Player player = game.getPlayer(playerId);
                    if (player != null) {
                        xSum += ManaUtil.playerPaysXGenericMana(false, "Collective Voyage", player, source, game);
                    }
                }
            }
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null && xSum != 0) {
                    TargetCardInLibrary target = new TargetCardInLibrary(0, xSum, StaticFilters.FILTER_CARD_BASIC_LAND);
                    if (player.searchLibrary(target, source, game)) {
                        toBattlefield.addAll(new CardsImpl(target.getTargets()).getCards(game));
                    }
                }
            }
            // must happen simultaneously Rule 101.4
            controller.moveCards(toBattlefield, Zone.BATTLEFIELD, source, game, true, false, true, null);

            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    player.shuffleLibrary(source, game);
                }
            }
            // prevent undo
            controller.resetStoredBookmark(game);
            return true;
        }
        return false;
    }
}
