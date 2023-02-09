package mage.cards.a;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.watchers.common.NumberOfTimesPermanentTargetedATurnWatcher;

import java.util.UUID;

/**
 * @author AustinYQM
 */
public final class AngelicCub extends CardImpl {

    public AngelicCub(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.ANGEL);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever Angelic Cub becomes the target of a spell or ability for the first time each turn, put a +1/+1 counter on it.
        this.addAbility(new AngelicCubAbility(), new NumberOfTimesPermanentTargetedATurnWatcher());
        // As long as Angelic Cub has three or more +1/+1 counters on it, it has flying.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(new GainAbilitySourceEffect(FlyingAbility.getInstance(), Duration.WhileOnBattlefield), new SourceHasCounterCondition(CounterType.P1P1, 3), "As long as {this} has three or more +1/+1 counters on it, it has flying.")));
    }

    private AngelicCub(final AngelicCub card) {
        super(card);
    }

    @Override
    public AngelicCub copy() {
        return new AngelicCub(this);
    }
}

class AngelicCubAbility extends TriggeredAbilityImpl {

    public AngelicCubAbility() {
        super(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.P1P1.createInstance()), false);
    }

    public AngelicCubAbility(final mage.cards.a.AngelicCubAbility ability) {
        super(ability);
    }

    @Override
    public mage.cards.a.AngelicCubAbility copy() {
        return new mage.cards.a.AngelicCubAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TARGETED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getTargetId().equals(this.getSourceId())) {
            Permanent permanent = game.getPermanent(event.getTargetId());
            if (permanent != null && permanent.isCreature(game)) {
                NumberOfTimesPermanentTargetedATurnWatcher watcher = game.getState().getWatcher(NumberOfTimesPermanentTargetedATurnWatcher.class);
                return watcher != null && watcher.notMoreThanOnceTargetedThisTurn(permanent, game);
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever {this} becomes the target of a spell or ability for the first time each turn, put a +1/+1 counter on it.";
    }
}