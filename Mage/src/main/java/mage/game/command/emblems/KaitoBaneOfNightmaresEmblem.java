package mage.game.command.emblems;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.command.Emblem;

/**
 * @author jackd149
 */
public final class KaitoBaneOfNightmaresEmblem extends Emblem {

    public KaitoBaneOfNightmaresEmblem() {
        super("Emblem Kaito");
        FilterCreaturePermanent filter = new FilterCreaturePermanent(SubType.NINJA, "Ninjas you control");
        this.getAbilities().add(new SimpleStaticAbility(Zone.COMMAND, new BoostControlledEffect(1, 1, Duration.EndOfGame, filter, false)));
    }

    private KaitoBaneOfNightmaresEmblem(final KaitoBaneOfNightmaresEmblem card) {
        super(card);
    }

    @Override
    public KaitoBaneOfNightmaresEmblem copy() {
        return new KaitoBaneOfNightmaresEmblem(this);
    }
    
}
