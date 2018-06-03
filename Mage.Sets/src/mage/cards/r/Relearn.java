
package mage.cards.r;

import java.util.UUID;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author Quercitron
 */
public final class Relearn extends CardImpl {

    static final FilterCard filter = new FilterCard("instant or sorcery card from your graveyard");
    
    static {
        filter.add(Predicates.or(
                new CardTypePredicate(CardType.INSTANT),
                new CardTypePredicate(CardType.SORCERY)));
    }
    
    public Relearn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{U}{U}");


        // Return target instant or sorcery card from your graveyard to your hand.
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToHandTargetEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(filter));
    }

    public Relearn(final Relearn card) {
        super(card);
    }

    @Override
    public Relearn copy() {
        return new Relearn(this);
    }
}
