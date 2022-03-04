package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldThisOrAnotherTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.TargetPlayer;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DragonsparkReactor extends CardImpl {

    private static final DynamicValue xValue = new CountersSourceCount(CounterType.CHARGE);

    public DragonsparkReactor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{R}");

        // Whenever Dragonspark Reactor or another artifact enters the battlefield under your control, put a charge counter on Dragonspark Reactor.
        this.addAbility(new EntersBattlefieldThisOrAnotherTriggeredAbility(
                new AddCountersSourceEffect(CounterType.CHARGE.createInstance()),
                StaticFilters.FILTER_PERMANENT_ARTIFACT, false, true
        ));

        // {4}, Sacrifice Dragonspark Reactor: It deals damage equal to the number of charge counters on it to target player and that much damage to up to one target creature.
        Ability ability = new SimpleActivatedAbility(
                new DamageTargetEffect(xValue).setText(
                        "it deals damage equal to the number of charge counters on it to target player " +
                                "and that much damage to up to one target creature"
                ), new GenericManaCost(4)
        );
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetPlayer());
        ability.addTarget(new TargetCreaturePermanent(0, 1));
        this.addAbility(ability);
    }

    private DragonsparkReactor(final DragonsparkReactor card) {
        super(card);
    }

    @Override
    public DragonsparkReactor copy() {
        return new DragonsparkReactor(this);
    }
}
