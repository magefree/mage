package mage.cards.a;

import java.util.UUID;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ColorlessPredicate;

/**
 * @author LevelX
 */
public final class AncientStirrings extends CardImpl {

    private static final FilterCard filter = new FilterCard("a colorless card");

    static {
        filter.add(ColorlessPredicate.instance);
    }

    public AncientStirrings(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{G}");

        // Look at the top five cards of your library. You may reveal a colorless card from among them and put it into your hand. 
        // Then put the rest on the bottom of your library in any order. (Cards with no colored mana in their mana costs are colorless. Lands are also colorless.)
        this.getSpellAbility().addEffect(new LookLibraryAndPickControllerEffect(5, 1, filter, PutCards.HAND, PutCards.BOTTOM_ANY));
    }

    private AncientStirrings(final AncientStirrings card) {
        super(card);
    }

    @Override
    public AncientStirrings copy() {
        return new AncientStirrings(this);
    }
}
