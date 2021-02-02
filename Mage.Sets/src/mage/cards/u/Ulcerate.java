
package mage.cards.u;

import java.util.UUID;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 * @author noxx
 */
public final class Ulcerate extends CardImpl {

    public Ulcerate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{B}");


        // Target creature gets -3/-3 until end of turn. You lose 3 life.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new BoostTargetEffect(-3, -3, Duration.EndOfTurn));
        this.getSpellAbility().addEffect(new LoseLifeSourceControllerEffect(3));
    }

    private Ulcerate(final Ulcerate card) {
        super(card);
    }

    @Override
    public Ulcerate copy() {
        return new Ulcerate(this);
    }
}
