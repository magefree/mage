
package mage.cards.f;

import java.util.UUID;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetSpell;

/**
 *
 * @author LevelX2
 */
public final class FailedInspection extends CardImpl {

    public FailedInspection(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{U}{U}");

        // Counter target spell.  Draw a card, then discard a card.
        this.getSpellAbility().addEffect(new CounterTargetEffect());
        this.getSpellAbility().addTarget(new TargetSpell());
        this.getSpellAbility().addEffect(new DrawDiscardControllerEffect(1, 1));
    }

    private FailedInspection(final FailedInspection card) {
        super(card);
    }

    @Override
    public FailedInspection copy() {
        return new FailedInspection(this);
    }
}
