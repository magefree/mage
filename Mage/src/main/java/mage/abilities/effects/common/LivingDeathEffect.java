package mage.abilities.effects.common;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.*;

public class LivingDeathEffect extends OneShotEffect {

    public LivingDeathEffect() {
        super(Outcome.Benefit);
        this.staticText = "Each player exiles all creature cards from their graveyard, then sacrifices all creatures they control, then puts all cards they exiled this way onto the battlefield";
    }

    public LivingDeathEffect(final LivingDeathEffect effect) {
        super(effect);
    }

    @Override
    public LivingDeathEffect copy() {
        return new LivingDeathEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source);
        if (controller != null && sourceObject != null) {
            Map<UUID, Set<Card>> exiledCards = new HashMap<>();

            // Move creature cards from graveyard to exile
            for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    Set<Card> cardsPlayer = player.getGraveyard().getCards(StaticFilters.FILTER_CARD_CREATURE, game);
                    if (!cardsPlayer.isEmpty()) {
                        exiledCards.put(player.getId(), cardsPlayer);
                        player.moveCards(cardsPlayer, Zone.EXILED, source, game);
                    }
                }
            }
            game.getState().processAction(game);

            // Sacrifice all creatures
            for (Permanent permanent : game.getBattlefield().getActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, source.getControllerId(), game)) {
                permanent.sacrifice(source, game);
            }

            game.getState().processAction(game);

            // Exiled cards are put onto the battlefield at the same time under their owner's control
            Set<Card> cardsToReturnFromExile = new HashSet<>();
            for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    Set<Card> cardsPlayer = exiledCards.get(playerId);
                    if (cardsPlayer != null
                            && !cardsPlayer.isEmpty()) {
                        cardsToReturnFromExile.addAll(cardsPlayer);
                    }
                }
            }
            controller.moveCards(cardsToReturnFromExile, Zone.BATTLEFIELD, source, game, false, false, true, null);
            return true;
        }
        return false;
    }
}
