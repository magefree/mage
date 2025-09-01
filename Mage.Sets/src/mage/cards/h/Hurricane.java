
package mage.cards.h;

import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.common.DamageEverythingEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author Quercitron
 */
public final class Hurricane extends CardImpl {

    public Hurricane(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{G}");

        // Hurricane deals X damage to each creature with flying and each player.
        this.getSpellAbility().addEffect(new DamageEverythingEffect(GetXValue.instance, StaticFilters.FILTER_CREATURE_FLYING));
    }

    private Hurricane(final Hurricane card) {
        super(card);
    }

    @Override
    public Hurricane copy() {
        return new Hurricane(this);
    }
}
