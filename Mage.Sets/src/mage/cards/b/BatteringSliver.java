
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;

/**
 *
 * @author KholdFuzion
 */
public final class BatteringSliver extends CardImpl {

    public BatteringSliver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{R}");
        this.subtype.add(SubType.SLIVER);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // All Sliver creatures have trample.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new GainAbilityAllEffect(
                        TrampleAbility.getInstance(),
                        Duration.WhileOnBattlefield,
                        StaticFilters.FILTER_PERMANENT_ALL_SLIVERS
                )
        ));
    }

    private BatteringSliver(final BatteringSliver card) {
        super(card);
    }

    @Override
    public BatteringSliver copy() {
        return new BatteringSliver(this);
    }
}
