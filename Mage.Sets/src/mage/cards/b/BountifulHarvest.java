

package mage.cards.b;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.common.FilterControlledPermanent;

/**
 * @author Loki
 */
public final class BountifulHarvest extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledLandPermanent();

    public BountifulHarvest(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{4}{G}");


        // You gain 1 life for each land you control.
        this.getSpellAbility().addEffect(new GainLifeEffect(new PermanentsOnBattlefieldCount(filter)));
    }

    private BountifulHarvest(final BountifulHarvest card) {
        super(card);
    }

    @Override
    public BountifulHarvest copy() {
        return new BountifulHarvest(this);
    }

}
