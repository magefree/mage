
package mage.cards.t;

import java.util.UUID;
import mage.abilities.effects.common.combat.MustBeBlockedByAllTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class TauntingChallenge extends CardImpl {

    public TauntingChallenge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{G}{G}");

        // All creatures able to block target creature this turn do so.
        this.getSpellAbility().addEffect(new MustBeBlockedByAllTargetEffect(Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private TauntingChallenge(final TauntingChallenge card) {
        super(card);
    }

    @Override
    public TauntingChallenge copy() {
        return new TauntingChallenge(this);
    }
}
