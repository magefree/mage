
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.SetPowerToughnessSourceEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.SuspendAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class FungalBehemoth extends CardImpl {

    public FungalBehemoth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");
        this.subtype.add(SubType.FUNGUS);

        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Fungal Behemoth's power and toughness are each equal to the number of +1/+1 counters on creatures you control.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetPowerToughnessSourceEffect(new P1P1CountersOnControlledCreaturesCount(), Duration.EndOfGame)));

        // Suspend X-{X}{G}{G}. X can't be 0.
        this.addAbility(new SuspendAbility(Integer.MAX_VALUE, new ManaCostsImpl<>("{G}{G}"), this, true));

        // Whenever a time counter is removed from Fungal Behemoth while it's exiled, you may put a +1/+1 counter on target creature.
        this.addAbility(new FungalBehemothTriggeredAbility());
    }

    private FungalBehemoth(final FungalBehemoth card) {
        super(card);
    }

    @Override
    public FungalBehemoth copy() {
        return new FungalBehemoth(this);
    }
}

class FungalBehemothTriggeredAbility extends TriggeredAbilityImpl {

    public FungalBehemothTriggeredAbility() {
        super(Zone.EXILED, new AddCountersTargetEffect(CounterType.P1P1.createInstance()), true);
        addTarget(new TargetCreaturePermanent());
    }

    public FungalBehemothTriggeredAbility(final FungalBehemothTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public FungalBehemothTriggeredAbility copy() {
        return new FungalBehemothTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.COUNTER_REMOVED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getData().equals(CounterType.TIME.getName()) && event.getTargetId().equals(this.getSourceId());
    }

    @Override
    public String getTriggerPhrase() {
        return "Whenever a time counter is removed from {this} while it's exiled, " ;
    }

}

class P1P1CountersOnControlledCreaturesCount implements DynamicValue {

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        int count = 0;
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, sourceAbility.getControllerId(), game)) {
            count += permanent.getCounters(game).getCount(CounterType.P1P1);
        }
        return count;
    }

    @Override
    public P1P1CountersOnControlledCreaturesCount copy() {
        return new P1P1CountersOnControlledCreaturesCount();
    }

    @Override
    public String toString() {
        return "X";
    }

    @Override
    public String getMessage() {
        return "+1/+1 counters on creatures you control";
    }
}
