
package mage.cards.a;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.CardsInAllGraveyardsCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.NamePredicate;

/**
 *
 * @author LevelX2
 */
public final class AccumulatedKnowledge extends CardImpl {

    private static final FilterCard filter = new FilterCard("card named Accumulated Knowledge");

    static {
        filter.add(new NamePredicate("Accumulated Knowledge"));
    }

    public AccumulatedKnowledge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{U}");


        // Draw a card, then draw cards equal to the number of cards named Accumulated Knowledge in all graveyards.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1));
        Effect effect = new DrawCardSourceControllerEffect(new CardsInAllGraveyardsCount(filter));
        effect.setText(", then draw cards equal to the number of cards named {this} in all graveyards");
        this.getSpellAbility().addEffect(effect);
    }

    private AccumulatedKnowledge(final AccumulatedKnowledge card) {
        super(card);
    }

    @Override
    public AccumulatedKnowledge copy() {
        return new AccumulatedKnowledge(this);
    }
}
