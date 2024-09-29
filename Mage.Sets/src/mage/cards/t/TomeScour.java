

package mage.cards.t;

import java.util.UUID;
import mage.abilities.effects.common.MillCardsTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPlayer;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class TomeScour extends CardImpl {

    public TomeScour(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{U}");

        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addEffect(new MillCardsTargetEffect(5));
    }

    private TomeScour(final TomeScour card) {
        super(card);
    }

    @Override
    public TomeScour copy() {
        return new TomeScour(this);
    }
}
