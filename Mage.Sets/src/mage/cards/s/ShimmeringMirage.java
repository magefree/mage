
package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BecomesBasicLandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author LoneFox
 */
public final class ShimmeringMirage extends CardImpl {

    public ShimmeringMirage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Target land becomes the basic land type of your choice until end of turn.
        this.getSpellAbility().addEffect(new BecomesBasicLandTargetEffect(Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetLandPermanent());
        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
    }

    private ShimmeringMirage(final ShimmeringMirage card) {
        super(card);
    }

    @Override
    public ShimmeringMirage copy() {
        return new ShimmeringMirage(this);
    }
}
