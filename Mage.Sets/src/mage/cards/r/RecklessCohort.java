
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalRequirementEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.combat.AttacksIfAbleSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.AnotherPredicate;

/**
 *
 * @author LevelX2
 */
public final class RecklessCohort extends CardImpl {

    private final static FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("another Ally");

    static {
        filter.add(new AnotherPredicate());
        filter.add(new SubtypePredicate(SubType.ALLY));
    }

    public RecklessCohort(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Reckless Cohort attacks each combat if able unless you control another Ally.
        Effect effect = new ConditionalRequirementEffect(
                new AttacksIfAbleSourceEffect(Duration.WhileOnBattlefield, true),
                new PermanentsOnTheBattlefieldCondition(filter, ComparisonType.FEWER_THAN, 1));
        effect.setText("{this} attacks each combat if able unless you control another Ally");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));
    }

    public RecklessCohort(final RecklessCohort card) {
        super(card);
    }

    @Override
    public RecklessCohort copy() {
        return new RecklessCohort(this);
    }
}
