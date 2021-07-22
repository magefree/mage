
package mage.cards.a;

import java.util.UUID;
import mage.abilities.effects.common.ExileCardYouChooseTargetOpponentEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.target.common.TargetOpponent;

/**
 *
 * @author North
 */
public final class AppetiteForBrains extends CardImpl {

    private static final FilterCard filter = new FilterCard("a card from it with mana value 4 or greater");
    
    static {
        filter.add(new ManaValuePredicate(ComparisonType.MORE_THAN, 3));
    }

    public AppetiteForBrains(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{B}");


        // Target opponent reveals their hand. You choose a card from it with converted mana cost 4 or greater and exile that card.
        this.getSpellAbility().addEffect(new ExileCardYouChooseTargetOpponentEffect(filter));
        this.getSpellAbility().addTarget(new TargetOpponent());
    }

    private AppetiteForBrains(final AppetiteForBrains card) {
        super(card);
    }

    @Override
    public AppetiteForBrains copy() {
        return new AppetiteForBrains(this);
    }
}
