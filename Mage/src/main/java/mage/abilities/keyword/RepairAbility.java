package mage.abilities.keyword;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.asthought.PlayFromNotOwnHandZoneTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.counter.RemoveCounterSourceEffect;
import mage.cards.Card;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author Styxo, Merlingilb
 */
public class RepairAbility extends DiesSourceTriggeredAbility {

    private final String ruleText;

    public RepairAbility(int count) {
        super(new AddCountersSourceEffect(
                CounterType.REPAIR.createInstance(), StaticValue.get(count), false, true));
        addSubAbility(new RepairBeginningOfUpkeepInterveningIfTriggeredAbility());
        addSubAbility(new RepairCastFromGraveyardTriggeredAbility());

        ruleText = "Repair " + count + " <i>(When this creature dies, put " + count
                + " repair counters on it. At the beginning of your upkeep, remove a repair counter. "
                + "Whenever the last is removed, you may cast it from graveyard until end of turn.)</i>";

    }

    public RepairAbility(final RepairAbility ability) {
        super(ability);
        this.ruleText = ability.ruleText;
    }

    @Override
    public String getRule() {
        return ruleText;
    }

    @Override
    public RepairAbility copy() {
        return new RepairAbility(this);
    }
}

class RepairedCastFromGraveyardEffect extends OneShotEffect {

    public RepairedCastFromGraveyardEffect() {
        super(Outcome.Benefit);
    }

    private RepairedCastFromGraveyardEffect(final RepairedCastFromGraveyardEffect effect) {
        super(effect);
    }

    @Override
    public RepairedCastFromGraveyardEffect copy() {
        return new RepairedCastFromGraveyardEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Card card = game.getCard(source.getSourceId());
        if (card == null) {
            return false;
        }
        game.addEffect(new PlayFromNotOwnHandZoneTargetEffect(Zone.GRAVEYARD, Duration.UntilYourNextEndStep)
                .setTargetPointer(new FixedTarget(card, game)), source);
        return true;
    }
}

class RepairCastFromGraveyardTriggeredAbility extends TriggeredAbilityImpl {

    public RepairCastFromGraveyardTriggeredAbility() {
        super(Zone.GRAVEYARD, new RepairedCastFromGraveyardEffect());
        setRuleVisible(false);
    }

    public RepairCastFromGraveyardTriggeredAbility(RepairCastFromGraveyardTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.COUNTER_REMOVED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getTargetId().equals(getSourceId())) {
            Card card = game.getCard(getSourceId());
            return card != null && game.getState().getZone(card.getId()) == Zone.GRAVEYARD
                    && card.getCounters(game).getCount(CounterType.REPAIR) == 0;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever the last repair counter is removed, you may cast {this} from your graveyard until end of turn";
    }

    @Override
    public RepairCastFromGraveyardTriggeredAbility copy() {
        return new RepairCastFromGraveyardTriggeredAbility(this);
    }
}

class RepairBeginningOfUpkeepInterveningIfTriggeredAbility extends ConditionalInterveningIfTriggeredAbility {

    public RepairBeginningOfUpkeepInterveningIfTriggeredAbility() {
        super(new BeginningOfUpkeepTriggeredAbility(Zone.GRAVEYARD,
                new RemoveCounterSourceEffect(CounterType.REPAIR.createInstance()),TargetController.YOU, false),
                new SourceHasCounterCondition(CounterType.REPAIR),
                "At the beginning of your upkeep, remove a repair counter from {this}");
        this.setRuleVisible(false);
    }

    public RepairBeginningOfUpkeepInterveningIfTriggeredAbility(final RepairBeginningOfUpkeepInterveningIfTriggeredAbility effect) {
        super(effect);
    }

    @Override
    public RepairBeginningOfUpkeepInterveningIfTriggeredAbility copy() {
        return new RepairBeginningOfUpkeepInterveningIfTriggeredAbility(this);
    }
}
