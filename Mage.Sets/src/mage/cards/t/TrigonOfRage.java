package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldWithCountersAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author Loki
 */
public final class TrigonOfRage extends CardImpl {

    public TrigonOfRage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // Trigon of Rage enters the battlefield with three charge counters on it.
        this.addAbility(new EntersBattlefieldWithCountersAbility(CounterType.CHARGE.createInstance(3)));

        // {R}{R}, {T}: Put a charge counter on Trigon of Rage.
        Ability ability2 = new SimpleActivatedAbility(new AddCountersSourceEffect(CounterType.CHARGE.createInstance()), new TapSourceCost());
        ability2.addCost(new ManaCostsImpl<>("{R}{R}"));
        this.addAbility(ability2);

        // {2}, {T}, Remove a charge counter from Trigon of Rage: Target creature gets +3/+0 until end of turn.
        Effect pumpEffect = new BoostTargetEffect(3, 0, Duration.EndOfTurn);
        Ability ability = new SimpleActivatedAbility(pumpEffect, new GenericManaCost(2));
        ability.addCost(new TapSourceCost());
        ability.addCost(new RemoveCountersSourceCost(CounterType.CHARGE.createInstance()));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private TrigonOfRage(final TrigonOfRage card) {
        super(card);
    }

    @Override
    public TrigonOfRage copy() {
        return new TrigonOfRage(this);
    }

}
