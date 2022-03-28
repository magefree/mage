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
        this.staticText = "You may play " + filter.getMessage() + " from your graveyard";
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
        // current card's part
        Card cardToCheck = game.getCard(objectId);
        if (cardToCheck == null) {
            return false;
        }

        // must be you
        if (!affectedControllerId.equals(source.getControllerId())) {
            return false;
        }

        // must be your card
        Player player = game.getPlayer(cardToCheck.getOwnerId());
        if (player == null || !player.getId().equals(affectedControllerId)) {
            return false;
        }

        // must be from your graveyard
        UUID needCardId = cardToCheck.getMainCard().getId();
        if (player.getGraveyard().getCards(game).stream().noneMatch(c -> c.getId().equals(needCardId))) {
            return false;
        }

        // can't cast without mana cost
        if (!cardToCheck.isLand(game) && cardToCheck.getManaCost().isEmpty()) {
            return false;
        }

        // must be correct card
        return filter.match(cardToCheck, affectedControllerId, source, game);
    }
}
