
package mage.cards.a;

import java.util.UUID;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.common.FilterBlockingCreature;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LoneFox
 */
public final class AlibansTower extends CardImpl {

    public AlibansTower(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{R}");

        // Target blocking creature gets +3/+1 until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(3, 1, Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(new FilterBlockingCreature()));
    }

    private AlibansTower(final AlibansTower card) {
        super(card);
    }

    @Override
    public AlibansTower copy() {
        return new AlibansTower(this);
    }
}
