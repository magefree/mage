package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.condition.common.CastNoncreatureSpellThisTurnCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DoWhenCostPaid;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.Counter;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class TheThing extends CardImpl {

    public TheThing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // At the beginning of combat on your turn, if you've cast a noncreature spell this turn, put four +1/+1 counters on The Thing.
        this.addAbility(new BeginningOfCombatTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance(4)))
                .withInterveningIf(CastNoncreatureSpellThisTurnCondition.instance)
                .addHint(CastNoncreatureSpellThisTurnCondition.getHint()));

        // Whenever The Thing attacks, you may pay {R}{G}{W}{U}. When you do, double the number of each kind of counter on any number of target permanents you control.
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(new TheThingEffect(), false);
        ability.addTarget(new TargetPermanent(0, Integer.MAX_VALUE, StaticFilters.FILTER_CONTROLLED_PERMANENTS));
        this.addAbility(new AttacksTriggeredAbility(new DoWhenCostPaid(
                ability, new ManaCostsImpl<>("{R}{G}{W}{U}"), "Pay {R}{G}{W}{U}?"
        )));
    }

    private TheThing(final TheThing card) {
        super(card);
    }

    @Override
    public TheThing copy() {
        return new TheThing(this);
    }
}

class TheThingEffect extends OneShotEffect {

    TheThingEffect() {
        super(Outcome.Benefit);
        staticText = "double the number of each kind of counter on any number of target permanents you control";
    }

    private TheThingEffect(final TheThingEffect effect) {
        super(effect);
    }

    @Override
    public TheThingEffect copy() {
        return new TheThingEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID targetId : getTargetPointer().getTargets(game, source)) {
            Permanent permanent = game.getPermanent(targetId);
            if (permanent == null) {
                continue;
            }
            Set<Counter> counters = permanent
                    .getCounters(game)
                    .values()
                    .stream()
                    .map(Counter::copy)
                    .collect(Collectors.toSet());
            for (Counter counter : counters) {
                permanent.addCounters(counter, source, game);
            }
        }
        return true;
    }
}
