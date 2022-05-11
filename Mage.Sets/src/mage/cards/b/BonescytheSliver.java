
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;

/**
 *
 * @author jeffwadsworth
 */
public final class BonescytheSliver extends CardImpl {

    public BonescytheSliver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");
        this.subtype.add(SubType.SLIVER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Sliver creatures you control have double strike.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new GainAbilityControlledEffect(DoubleStrikeAbility.getInstance(),
                        Duration.WhileOnBattlefield, StaticFilters.FILTER_PERMANENT_SLIVERS)));
    }

    private BonescytheSliver(final BonescytheSliver card) {
        super(card);
    }

    @Override
    public BonescytheSliver copy() {
        return new BonescytheSliver(this);
    }
}
