
package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCounterCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.GraftAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.target.TargetPermanent;

/**
 *
 * @author JotaPeRL
 */
public final class NovijenSages extends CardImpl {

    // Creatures you control with +1/+1 counters, name is what it is to make rules come out
    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("among creatures you control");

    static {
        filter.add(CounterType.P1P1.getPredicate());
    }

    public NovijenSages(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{U}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ADVISOR);
        this.subtype.add(SubType.MUTANT);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Graft 4
        this.addAbility(new GraftAbility(this, 4));

        // {1}, Remove two +1/+1 counters from among creatures you control: Draw a card.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1), new GenericManaCost(1));
        ability.addCost(new RemoveCounterCost(new TargetPermanent(1, 2, filter, true), CounterType.P1P1, 2));
        this.addAbility(ability);
    }

    private NovijenSages(final NovijenSages card) {
        super(card);
    }

    @Override
    public NovijenSages copy() {
        return new NovijenSages(this);
    }
}
