package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CleavingSliver extends CardImpl {

    public CleavingSliver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.SLIVER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Sliver creatures you control get +2/+0.
        this.addAbility(new SimpleStaticAbility(new BoostControlledEffect(
                2, 0, Duration.WhileOnBattlefield,
                StaticFilters.FILTER_PERMANENT_SLIVERS
        )));
    }

    private CleavingSliver(final CleavingSliver card) {
        super(card);
    }

    @Override
    public CleavingSliver copy() {
        return new CleavingSliver(this);
    }
}
