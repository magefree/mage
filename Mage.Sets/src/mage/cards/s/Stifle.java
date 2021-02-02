
package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetActivatedOrTriggeredAbility;


/**
 *
 * @author Plopman
 */
public final class Stifle extends CardImpl {

    public Stifle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{U}");


        // Counter target activated or triggered ability.
        this.getSpellAbility().addEffect(new CounterTargetEffect());
        this.getSpellAbility().addTarget(new TargetActivatedOrTriggeredAbility());
    }

    private Stifle(final Stifle card) {
        super(card);
    }

    @Override
    public Stifle copy() {
        return new Stifle(this);
    }
}
