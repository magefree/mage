

package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class StabbingPain extends CardImpl {

    public StabbingPain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{B}");

        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new BoostTargetEffect(-1, -1, Duration.EndOfTurn));
        this.getSpellAbility().addEffect(new TapTargetEffect("tap that creature"));
    }

    private StabbingPain(final StabbingPain card) {
        super(card);
    }

    @Override
    public StabbingPain copy() {
        return new StabbingPain(this);
    }
}
