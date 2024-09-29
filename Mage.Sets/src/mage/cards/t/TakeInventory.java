
package mage.cards.t;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.NamePredicate;

/**
 *
 * @author fireshoes
 * modified tiera3 - added Hint
 */
public final class TakeInventory extends CardImpl {

    private static final FilterCard filter = new FilterCard("card named Take Inventory");

    static {
        filter.add(new NamePredicate("Take Inventory"));
    }
    private static final Hint hint = new ValueHint(
            "Cards named Take Inventory in your graveyard", new CardsInControllerGraveyardCount(filter)
    );

    public TakeInventory(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{U}");

        // Draw a card, then draw cards equal to the number of cards named Take Inventory in your graveyard.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1));
        Effect effect = new DrawCardSourceControllerEffect(new CardsInControllerGraveyardCount(filter));
        effect.setText(", then draw cards equal to the number of cards named {this} in your graveyard");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addHint(hint);
    }

    private TakeInventory(final TakeInventory card) {
        super(card);
    }

    @Override
    public TakeInventory copy() {
        return new TakeInventory(this);
    }
}
