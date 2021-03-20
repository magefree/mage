
package mage.cards.c;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.mageobject.AnotherPredicate;

import java.util.UUID;

/**
 *
 * @author North
 */
public final class CrabappleCohort extends CardImpl {

    private static final String rule = "{this} gets +1/+1 as long as you control another green creature";

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent();

    static {
        filter.add(new ColorPredicate(ObjectColor.GREEN));
        filter.add(AnotherPredicate.instance);
    }

    public CrabappleCohort(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}");
        this.subtype.add(SubType.TREEFOLK);
        this.subtype.add(SubType.WARRIOR);

        this.color.setGreen(true);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Crabapple Cohort gets +1/+1 as long as you control another green creature.
        Condition condition = new PermanentsOnTheBattlefieldCondition(filter);
        Effect effect = new ConditionalContinuousEffect(new BoostSourceEffect(1, 1, Duration.WhileOnBattlefield), condition, rule);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));
    }

    private CrabappleCohort(final CrabappleCohort card) {
        super(card);
    }

    @Override
    public CrabappleCohort copy() {
        return new CrabappleCohort(this);
    }
}
