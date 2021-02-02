
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author LevelX2
 */
public final class HarvesterTroll extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("a creature or land");

    static {
        filter.add(Predicates.or(CardType.CREATURE.getPredicate(), CardType.LAND.getPredicate()));
    }

    public HarvesterTroll(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}");
        this.subtype.add(SubType.TROLL);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // When Harvester Troll enters the battlefield, you may sacrifice a creature or land. If you do, put two +1/+1 counters on Harvester Troll.
        EntersBattlefieldTriggeredAbility ability
                = new EntersBattlefieldTriggeredAbility(new DoIfCostPaid(new AddCountersSourceEffect(CounterType.P1P1.createInstance(2)),
                                new SacrificeTargetCost(new TargetControlledPermanent(filter))), false);
        this.addAbility(ability);
    }

    private HarvesterTroll(final HarvesterTroll card) {
        super(card);
    }

    @Override
    public HarvesterTroll copy() {
        return new HarvesterTroll(this);
    }
}
