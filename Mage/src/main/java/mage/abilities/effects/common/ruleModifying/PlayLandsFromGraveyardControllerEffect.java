package mage.abilities.effects.common.ruleModifying;

import mage.abilities.Ability;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.cards.Card;
import mage.constants.AsThoughEffectType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.filter.common.FilterLandCard;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author JayDi85
 */
public class PlayLandsFromGraveyardControllerEffect extends AsThoughEffectImpl {

    private final FilterCard filter;

    public PlayLandsFromGraveyardControllerEffect() {
        this(new FilterLandCard("lands"));
    }

    public PlayLandsFromGraveyardControllerEffect(FilterCard filter) {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.WhileOnBattlefield, Outcome.Benefit);
        this.filter = filter;
        staticText = "You may play " + filter.getMessage() + " from your graveyard";
    }

    public PlayLandsFromGraveyardControllerEffect(final PlayLandsFromGraveyardControllerEffect effect) {
        super(effect);
        this.filter = effect.filter;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }


    @Override
    public PlayLandsFromGraveyardControllerEffect copy() {
        return new PlayLandsFromGraveyardControllerEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        return applies(objectId, null, source, game, affectedControllerId);
    }

    @Override
    public boolean applies(UUID objectId, Ability affectedAbility, Ability source, Game game, UUID playerId) {
        Card cardToCheck = game.getCard(objectId);
        objectId = CardUtil.getMainCardId(game, objectId); // for split cards
        if (cardToCheck == null) {
            return false;
        }

        Player player = game.getPlayer(cardToCheck.getOwnerId());
        if (player == null) {
            return false;
        }

        UUID needCardId = objectId;
        if (player.getGraveyard().getCards(game).stream().noneMatch(c -> c.getId().equals(needCardId))) {
            return false;
        }

        return playerId.equals(source.getControllerId())
                && cardToCheck.isOwnedBy(source.getControllerId())
                && (!cardToCheck.getManaCost().isEmpty() || cardToCheck.isLand())
                && filter.match(cardToCheck, game);
    }
}
