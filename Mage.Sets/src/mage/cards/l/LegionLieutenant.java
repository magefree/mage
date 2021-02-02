
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

/**
 *
 * @author LevelX2
 */
public final class LegionLieutenant extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Vampires you control");

    static {
        filter.add(SubType.VAMPIRE.getPredicate());
        filter.add(TargetController.YOU.getControllerPredicate());
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

    private LegionLieutenant(final LegionLieutenant card) {
        super(card);
    }

    @Override
    public LegionLieutenant copy() {
        return new LegionLieutenant(this);
    }
}
