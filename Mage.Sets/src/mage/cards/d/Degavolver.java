
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.KickedCostCondition;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.EntersBattlefieldEffect;
import mage.abilities.effects.common.RegenerateSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.counters.CounterType;

/**
 *
 * @author Loki
 */
public final class Degavolver extends CardImpl {

    public Degavolver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}");
        this.subtype.add(SubType.VOLVER);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

         // Kicker {1}{B} and/or {R} (You may pay an additional {1}{B} and/or {R} as you cast this spell.)
        KickerAbility kickerAbility = new KickerAbility("{1}{B}");
        kickerAbility.addKickerCost("{R}");
        this.addAbility(kickerAbility);

        // If Degavolver was kicked with its {1}{B} kicker, it enters the battlefield with two +1/+1 counters on it and with "Pay 3 life: Regenerate Degavolver."
        EntersBattlefieldAbility ability1 = new EntersBattlefieldAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance(2),false),
                new KickedCostCondition("{1}{B}"), "If Degavolver was kicked with its {1}{B} kicker, it enters the battlefield with two +1/+1 counters on it and with \"Pay 3 life: Regenerate Degavolver.\"",
                "{this} enters the battlefield with two +1/+1 counters on it and with \"Pay 3 life: Regenerate Degavolver.\"");
        ((EntersBattlefieldEffect)ability1.getEffects().get(0)).addEffect(new GainAbilitySourceEffect(new SimpleActivatedAbility(Zone.BATTLEFIELD, new RegenerateSourceEffect(), new PayLifeCost(3)), Duration.WhileOnBattlefield));
        this.addAbility(ability1);

        // If Degavolver was kicked with its {R} kicker, it enters the battlefield with a +1/+1 counter on it and with first strike.
        EntersBattlefieldAbility ability2 = new EntersBattlefieldAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance(1),false), new KickedCostCondition("{R}"),
                "If Degavolver was kicked with its {R} kicker, it enters the battlefield with a +1/+1 counter on it and with first strike.",
                "{this} enters the battlefield with a +1/+1 counter on it and with first strike");
        ((EntersBattlefieldEffect)ability2.getEffects().get(0)).addEffect(new GainAbilitySourceEffect(FirstStrikeAbility.getInstance(), Duration.WhileOnBattlefield));
        this.addAbility(ability2);
    }

    private Degavolver(final Degavolver card) {
        super(card);
    }

    @Override
    public Degavolver copy() {
        return new Degavolver(this);
    }
}
