package mage.cards.b;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetDiscard;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author fireshoes
 */
public final class BorderlandExplorer extends CardImpl {

    public BorderlandExplorer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.ELF, SubType.SCOUT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // When Borderland Explorer enters the battlefield, each player may discard a card. Each player who discarded a card this way may search their library
        // for a basic land card, reveal it, put it into their hand, then shuffle their library.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new BorderlandExplorerEffect()));
    }

    public BorderlandExplorer(final BorderlandExplorer card) {
        super(card);
    }

    @Override
    public BorderlandExplorer copy() {
        return new BorderlandExplorer(this);
    }
}

class BorderlandExplorerEffect extends OneShotEffect {

    public BorderlandExplorerEffect() {
        super(Outcome.Neutral);
        this.staticText = "each player may discard a card. Each player who discarded a card this way may search their library "
                + "for a basic land card, reveal it, put it into their hand, then shuffle their library";
    }

    public BorderlandExplorerEffect(final BorderlandExplorerEffect effect) {
        super(effect);
    }

    @Override
    public BorderlandExplorerEffect copy() {
        return new BorderlandExplorerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller != null && sourceObject != null) {
            // Store for each player the cards to discard, that's important because all discard shall happen at the same time
            Map<UUID, Cards> cardsToDiscard = new HashMap<>();
            // Store for each player the lands to reveal, that's important because all reveals shall happen at the same time
            Map<UUID, Cards> cardsToReveal = new HashMap<>();

            // choose cards to discard
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    Target target = new TargetDiscard(0, 1, new FilterCard(), playerId);
                    player.chooseTarget(outcome, target, source, game);
                    Cards cards = new CardsImpl(target.getTargets());
                    cardsToDiscard.put(playerId, cards);
                }
            }
            // discard all chosen cards
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    Cards cardsPlayer = cardsToDiscard.get(playerId);
                    if (cardsPlayer != null) {
                        for (UUID cardId : cardsPlayer) {
                            Card card = game.getCard(cardId);
                            player.discard(card, source, game);

                        }
                    }
                }
            }
            // search for a land for each player that discarded
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    Cards cardsPlayer = cardsToDiscard.get(playerId);
                    if (cardsPlayer != null && !cardsPlayer.isEmpty()) {
                        TargetCardInLibrary target = new TargetCardInLibrary(0, 1, StaticFilters.FILTER_CARD_BASIC_LAND);
                        if (player.searchLibrary(target, source, game)) {
                            if (!target.getTargets().isEmpty()) {
                                Cards cards = new CardsImpl(target.getTargets());
                                cards.addAll(target.getTargets());
                                cardsToReveal.put(playerId, cards);
                            }
                        }
                    }
                }
            }
            // reveal the searched lands, put in hands, and shuffle
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    Cards cardsPlayer = cardsToReveal.get(playerId);
                    if (cardsPlayer != null) {
                        for (UUID cardId : cardsPlayer) {
                            Card card = game.getCard(cardId);
                            Cards cards = new CardsImpl(game.getCard(cardId));
                            if (card != null && !cards.isEmpty()) {
                                player.revealCards(sourceObject.getIdName() + " (" + player.getName() + ')', cards, game);
                                player.moveCards(card, Zone.HAND, source, game);
                                player.shuffleLibrary(source, game);
                            }
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }
}
