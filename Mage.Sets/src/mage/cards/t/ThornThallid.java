
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author fireshoes
 */
public final class ThornThallid extends CardImpl {

    public ThornThallid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}{G}");
        this.subtype.add(SubType.FUNGUS);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // At the beginning of your upkeep, put a spore counter on Thorn Thallid.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new AddCountersSourceEffect(CounterType.SPORE.createInstance()), TargetController.YOU, false));
        // Remove three spore counters from Thorn Thallid: Thorn Thallid deals 1 damage to any target.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new DamageTargetEffect(1, "it"),
                new RemoveCountersSourceCost(CounterType.SPORE.createInstance(3)));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private ThornThallid(final ThornThallid card) {
        super(card);
    }

    @Override
    public ThornThallid copy() {
        return new ThornThallid(this);
    }
}
