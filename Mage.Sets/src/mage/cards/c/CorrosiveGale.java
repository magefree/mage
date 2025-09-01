
package mage.cards.c;

import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.common.DamageAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author North
 */
public final class CorrosiveGale extends CardImpl {

    public CorrosiveGale(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{G/P}");

        this.getSpellAbility().addEffect(new DamageAllEffect(GetXValue.instance, StaticFilters.FILTER_CREATURE_FLYING));
    }

    private CorrosiveGale(final CorrosiveGale card) {
        super(card);
    }

    @Override
    public CorrosiveGale copy() {
        return new CorrosiveGale(this);
    }
}
