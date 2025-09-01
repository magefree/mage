
package mage.cards.e;

import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.SacrificeEffect;
import mage.abilities.effects.common.SacrificeSourceUnlessPaysEffect;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 *
 * @author TheElk801
 */
public final class Earthlink extends CardImpl {

    public Earthlink(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{B}{R}{G}");

        // At the beginning of your upkeep, sacrifice Earthlink unless you pay {2}.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new SacrificeSourceUnlessPaysEffect(new ManaCostsImpl<>("{2}"))));

        // Whenever a creature dies, that creature's controller sacrifices a land.
        this.addAbility(new DiesCreatureTriggeredAbility(
                new SacrificeEffect(StaticFilters.FILTER_LAND, 1, "that creature's controller"),
                SetTargetPointer.PLAYER));
    }

    private Earthlink(final Earthlink card) {
        super(card);
    }

    @Override
    public Earthlink copy() {
        return new Earthlink(this);
    }
}
