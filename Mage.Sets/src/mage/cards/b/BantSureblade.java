
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.MulticoloredPredicate;
import mage.filter.predicate.mageobject.AnotherPredicate;

/**
 *
 * @author jeffwadsworth
 */
public final class BantSureblade extends CardImpl {
    
    private static final FilterControlledPermanent filter = new FilterControlledPermanent("another multicolor permanent");
    
    static {
        filter.add(MulticoloredPredicate.instance);
        filter.add(AnotherPredicate.instance);
    }

    public BantSureblade(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{G/U}{W}");
        this.subtype.add(SubType.HUMAN, SubType.SOLDIER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // As long as you control another multicolored permanent, Bant Sureblade gets +1/+1 and has first strike.
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(
                new BoostSourceEffect(1,1, Duration.WhileOnBattlefield),
                new PermanentsOnTheBattlefieldCondition(filter),
                "As long as you control another multicolored permanent, {this} gets +1/+1"));
        ability.addEffect(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(FirstStrikeAbility.getInstance()),
                new PermanentsOnTheBattlefieldCondition(filter),
                "and has first strike"));
        this.addAbility(ability);
    }

    private BantSureblade(final BantSureblade card) {
        super(card);
    }

    @Override
    public BantSureblade copy() {
        return new BantSureblade(this);
    }
}
