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

    protected final MageObjectReference mor;
    protected final UUID playerId;
    protected final Condition condition;

    public CanPlayCardControllerEffect(Game game, UUID cardId, int cardZCC, boolean useCastSpellOnly, Duration duration) {
        this(game, cardId, cardZCC, useCastSpellOnly, duration, null, null);
    }

    public CanPlayCardControllerEffect(Game game, UUID cardId, int cardZCC, boolean useCastSpellOnly, Duration duration, UUID playerId, Condition condition) {
        super(useCastSpellOnly ? AsThoughEffectType.CAST_FROM_NOT_OWN_HAND_ZONE : AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, duration, Outcome.Benefit);
        this.staticText = useCastSpellOnly ? "You may cast this card" : "You may play this card";
        this.mor = new MageObjectReference(cardId, cardZCC, game);
        this.playerId = playerId;
        this.condition = condition;
    }

    protected CanPlayCardControllerEffect(final CanPlayCardControllerEffect effect) {
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
    public boolean applies(UUID objectId, Ability ability, UUID affectedControllerId, Game game) {
        if (mor.getCard(game) == null) {
            discard();
            return false;
        }

        if (condition != null && !condition.apply(game, ability)) {
            return false;
        }

        UUID objectIdToCast = CardUtil.getMainCardId(game, objectId); // affected to all card's parts
        return mor.refersTo(objectIdToCast, game)
                && (playerId == null ? ability.isControlledBy(affectedControllerId) : playerId.equals(affectedControllerId));
    }
}
