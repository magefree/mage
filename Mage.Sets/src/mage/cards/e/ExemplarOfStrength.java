
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class ExemplarOfStrength extends CardImpl {

    public ExemplarOfStrength(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // When Exemplar of Strength enters the battlefield, put three -1/-1 counters on target creature you control.
        Ability ability = new EntersBattlefieldTriggeredAbility(new AddCountersTargetEffect(CounterType.M1M1.createInstance(3)));
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);

        // Whenever Exemplar of Strength attacks, remove a -1/-1 counter from it. If you do, you gain 1 life.
        this.addAbility(new AttacksTriggeredAbility(new DoIfCostPaid(
                new GainLifeEffect(1),
                new RemoveCountersSourceCost(CounterType.M1M1.createInstance()).setText("remove a -1/-1 counter from it"),
                null, false
        ), false));
    }

    private ExemplarOfStrength(final ExemplarOfStrength card) {
        super(card);
    }

    @Override
    public ExemplarOfStrength copy() {
        return new ExemplarOfStrength(this);
    }
}
