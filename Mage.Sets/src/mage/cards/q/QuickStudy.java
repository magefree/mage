

package mage.cards.q;

import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author Loki
 */
public final class QuickStudy extends CardImpl {

    public QuickStudy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{U}");

        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(2));
    }

    private QuickStudy(final QuickStudy card) {
        super(card);
    }

    @Override
    public QuickStudy copy() {
        return new QuickStudy(this);
    }

}
