
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;

/**
 *
 * @author jeffwadsworth
 */
public final class SapphireDrake extends CardImpl {
    
    private static final FilterPermanent filter = new FilterPermanent();
    
    static {
        filter.add(CardType.CREATURE.getPredicate());
        filter.add(TargetController.YOU.getControllerPredicate());
        filter.add(CounterType.P1P1.getPredicate());
    }
    
    static final String rule = "Each creature you control with a +1/+1 counter on it has flying";

    public SapphireDrake(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{U}");
        this.subtype.add(SubType.DRAKE);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        
        // Each creature you control with a +1/+1 counter on it has flying.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAllEffect(FlyingAbility.getInstance(), Duration.WhileOnBattlefield, filter, rule)));
    }

    public SapphireDrake(final SapphireDrake card) {
        super(card);
    }

    @Override
    public SapphireDrake copy() {
        return new SapphireDrake(this);
    }
}
