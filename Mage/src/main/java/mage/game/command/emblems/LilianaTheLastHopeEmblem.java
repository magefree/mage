package mage.game.command.emblems;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.command.Emblem;
import mage.game.permanent.token.ZombieToken;

import java.util.Arrays;

/**
 * @author spjspj
 */
public final class LilianaTheLastHopeEmblem extends Emblem {

    // "At the beginning of your end step, create X 2/2 black Zombie creature tokens, where X is two plus the number of Zombies you control."
    public LilianaTheLastHopeEmblem() {
        this.setName("Emblem Liliana");
        Ability ability = new BeginningOfEndStepTriggeredAbility(Zone.COMMAND, new CreateTokenEffect(new ZombieToken(), new LilianaZombiesCount()),
                TargetController.YOU, null, false);
        this.getAbilities().add(ability);

        availableImageSetCodes = Arrays.asList("EMN", "MED");
    }
}

class LilianaZombiesCount implements DynamicValue {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent();

    static {
        filter.add(SubType.ZOMBIE.getPredicate());
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        int amount = game.getBattlefield().countAll(filter, sourceAbility.getControllerId(), game) + 2;
        return amount;
    }

    @Override
    public LilianaZombiesCount copy() {
        return new LilianaZombiesCount();
    }

    @Override
    public String toString() {
        return "X";
    }

    @Override
    public String getMessage() {
        return "two plus the number of Zombies you control";
    }
}
