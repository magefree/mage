
package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.common.PreventDamageToTargetEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardSetInfo;
import mage.cards.SplitCard;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SpellAbilityType;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LoneFox
 */
public final class StandDeliver extends SplitCard {

    public StandDeliver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{W}", "{2}{U}", SpellAbilityType.SPLIT);

        // Stand
        // Prevent the next 2 damage that would be dealt to target creature this turn.
        this.getLeftHalfCard().getSpellAbility().addEffect(new PreventDamageToTargetEffect(Duration.EndOfTurn, 2));
        this.getLeftHalfCard().getSpellAbility().addTarget(new TargetCreaturePermanent());
        // Deliver
        // Return target permanent to its owner's hand.
        this.getRightHalfCard().getSpellAbility().addEffect(new ReturnToHandTargetEffect());
        this.getRightHalfCard().getSpellAbility().addTarget(new TargetPermanent());
    }

    private StandDeliver(final StandDeliver card) {
        super(card);
    }

    @Override
    public StandDeliver copy() {
        return new StandDeliver(this);
    }
}
