
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.MountainwalkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.StaticFilters;

/**
 * @author Loki
 */
public final class GoblinKing extends CardImpl {

    public GoblinKing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{R}");
        this.subtype.add(SubType.GOBLIN);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Other Goblin creatures get +1/+1 and have mountainwalk.
        Effect effect = new BoostAllEffect(1, 1, Duration.WhileOnBattlefield, StaticFilters.FILTER_PERMANENT_CREATURE_GOBLINS, true);
        effect.setText("Other Goblins get +1/+1");
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, effect);
        effect = new GainAbilityAllEffect(new MountainwalkAbility(), Duration.WhileOnBattlefield, StaticFilters.FILTER_PERMANENT_CREATURE_GOBLINS, true);
        effect.setText("and have mountainwalk");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    private GoblinKing(final GoblinKing card) {
        super(card);
    }

    @Override
    public GoblinKing copy() {
        return new GoblinKing(this);
    }
}
