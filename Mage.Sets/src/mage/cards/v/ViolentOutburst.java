
package mage.cards.v;

import java.util.UUID;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.CascadeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author North
 */
public final class ViolentOutburst extends CardImpl {

    public ViolentOutburst(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{R}{G}");




        this.addAbility(new CascadeAbility());
        this.getSpellAbility().addEffect(new BoostControlledEffect(1, 0, Duration.EndOfTurn));
    }

    private ViolentOutburst(final ViolentOutburst card) {
        super(card);
    }

    @Override
    public ViolentOutburst copy() {
        return new ViolentOutburst(this);
    }
}
