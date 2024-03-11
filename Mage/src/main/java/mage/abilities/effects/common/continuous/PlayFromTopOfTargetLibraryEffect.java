package mage.abilities.effects.common.continuous;

import mage.constants.TargetController;

/**
 * @author JayDi85
 */
public class PlayFromTopOfTargetLibraryEffect extends PlayFromTopOfLibraryEffect {

    public PlayFromTopOfTargetLibraryEffect() {
        super(TargetController.SOURCE_TARGETS);
    }

    protected PlayFromTopOfTargetLibraryEffect(final PlayFromTopOfTargetLibraryEffect effect) {
        super(effect);
    }

    @Override
    public PlayFromTopOfTargetLibraryEffect copy() {
        return new PlayFromTopOfTargetLibraryEffect(this);
    }
}
