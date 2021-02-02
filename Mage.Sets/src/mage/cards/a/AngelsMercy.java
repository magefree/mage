

package mage.cards.a;

import java.util.UUID;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class AngelsMercy extends CardImpl {

    public AngelsMercy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{W}{W}");

        this.getSpellAbility().addEffect(new GainLifeEffect(7));
    }

    private AngelsMercy(final AngelsMercy card) {
        super(card);
    }

    @Override
    public AngelsMercy copy() {
        return new AngelsMercy(this);
    }
}
