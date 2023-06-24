package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RecklessHandling extends CardImpl {

    public RecklessHandling(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{R}");

        // Search your library for an artifact card, reveal it, put it into your hand, shuffle, then discard a card at random. If an artifact card was discarded this way, Reckless Handling deals 2 damage to each opponent.
        this.getSpellAbility().addEffect(new SearchLibraryPutInHandEffect(
                new TargetCardInLibrary(StaticFilters.FILTER_CARD_ARTIFACT_AN), true
        ).setText("search your library for an artifact card, reveal it, put it into your hand, shuffle"));
        this.getSpellAbility().addEffect(new RecklessHandlingEffect());
    }

    private RecklessHandling(final RecklessHandling card) {
        super(card);
    }

    @Override
    public RecklessHandling copy() {
        return new RecklessHandling(this);
    }
}

class RecklessHandlingEffect extends OneShotEffect {

    RecklessHandlingEffect() {
        super(Outcome.Benefit);
        staticText = ", then discard a card at random. If an artifact card " +
                "was discarded this way, {this} deals 2 damage to each opponent";
    }

    private RecklessHandlingEffect(final RecklessHandlingEffect effect) {
        super(effect);
    }

    @Override
    public RecklessHandlingEffect copy() {
        return new RecklessHandlingEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null || player.getHand().isEmpty()) {
            return false;
        }
        Card card = player.discard(1, true, false, source, game).getRandom(game);
        if (card == null || !card.isArtifact(game)) {
            return false;
        }
        for (UUID playerId : game.getOpponents(source.getControllerId())) {
            Player opponent = game.getPlayer(playerId);
            if (opponent != null) {
                opponent.damage(2, source, game);
            }
        }
        return true;
    }
}
