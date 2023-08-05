package mage.cards.b;

import mage.abilities.abilityword.ConstellationAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BoonOfTheSpiritRealm extends CardImpl {

    private static final DynamicValue xValue = new CountersSourceCount(CounterType.BLESSING);

    public BoonOfTheSpiritRealm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{W}{W}");

        // Constellation -- Whenever Boon of the Spirit Realm or another enchantment enters the battlefield under your control, put a blessing counter on Boon of the Spirit Realm.
        this.addAbility(new ConstellationAbility(new AddCountersSourceEffect(CounterType.BLESSING.createInstance())));

        // Creatures you control get +1/+1 for each blessing counter on Boon of the Spirit Realm.
        this.addAbility(new SimpleStaticAbility(new BoostControlledEffect(xValue, xValue, Duration.WhileOnBattlefield)));
    }

    private BoonOfTheSpiritRealm(final BoonOfTheSpiritRealm card) {
        super(card);
    }

    @Override
    public BoonOfTheSpiritRealm copy() {
        return new BoonOfTheSpiritRealm(this);
    }
}
