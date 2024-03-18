package mage.game.command.emblems;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.command.Emblem;
import mage.game.permanent.token.ZombieToken;

/**
 * @author spjspj
 */
public final class LilianaTheLastHopeEmblem extends Emblem {

    // "At the beginning of your end step, create X 2/2 black Zombie creature tokens, where X is two plus the number of Zombies you control."
    public LilianaTheLastHopeEmblem() {
        super("Emblem Liliana");
        Ability ability = new BeginningOfEndStepTriggeredAbility(Zone.COMMAND, new CreateTokenEffect(new ZombieToken(), LilianaZombiesCount.instance),
                TargetController.YOU, null, false);
        this.getAbilities().add(ability);
    }

    private LilianaTheLastHopeEmblem(final LilianaTheLastHopeEmblem card) {
        super(card);
    }

    @Override
    public LilianaTheLastHopeEmblem copy() {
        return new LilianaTheLastHopeEmblem(this);
    }
}

enum LilianaZombiesCount implements DynamicValue {
    instance;

    private static final FilterControlledPermanent filter = new FilterControlledPermanent(SubType.ZOMBIE);

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return game.getBattlefield().countAll(filter, sourceAbility.getControllerId(), game) + 2;
    }

    @Override
    public LilianaZombiesCount copy() {
        return this;
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
