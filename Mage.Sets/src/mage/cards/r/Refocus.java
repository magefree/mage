
package mage.cards.r;

import java.util.UUID;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class Refocus extends CardImpl {

    public Refocus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{U}");

        // Untap target creature.
        this.getSpellAbility().addEffect(new UntapTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
    }

    private Refocus(final Refocus card) {
        super(card);
    }

    @Override
    public Refocus copy() {
        return new Refocus(this);
    }
}
