
package mage.cards.g;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.combat.AttacksIfAbleAllEffect;
import mage.abilities.effects.common.combat.BlocksIfAbleAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.watchers.common.AttackedThisTurnWatcher;

/**
 *
 * @author fenhl
 */
public final class GrandMelee extends CardImpl {

    public GrandMelee(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{R}");

        // All creatures attack each turn if able.
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new AttacksIfAbleAllEffect(StaticFilters.FILTER_PERMANENT_CREATURES));
        this.addAbility(ability, new AttackedThisTurnWatcher());

        // All creatures block each turn if able.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BlocksIfAbleAllEffect(StaticFilters.FILTER_PERMANENT_CREATURES)));
    }

    private GrandMelee(final GrandMelee card) {
        super(card);
    }

    @Override
    public GrandMelee copy() {
        return new GrandMelee(this);
    }
}
