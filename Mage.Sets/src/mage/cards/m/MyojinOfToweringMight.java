package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.CastFromHandSourcePermanentCondition;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.counter.DistributeCountersEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanentAmount;
import mage.watchers.common.CastFromHandWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MyojinOfToweringMight extends CardImpl {

    public MyojinOfToweringMight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{G}{G}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(8);
        this.toughness = new MageInt(8);

        // Myojin of Towering Might enters the battlefield with an indestructible counter on it if you cast it from your hand.
        this.addAbility(new EntersBattlefieldAbility(
                new AddCountersSourceEffect(CounterType.INDESTRUCTIBLE.createInstance()),
                CastFromHandSourcePermanentCondition.instance, null,
                "with an indestructible counter on it if you cast it from your hand"
        ), new CastFromHandWatcher());

        // Remove an indestructible counter from Myojin of Towering Might: Distribute eight +1/+1 counters among any number of target creatures you control. They gain trample until end of turn.
        Ability ability = new SimpleActivatedAbility(new DistributeCountersEffect(
                CounterType.P1P1, 8, false,
                "any number of target creatures you control"
        ), new RemoveCountersSourceCost(CounterType.INDESTRUCTIBLE.createInstance()));
        ability.addEffect(new GainAbilityTargetEffect(
                TrampleAbility.getInstance(), Duration.EndOfTurn
        ).setText("They gain trample until end of turn"));
        ability.addTarget(new TargetCreaturePermanentAmount(8, StaticFilters.FILTER_CONTROLLED_CREATURES));
        this.addAbility(ability);
    }

    private MyojinOfToweringMight(final MyojinOfToweringMight card) {
        super(card);
    }

    @Override
    public MyojinOfToweringMight copy() {
        return new MyojinOfToweringMight(this);
    }
}
