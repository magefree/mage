
package mage.cards.g;

import java.util.UUID;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.effects.common.search.SearchLibraryGraveyardPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public final class GraspingCurrent extends CardImpl {

    private static final FilterCard filter = new FilterCard("Jace, Ingenious Mind-Mage");

    static {
        filter.add(new NamePredicate("Jace, Ingenious Mind-Mage"));
    }

    public GraspingCurrent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{U}");

        // Return up to two target creatures to their owner's hand.
        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0, 2));

        // Search your library and/or graveyard for a card named Jace, Ingenious Mind-Mage, reveal it, then put it into your hand. If you searched your library this way, shuffle it.
        this.getSpellAbility().addEffect(new SearchLibraryGraveyardPutInHandEffect(filter).concatBy("<br>"));
    }

    private GraspingCurrent(final GraspingCurrent card) {
        super(card);
    }

    @Override
    public GraspingCurrent copy() {
        return new GraspingCurrent(this);
    }
}
