
package mage.cards.f;

import java.util.UUID;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author fireshoes
 */
public final class FuriousReprisal extends CardImpl {

    public FuriousReprisal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{R}");

        // Furious Reprisal deals 2 damage to each of two target creatures and/or players.
        this.getSpellAbility().addEffect(new DamageTargetEffect(2, true, "each of two targets"));
        this.getSpellAbility().addTarget(new TargetAnyTarget(2, 2));
    }

    private FuriousReprisal(final FuriousReprisal card) {
        super(card);
    }

    @Override
    public FuriousReprisal copy() {
        return new FuriousReprisal(this);
    }
}
