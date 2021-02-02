
package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author LoneFox
 */
public final class SagesKnowledge extends CardImpl {

    static final FilterCard filter = new FilterCard("sorcery card from your graveyard");

    static {
        filter.add(CardType.SORCERY.getPredicate());
    }

    public SagesKnowledge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{U}");

        // Return target sorcery card from your graveyard to your hand.
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToHandTargetEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(filter));
    }

    private SagesKnowledge(final SagesKnowledge card) {
        super(card);
    }

    @Override
    public SagesKnowledge copy() {
        return new SagesKnowledge(this);
    }
}
