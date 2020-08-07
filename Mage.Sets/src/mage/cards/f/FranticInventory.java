package mage.cards.f;

import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.NamePredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FranticInventory extends CardImpl {

    private static final FilterCard filter = new FilterCard();

    static {
        filter.add(new NamePredicate("Frantic Inventory"));
    }

    private static final DynamicValue xValue = new CardsInControllerGraveyardCount(filter);

    public FranticInventory(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Draw a card, then draw cards equal to the number of cards named Frantic Inventory in your graveyard.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1));
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(xValue)
                .setText(", then draw cards equal to the number of cards named Frantic Inventory in your graveyard"));
    }

    private FranticInventory(final FranticInventory card) {
        super(card);
    }

    @Override
    public FranticInventory copy() {
        return new FranticInventory(this);
    }
}
