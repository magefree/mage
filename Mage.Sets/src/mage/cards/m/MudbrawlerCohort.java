
package mage.cards.m;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.HasteAbility;
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
public final class MudbrawlerCohort extends CardImpl {

    private static final String rule = "{this} gets +1/+1 as long as you control another red creature";

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent();

    static {
        filter.add(new ColorPredicate(ObjectColor.RED));
        filter.add(AnotherPredicate.instance);
    }

    public MudbrawlerCohort(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.WARRIOR);

        this.color.setRed(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Haste
        this.addAbility(HasteAbility.getInstance());
        // Mudbrawler Cohort gets +1/+1 as long as you control another red creature.
        Condition condition = new PermanentsOnTheBattlefieldCondition(filter);
        Effect effect = new ConditionalContinuousEffect(new BoostSourceEffect(1, 1, Duration.WhileOnBattlefield), condition, rule);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));
    }

    private MudbrawlerCohort(final MudbrawlerCohort card) {
        super(card);
    }

    @Override
    public MudbrawlerCohort copy() {
        return new MudbrawlerCohort(this);
    }
}
