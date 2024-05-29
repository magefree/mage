package mage.game.command.emblems;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.MaximumHandSizeControllerEffect;
import mage.abilities.effects.common.continuous.MaximumHandSizeControllerEffect.HandSizeModification;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.game.command.Emblem;

/**
 * @author Susucr
 */
public final class TamiyoSeasonedScholarEmblem extends Emblem {

    /**
     * Emblem with "You have no maximum hand size"
     */

    public TamiyoSeasonedScholarEmblem() {
        super("Emblem Tamiyo");
        this.getAbilities().add(new SimpleStaticAbility(Zone.COMMAND, new MaximumHandSizeControllerEffect(
                Integer.MAX_VALUE, Duration.Custom, HandSizeModification.SET
        )));
    }

    private TamiyoSeasonedScholarEmblem(final TamiyoSeasonedScholarEmblem card) {
        super(card);
    }

    @Override
    public TamiyoSeasonedScholarEmblem copy() {
        return new TamiyoSeasonedScholarEmblem(this);
    }
}
