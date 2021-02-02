
package mage.cards.r;

import java.util.UUID;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.MulticoloredPredicate;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author jeffwadsworth
 */
public final class RebornHope extends CardImpl {
    
    private static final FilterCard filter = new FilterCard("multicolored card from your graveyard");
    
    static {
        filter.add(MulticoloredPredicate.instance);
    }

    public RebornHope(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{G}{W}");




        // Return target multicolored card from your graveyard to your hand.
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(filter));
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToHandTargetEffect());
    }

    private RebornHope(final RebornHope card) {
        super(card);
    }

    @Override
    public RebornHope copy() {
        return new RebornHope(this);
    }
}
