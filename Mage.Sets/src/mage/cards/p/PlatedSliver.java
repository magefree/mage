
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.StaticFilters;

/**
 *
 * @author cbt33
 */
public final class PlatedSliver extends CardImpl {

    public PlatedSliver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}");
        this.subtype.add(SubType.SLIVER);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // All Sliver creatures get +0/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostAllEffect(0, 1, Duration.WhileOnBattlefield, StaticFilters.FILTER_PERMANENT_ALL_SLIVERS, false)));
    }

    private PlatedSliver(final PlatedSliver card) {
        super(card);
    }

    @Override
    public PlatedSliver copy() {
        return new PlatedSliver(this);
    }
}
