
package mage.cards.l;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.token.AngelToken;
import mage.watchers.common.PlayerLostLifeWatcher;

/**
 *
 * @author jeffwadsworth
 */
public final class LuminarchAscension extends CardImpl {

    private String rule = "At the beginning of each opponent's end step, if you didn't lose life this turn, you may put a quest counter on {this}. (Damage causes loss of life.)";

    public LuminarchAscension(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}");

        // At the beginning of each opponent's end step, if you didn't lose life this turn, you may put a quest counter on Luminarch Ascension.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(new LuminarchAscensionTriggeredAbility(), YouLostNoLifeThisTurnCondition.instance, rule));

        // {1}{W}: Create a 4/4 white Angel creature token with flying. Activate this ability only if Luminarch Ascension has four or more quest counters on it.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new AngelToken()), new ManaCostsImpl("{1}{W}"));
        ability.addCost(new SourceHasCountersCost(4, CounterType.QUEST));
        this.addAbility(ability);
    }

    public LuminarchAscension(final LuminarchAscension card) {
        super(card);
    }

    @Override
    public LuminarchAscension copy() {
        return new LuminarchAscension(this);
    }
}

class LuminarchAscensionTriggeredAbility extends TriggeredAbilityImpl {

    public LuminarchAscensionTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.QUEST.createInstance()), true);
    }

    public LuminarchAscensionTriggeredAbility(LuminarchAscensionTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public LuminarchAscensionTriggeredAbility copy() {
        return new LuminarchAscensionTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.END_TURN_STEP_PRE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return game.getOpponents(controllerId).contains(event.getPlayerId());
    }
}

class SourceHasCountersCost extends CostImpl {

    private final int counters;
    private final CounterType counterType;

    public SourceHasCountersCost(int counters, CounterType counterType) {
        this.counters = counters;
        this.counterType = counterType;
        this.text = "Activate this ability only if Luminarch Ascension has 4 or more quest counters on it";
    }

    public SourceHasCountersCost(final SourceHasCountersCost cost) {
        super(cost);
        this.counters = cost.counters;
        this.counterType = cost.counterType;
    }

    @Override
    public boolean canPay(Ability ability, UUID sourceId, UUID controllerId, Game game) {
        return (game.getPermanent(sourceId).getCounters(game).getCount(counterType) >= counters);
    }

    @Override
    public boolean pay(Ability ability, Game game, UUID sourceId, UUID controllerId, boolean noMana, Cost costToPay) {
        this.paid = true;
        return paid;
    }

    @Override
    public SourceHasCountersCost copy() {
        return new SourceHasCountersCost(this);
    }
}

enum YouLostNoLifeThisTurnCondition implements Condition {

    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        PlayerLostLifeWatcher watcher = (PlayerLostLifeWatcher) game.getState().getWatchers().get(PlayerLostLifeWatcher.class.getSimpleName());
        if (watcher != null) {
            return (watcher.getLiveLost(source.getControllerId()) == 0);
        }
        return false;
    }
}
