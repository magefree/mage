

package mage.cards.p;

import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author Loki
 */
public final class PullUnder extends CardImpl {

    public PullUnder (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{5}{B}");
        this.subtype.add(SubType.ARCANE);

        this.getSpellAbility().addEffect(new BoostTargetEffect(-5, -5, Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private PullUnder(final PullUnder card) {
        super(card);
    }

    @Override
    public PullUnder copy() {
        return new PullUnder(this);
    }

}
