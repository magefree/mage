
package mage.cards.i;

import java.util.UUID;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.common.FilterCreaturePermanent;

/**
 *
 * @author Loki
 */
public final class Infest extends CardImpl {

    public Infest(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{B}{B}");


        // All creatures get -2/-2 until end of turn.
        this.getSpellAbility().addEffect(new BoostAllEffect(-2, -2, Duration.EndOfTurn, new FilterCreaturePermanent("All creatures"), false));
    }

    private Infest(final Infest card) {
        super(card);
    }

    @Override
    public Infest copy() {
        return new Infest(this);
    }
}
