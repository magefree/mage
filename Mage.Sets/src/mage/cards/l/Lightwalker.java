
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;

/**
 *
 * @author fireshoes
 */
public final class Lightwalker extends CardImpl {

    public Lightwalker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

            // Lightwalker has flying as long as it has a +1/+1 counter on it.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, 
                new ConditionalContinuousEffect(new GainAbilitySourceEffect(FlyingAbility.getInstance()), 
                new SourceHasCounterCondition(CounterType.P1P1),"Lightwalker has flying as long as it has a +1/+1 counter on it")));
    }

    private Lightwalker(final Lightwalker card) {
        super(card);
    }

    @Override
    public Lightwalker copy() {
        return new Lightwalker(this);
    }
}
