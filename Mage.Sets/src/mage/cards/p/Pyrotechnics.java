
package mage.cards.p;

import java.util.UUID;
import mage.abilities.effects.common.DamageMultiEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAnyTargetAmount;

/**
 *
 * @author Quercitron
 */
public final class Pyrotechnics extends CardImpl {

    public Pyrotechnics(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{4}{R}");


        // Pyrotechnics deals 4 damage divided as you choose among any number of targets.
        this.getSpellAbility().addEffect(new DamageMultiEffect(4));
        this.getSpellAbility().addTarget(new TargetAnyTargetAmount(4));
    }

    private Pyrotechnics(final Pyrotechnics card) {
        super(card);
    }

    @Override
    public Pyrotechnics copy() {
        return new Pyrotechnics(this);
    }
}
