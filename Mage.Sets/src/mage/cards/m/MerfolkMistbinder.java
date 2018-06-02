
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;

/**
 *
 * @author LevelX2
 */
public final class MerfolkMistbinder extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Merfolk you control");

    static {
        filter.add(new SubtypePredicate(SubType.MERFOLK));
        filter.add(new ControllerPredicate(TargetController.YOU));
    }

    public MerfolkMistbinder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}{U}");

        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Other Merfolk you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostAllEffect(1, 1, Duration.WhileOnBattlefield, filter, true)));
    }

    public MerfolkMistbinder(final MerfolkMistbinder card) {
        super(card);
    }

    @Override
    public MerfolkMistbinder copy() {
        return new MerfolkMistbinder(this);
    }
}
