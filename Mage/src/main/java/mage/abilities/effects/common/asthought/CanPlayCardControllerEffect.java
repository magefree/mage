package mage.abilities.effects.common.asthought;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.constants.AsThoughEffectType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * Play card from current zone. Will be discarded on any card movements or blinks.
 * <p>
 * Recommends to use combo effects from CardUtil.makeCardPlayableAndSpendManaAsAnyColor instead signle effect
 * <p>
 * Affected to all card's parts
 *
 * @author JayDi85
 */
public class CanPlayCardControllerEffect extends AsThoughEffectImpl {

    private final MageObjectReference mor;
    private final UUID playerId;
    private final Condition condition;

    public CanPlayCardControllerEffect(Game game, UUID cardId, int cardZCC, Duration duration) {
        this(game, cardId, cardZCC, duration, null, null);
    }

    public CanPlayCardControllerEffect(Game game, UUID cardId, int cardZCC, Duration duration, UUID playerId, Condition condition) {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, duration, Outcome.Benefit);
        this.staticText = "You may play those card";
        this.mor = new MageObjectReference(cardId, cardZCC, game);
        this.playerId = playerId;
        this.condition = condition;
    }

    public CanPlayCardControllerEffect(final CanPlayCardControllerEffect effect) {
        super(effect);
        this.mor = effect.mor;
        this.playerId = effect.playerId;
        this.condition = effect.condition;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public CanPlayCardControllerEffect copy() {
        return new CanPlayCardControllerEffect(this);
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        if (mor.getCard(game) == null) {
            discard();
            return false;
        }

        if (condition != null && !condition.apply(game, source)) {
            return false;
        }

        UUID objectIdToCast = CardUtil.getMainCardId(game, sourceId); // affected to all card's parts
        return mor.refersTo(objectIdToCast, game)
                && (playerId == null ? source.isControlledBy(affectedControllerId) : playerId.equals(affectedControllerId));
    }
}
