
package mage.cards.t;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterLandCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author LevelX2
 */
public final class TemptWithDiscovery extends CardImpl {

    public TemptWithDiscovery(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{G}");

        // Tempting offer - Search your library for a land card and put it onto the battlefield.
        // Each opponent may search their library for a land card and put it onto the battlefield.
        // For each opponent who searches a library this way, search your library for a land card and put it onto the battlefield.
        // Then each player who searched a library this way shuffles it.
        this.getSpellAbility().addEffect(new TemptWithDiscoveryEffect());
    }

    private TemptWithDiscovery(final TemptWithDiscovery card) {
        super(card);
    }

    @Override
    public TemptWithDiscovery copy() {
        return new TemptWithDiscovery(this);
    }
}

class TemptWithDiscoveryEffect extends OneShotEffect {

    public TemptWithDiscoveryEffect() {
        super(Outcome.PutLandInPlay);
        this.staticText = "<i>Tempting offer</i> &mdash; Search your library for a land card and put it onto the battlefield. Each opponent may search their library for a land card and put it onto the battlefield. For each opponent who searches a library this way, search your library for a land card and put it onto the battlefield. Then each player who searched a library this way shuffles";
    }

    private TemptWithDiscoveryEffect(final TemptWithDiscoveryEffect effect) {
        super(effect);
    }

    @Override
    public TemptWithDiscoveryEffect copy() {
        return new TemptWithDiscoveryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Set<UUID> playersShuffle = new LinkedHashSet<>();
            playersShuffle.add(controller.getId());
            TargetCardInLibrary target = new TargetCardInLibrary(new FilterLandCard());
            if (controller.searchLibrary(target, source, game)) {
                for (UUID cardId : target.getTargets()) {
                    Card card = game.getCard(cardId);
                    if (card != null) {
                        controller.moveCards(card, Zone.BATTLEFIELD, source, game);
                    }
                }
            }
            int opponentsUsedSearch = 0;
            for (UUID playerId : game.getOpponents(controller.getId())) {
                Player opponent = game.getPlayer(playerId);
                if (opponent != null) {
                    if (opponent.chooseUse(outcome, "Search your library for a land card and put it onto the battlefield?", source, game)) {
                        target.clearChosen();
                        opponentsUsedSearch++;
                        playersShuffle.add(playerId);
                        if (opponent.searchLibrary(target, source, game)) {
                            for (UUID cardId : target.getTargets()) {
                                Card card = game.getCard(cardId);
                                if (card != null) {
                                    opponent.moveCards(card, Zone.BATTLEFIELD, source, game);
                                }
                            }
                        }
                    }
                }
            }
            if (opponentsUsedSearch > 0) {
                target = new TargetCardInLibrary(0, opponentsUsedSearch, new FilterLandCard());
                if (controller.searchLibrary(target, source, game)) {
                    for (UUID cardId : target.getTargets()) {
                        Card card = game.getCard(cardId);
                        if (card != null) {
                            controller.moveCards(card, Zone.BATTLEFIELD, source, game);
                        }
                    }
                }
            }
            for (UUID playerId : playersShuffle) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    player.shuffleLibrary(source, game);
                }
            }
            return true;
        }

        return false;
    }
}
