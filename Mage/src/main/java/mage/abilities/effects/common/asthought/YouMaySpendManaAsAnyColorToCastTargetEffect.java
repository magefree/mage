package mage.abilities.effects.common.asthought;

import mage.abilities.Ability;
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
 *
 * @author JayDi85
 */
public class YouMaySpendManaAsAnyColorToCastTargetEffect extends AsThoughEffectImpl implements AsThoughManaEffect {

    public YouMaySpendManaAsAnyColorToCastTargetEffect(Duration duration) {
        super(AsThoughEffectType.SPEND_OTHER_MANA, duration, Outcome.Benefit);
        this.staticText = "You may spend mana as though it were mana of any color to cast it";
    }

    public YouMaySpendManaAsAnyColorToCastTargetEffect(final YouMaySpendManaAsAnyColorToCastTargetEffect effect) {
        super(effect);
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
        objectId = CardUtil.getMainCardId(game, objectId); // for split cards
        FixedTarget fixedTarget = ((FixedTarget) getTargetPointer());
        return source.isControlledBy(affectedControllerId)
                && Objects.equals(objectId, fixedTarget.getTarget())
                && game.getState().getZoneChangeCounter(objectId) <= fixedTarget.getZoneChangeCounter() + 1
                && (game.getState().getZone(objectId) == Zone.STACK || game.getState().getZone(objectId) == Zone.EXILED);
    }

    @Override
    public ManaType getAsThoughManaType(ManaType manaType, ManaPoolItem mana, UUID affectedControllerId, Ability source, Game game) {
        return mana.getFirstAvailable();
    }
}