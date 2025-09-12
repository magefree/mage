
package mage.cards.s;

import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.common.DamageEverythingEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author LoneFox
 */
public final class SquallLine extends CardImpl {

    public SquallLine(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{X}{G}{G}");

        // Squall Line deals X damage to each creature with flying and each player.
        this.getSpellAbility().addEffect(new DamageEverythingEffect(GetXValue.instance, StaticFilters.FILTER_CREATURE_FLYING));
    }

    private SquallLine(final SquallLine card) {
        super(card);
    }

    @Override
    public SquallLine copy() {
        return new SquallLine(this);
    }
}
