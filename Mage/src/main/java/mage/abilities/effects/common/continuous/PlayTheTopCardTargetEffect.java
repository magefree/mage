package mage.abilities.effects.common.continuous;

import mage.constants.TargetController;

/**
 * @author JayDi85
 */
public class PlayTheTopCardTargetEffect extends PlayTheTopCardEffect {

    public PlayTheTopCardTargetEffect() {
        super(TargetController.SOURCE_TARGETS);
    }

    public PlayTheTopCardTargetEffect(final PlayTheTopCardTargetEffect effect) {
        super(effect);
    }

    @Override
    public PlayTheTopCardTargetEffect copy() {
        return new PlayTheTopCardTargetEffect(this);
    }
}
