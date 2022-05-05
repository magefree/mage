
package mage.cards.i;

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
 * @author jeffwadsworth

 */
public final class InsideOut extends CardImpl {

    public InsideOut(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{U/R}");


        // Switch target creature's power and toughness until end of turn.
        this.getSpellAbility().addEffect(new SwitchPowerToughnessTargetEffect(Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        
        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
        
    }

    private InsideOut(final InsideOut card) {
        super(card);
    }

    @Override
    public InsideOut copy() {
        return new InsideOut(this);
    }
}
