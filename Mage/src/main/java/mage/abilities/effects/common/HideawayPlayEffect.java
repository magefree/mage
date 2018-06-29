
package mage.abilities.effects.common;

import java.util.UUID;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.game.ExileZone;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

/**
 * @author LevelX2
 *
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
        ExileZone zone = game.getExile().getExileZone(CardUtil.getCardExileZoneId(game, source));
        if (zone == null || zone.isEmpty()) {
            return true;
        }
        Card card = zone.getCards(game).iterator().next();
        Player controller = game.getPlayer(source.getControllerId());
        if (card != null && controller != null) {
            if (controller.chooseUse(Outcome.PlayForFree, "Do you want to play " + card.getIdName() + " for free now?", source, game)) {
                card.setFaceDown(false, game);
                int zcc = card.getZoneChangeCounter(game);

                /* 702.74. Hideaway, rulings:
                 * If the removed card is a land, you may play it as a result of the last ability only if it's your turn
                 * and you haven't already played a land that turn. This counts as your land play for the turn.
                 */
                if (card.isLand()) {
                    UUID playerId = controller.getId();
                    if (!game.isActivePlayer(playerId) || !game.getPlayer(playerId).canPlayLand()) {
                        return false;
                    }
                }

                if (!controller.playCard(card, game, true, true, new MageObjectReference(source.getSourceObject(game), game))) {
                    if (card.getZoneChangeCounter(game) == zcc) {
                        card.setFaceDown(true, game);
                    }
                }
            }
            return true;
        }
        return false;
    }
}
