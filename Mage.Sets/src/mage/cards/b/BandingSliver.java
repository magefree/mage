
package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.BandingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 *
 * @author Susucr
 */
public final class BandingSliver extends CardImpl {

    public BandingSliver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}{W}");
        this.subtype.add(SubType.SLIVER);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // All Sliver creatures have banding.
        this.addAbility(new SimpleStaticAbility(
                new GainAbilityAllEffect(
                        BandingAbility.getInstance(),
                        Duration.WhileOnBattlefield,
                        StaticFilters.FILTER_PERMANENT_ALL_SLIVERS
                )
        ));
    }

    private BandingSliver(final BandingSliver card) {
        super(card);
    }

    @Override
    public BandingSliver copy() {
        return new BandingSliver(this);
    }
}
