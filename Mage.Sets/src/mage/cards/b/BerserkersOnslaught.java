package mage.cards.b;

import java.util.UUID;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;

/**
 *
 * @author LevelX2
 */
public final class BerserkersOnslaught extends CardImpl {

    public BerserkersOnslaught(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{3}{R}{R}");

        // Attacking creatures you control have double strike.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(DoubleStrikeAbility.getInstance(), Duration.WhileOnBattlefield, StaticFilters.FILTER_ATTACKING_CREATURES, false)));
    }

    private BerserkersOnslaught(final BerserkersOnslaught card) {
        super(card);
    }

    @Override
    public BerserkersOnslaught copy() {
        return new BerserkersOnslaught(this);
    }
}
