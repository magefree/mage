
package mage.cards.w;

import java.util.UUID;
import mage.abilities.effects.common.PreventDamageByTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetAttackingCreature;

/**
 *
 * @author LoneFox
 */
public final class Warning extends CardImpl {

    public Warning(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{W}");

        // Prevent all combat damage that would be dealt by target attacking creature this turn.
        this.getSpellAbility().addEffect(new PreventDamageByTargetEffect(Duration.EndOfTurn, true));
        this.getSpellAbility().addTarget(new TargetAttackingCreature());
    }

    private Warning(final Warning card) {
        super(card);
    }

    @Override
    public Warning copy() {
        return new Warning(this);
    }
}
