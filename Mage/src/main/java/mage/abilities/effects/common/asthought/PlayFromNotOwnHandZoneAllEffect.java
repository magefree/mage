package mage.abilities.effects.common.asthought;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.cards.Card;
import mage.constants.AsThoughEffectType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;

/**
 *
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

    public PlayFromNotOwnHandZoneAllEffect(final PlayFromNotOwnHandZoneAllEffect effect) {
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
        Card card = game.getCard(objectId);
        if (card != null) {
            switch (allowedCaster) {
                case YOU:
                    if (affectedControllerId != source.getControllerId()) {
                        return false;
                    }
                    break;
                case OPPONENT:
                    if (!game.getOpponents(source.getControllerId()).contains(affectedControllerId)) {
                        return false;
                    }
                    break;
            }
            return !onlyOwnedCards || card.getOwnerId().equals(source.getControllerId())
                    && filter.match(card, game)
                    && game.getState().getZone(card.getId()).match(fromZone);
        }
        return false;
    }
}
