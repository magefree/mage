
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.CycleOrDiscardControllerTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author stravant
 */
public final class RuthlessSniper extends CardImpl {

    public RuthlessSniper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}");
        
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARCHER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Whenever you cycle or discard a card, you may pay {1}. If you do, put a -1/-1 counter on target creature.
        CycleOrDiscardControllerTriggeredAbility ability = new CycleOrDiscardControllerTriggeredAbility(
                new DoIfCostPaid(
                        new AddCountersTargetEffect(
                                CounterType.M1M1.createInstance(),
                                StaticValue.get(1),
                                Outcome.Removal),
                        new ManaCostsImpl<>("{1}")));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private RuthlessSniper(final RuthlessSniper card) {
        super(card);
    }

    @Override
    public RuthlessSniper copy() {
        return new RuthlessSniper(this);
    }
}
