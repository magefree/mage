
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.filter.predicate.mageobject.AnotherPredicate;

/**
 *
 * @author Plopman
 */
public final class RadiantArchangel extends CardImpl {
    
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("other creature with flying on the battlefield");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(new AbilityPredicate(FlyingAbility.class));
    }
    
    
    public RadiantArchangel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}{W}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ANGEL);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());
        
        // Radiant, Archangel gets +1/+1 for each other creature with flying on the battlefield.
        DynamicValue xValue = new PermanentsOnBattlefieldCount(filter);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostSourceEffect(xValue, xValue, Duration.WhileOnBattlefield)));        
    }

    private RadiantArchangel(final RadiantArchangel card) {
        super(card);
    }

    @Override
    public RadiantArchangel copy() {
        return new RadiantArchangel(this);
    }
}
