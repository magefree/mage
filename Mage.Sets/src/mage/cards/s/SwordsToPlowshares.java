
package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.common.ExileAndGainLifeEqualPowerTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author jonubuu
 */
public final class SwordsToPlowshares extends CardImpl {

    public SwordsToPlowshares(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{W}");

        // Exile target creature. Its controller gains life equal to its power.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new ExileAndGainLifeEqualPowerTargetEffect());

    }

    private SwordsToPlowshares(final SwordsToPlowshares card) {
        super(card);
    }

    @Override
    public SwordsToPlowshares copy() {
        return new SwordsToPlowshares(this);
    }
}
