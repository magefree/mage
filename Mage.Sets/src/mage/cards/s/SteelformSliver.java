
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.StaticFilters;

/**
 *
 * @author Plopman
 */
public final class SteelformSliver extends CardImpl {

    public SteelformSliver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");
        this.subtype.add(SubType.SLIVER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Sliver creatures you control get +0/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new BoostControlledEffect(0, 1, Duration.WhileOnBattlefield,
                        StaticFilters.FILTER_PERMANENT_SLIVERS, false)));
    }

    private SteelformSliver(final SteelformSliver card) {
        super(card);
    }

    @Override
    public SteelformSliver copy() {
        return new SteelformSliver(this);
    }
}
