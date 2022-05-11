package mage.cards.w;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author jeffwadsworth
 */
public final class Worldpurge extends CardImpl {

    public Worldpurge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{W/U}{W/U}{W/U}{W/U}");

        // Return all permanents to their owners' hands. Each player chooses up to seven cards in their hand, then shuffles the rest into their library. Each player loses all unspent mana.
        this.getSpellAbility().addEffect(new WorldpurgeEffect());

    }

    private Worldpurge(final Worldpurge card) {
        super(card);
    }

    @Override
    public Worldpurge copy() {
        return new Worldpurge(this);
    }
}

class WorldpurgeEffect extends OneShotEffect {

    public WorldpurgeEffect() {
        super(Outcome.Discard);
        this.staticText = "Return all permanents to their owners' hands. Each player chooses up to seven cards in their hand, then shuffles the rest into their library. Each player loses all unspent mana";
    }

    public WorldpurgeEffect(final WorldpurgeEffect effect) {
        super(effect);
    }

    @Override
    public WorldpurgeEffect copy() {
        return new WorldpurgeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source);
        if (controller != null && sourceObject != null) {
            Set<Card> allPermanents = new HashSet<>();
            allPermanents.addAll(game.getBattlefield().getActivePermanents(new FilterPermanent(), source.getControllerId(), source, game));
            controller.moveCards(allPermanents, Zone.HAND, source, game, false, false, true, null);
            game.informPlayers(sourceObject.getLogName() + " - All permanents returned to owners' hands");

            for (UUID playerId : game.getState().getPlayerList(controller.getId())) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    Cards hand = player.getHand();
                    int numberInHand = Math.min(7, hand.size());
                    TargetCardInHand target = new TargetCardInHand(0, numberInHand, new FilterCard("cards to keep in hand"));
                    Cards cardsToLibrary = new CardsImpl();
                    cardsToLibrary.addAll(player.getHand());
                    if (player.choose(Outcome.Benefit, target, source, game)) {
                        cardsToLibrary.removeAll(target.getTargets());
                    }
                    player.shuffleCardsToLibrary(cardsToLibrary, game, source);
                }
            }
            game.emptyManaPools(source);
            game.informPlayers(sourceObject.getLogName() + " - All players have lost all unspent mana");
            return true;
        }
        return false;
    }
}
