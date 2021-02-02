
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.SplitSecondAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;

/**
 *
 * @author LevelX2
 */
public final class SulfurElemental extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("white creatures");

    static {
        filter.add(new ColorPredicate(ObjectColor.WHITE));
    }

    public SulfurElemental(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");
        this.subtype.add(SubType.ELEMENTAL);

        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Split second
        this.addAbility(new SplitSecondAbility());

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // White creatures get +1/-1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostAllEffect(1, -1, Duration.WhileOnBattlefield, filter, false)));
    }

    private SulfurElemental(final SulfurElemental card) {
        super(card);
    }

    @Override
    public SulfurElemental copy() {
        return new SulfurElemental(this);
    }
}
