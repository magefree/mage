package mage.cards.i;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.constants.Zone;
import mage.filter.predicate.mageobject.SubtypePredicate;



import java.util.UUID;

/**
 * @author TheElk801
 */
public final class InspiringVeteran extends CardImpl {

    private static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent(SubType.KNIGHT, "Knights");

            static
            {
                filter.add(new SubtypePredicate(SubType.KNIGHT));
                filter.add(new ControllerPredicate(TargetController.YOU));
            }

    public InspiringVeteran(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Other Knights you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostAllEffect(1, 1, Duration.WhileOnBattlefield, filter, true)));
    }

    private InspiringVeteran(final InspiringVeteran card) {
        super(card);
    }

    @Override
    public InspiringVeteran copy() {
        return new InspiringVeteran(this);
    }
}
