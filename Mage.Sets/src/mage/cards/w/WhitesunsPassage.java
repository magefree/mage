

package mage.cards.w;

import java.util.UUID;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author Loki
 */
public final class WhitesunsPassage extends CardImpl {

    public WhitesunsPassage (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{W}");

        this.getSpellAbility().addEffect(new GainLifeEffect(5));
    }

    private WhitesunsPassage(final WhitesunsPassage card) {
        super(card);
    }

    @Override
    public WhitesunsPassage copy() {
        return new WhitesunsPassage(this);
    }

}
