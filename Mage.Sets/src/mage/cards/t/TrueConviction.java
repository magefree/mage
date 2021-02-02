
package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 *
 * @author nantuko
 */
public final class TrueConviction extends CardImpl {

    public TrueConviction(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{W}{W}{W}");

        // Creatures you control have double strike and lifelink.
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityControlledEffect(DoubleStrikeAbility.getInstance(), Duration.WhileOnBattlefield, StaticFilters.FILTER_PERMANENT_CREATURES));
        Effect effect = new GainAbilityControlledEffect(LifelinkAbility.getInstance(), Duration.WhileOnBattlefield, StaticFilters.FILTER_PERMANENT_CREATURES);
        effect.setText(" and lifelink");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    private TrueConviction(final TrueConviction card) {
        super(card);
    }

    @Override
    public TrueConviction copy() {
        return new TrueConviction(this);
    }

}
