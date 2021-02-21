
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.abilities.keyword.DevoidAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.ComparisonType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorlessPredicate;
import mage.filter.predicate.mageobject.AnotherPredicate;

/**
 *
 * @author LevelX2
 */
public final class DustStalker extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("if you control no other colorless creatures");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(ColorlessPredicate.instance);
    }

    public DustStalker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}{R}");
        this.subtype.add(SubType.ELDRAZI);
        this.power = new MageInt(5);
        this.toughness = new MageInt(3);

        // Devoid
        this.addAbility(new DevoidAbility(this.color));
        // Haste
        this.addAbility(HasteAbility.getInstance());
        // At the beginning of each end step, if you control no other colorless creatures, return Dust Stalker to its owner's hand.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(Zone.BATTLEFIELD, new ReturnToHandSourceEffect(), TargetController.ANY,
                new PermanentsOnTheBattlefieldCondition(filter, ComparisonType.EQUAL_TO, 0), false));
    }

    private DustStalker(final DustStalker card) {
        super(card);
    }

    @Override
    public DustStalker copy() {
        return new DustStalker(this);
    }
}
