package mage.cards.l;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.MyriadAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LegionLoyalty extends CardImpl {

    public LegionLoyalty(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{6}{W}{W}");

        // Creatures you control have myriad.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                new MyriadAbility(), Duration.WhileOnBattlefield, StaticFilters.FILTER_PERMANENT_CREATURES
        )));
    }

    private LegionLoyalty(final LegionLoyalty card) {
        super(card);
    }

    @Override
    public LegionLoyalty copy() {
        return new LegionLoyalty(this);
    }
}
