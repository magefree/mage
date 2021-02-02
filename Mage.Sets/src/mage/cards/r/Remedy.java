
package mage.cards.r;

import java.util.UUID;
import mage.abilities.effects.common.PreventDamageToTargetMultiAmountEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetAnyTargetAmount;

/**
 *
 * @author Quercitron
 */
public final class Remedy extends CardImpl {

    public Remedy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{W}");


        // Prevent the next 5 damage that would be dealt this turn to any number of target creatures and/or players, divided as you choose.
        this.getSpellAbility().addEffect(new PreventDamageToTargetMultiAmountEffect(Duration.EndOfTurn, 5));
        this.getSpellAbility().addTarget(new TargetAnyTargetAmount(5));
    }

    private Remedy(final Remedy card) {
        super(card);
    }

    @Override
    public Remedy copy() {
        return new Remedy(this);
    }
}
