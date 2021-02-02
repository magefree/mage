
package mage.cards.c;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.common.PutCardIntoGraveFromAnywhereAllTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ColorPredicate;

/**
 *
 * @author emerald000
 */
public final class Compost extends CardImpl {

    private static final FilterCard filter = new FilterCard("a black card");

    static {
        filter.add(new ColorPredicate(ObjectColor.BLACK));
    }

    public Compost(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{G}");

        // Whenever a black card is put into an opponent's graveyard from anywhere, you may draw a card.
        this.addAbility(new PutCardIntoGraveFromAnywhereAllTriggeredAbility(
                new DrawCardSourceControllerEffect(1), true, filter, TargetController.OPPONENT));
    }

    private Compost(final Compost card) {
        super(card);
    }

    @Override
    public Compost copy() {
        return new Compost(this);
    }
}
