
package mage.cards.f;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.watchers.common.PlayerLostLifeWatcher;

/**
 *
 * @author emerald000
 */
public final class FeastOnTheFallen extends CardImpl {

    public FeastOnTheFallen(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{B}");


        // At the beginning of each upkeep, if an opponent lost life last turn, put a +1/+1 counter on target creature you control. 
        Ability ability = new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD,
                        new AddCountersTargetEffect(CounterType.P1P1.createInstance()),
                        TargetController.ANY, false),
                FeastOnTheFallenCondition.instance,
                "At the beginning of each upkeep, if an opponent lost life last turn, put a +1/+1 counter on target creature you control.");
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
        if (watcher != null) {
            for (UUID opponentId : game.getOpponents(source.getControllerId())) {
                if (watcher.getLifeLostLastTurn(opponentId) > 0) {
                    return true;
                }
            }
        }
        return false;
    }
}
