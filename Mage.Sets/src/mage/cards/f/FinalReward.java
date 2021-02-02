
package mage.cards.f;

import java.util.UUID;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class FinalReward extends CardImpl {

    public FinalReward(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{B}");

        // Exile target creature.
        getSpellAbility().addEffect(new ExileTargetEffect());
        getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private FinalReward(final FinalReward card) {
        super(card);
    }

    @Override
    public FinalReward copy() {
        return new FinalReward(this);
    }
}
