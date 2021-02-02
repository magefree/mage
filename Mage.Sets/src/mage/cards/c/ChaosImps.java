
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.UnleashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;

/**
 *
 * @author LevelX2
 */
public final class ChaosImps extends CardImpl {
 
    public ChaosImps(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{R}{R}");
        this.subtype.add(SubType.IMP);

        this.power = new MageInt(6);
        this.toughness = new MageInt(5);
 
        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Unleash (You may have this creature enter the battlefield with a +1/+1 counter on it. It can't block as long as it has a +1/+1 counter on it.)
        this.addAbility(new UnleashAbility());
        
        // Chaos Imps has trample as long as it has a +1/+1 counter on it.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, 
                new ConditionalContinuousEffect(new GainAbilitySourceEffect(TrampleAbility.getInstance()), 
                new SourceHasCounterCondition(CounterType.P1P1),"{this} has trample as long as it has a +1/+1 counter on it")));
        
    }
 
    private ChaosImps(final ChaosImps card) {
        super(card);
    }
 
    @Override
    public ChaosImps copy() {
        return new ChaosImps(this);
    }
}