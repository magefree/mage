
package mage.cards.o;

import java.util.UUID;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author jeffwadsworth
 */
public final class ObzedatsAid extends CardImpl {
    
    private static final FilterCard filter = new FilterCard("permanent card from your graveyard");
    
    static {
        filter.add(Predicates.or(
                new CardTypePredicate(CardType.ARTIFACT),
                new CardTypePredicate(CardType.CREATURE),
                new CardTypePredicate(CardType.ENCHANTMENT),
                new CardTypePredicate(CardType.LAND),
                new CardTypePredicate(CardType.PLANESWALKER)));
    }

    public ObzedatsAid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{W}{B}");


        // Return target permanent card from your graveyard to the battlefield.
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToBattlefieldTargetEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(filter));
        
    }

    public ObzedatsAid(final ObzedatsAid card) {
        super(card);
    }

    @Override
    public ObzedatsAid copy() {
        return new ObzedatsAid(this);
    }
}
