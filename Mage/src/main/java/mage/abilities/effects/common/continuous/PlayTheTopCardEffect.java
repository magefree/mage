
package mage.abilities.effects.common.continuous;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.cards.Card;
import mage.constants.AsThoughEffectType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;

/**
 * @author nantuko
 */
public class PlayTheTopCardEffect extends AsThoughEffectImpl {

    private FilterCard filter;

    public PlayTheTopCardEffect() {
        this(new FilterCard());
        staticText = "You may play the top card of your library";
    }

    public PlayTheTopCardEffect(FilterCard filter) {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.WhileOnBattlefield, Outcome.Benefit);
        this.filter = filter;
        staticText = "You may play the top card of your library if it's a " + filter.getMessage();
    }

    public PlayTheTopCardEffect(final PlayTheTopCardEffect effect) {
        super(effect);
        this.filter = effect.filter;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public PlayTheTopCardEffect copy() {
        return new PlayTheTopCardEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        Card cardOnTop = game.getCard(objectId);
        if (cardOnTop != null
                && affectedControllerId.equals(source.getControllerId())
                && cardOnTop.isOwnedBy(source.getControllerId())
                && (!cardOnTop.getManaCost().isEmpty() || cardOnTop.isLand())
                && filter.match(cardOnTop, game)) {
            Player player = game.getPlayer(cardOnTop.getOwnerId());
            if (player != null && cardOnTop.equals(player.getLibrary().getFromTop(game))) {
                return true;
            }
        }
        return false;
    }

}
