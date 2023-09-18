package mage.abilities.effects.common.continuous;

import mage.constants.Duration;
import mage.constants.TargetController;

/**
 * @author JayDi85
 */
public class LookAtTopCardOfLibraryAnyTimeTargetEffect extends LookAtTopCardOfLibraryAnyTimeEffect {

    public LookAtTopCardOfLibraryAnyTimeTargetEffect(Duration duration) {
        super(TargetController.SOURCE_TARGETS, duration);
    }

    private LookAtTopCardOfLibraryAnyTimeTargetEffect(final LookAtTopCardOfLibraryAnyTimeTargetEffect effect) {
        super(effect);
    }

    @Override
    public LookAtTopCardOfLibraryAnyTimeTargetEffect copy() {
        return new LookAtTopCardOfLibraryAnyTimeTargetEffect(this);
    }
}
