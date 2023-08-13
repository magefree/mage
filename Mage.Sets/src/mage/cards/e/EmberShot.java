
package mage.cards.e;

import java.util.UUID;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author LoneFox
 */
public final class EmberShot extends CardImpl {

    public EmberShot(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{6}{R}");

        // Ember Shot deals 3 damage to any target.
        this.getSpellAbility().addEffect(new DamageTargetEffect(3));
        this.getSpellAbility().addTarget(new TargetAnyTarget());
        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
    }

    private EmberShot(final EmberShot card) {
        super(card);
    }

    @Override
    public EmberShot copy() {
        return new EmberShot(this);
    }
}
