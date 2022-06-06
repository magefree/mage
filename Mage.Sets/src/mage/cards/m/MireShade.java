
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledPermanent;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author TheElk801
 */
public final class MireShade extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("a Swamp");

    static {
        filter.add(SubType.SWAMP.getPredicate());
    }

    public MireShade(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.SHADE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {B}, Sacrifice a Swamp: Put a +1/+1 counter on Mire Shade. Activate this ability only any time you could cast a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.P1P1.createInstance()), new ManaCostsImpl<>("{B}"));
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(filter)));
        this.addAbility(ability);
    }

    private MireShade(final MireShade card) {
        super(card);
    }

    @Override
    public MireShade copy() {
        return new MireShade(this);
    }
}
