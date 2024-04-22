package mage.game.command.emblems;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.command.Emblem;
import mage.game.permanent.token.SoldierToken;

public final class BasriKetEmblem extends Emblem {
    /**
     * Emblem with "At the beginning of combat on your turn, create a 1/1 white Soldier creature token, then put a +1/+1 counter on each creature you control."
     */

    public BasriKetEmblem() {
        super("Emblem Basri");
        Ability ability = new BeginningOfCombatTriggeredAbility(
                Zone.COMMAND,
                new CreateTokenEffect(new SoldierToken()),
                TargetController.YOU, false, false);
        ability.addEffect(
                new AddCountersAllEffect(CounterType.P1P1.createInstance(), StaticFilters.FILTER_PERMANENT_CREATURE_CONTROLLED)
                        .setText(", then put a +1/+1 counter on each creature you control")
        );
        this.getAbilities().add(ability);
    }

    private BasriKetEmblem(final BasriKetEmblem card) {
        super(card);
    }

    @Override
    public BasriKetEmblem copy() {
        return new BasriKetEmblem(this);
    }
}
