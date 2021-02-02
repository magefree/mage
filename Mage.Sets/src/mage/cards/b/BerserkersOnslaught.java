
package mage.cards.b;

import java.util.UUID;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterAttackingCreature;

/**
 *
 * @author LevelX2
 */
public final class BerserkersOnslaught extends CardImpl {

    public BerserkersOnslaught(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{3}{R}{R}");

        // Attacking creatures you control have double strike.
        GainAbilityControlledEffect gainEffect = new GainAbilityControlledEffect(DoubleStrikeAbility.getInstance(), Duration.WhileOnBattlefield, new FilterAttackingCreature("Attacking creatures"), false);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, gainEffect));

    }

    private BerserkersOnslaught(final BerserkersOnslaught card) {
        super(card);
    }

    @Override
    public BerserkersOnslaught copy() {
        return new BerserkersOnslaught(this);
    }
}
