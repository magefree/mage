package mage.abilities.effects.common.asthought;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.PlayLandAbility;
import mage.abilities.condition.Condition;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.cards.Card;
import mage.constants.AsThoughEffectType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * Allow Play or Cast of a card from current zone.
 * Will be discarded on any card movements or blinks.
 * <p>
 * Recommends to use combo effects like CardUtil.makeCardPlayable or CardUtil.makeCardCastable
 * <p>
 * Affected to all card's parts
 *
 * @author JayDi85, Susucr
 */
public class CanPlayCardControllerEffect extends AsThoughEffectImpl {

    protected final MageObjectReference mor;
    protected final UUID playerId;
    protected final Condition condition;
    protected final boolean withoutPaying;
    protected final boolean isCastNotPlay;

    public CanPlayCardControllerEffect(Game game, UUID cardId, int cardZCC, Duration duration) {
        this(game, cardId, cardZCC, duration, null, null, false, false);
    }

    public CanPlayCardControllerEffect(
        Game game, UUID cardId, int cardZCC, Duration duration,
        UUID playerId, Condition conditionOnSpell, boolean withoutPaying,
        boolean isCastNotPlay
    ) {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, duration, Outcome.Benefit);
        this.staticText = "You may play those card"; // TODO: we can do better!
        this.mor = new MageObjectReference(cardId, cardZCC, game);
        this.playerId = playerId;
        this.withoutPaying = withoutPaying;
        this.condition = conditionOnSpell;
        this.isCastNotPlay = isCastNotPlay;
    }

    public CanPlayCardControllerEffect(final CanPlayCardControllerEffect effect) {
        super(effect);
        this.mor = effect.mor;
        this.playerId = effect.playerId;
        this.condition = effect.condition;
        this.withoutPaying = effect.withoutPaying;
        this.isCastNotPlay = effect.isCastNotPlay;
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
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        return applies(objectId, null, source, game, affectedControllerId);
    }

    @Override
    public boolean applies(UUID objectId, Ability affectedAbility, Ability source, Game game, UUID affectedPlayerId) {
        Card card = mor.getCard(game);
        if (card == null) {
            discard();
            return false;
        }

        if (condition != null && !condition.apply(game, affectedAbility)) {
            return false;
        }

        if (!affectedAbility.getAbilityType().isPlayCardAbility()){
            return false;
        }

        if(isCastNotPlay && affectedAbility instanceof PlayLandAbility) {
            return false;
        }

        UUID objectIdToCast = CardUtil.getMainCardId(game, objectId); // to handle multiple castables faces
        if(!mor.refersTo(objectIdToCast, game)) {
            return false;
        }

        if(playerId == null
            ? !source.isControlledBy(affectedPlayerId)
            : !playerId.equals(affectedPlayerId)){
            return false;
        }

        // We are allowed to use the ability after all those checks.
        if (withoutPaying) {
            allowCardToPlayWithoutMana(objectIdToCast, affectedAbility, affectedPlayerId, game);
        }
        return true;
    }
}
