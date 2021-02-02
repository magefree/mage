
package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.common.FilterCreaturePermanent;

/**
 *
 * @author North
 */
public final class Shrivel extends CardImpl {

    public Shrivel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{B}");


        this.getSpellAbility().addEffect(new BoostAllEffect(-1, -1, Duration.EndOfTurn, new FilterCreaturePermanent("All creatures"), false));
    }

    private Shrivel(final Shrivel card) {
        super(card);
    }

    @Override
    public Shrivel copy() {
        return new Shrivel(this);
    }
}
