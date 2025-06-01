
package mage.cards.r;

import mage.abilities.effects.common.PreventNextDamageFromChosenSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

import java.util.UUID;

/**
 * @author Quercitron
 */
public final class ReverseDamage extends CardImpl {

    public ReverseDamage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}{W}");


        // The next time a source of your choice would deal damage to you this turn, prevent that damage. You gain life equal to the damage prevented this way.
        this.getSpellAbility().addEffect(
                new PreventNextDamageFromChosenSourceEffect(
                        Duration.EndOfTurn, true,
                        PreventNextDamageFromChosenSourceEffect.ON_PREVENT_YOU_GAIN_THAT_MUCH_LIFE
                )
        );
    }

    private ReverseDamage(final ReverseDamage card) {
        super(card);
    }

    @Override
    public ReverseDamage copy() {
        return new ReverseDamage(this);
    }
}