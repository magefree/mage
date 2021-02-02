
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.StaticFilters;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class GoblinChieftain extends CardImpl {

    public GoblinChieftain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{R}");
        this.subtype.add(SubType.GOBLIN);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Haste (This creature can attack and as soon as it comes under your control.)
        this.addAbility(HasteAbility.getInstance());

        // Other Goblin creatures you control get +1/+1 and have haste.
        Effect effect = new BoostControlledEffect(1, 1, Duration.WhileOnBattlefield, StaticFilters.FILTER_PERMANENT_CREATURE_GOBLINS, true);
        effect.setText("Other Goblin creatures you control get +1/+1");
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, effect);
        effect = new GainAbilityControlledEffect(HasteAbility.getInstance(), Duration.WhileOnBattlefield, StaticFilters.FILTER_PERMANENT_CREATURE_GOBLINS, true);
        effect.setText("and have haste");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    private GoblinChieftain(final GoblinChieftain card) {
        super(card);
    }

    @Override
    public GoblinChieftain copy() {
        return new GoblinChieftain(this);
    }

}
