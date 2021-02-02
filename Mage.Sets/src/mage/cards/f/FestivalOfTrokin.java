
package mage.cards.f;

import java.util.UUID;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterControlledCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class FestivalOfTrokin extends CardImpl {

    public FestivalOfTrokin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{W}");

        // You gain 2 life for each creature you control.
        DynamicValue amount = new PermanentsOnBattlefieldCount(new FilterControlledCreaturePermanent(), 2);
        this.getSpellAbility().addEffect(new GainLifeEffect(amount));
    }

    private FestivalOfTrokin(final FestivalOfTrokin card) {
        super(card);
    }

    @Override
    public FestivalOfTrokin copy() {
        return new FestivalOfTrokin(this);
    }
}
