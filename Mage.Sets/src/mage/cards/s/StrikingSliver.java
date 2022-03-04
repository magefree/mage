
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.StaticFilters;

/**
 *
 * @author LevelX2
 */
public final class StrikingSliver extends CardImpl {

    public StrikingSliver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}");
        this.subtype.add(SubType.SLIVER);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Sliver creatures you control have first strike.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new GainAbilityControlledEffect(FirstStrikeAbility.getInstance(),
                        Duration.WhileOnBattlefield, StaticFilters.FILTER_PERMANENT_SLIVERS)));
    }

    private StrikingSliver(final StrikingSliver card) {
        super(card);
    }

    @Override
    public StrikingSliver copy() {
        return new StrikingSliver(this);
    }
}
