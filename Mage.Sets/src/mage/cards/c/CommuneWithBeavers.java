package mage.cards.c;

import java.util.UUID;

import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.filter.FilterCard;
import mage.filter.common.FilterPermanentCard;
import mage.filter.predicate.Predicates;

/**
 * @author balazskristof
 */
public final class CommuneWithBeavers extends CardImpl {

    private static final FilterCard filter = new FilterPermanentCard("an artifact, creature, or land card");

    static {
        filter.add(Predicates.or(
            CardType.ARTIFACT.getPredicate(),
            CardType.CREATURE.getPredicate(),
            CardType.LAND.getPredicate()
        ));
    }

    public CommuneWithBeavers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{G}");

        // Look at the top three cards of your library. You may reveal an artifact, creature, or land card from among them and put it into your hand. Put the rest on hte bottom of your library in any order.
        this.getSpellAbility().addEffect(new LookLibraryAndPickControllerEffect(3, 1, filter, PutCards.HAND, PutCards.BOTTOM_ANY));
    }

    private CommuneWithBeavers(final CommuneWithBeavers card) {
        super(card);
    }

    @Override
    public CommuneWithBeavers copy() {
        return new CommuneWithBeavers(this);
    }
}