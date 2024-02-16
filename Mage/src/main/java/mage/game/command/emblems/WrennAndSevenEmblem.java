package mage.game.command.emblems;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.MaximumHandSizeControllerEffect;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.game.command.Emblem;

/**
 * @author TheElk801
 */
public final class WrennAndSevenEmblem extends Emblem {

    // You get an emblem with "You have no maximum hand size."
    public WrennAndSevenEmblem() {
        super("Emblem Wrenn");
        this.getAbilities().add(new SimpleStaticAbility(Zone.COMMAND, new MaximumHandSizeControllerEffect(
                Integer.MAX_VALUE, Duration.WhileOnBattlefield, MaximumHandSizeControllerEffect.HandSizeModification.SET
        )));
    }

    private WrennAndSevenEmblem(final WrennAndSevenEmblem card) {
        super(card);
    }

    @Override
    public WrennAndSevenEmblem copy() {
        return new WrennAndSevenEmblem(this);
    }
}
