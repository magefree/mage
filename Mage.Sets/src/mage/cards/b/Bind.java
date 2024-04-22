
package mage.cards.b;

import java.util.UUID;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetActivatedAbility;

/**
 *
 * @author LoneFox

 */
public final class Bind extends CardImpl {

    public Bind(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{G}");

        // Counter target activated ability.
        this.getSpellAbility().addEffect(new CounterTargetEffect());
        this.getSpellAbility().addTarget(new TargetActivatedAbility());
        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
    }

    private Bind(final Bind card) {
        super(card);
    }

    @Override
    public Bind copy() {
        return new Bind(this);
    }
}
