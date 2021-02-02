
package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.common.PreventDamageToTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class ShieldedPassage extends CardImpl {

    public ShieldedPassage (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{W}");


        // Prevent all damage that would be dealt to target creature this turn.
        this.getSpellAbility().addEffect(new PreventDamageToTargetEffect(Duration.EndOfTurn, Integer.MAX_VALUE));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private ShieldedPassage(final ShieldedPassage card) {
        super(card);
    }

    @Override
    public ShieldedPassage  copy() {
        return new ShieldedPassage(this);
    }
}
