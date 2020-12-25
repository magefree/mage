package mage.abilities.effects.common.continuous;

import mage.abilities.Ability;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.cards.Card;
import mage.constants.AsThoughEffectType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author nantuko
 */
public class PlayTheTopCardEffect extends AsThoughEffectImpl {

    private final FilterCard filter;

    public PlayTheTopCardEffect() {
        this(StaticFilters.FILTER_CARD);
        staticText = "You may play lands and cast spells from the top of your library";
    }

    public PlayTheTopCardEffect(FilterCard filter) {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.WhileOnBattlefield, Outcome.Benefit);
        this.filter = filter;
        staticText = "You may " + filter.getMessage() + " from the top of your library";
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
        return applies(objectId, null, source, game, affectedControllerId);
    }

    @Override
    public boolean applies(UUID objectId, Ability affectedAbility, Ability source, Game game, UUID playerId) {
        Card cardToCheck = game.getCard(objectId);
        objectId = CardUtil.getMainCardId(game, objectId); // for split cards

        if (cardToCheck != null
                && playerId.equals(source.getControllerId())
                && cardToCheck.isOwnedBy(source.getControllerId())
                && (!cardToCheck.getManaCost().isEmpty() || cardToCheck.isLand())
                && filter.match(cardToCheck, source.getSourceId(), source.getControllerId(), game)) {
            Player player = game.getPlayer(cardToCheck.getOwnerId());

            UUID needCardID = player.getLibrary().getFromTop(game) == null ? null : player.getLibrary().getFromTop(game).getId();
            return objectId.equals(needCardID);
        }
        return false;
    }

}
