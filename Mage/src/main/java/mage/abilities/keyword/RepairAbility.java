package mage.abilities.keyword;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.DiesSourceTriggeredAbility;
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
 * @author Styxo, Merlingilb
 */
public class RepairAbility extends DiesSourceTriggeredAbility {

    private final int count;

    public RepairAbility(int count) {
        super(new AddCountersSourceEffect(
                CounterType.REPAIR.createInstance(), StaticValue.get(count), false, true));
        addSubAbility(new BeginningOfUpkeepTriggeredAbility(Zone.GRAVEYARD,
                new RemoveCounterSourceEffect(CounterType.REPAIR.createInstance()), TargetController.YOU, false)
                .setRuleVisible(false));
        addSubAbility(new RepairCastFromGraveyardTriggeredAbility());
        this.count = count;
    }

    protected RepairAbility(final RepairAbility ability) {
        super(ability);
        this.count = ability.count;
    }

    @Override
    public String getRule() {
        return "Repair " + count + " <i>(When this creature dies, put " + count
                + " repair counters on it. At the beginning of your upkeep, remove a repair counter. "
                + "Whenever the last is removed, you may cast it from graveyard until end of turn.)</i>";
    }

    @Override
    public RepairAbility copy() {
        return new RepairAbility(this);
    }
}

class RepairedCastFromGraveyardEffect extends OneShotEffect {

    RepairedCastFromGraveyardEffect() {
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
        Card card = source.getSourceCardIfItStillExists(game);
        if (controller == null || card == null) {
            return false;
        }
        game.addEffect(new PlayFromNotOwnHandZoneTargetEffect(Zone.GRAVEYARD, Duration.UntilYourNextEndStep)
                .setTargetPointer(new FixedTarget(card, game)), source);
        return true;
    }
}

class RepairCastFromGraveyardTriggeredAbility extends TriggeredAbilityImpl {

    RepairCastFromGraveyardTriggeredAbility() {
        super(Zone.GRAVEYARD, new RepairedCastFromGraveyardEffect());
        setRuleVisible(false);
    }

    private RepairCastFromGraveyardTriggeredAbility(RepairCastFromGraveyardTriggeredAbility ability) {
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
                    && event.getData().equals(CounterType.REPAIR.getName())
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
