
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.StaticFilters;

/**
 *
 * @author Sir-Speshkitty
 */
public final class FurySliver extends CardImpl {

    public FurySliver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{R}");
        this.subtype.add(SubType.SLIVER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // All Sliver creatures have double strike.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new GainAbilityAllEffect(DoubleStrikeAbility.getInstance(),
                        Duration.WhileOnBattlefield,
                        StaticFilters.FILTER_PERMANENT_ALL_SLIVERS
                )
        ));
    }

    private FurySliver(final FurySliver card) {
        super(card);
    }

    @Override
    public FurySliver copy() {
        return new FurySliver(this);
    }
}
