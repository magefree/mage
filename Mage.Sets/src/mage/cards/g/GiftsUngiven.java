package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class GiftsUngiven extends CardImpl {

    public GiftsUngiven(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{U}");

        // Search your library for up to four cards with different names and reveal them. Target opponent chooses two of those cards. Put the chosen cards into your graveyard and the rest into your hand. Then shuffle your library.
        this.getSpellAbility().addEffect(new GiftsUngivenEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());
    }

    private GiftsUngiven(final GiftsUngiven card) {
        super(card);
    }

    @Override
    public GiftsUngiven copy() {
        return new GiftsUngiven(this);
    }
}

class GiftsUngivenEffect extends OneShotEffect {

    public GiftsUngivenEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Search your library for up to four cards with different names and reveal them. Target opponent chooses two of those cards. Put the chosen cards into your graveyard and the rest into your hand. Then shuffle";
    }

    public GiftsUngivenEffect(final GiftsUngivenEffect effect) {
        super(effect);
    }

    @Override
    public GiftsUngivenEffect copy() {
        return new GiftsUngivenEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Card sourceCard = game.getCard(source.getSourceId());
        if (player == null || sourceCard == null) {
            return false;
        }
        Player opponent = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (opponent == null) {
            return false;
        }
        GiftsUngivenTarget target = new GiftsUngivenTarget();
        if (player.searchLibrary(target, source, game)) {
            if (!target.getTargets().isEmpty()) {
                Cards cards = new CardsImpl();
                for (UUID cardId : target.getTargets()) {
                    Card card = player.getLibrary().remove(cardId, game);
                    if (card != null) {
                        cards.add(card);
                    }
                }
                player.revealCards(sourceCard.getIdName(), cards, game);

                CardsImpl cardsToKeep = new CardsImpl();
                if (cards.size() > 2) {
                    cardsToKeep.addAll(cards);
                    TargetCard targetDiscard = new TargetCard(2, Zone.LIBRARY, new FilterCard("cards to put in graveyard"));
                    if (opponent.choose(Outcome.Discard, cards, targetDiscard, game)) {
                        cardsToKeep.removeAll(targetDiscard.getTargets());
                        cards.removeAll(cardsToKeep);
                    }
                }

                player.moveCards(cards, Zone.GRAVEYARD, source, game);
                player.moveCards(cardsToKeep, Zone.HAND, source, game);
            }
            player.shuffleLibrary(source, game);
            return true;
        }
        player.shuffleLibrary(source, game);
        return false;
    }
}

class GiftsUngivenTarget extends TargetCardInLibrary {

    public GiftsUngivenTarget() {
        super(0, 4, new FilterCard("cards with different names"));
    }

    public GiftsUngivenTarget(final GiftsUngivenTarget target) {
        super(target);
    }

    @Override
    public GiftsUngivenTarget copy() {
        return new GiftsUngivenTarget(this);
    }

    @Override
    public boolean canTarget(UUID playerId, UUID id, Ability source, Cards cards, Game game) {
        Card card = cards.get(id, game);
        if (card != null) {
            for (UUID targetId : this.getTargets()) {
                Card iCard = game.getCard(targetId);
                if (iCard != null && iCard.getName().equals(card.getName())) {
                    return false;
                }
            }
            return filter.match(card, playerId, game);
        }
        return false;
    }
}
