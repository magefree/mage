
package mage.cards.b;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.mageobject.AnotherPredicate;

import java.util.UUID;

/**
 *
 * @author jeffwadsworth
 */
public final class BriarberryCohort extends CardImpl {
    
    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent();
    
    static {
        filter.add(new ColorPredicate(ObjectColor.BLUE));
        filter.add(AnotherPredicate.instance);
    }
    
    private static final String rule = "{this} gets +1/+1 as long as you control another blue creature";

    public BriarberryCohort(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}");
        this.subtype.add(SubType.FAERIE, SubType.SOLDIER);

        this.color.setBlue(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        
        // Briarberry Cohort gets +1/+1 as long as you control another blue creature.
        Condition condition = new PermanentsOnTheBattlefieldCondition(filter);
        Effect effect = new ConditionalContinuousEffect(new BoostSourceEffect(1, 1, Duration.WhileOnBattlefield), condition, rule);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));
        
    }

    private BriarberryCohort(final BriarberryCohort card) {
        super(card);
    }

    @Override
    public BriarberryCohort copy() {
        return new BriarberryCohort(this);
    }
}
