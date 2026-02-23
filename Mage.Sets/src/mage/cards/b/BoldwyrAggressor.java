package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BoldwyrAggressor extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.GIANT, "Giants");

    public BoldwyrAggressor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{R}");

        this.subtype.add(SubType.GIANT);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(5);

        // Double strike
        this.addAbility(DoubleStrikeAbility.getInstance());

        // Other Giants you control have double strike.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                DoubleStrikeAbility.getInstance(), Duration.WhileOnBattlefield, filter, true
        )));
    }

    private BoldwyrAggressor(final BoldwyrAggressor card) {
        super(card);
    }

    @Override
    public BoldwyrAggressor copy() {
        return new BoldwyrAggressor(this);
    }
}
