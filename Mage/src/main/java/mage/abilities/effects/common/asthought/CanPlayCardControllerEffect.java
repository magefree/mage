package mage.abilities.effects.common.asthought;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.constants.AsThoughEffectType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;

import java.util.UUID;

/**
 * Play card from current zone. Will be discarded on any card movements or blinks.
 * <p>
 * Recommends to use combo effects from CardUtil.makeCardPlayableAndSpendManaAsAnyColor instead signle effect
 *
 * @author JayDi85
 */
public class CanPlayCardControllerEffect extends AsThoughEffectImpl {

    protected final MageObjectReference mor;

    public CanPlayCardControllerEffect(Game game, UUID cardId, int cardZCC, Duration duration) {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, duration, Outcome.Benefit);
        this.staticText = "You may play those card";
        this.mor = new MageObjectReference(cardId, cardZCC, game);
    }

    public CanPlayCardControllerEffect(final CanPlayCardControllerEffect effect) {
        super(effect);
        this.mor = effect.mor;
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
        return mor.refersTo(sourceId, game) && source.isControlledBy(affectedControllerId);
    }
}