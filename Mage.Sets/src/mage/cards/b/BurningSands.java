
package mage.cards.b;

import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.common.SacrificeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class BurningSands extends CardImpl {

    public BurningSands(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{R}{R}");

        // Whenever a creature dies, that creature's controller sacrifices a land.
        this.addAbility(new DiesCreatureTriggeredAbility(
                new SacrificeEffect(StaticFilters.FILTER_LAND, 1, "that creature's controller"),
                SetTargetPointer.PLAYER));
    }

    private BurningSands(final BurningSands card) {
        super(card);
    }

    @Override
    public BurningSands copy() {
        return new BurningSands(this);
    }
}
