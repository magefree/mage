

package mage.cards.t;

import java.util.UUID;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.SwitchPowerToughnessTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author ayratn
 */
public final class TwistedImage extends CardImpl {

    public TwistedImage (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{U}");

        this.getSpellAbility().addEffect(new SwitchPowerToughnessTargetEffect(Duration.EndOfTurn));
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    public TwistedImage (final TwistedImage card) {
        super(card);
    }

    @Override
    public TwistedImage copy() {
        return new TwistedImage(this);
    }

}
