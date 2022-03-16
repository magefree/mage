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
import mage.util.CardUtil;

import java.util.Objects;
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

    private static final FilterCard filter = new FilterCard("cards to put in graveyard");

    public GiftsUngivenEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Search your library for up to four cards with different names and reveal them. " +
                "Target opponent chooses two of those cards. Put the chosen cards into your graveyard " +
                "and the rest into your hand. Then shuffle";
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
        Player opponent = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (player == null || opponent == null) {
            return false;
        }
        GiftsUngivenTarget target = new GiftsUngivenTarget();
        player.searchLibrary(target, source, game);
        Cards cards = new CardsImpl();
        target.getTargets()
                .stream()
                .map(uuid -> player.getLibrary().getCard(uuid, game))
                .forEach(cards::add);
        if (cards.isEmpty()) {
            player.shuffleLibrary(source, game);
        }
        player.revealCards(source, cards, game);

        if (cards.size() > 2) {
            Cards cardsToKeep = new CardsImpl();
            cardsToKeep.addAll(cards);
            TargetCard targetDiscard = new TargetCard(2, Zone.LIBRARY, filter);
            if (opponent.choose(Outcome.Discard, cards, targetDiscard, game)) {
                cardsToKeep.removeIf(targetDiscard.getTargets()::contains);
                cards.removeAll(cardsToKeep);
            }
            player.moveCards(cardsToKeep, Zone.HAND, source, game);
        }

        player.moveCards(cards, Zone.GRAVEYARD, source, game);
        player.shuffleLibrary(source, game);
        return true;
    }
}

class GiftsUngivenTarget extends TargetCardInLibrary {

    private static final FilterCard filter = new FilterCard("cards with different names");

    GiftsUngivenTarget() {
        super(0, 4, filter);
    }

    private GiftsUngivenTarget(final GiftsUngivenTarget target) {
        super(target);
    }

    @Override
    public GiftsUngivenTarget copy() {
        return new GiftsUngivenTarget(this);
    }

    @Override
    public boolean canTarget(UUID playerId, UUID id, Ability source, Cards cards, Game game) {
        if (!super.canTarget(playerId, id, source, cards, game)) {
            return false;
        }
        Card card = cards.get(id, game);
        return card != null
                && this
                .getTargets()
                .stream()
                .map(game::getCard)
                .filter(Objects::nonNull)
                .noneMatch(c -> CardUtil.haveSameNames(c, card));
    }
}
