package mage.abilities.effects.common.asthought;

import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.cards.Card;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.game.Game;

import java.util.UUID;

/**
 * @author LevelX2
 */
public class PlayFromNotOwnHandZoneAllEffect extends AsThoughEffectImpl {

    private final FilterCard filter;
    private final Zone fromZone;
    private final boolean onlyOwnedCards;
    private final TargetController allowedCaster;

    public PlayFromNotOwnHandZoneAllEffect(FilterCard filter, Zone fromZone, boolean onlyOwnedCards, TargetController allowedCaster, Duration duration) {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, duration, Outcome.Benefit);
        this.filter = filter;
        this.fromZone = fromZone;
        this.onlyOwnedCards = onlyOwnedCards;
        this.allowedCaster = allowedCaster;
    }

    protected PlayFromNotOwnHandZoneAllEffect(final PlayFromNotOwnHandZoneAllEffect effect) {
        super(effect);
        this.filter = effect.filter;
        this.fromZone = effect.fromZone;
        this.onlyOwnedCards = effect.onlyOwnedCards;
        this.allowedCaster = effect.allowedCaster;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public PlayFromNotOwnHandZoneAllEffect copy() {
        return new PlayFromNotOwnHandZoneAllEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        throw new IllegalArgumentException("Wrong code usage: can't call applies method on empty affectedAbility");
    }
    @Override
    public boolean applies(UUID objectId, Ability affectedAbility, Ability source, Game game, UUID playerId) {
        Card card = game.getCard(objectId);
        if (card != null) {
            if (affectedAbility instanceof SpellAbility) {
                card = ((SpellAbility) affectedAbility).getCharacteristics(game);
            }
            switch (allowedCaster) {
                case YOU:
                    if (playerId != source.getControllerId()) {
                        return false;
                    }
                    break;
                case OPPONENT:
                    if (!game.getOpponents(source.getControllerId()).contains(playerId)) {
                        return false;
                    }
                    break;
            }
            return (!onlyOwnedCards || card.getOwnerId().equals(source.getControllerId()))
                    && filter.match(card, game)
                    && game.getState().getZone(card.getId()).match(fromZone);
        }
        return false;
    }
}
