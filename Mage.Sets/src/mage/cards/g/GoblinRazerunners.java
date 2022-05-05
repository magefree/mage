
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfYourEndStepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetPlayerOrPlaneswalker;

/**
 *
 * @author Loki
 */
public final class GoblinRazerunners extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledLandPermanent("a land");

    public GoblinRazerunners(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{R}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // {1}{R}, Sacrifice a land: Put a +1/+1 counter on Goblin Razerunners.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.P1P1.createInstance()), new ManaCostsImpl("{1}{R}"));
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(filter)));
        this.addAbility(ability);

        // At the beginning of your end step, you may have Goblin Razerunners deal damage equal to the number of +1/+1 counters on it to target player.
        ability = new BeginningOfYourEndStepTriggeredAbility(new DamageTargetEffect(new CountersSourceCount(CounterType.P1P1)).setText("you may have {this} deal damage equal to the number of +1/+1 counters on it to target player or planeswalker"), true);
        ability.addTarget(new TargetPlayerOrPlaneswalker());
        this.addAbility(ability);
    }

    private GoblinRazerunners(final GoblinRazerunners card) {
        super(card);
    }

    @Override
    public GoblinRazerunners copy() {
        return new GoblinRazerunners(this);
    }
}
