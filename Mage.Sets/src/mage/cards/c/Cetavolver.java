
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.condition.common.KickedCostCondition;
import mage.abilities.effects.EntersBattlefieldEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FirstStrikeAbility;
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
 * @author Loki
 */
public final class Cetavolver extends CardImpl {

    public Cetavolver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}");
        this.subtype.add(SubType.VOLVER);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Kicker {1}{R} and/or {G} (You may pay an additional {1}{R} and/or {G} as you cast this spell.)
        KickerAbility kickerAbility = new KickerAbility("{1}{R}");
        kickerAbility.addKickerCost("{G}");
        this.addAbility(kickerAbility);

        // If Cetavolver was kicked with its {1}{R} kicker, it enters the battlefield with two +1/+1 counters on it and with first strike.
        EntersBattlefieldAbility ability1 = new EntersBattlefieldAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance(2),false),
                new KickedCostCondition("{1}{R}"), "If Cetavolver was kicked with its {1}{R} kicker, it enters the battlefield with two +1/+1 counters on it and with first strike.",
                "{this} enters the battlefield with two +1/+1 counters on it and with first strike");
        ((EntersBattlefieldEffect)ability1.getEffects().get(0)).addEffect(new GainAbilitySourceEffect(FirstStrikeAbility.getInstance(), Duration.WhileOnBattlefield));
        this.addAbility(ability1);

        // If Cetavolver was kicked with its {G} kicker, it enters the battlefield with a +1/+1 counter on it and with trample.
        EntersBattlefieldAbility ability2 = new EntersBattlefieldAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance(1),false), new KickedCostCondition("{G}"),
                "If Cetavolver was kicked with its {G} kicker, it enters the battlefield with a +1/+1 counter on it and with trample.",
                "{this} enters the battlefield with a +1/+1 counter on it and with trample");
        ((EntersBattlefieldEffect)ability2.getEffects().get(0)).addEffect(new GainAbilitySourceEffect(TrampleAbility.getInstance(), Duration.WhileOnBattlefield));
        this.addAbility(ability2);
    }

    private Cetavolver(final Cetavolver card) {
        super(card);
    }

    @Override
    public Cetavolver copy() {
        return new Cetavolver(this);
    }
}
