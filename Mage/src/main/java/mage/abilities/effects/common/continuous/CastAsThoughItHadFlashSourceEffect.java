
package mage.abilities.effects.common.continuous;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.constants.AsThoughEffectType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;

/**
 * @author LevelX2
 */

public class CastAsThoughItHadFlashSourceEffect extends AsThoughEffectImpl {

    public CastAsThoughItHadFlashSourceEffect(Duration duration) {
        super(AsThoughEffectType.CAST_AS_INSTANT, duration, Outcome.Benefit);
        staticText = "you may cast {this} as though it had flash";
    }

    protected CastAsThoughItHadFlashSourceEffect(final CastAsThoughItHadFlashSourceEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public CastAsThoughItHadFlashSourceEffect copy() {
        return new CastAsThoughItHadFlashSourceEffect(this);
    }

    @Override
    public boolean applies(UUID affectedSpellId, Ability source, UUID affectedControllerId, Game game) {
        return affectedSpellId.equals(source.getSourceId());
    }
}
