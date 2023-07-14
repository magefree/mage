package mage.cards.t;

import mage.ObjectColor;
import mage.abilities.StateTriggeredAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.CastOnlyIfConditionIsTrueAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.common.RemoveAllCountersSourceEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;

import java.util.UUID;

/**
 * @author awjackson
 */
public final class TidalInfluence extends CardImpl {

    private static final FilterPermanent filterName = new FilterPermanent("no permanents named Tidal Influence are on the battlefield");
    private static final FilterCreaturePermanent filterBlue = new FilterCreaturePermanent("all blue creatures");

    static {
        filterName.add(new NamePredicate("Tidal Influence"));
        filterBlue.add(new ColorPredicate(ObjectColor.BLUE));
    }

    private static final Condition conditionCast = new PermanentsOnTheBattlefieldCondition(
            filterName, ComparisonType.EQUAL_TO, 0, false);
    private static final Condition condition1 = new SourceHasCounterCondition(CounterType.TIDE, 1, 1);
    private static final Condition condition3 = new SourceHasCounterCondition(CounterType.TIDE, 3, 3);

    public TidalInfluence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}");

        // Cast this spell only if no permanents named Tidal Influence are on the battlefield.
        this.addAbility(new CastOnlyIfConditionIsTrueAbility(conditionCast));

        // Tidal Influence enters the battlefield with a tide counter on it.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.TIDE.createInstance()),
                "with a tide counter on it."));

        // At the beginning of your upkeep, put a tide counter on Tidal Influence.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new AddCountersSourceEffect(CounterType.TIDE.createInstance()),
                TargetController.YOU, false));

        // As long as there is exactly one tide counter on Tidal Influence, all blue creatures get -2/-0.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new BoostAllEffect(-2, -0, Duration.WhileOnBattlefield, filterBlue, false),
                condition1,
                "As long as there is exactly one tide counter on {this}, all blue creatures get -2/-0.")));

        // As long as there are exactly three tide counters on Tidal Influence, all blue creatures get +2/+0.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new BoostAllEffect(2, 0, Duration.WhileOnBattlefield, filterBlue, false),
                condition3,
                "As long as there are exactly three tide counter on {this}, all blue creatures get +2/+0.")));

        // Whenever there are four tide counters on Tidal Influence, remove all tide counters from it.
        this.addAbility(new TidalInfluenceTriggeredAbility());
    }

    private TidalInfluence(final TidalInfluence card) {
        super(card);
    }

    @Override
    public TidalInfluence copy() {
        return new TidalInfluence(this);
    }
}

class TidalInfluenceTriggeredAbility extends StateTriggeredAbility {

    public TidalInfluenceTriggeredAbility() {
        super(Zone.BATTLEFIELD, new RemoveAllCountersSourceEffect(CounterType.TIDE));
        setTriggerPhrase("Whenever there are four tide counters on {this}, ");
    }

    public TidalInfluenceTriggeredAbility(final TidalInfluenceTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public TidalInfluenceTriggeredAbility copy() {
        return new TidalInfluenceTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return new CountersSourceCount(CounterType.TIDE).calculate(game, this, null) == 4;
    }
}
