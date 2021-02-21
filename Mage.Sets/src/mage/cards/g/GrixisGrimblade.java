
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.MulticoloredPredicate;
import mage.filter.predicate.mageobject.AnotherPredicate;

/**
 *
 * @author jeffwadsworth
 */
public final class GrixisGrimblade extends CardImpl {
    
    private static final FilterControlledPermanent filter = new FilterControlledPermanent("another multicolor permanent");
    
    static {
        filter.add(MulticoloredPredicate.instance);
        filter.add(AnotherPredicate.instance);
    }

    public GrixisGrimblade(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{U/R}{B}");
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.WARRIOR);



        
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // As long as you control another multicolored permanent, Grixis Grimblade gets +1/+1 and has deathtouch.
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(
                new BoostSourceEffect(1,1, Duration.WhileOnBattlefield),
                new PermanentsOnTheBattlefieldCondition(filter),
                "As long as you control another multicolored permanent, {this} gets +1/+1"));
        ability.addEffect(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(DeathtouchAbility.getInstance()),
                new PermanentsOnTheBattlefieldCondition(filter),
                "and has deathtouch"));
        this.addAbility(ability);
        
    }

    private GrixisGrimblade(final GrixisGrimblade card) {
        super(card);
    }

    @Override
    public GrixisGrimblade copy() {
        return new GrixisGrimblade(this);
    }
}
