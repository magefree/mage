

package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.MenaceAbility;
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
public final class TwoHeadedSliver extends CardImpl {

    public TwoHeadedSliver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");
        this.subtype.add(SubType.SLIVER);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // All Sliver creatures have menace. (They can't be blocked except by two or more creatures.)
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAllEffect(
                new MenaceAbility(),
                Duration.WhileOnBattlefield, StaticFilters.FILTER_PERMANENT_ALL_SLIVERS,
                "All Sliver creatures have menace. <i>(They can't be blocked except by two or more creatures.)</i>")));
    }

    private TwoHeadedSliver(final TwoHeadedSliver card) {
        super(card);
    }

    @Override
    public TwoHeadedSliver copy() {
        return new TwoHeadedSliver(this);
    }
}
