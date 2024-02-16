
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.GainLifeControllerTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class SereneSteward extends CardImpl {

    public SereneSteward(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever you gain life, you may pay {W}. If you do, put a +1/+1 counter on target creature.
        Ability ability = new GainLifeControllerTriggeredAbility(
                new DoIfCostPaid(new AddCountersTargetEffect(CounterType.P1P1.createInstance()), new ManaCostsImpl<>("{W}")),
                false);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private SereneSteward(final SereneSteward card) {
        super(card);
    }

    @Override
    public SereneSteward copy() {
        return new SereneSteward(this);
    }
}
