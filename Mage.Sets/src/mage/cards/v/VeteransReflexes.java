

package mage.cards.v;

import java.util.UUID;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Loki
 */
public final class VeteransReflexes extends CardImpl {

    public VeteransReflexes (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{W}");

        this.getSpellAbility().addEffect(new BoostTargetEffect(1, 1, Duration.EndOfTurn));
        this.getSpellAbility().addEffect(new UntapTargetEffect().setText("untap that creature"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    public VeteransReflexes (final VeteransReflexes card) {
        super(card);
    }

    @Override
    public VeteransReflexes copy() {
        return new VeteransReflexes(this);
    }

}
