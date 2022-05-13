package mage.cards.w;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.MageObject;
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

/**
 *
 * @author LevelX2
 */
public final class WeirdHarvest extends CardImpl {

    public WeirdHarvest(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{G}{G}");

        // Each player may search their library for up to X creature cards, reveal those cards, and put them into their hand. Then each player who searched their library this way shuffles it.
        getSpellAbility().addEffect(new WeirdHarvestEffect());
    }

    private WeirdHarvest(final WeirdHarvest card) {
        super(card);
    }

    @Override
    public WeirdHarvest copy() {
        return new WeirdHarvest(this);
    }
}

class WeirdHarvestEffect extends OneShotEffect {

    public WeirdHarvestEffect() {
        super(Outcome.Detriment);
        this.staticText = "each player may search their library for up to X creature cards, reveal those cards, put them into their hand, then shuffle";
    }

    public WeirdHarvestEffect(final WeirdHarvestEffect effect) {
        super(effect);
    }

    @Override
    public WeirdHarvestEffect copy() {
        return new WeirdHarvestEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source);
        if (controller != null && sourceObject != null) {
            int xValue = source.getManaCostsToPay().getX();
            if (xValue > 0) {
                List<Player> usingPlayers = new ArrayList<>();
                this.chooseAndSearchLibrary(usingPlayers, controller, xValue, source, sourceObject, game);
                for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                    if (!playerId.equals(controller.getId())) {
                        Player player = game.getPlayer(playerId);
                        if (player != null) {
                            this.chooseAndSearchLibrary(usingPlayers, player, xValue, source, sourceObject, game);
                        }
                    }
                }
                for (Player player : usingPlayers) {
                    player.shuffleLibrary(source, game);
                }
                return true;
            }
        }
        return false;
    }

    private void chooseAndSearchLibrary(List<Player> usingPlayers, Player player, int xValue, Ability source, MageObject sourceObject, Game game) {
        if (player.chooseUse(Outcome.PutCardInPlay, "Search your library for up " + xValue + " creature cards and put them into your hand?", source, game)) {
            usingPlayers.add(player);
            TargetCardInLibrary target = new TargetCardInLibrary(0, xValue, StaticFilters.FILTER_CARD_CREATURE);
            if (player.searchLibrary(target, source, game)) {
                if (!target.getTargets().isEmpty()) {
                    Cards cards = new CardsImpl(target.getTargets());
                    player.moveCards(cards, Zone.HAND, source, game);
                    player.revealCards(sourceObject.getIdName() + " (" + player.getName() + ')', cards, game);
                }
            }
        }
    }

}
