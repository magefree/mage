
package mage.cards.t;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.UntapAllControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterControlledCreaturePermanent;

/**
 *
 * @author jj-marcel
 */
public final class ToArms extends CardImpl {

     private static final String rule = "untap all creatures you control";
      
    public ToArms(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{W}");


        // Untap all creatures you control.
        Effect effect = new UntapAllControllerEffect(new FilterControlledCreaturePermanent(), rule);
        this.getSpellAbility().addEffect(effect);
        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
    }

    private ToArms(final ToArms card) {
        super(card);
    }

    @Override
    public ToArms copy() {
        return new ToArms(this);
    }
}
