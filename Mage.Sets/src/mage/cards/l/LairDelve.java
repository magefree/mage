
package mage.cards.l;

import java.util.UUID;
import mage.abilities.effects.common.RevealLibraryPutIntoHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;

/**
 *
 * @author North
 */
public final class LairDelve extends CardImpl {

    private static final FilterCard filter = new FilterCard("creature and land cards");

    static {
        filter.add(Predicates.or(CardType.LAND.getPredicate(), CardType.CREATURE.getPredicate()));

    }

    public LairDelve(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{G}");

        // Reveal the top two cards of your library. Put all creature and land cards revealed this way into your hand and the rest on the bottom of your library in any order.
        this.getSpellAbility().addEffect(new RevealLibraryPutIntoHandEffect(2, filter, Zone.LIBRARY));
    }

    private LairDelve(final LairDelve card) {
        super(card);
    }

    @Override
    public LairDelve copy() {
        return new LairDelve(this);
    }
}
