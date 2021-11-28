package mage.abilities.effects.common.asthought;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.AsThoughManaEffect;
import mage.constants.*;
import mage.game.Game;
import mage.players.ManaPoolItem;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

import java.util.Objects;
import java.util.UUID;

/**
 * Spend mana as any color to cast targeted card. Will not affected after any card movements or blinks.
 * Affects to all card's parts
 *
 * @author JayDi85
 */
public class YouMaySpendManaAsAnyColorToCastTargetEffect extends AsThoughEffectImpl implements AsThoughManaEffect {

    private final UUID playerId;
    private final Condition condition;

    public YouMaySpendManaAsAnyColorToCastTargetEffect(Duration duration) {
        this(duration, null, null);
    }

    public YouMaySpendManaAsAnyColorToCastTargetEffect(Duration duration, UUID playerId, Condition condition) {
        super(AsThoughEffectType.SPEND_OTHER_MANA, duration, Outcome.Benefit);
        this.staticText = "You may spend mana as though it were mana of any color to cast it";
        this.playerId = playerId;
        this.condition = condition;
    }

    public YouMaySpendManaAsAnyColorToCastTargetEffect(final YouMaySpendManaAsAnyColorToCastTargetEffect effect) {
        super(effect);
        this.playerId = effect.playerId;
        this.condition = effect.condition;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public YouMaySpendManaAsAnyColorToCastTargetEffect copy() {
        return new YouMaySpendManaAsAnyColorToCastTargetEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        if (condition != null && !condition.apply(game, source)) {
            return false;
        }

        objectId = CardUtil.getMainCardId(game, objectId); // for split cards
        FixedTarget fixedTarget = ((FixedTarget) getTargetPointer());
        UUID targetId = CardUtil.getMainCardId(game, fixedTarget.getTarget()); // Affects to all card's parts (example: Hostage Taker exile mdf card)
        return (playerId == null ? source.isControlledBy(affectedControllerId) : playerId.equals(affectedControllerId))
                && Objects.equals(objectId, targetId)
                && game.getState().getZoneChangeCounter(objectId) <= fixedTarget.getZoneChangeCounter() + 1
                && (game.getState().getZone(objectId) == Zone.STACK || game.getState().getZone(objectId) == Zone.EXILED);
    }

    @Override
    public ManaType getAsThoughManaType(ManaType manaType, ManaPoolItem mana, UUID affectedControllerId, Ability source, Game game) {
        return mana.getFirstAvailable();
    }
}