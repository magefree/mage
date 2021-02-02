
package mage.cards.a;

import java.util.UUID;
import mage.abilities.effects.common.combat.MustBeBlockedByAllTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author ilcartographer
 */
public final class AlluringScent extends CardImpl {

    public AlluringScent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{G}{G}");

        // All creatures able to block target creature this turn do so.
        this.getSpellAbility().addEffect(new MustBeBlockedByAllTargetEffect(Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private AlluringScent(final AlluringScent card) {
        super(card);
    }

    @Override
    public AlluringScent copy() {
        return new AlluringScent(this);
    }
}
