package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.GainLifeTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.target.TargetPlayer;

/**
 * @author nantuko
 */
public final class TrigonOfMending extends CardImpl {

    public TrigonOfMending(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{2}");

        // Trigon of Mending enters the battlefield with three charge counters on it.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.CHARGE.createInstance(3)), "with three charge counters on it"));

        // {W}{W}, {T}: Put a charge counter on Trigon of Mending.
        Ability ability2 = new SimpleActivatedAbility(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.CHARGE.createInstance()), new TapSourceCost());
        ability2.addCost(new ManaCostsImpl<>("{W}{W}"));
        this.addAbility(ability2);

        // {2}, {T}, Remove a charge counter from Trigon of Mending: Target player gains 3 life.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainLifeTargetEffect(3), new GenericManaCost(2));
        ability.addCost(new TapSourceCost());
        ability.addCost(new RemoveCountersSourceCost(CounterType.CHARGE.createInstance()));
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);

    }

    private TrigonOfMending(final TrigonOfMending card) {
        super(card);
    }

    @Override
    public TrigonOfMending copy() {
        return new TrigonOfMending(this);
    }

}
