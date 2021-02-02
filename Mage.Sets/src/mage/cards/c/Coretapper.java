

package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.target.TargetPermanent;

/**
 * @author Loki
 */
public final class Coretapper extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("artifact");

    static {
        filter.add(CardType.ARTIFACT.getPredicate());
    }

    public Coretapper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{2}");
        this.subtype.add(SubType.MYR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
        Ability firstAbility = new SimpleActivatedAbility(Zone.BATTLEFIELD, new AddCountersTargetEffect(CounterType.CHARGE.createInstance()), new TapSourceCost());
        firstAbility.addTarget(new TargetPermanent(filter));
        this.addAbility(firstAbility);
        Ability secondAbility = new SimpleActivatedAbility(Zone.BATTLEFIELD, new AddCountersTargetEffect(CounterType.CHARGE.createInstance(2)), new SacrificeSourceCost());
        secondAbility.addTarget(new TargetPermanent(filter));
        this.addAbility(secondAbility);
    }

    private Coretapper(final Coretapper card) {
        super(card);
    }

    @Override
    public Coretapper copy() {
        return new Coretapper(this);
    }

}
