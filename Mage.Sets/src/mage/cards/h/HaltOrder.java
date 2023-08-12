
package mage.cards.h;

import java.util.UUID;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterArtifactSpell;
import mage.target.TargetSpell;

/**
 *
 * @author Loki
 */
public final class HaltOrder extends CardImpl {
    public HaltOrder (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{U}");

        // Counter target artifact spell. Draw a card.
        this.getSpellAbility().addTarget(new TargetSpell(new FilterArtifactSpell()));
        this.getSpellAbility().addEffect(new CounterTargetEffect());
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
    }

    public HaltOrder (final HaltOrder card) {
        super(card);
    }

    @Override
    public HaltOrder copy() {
        return new HaltOrder(this);
    }
}
