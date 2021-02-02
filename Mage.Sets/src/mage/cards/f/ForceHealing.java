
package mage.cards.f;

import java.util.UUID;
import mage.abilities.effects.common.PreventDamageToTargetEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author Styxo
 */
public final class ForceHealing extends CardImpl {

    public ForceHealing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{W}");

        // Prevent the next 4 damage that would be dealt to any target this turn.
        this.getSpellAbility().addEffect(new PreventDamageToTargetEffect(Duration.EndOfTurn, 4));
        this.getSpellAbility().addTarget(new TargetAnyTarget());

        // Scry 1
        this.getSpellAbility().addEffect(new ScryEffect(1));
    }

    private ForceHealing(final ForceHealing card) {
        super(card);
    }

    @Override
    public ForceHealing copy() {
        return new ForceHealing(this);
    }
}
