package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.game.Game;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.watchers.common.PlayerLostLifeWatcher;

import java.util.UUID;

/**
 * @author emerald000
 */
public final class FeastOnTheFallen extends CardImpl {

    public FeastOnTheFallen(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}");

        // At the beginning of each upkeep, if an opponent lost life last turn, put a +1/+1 counter on target creature you control. 
        Ability ability = new BeginningOfUpkeepTriggeredAbility(
                TargetController.ANY, new AddCountersTargetEffect(CounterType.P1P1.createInstance()), false
        ).withInterveningIf(FeastOnTheFallenCondition.instance);
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);
    }

    private FeastOnTheFallen(final FeastOnTheFallen card) {
        super(card);
    }

    @Override
    public FeastOnTheFallen copy() {
        return new FeastOnTheFallen(this);
    }
}

enum FeastOnTheFallenCondition implements Condition {

    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        PlayerLostLifeWatcher watcher = game.getState().getWatcher(PlayerLostLifeWatcher.class);
        return watcher != null
                && game
                .getOpponents(source.getControllerId())
                .stream()
                .mapToInt(watcher::getLifeLostLastTurn)
                .anyMatch(x -> x > 0);
    }

    @Override
    public String toString() {
        return "an opponent lost life last turn";
    }
}
