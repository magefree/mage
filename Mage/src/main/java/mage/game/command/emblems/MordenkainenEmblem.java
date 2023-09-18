package mage.game.command.emblems;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.MaximumHandSizeControllerEffect;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.game.command.Emblem;

/**
 * @author weirddan455
 */
public final class MordenkainenEmblem extends Emblem {

    // You get an emblem with "You have no maximum hand size."
    public MordenkainenEmblem() {
        super("Emblem Mordenkainen");
        this.getAbilities().add(new SimpleStaticAbility(Zone.COMMAND, new MaximumHandSizeControllerEffect(
                Integer.MAX_VALUE, Duration.WhileOnBattlefield, MaximumHandSizeControllerEffect.HandSizeModification.SET
        )));
    }

    private MordenkainenEmblem(final MordenkainenEmblem card) {
        super(card);
    }

    @Override
    public MordenkainenEmblem copy() {
        return new MordenkainenEmblem(this);
    }
}
