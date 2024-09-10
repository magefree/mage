package mage.cards.r;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RisingOfTheDay extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("legendary creatures");

    static {
        filter.add(SuperType.LEGENDARY.getPredicate());
    }

    public RisingOfTheDay(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}");


        // Creatures you control have haste.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                HasteAbility.getInstance(), Duration.WhileOnBattlefield,
                StaticFilters.FILTER_PERMANENT_CREATURES
        )));

        // Legendary creatures you control get +1/+0.
        this.addAbility(new SimpleStaticAbility(new BoostControlledEffect(
                1, 0, Duration.WhileOnBattlefield, filter
        )));
    }

    private RisingOfTheDay(final RisingOfTheDay card) {
        super(card);
    }

    @Override
    public RisingOfTheDay copy() {
        return new RisingOfTheDay(this);
    }
}
