
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.KickerAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.counters.CounterType;

/**
 *
 * @author Backfir3
 */
public final class KavuTitan extends CardImpl {

    public KavuTitan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}");
        this.subtype.add(SubType.KAVU);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Kicker {2}{G}
        this.addAbility(new KickerAbility("{2}{G}"));
        // If Kavu Titan was kicked, it enters the battlefield with three +1/+1 counters on it and with trample.
        Ability ability = new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance(3)),
                KickedCondition.ONCE,
                "If Kavu Titan was kicked, it enters the battlefield with three +1/+1 counters on it and with trample.", "");
        ability.addEffect(new GainAbilitySourceEffect(TrampleAbility.getInstance(), Duration.WhileOnBattlefield));
        this.addAbility(ability);
    }

    private KavuTitan(final KavuTitan card) {
        super(card);
    }

    @Override
    public KavuTitan copy() {
        return new KavuTitan(this);
    }
}
