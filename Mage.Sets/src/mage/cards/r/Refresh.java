
package mage.cards.r;

import java.util.UUID;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.RegenerateTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author cbt33
 */
public final class Refresh extends CardImpl {

    public Refresh(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{G}");


        // Regenerate target creature.
        this.getSpellAbility().addEffect(new RegenerateTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
    }

    private Refresh(final Refresh card) {
        super(card);
    }

    @Override
    public Refresh copy() {
        return new Refresh(this);
    }
}
