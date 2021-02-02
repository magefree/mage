
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.StaticFilters;

/**
 *
 * @author FenrisulfrX
 */
public final class SpidersilkArmor extends CardImpl {

    public SpidersilkArmor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{G}");

        // Creatures you control get +0/+1 and have reach.
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD,
                new BoostControlledEffect(0, 1, Duration.WhileOnBattlefield));
        Effect effect = new GainAbilityControlledEffect(ReachAbility.getInstance(),
                Duration.WhileOnBattlefield, StaticFilters.FILTER_PERMANENT_CREATURES);
        effect.setText("and have reach.");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    private SpidersilkArmor(final SpidersilkArmor card) {
        super(card);
    }

    @Override
    public SpidersilkArmor copy() {
        return new SpidersilkArmor(this);
    }
}
