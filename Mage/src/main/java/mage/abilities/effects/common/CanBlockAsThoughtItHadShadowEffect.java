package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.constants.AsThoughEffectType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;

import java.util.UUID;

public class CanBlockAsThoughtItHadShadowEffect extends AsThoughEffectImpl {

    public CanBlockAsThoughtItHadShadowEffect(Duration duration) {
        super(AsThoughEffectType.BLOCK_SHADOW, duration, Outcome.Benefit);
        staticText = "{this} can block creatures with shadow as though {this} had shadow";
    }

    public CanBlockAsThoughtItHadShadowEffect(final CanBlockAsThoughtItHadShadowEffect effect) {
        super(effect);
    }

    @Override
    public CanBlockAsThoughtItHadShadowEffect copy() {
        return new CanBlockAsThoughtItHadShadowEffect(this);
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        return sourceId.equals(source.getSourceId());
    }

}
