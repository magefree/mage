
package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsDamageGainLifeSourceTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.condition.common.KickedCostCondition;
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
 * @author LoneFox

 */
public final class Necravolver extends CardImpl {

    public Necravolver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.VOLVER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Kicker {1}{G} and/or {W}
        KickerAbility kickerAbility = new KickerAbility("{1}{G}");
        kickerAbility.addKickerCost("{W}");
        this.addAbility(kickerAbility);
        // If Necravolver was kicked with its {1}{G} kicker, it enters the battlefield with two +1/+1 counters on it and with trample.
        Ability ability = new  EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance(2)),
            new KickedCostCondition("{1}{G}"), "If {this} was kicked with its {1}{G} kicker, it enters the battlefield with two +1/+1 counters on it and with trample.", "");
        ability.addEffect(new GainAbilitySourceEffect(TrampleAbility.getInstance(), Duration.WhileOnBattlefield));
        this.addAbility(ability);
        // If Necravolver was kicked with its {W} kicker, it enters the battlefield with a +1/+1 counter on it and with "Whenever Necravolver deals damage, you gain that much life."
        ability = new  EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance(1)),
            new KickedCostCondition("{W}"), "If {this} was kicked with its {W} kicker, it enters the battlefield with a +1/+1 counter on it and with \"Whenever {this} deals damage, you gain that much life.\"", "");
        ability.addEffect(new GainAbilitySourceEffect(new DealsDamageGainLifeSourceTriggeredAbility(), Duration.WhileOnBattlefield));
        this.addAbility(ability);
    }

    private Necravolver(final Necravolver card) {
        super(card);
    }

    @Override
    public Necravolver copy() {
        return new Necravolver(this);
    }
}
