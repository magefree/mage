
package mage.cards.l;

import java.util.UUID;
import mage.abilities.effects.keyword.SupportEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author LevelX2
 */
public final class LeadByExample extends CardImpl {

    public LeadByExample(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{G}");

        // Support 2.
        getSpellAbility().addEffect(new SupportEffect(this, 2, false));
    }

    private LeadByExample(final LeadByExample card) {
        super(card);
    }

    @Override
    public LeadByExample copy() {
        return new LeadByExample(this);
    }
}
