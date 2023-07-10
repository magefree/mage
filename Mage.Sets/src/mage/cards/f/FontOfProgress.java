package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.common.MillCardsTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FontOfProgress extends CardImpl {

    private static final DynamicValue xValue = new CountersSourceCount(CounterType.OIL);

    public FontOfProgress(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{U}");

        // Font of Progress enters the battlefield with two oil counters on it.
        this.addAbility(new EntersBattlefieldAbility(
                new AddCountersSourceEffect(CounterType.OIL.createInstance(2)),
                "with two oil counters on it"
        ));

        // {3}, {T}: Target player mills X cards, where X is the number of oil counters on Font of Progress.
        Ability ability = new SimpleActivatedAbility(new MillCardsTargetEffect(xValue)
                .setText("target player mills X cards, where X is the number of oil counters on {this}"), new GenericManaCost(3));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private FontOfProgress(final FontOfProgress card) {
        super(card);
    }

    @Override
    public FontOfProgress copy() {
        return new FontOfProgress(this);
    }
}
