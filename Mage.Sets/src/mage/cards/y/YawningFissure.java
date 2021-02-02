
package mage.cards.y;

import java.util.UUID;
import mage.abilities.effects.common.SacrificeOpponentsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterControlledLandPermanent;

/**
 *
 * @author North
 */
public final class YawningFissure extends CardImpl {

    public YawningFissure(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{4}{R}");


        // Each opponent sacrifices a land.
        this.getSpellAbility().addEffect(new SacrificeOpponentsEffect(new FilterControlledLandPermanent("a land")));
    }

    private YawningFissure(final YawningFissure card) {
        super(card);
    }

    @Override
    public YawningFissure copy() {
        return new YawningFissure(this);
    }
}
