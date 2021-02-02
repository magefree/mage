
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DealsDamageGainLifeSourceTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LoneFox
 */
public final class HornedCheetah extends CardImpl {

    public HornedCheetah(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}{W}");
        this.subtype.add(SubType.CAT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Horned Cheetah deals damage, you gain that much life.
        this.addAbility(new DealsDamageGainLifeSourceTriggeredAbility());
    }

    private HornedCheetah(final HornedCheetah card) {
        super(card);
    }

    @Override
    public HornedCheetah copy() {
        return new HornedCheetah(this);
    }
}
