package mage.cards.t;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.StateTriggeredAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.RemoveAllCountersSourceEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;

import java.util.UUID;

/**
 * @author LoneFox
 */
public final class TidalInfluence extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("blue creatures");

    static {
        filter.add(new ColorPredicate(ObjectColor.BLUE));
    }

    public TidalInfluence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}");

        // Cast Tidal Influence only if no permanents named Tidal Influence are on the battlefield.
        this.getSpellAbility().addCost(new TidalInfluenceCost());
        // Tidal Influence enters the battlefield with a tide counter on it.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.TIDE.createInstance()),
                "with a tide counter on it."));
        // At the beginning of your upkeep, put a tide counter on Tidal Influence.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new AddCountersSourceEffect(CounterType.TIDE.createInstance()),
                TargetController.YOU, false));
        // As long as there is exactly one tide counter on Tidal Influence, all blue creatures get -2/-0.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(
                new BoostAllEffect(-2, -0, Duration.WhileOnBattlefield, filter, false),
                new SourceHasCounterCondition(CounterType.TIDE, 1, 1),
                "As long as there is exactly one tide counter on {this}, all blue creatures get -2/-0.")));
        // As long as there are exactly three tide counters on Tidal Influence, all blue creatures get +2/+0.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(
                new BoostAllEffect(2, -0, Duration.WhileOnBattlefield, filter, false),
                new SourceHasCounterCondition(CounterType.TIDE, 3, 3),
                "As long as there are exactly three tide counter on {this}, all blue creatures get +2/-0.")));
        // Whenever there are four tide counters on Tidal Influence, remove all tide counters from it.
        this.addAbility(new TidalInfluenceTriggeredAbility(new RemoveAllCountersSourceEffect(CounterType.TIDE)));
    }

    private TidalInfluence(final TidalInfluence card) {
        super(card);
    }

    @Override
    public TidalInfluence copy() {
        return new TidalInfluence(this);
    }
}


class TidalInfluenceCost extends CostImpl {

    private static final FilterPermanent filter = new FilterPermanent();

    static {
        filter.add(new NamePredicate("Tidal Influence"));
    }

    public TidalInfluenceCost() {
        this.text = "Cast Tidal Influence only if no permanents named Tidal Influence are on the battlefield";
    }

    public TidalInfluenceCost(final TidalInfluenceCost cost) {
        super(cost);
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        return !game.getBattlefield().contains(filter, source, game, 1);
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        this.paid = true;
        return paid;
    }

    @Override
    public TidalInfluenceCost copy() {
        return new TidalInfluenceCost(this);
    }
}


class TidalInfluenceTriggeredAbility extends StateTriggeredAbility {

    public TidalInfluenceTriggeredAbility(Effect effect) {
        super(Zone.BATTLEFIELD, effect);
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
