
package mage.cards.l;

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
public final class LegionLieutenant extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Vampires you control");

    static {
        filter.add(new SubtypePredicate(SubType.VAMPIRE));
        filter.add(new ControllerPredicate(TargetController.YOU));
    }

    public LegionLieutenant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{B}");

        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Other Vampires you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostAllEffect(1, 1, Duration.WhileOnBattlefield, filter, true)));
    }

    public LegionLieutenant(final LegionLieutenant card) {
        super(card);
    }

    @Override
    public LegionLieutenant copy() {
        return new LegionLieutenant(this);
    }
}
