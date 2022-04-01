
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.continuous.BecomesCreatureTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.permanent.token.custom.CreatureToken;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author L_J
 */
public final class SpikeTiller extends CardImpl {

    public SpikeTiller(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}{G}");
        this.subtype.add(SubType.SPIKE);

        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Spike Tiller enters the battlefield with three +1/+1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance(3)), "with three +1/+1 counters on it"));

        // {2}, Remove a +1/+1 counter from Spike Tiller: Put a +1/+1 counter on target creature.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new AddCountersTargetEffect(CounterType.P1P1.createInstance()), new GenericManaCost(2));
        ability.addCost(new RemoveCountersSourceCost(CounterType.P1P1.createInstance()));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // {2}, Remove a +1/+1 counter from Spike Tiller: Target land becomes a 2/2 creature that's still a land. Put a +1/+1 counter on it.
        Ability ability2 = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BecomesCreatureTargetEffect(new CreatureToken(2, 2), false, true, Duration.EndOfGame).setText("Target land becomes a 2/2 creature that's still a land"), new GenericManaCost(2));
        ability2.addCost(new RemoveCountersSourceCost(CounterType.P1P1.createInstance()));
        ability2.addEffect(new AddCountersTargetEffect(CounterType.P1P1.createInstance()).setText("Put a +1/+1 counter on it."));
        ability2.addTarget(new TargetLandPermanent());
        this.addAbility(ability2);
    }

    private SpikeTiller(final SpikeTiller card) {
        super(card);
    }

    @Override
    public SpikeTiller copy() {
        return new SpikeTiller(this);
    }
}
