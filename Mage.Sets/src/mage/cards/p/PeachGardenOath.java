
package mage.cards.p;

import java.util.UUID;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;

/**
 *
 * @author North
 */
public final class PeachGardenOath extends CardImpl {

    public PeachGardenOath(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{W}");

        // You gain 2 life for each creature you control.
        DynamicValue amount = new PermanentsOnBattlefieldCount(StaticFilters.FILTER_CONTROLLED_CREATURE, 2);
        this.getSpellAbility().addEffect(new GainLifeEffect(amount));
    }

    private PeachGardenOath(final PeachGardenOath card) {
        super(card);
    }

    @Override
    public PeachGardenOath copy() {
        return new PeachGardenOath(this);
    }
}
