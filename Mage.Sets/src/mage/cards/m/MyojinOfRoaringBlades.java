package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.CastFromHandSourcePermanentCondition;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.target.common.TargetAnyTarget;
import mage.watchers.common.CastFromHandWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MyojinOfRoaringBlades extends CardImpl {

    public MyojinOfRoaringBlades(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{R}{R}{R}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(7);
        this.toughness = new MageInt(4);

        // Myojin of Roaring Blades enters the battlefield with an indestructible counter on it if you cast it from your hand.
        this.addAbility(new EntersBattlefieldAbility(
                new AddCountersSourceEffect(CounterType.INDESTRUCTIBLE.createInstance()),
                CastFromHandSourcePermanentCondition.instance, null,
                "with an indestructible counter on it if you cast it from your hand"
        ), new CastFromHandWatcher());

        // Remove an indestructible counter from Myojin of Roaring Blades: It deals 7 damage to each of up to three targets.
        Ability ability = new SimpleActivatedAbility(
                new DamageTargetEffect(7)
                        .setText("it deals 7 damage to each of up to three targets"),
                new RemoveCountersSourceCost(CounterType.INDESTRUCTIBLE.createInstance())
        );
        ability.addTarget(new TargetAnyTarget(0, 3));
        this.addAbility(ability);
    }

    private MyojinOfRoaringBlades(final MyojinOfRoaringBlades card) {
        super(card);
    }

    @Override
    public MyojinOfRoaringBlades copy() {
        return new MyojinOfRoaringBlades(this);
    }
}
