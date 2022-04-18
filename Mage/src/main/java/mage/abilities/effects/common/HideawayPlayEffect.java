package mage.abilities.effects.common;

import mage.ApprovingObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.game.ExileZone;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author LevelX2
 */
public class HideawayPlayEffect extends OneShotEffect {

    public HideawayPlayEffect() {
        super(Outcome.Benefit);
        staticText = "You may play the exiled card without paying its mana cost";
    }

    public HideawayPlayEffect(final HideawayPlayEffect effect) {
        super(effect);
    }

    @Override
    public HideawayPlayEffect copy() {
        return new HideawayPlayEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        ExileZone zone = game.getExile().getExileZone(CardUtil.getExileZoneId(game, source));
        if (controller == null || zone == null || zone.isEmpty()) {
            return true;
        }
        Card card = zone.getRandom(game);
        if (card == null) {
            return false;
        }
        if (!controller.chooseUse(Outcome.PlayForFree, "Play " + card.getIdName() + " for free?", source, game)) {
            return false;
        }
        card.setFaceDown(false, game);
        int zcc = card.getZoneChangeCounter(game);

        /* 702.74. Hideaway, rulings:
         * If the removed card is a land, you may play it as a result of the last ability only if it's your turn
         * and you haven't already played a land that turn. This counts as your land play for the turn.
         * TODO: this doesn't work correctly with the back half of MDFCs
         */
        if (card.isLand(game)) {
            UUID playerId = controller.getId();
            if (!game.isActivePlayer(playerId) || !game.getPlayer(playerId).canPlayLand()) {
                return false;
            }
        }

        if (!controller.playCard(card, game, true, new ApprovingObject(source, game))) {
            if (card.getZoneChangeCounter(game) == zcc) {
                card.setFaceDown(true, game);
            }
        }
        return true;
    }
}
