

package mage.cards.a;

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
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;

/**
 * @author Loki
 */
public final class Anavolver extends CardImpl {

    public Anavolver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}");
        this.subtype.add(SubType.VOLVER);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);
        
        // Kicker {1}{U} and/or {B} (You may pay an additional {1}{U} and/or {B} as you cast this spell.)
        KickerAbility kickerAbility = new KickerAbility("{1}{U}");
        kickerAbility.addKickerCost("{B}");
        this.addAbility(kickerAbility);

        // If Anavolver was kicked with its {1}{U} kicker, it enters the battlefield with two +1/+1 counters on it and with flying.
        EntersBattlefieldAbility ability1 = new EntersBattlefieldAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance(2),false),
                new KickedCostCondition("{1}{U}"), "If {this} was kicked with its {1}{U} kicker, it enters the battlefield with two +1/+1 counters on it and with flying.",
                "{this} enters the battlefield with two +1/+1 counters on it and with flying");
        ((EntersBattlefieldEffect)ability1.getEffects().get(0)).addEffect(new GainAbilitySourceEffect(FlyingAbility.getInstance(), Duration.WhileOnBattlefield));
        this.addAbility(ability1);

        // If Anavolver was kicked with its {B} kicker, it enters the battlefield with a +1/+1 counter on it and with "Pay 3 life: Regenerate Anavolver."
        EntersBattlefieldAbility ability2 = new EntersBattlefieldAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance(1),false), new KickedCostCondition("{B}"),
                "If {this} was kicked with its {B} kicker, it enters the battlefield with a +1/+1 counter on it and with \"Pay 3 life: Regenerate Anavolver.\"",
                "{this} enters the battlefield with a +1/+1 counter on it and with \"Pay 3 life: Regenerate Anavolver.\"");
        ((EntersBattlefieldEffect)ability2.getEffects().get(0)).addEffect(new GainAbilitySourceEffect(new SimpleActivatedAbility(Zone.BATTLEFIELD, new RegenerateSourceEffect(), new PayLifeCost(3)), Duration.WhileOnBattlefield));
        this.addAbility(ability2);
    }

    private Anavolver(final Anavolver card) {
        super(card);
    }

    @Override
    public Anavolver copy() {
        return new Anavolver(this);
    }
}
