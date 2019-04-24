
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.CastFromHandSourceCondition;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.watchers.common.CastFromHandWatcher;

/**
 * @author Loki
 */
public final class MyojinOfCleansingFire extends CardImpl {
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("other creatures");

    static {
        filter.add(new AnotherPredicate());
    }

    public MyojinOfCleansingFire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{W}{W}{W}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(4);
        this.toughness = new MageInt(6);

        this.getSpellAbility().addWatcher(new CastFromHandWatcher());

        // Myojin of Cleansing Fire enters the battlefield with a divinity counter on it if you cast it from your hand.
        this.addAbility(new EntersBattlefieldAbility(new ConditionalOneShotEffect(new AddCountersSourceEffect(CounterType.DIVINITY.createInstance()), CastFromHandSourceCondition.instance, ""), "{this} enters the battlefield with a divinity counter on it if you cast it from your hand"));
        // Myojin of Cleansing Fire is indestructible as long as it has a divinity counter on it.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(new GainAbilitySourceEffect(IndestructibleAbility.getInstance(), Duration.WhileOnBattlefield),
                new SourceHasCounterCondition(CounterType.DIVINITY), "{this} is indestructible as long as it has a divinity counter on it")));
        // Remove a divinity counter from Myojin of Cleansing Fire: Destroy all other creatures.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new DestroyAllEffect(filter), new RemoveCountersSourceCost(CounterType.DIVINITY.createInstance())));
    }

    public MyojinOfCleansingFire(final MyojinOfCleansingFire card) {
        super(card);
    }

    @Override
    public MyojinOfCleansingFire copy() {
        return new MyojinOfCleansingFire(this);
    }
}
