
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
import mage.filter.predicate.mageobject.AnotherPredicate;

/**
 *
 * @author LevelX2
 */
public final class RecklessCohort extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("another Ally");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(SubType.ALLY.getPredicate());
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

    private RecklessCohort(final RecklessCohort card) {
        super(card);
    }

    @Override
    public RecklessCohort copy() {
        return new RecklessCohort(this);
    }
}
