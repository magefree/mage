
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsDamageGainLifeSourceTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.condition.common.KickedCostCondition;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.KickerAbility;
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
public final class Rakavolver extends CardImpl {

    public Rakavolver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}");
        this.subtype.add(SubType.VOLVER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Kicker {1}{W} and/or {U}
        KickerAbility kickerAbility = new KickerAbility("{1}{W}");
        kickerAbility.addKickerCost("{U}");
        this.addAbility(kickerAbility);
        // If Rakavolver was kicked with its {1}{W} kicker, it enters the battlefield with two +1/+1 counters on it and with "Whenever Rakavolver deals damage, you gain that much life."
        Ability ability = new  EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance(2)),
            new KickedCostCondition("{1}{W}"), "If {this} was kicked with its {1}{W} kicker, it enters the battlefield with two +1/+1 counters on it and with \"Whenever {this} deals damage, you gain that much life.\"", "");
        ability.addEffect(new GainAbilitySourceEffect(new DealsDamageGainLifeSourceTriggeredAbility(), Duration.WhileOnBattlefield));
        this.addAbility(ability);
        // If Rakavolver was kicked with its {U} kicker, it enters the battlefield with a +1/+1 counter on it and with flying.
        ability = new  EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance(1)),
            new KickedCostCondition("{U}"), "If {this} was kicked with its {U} kicker, it enters the battlefield with a +1/+1 counter on it and with flying.", "");
        ability.addEffect(new GainAbilitySourceEffect(FlyingAbility.getInstance(), Duration.WhileOnBattlefield));
        this.addAbility(ability);

    }

    private Rakavolver(final Rakavolver card) {
        super(card);
    }

    @Override
    public Rakavolver copy() {
        return new Rakavolver(this);
    }
}
