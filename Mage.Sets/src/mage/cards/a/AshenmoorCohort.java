
package mage.cards.a;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
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
 * @author jeffwadsworth
 */
public final class AshenmoorCohort extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent();

    static {
        filter.add(new ColorPredicate(ObjectColor.BLACK));
        filter.add(AnotherPredicate.instance);
    }

    private static final String rule = "Ashenmoor Cohort gets +1/+1 as long as you control another black creature";

    public AshenmoorCohort(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{B}");
        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Ashenmoor Cohort gets +1/+1 as long as you control another black creature.
        Condition condition = new PermanentsOnTheBattlefieldCondition(filter);
        ConditionalContinuousEffect effect = new ConditionalContinuousEffect(new BoostSourceEffect(1, 1, Duration.WhileOnBattlefield), condition, rule);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));

    }

    private AshenmoorCohort(final AshenmoorCohort card) {
        super(card);
    }

    @Override
    public AshenmoorCohort copy() {
        return new AshenmoorCohort(this);
    }
}
