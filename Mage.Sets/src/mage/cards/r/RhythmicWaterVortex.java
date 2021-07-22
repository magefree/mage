package mage.cards.r;

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
public final class RhythmicWaterVortex extends CardImpl {

    private static final FilterCard filter = new FilterCard("Mu Yanling");

    static {
        filter.add(new NamePredicate("Mu Yanling"));
    }

    public RhythmicWaterVortex(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{U}{U}");

        // Return up to two target creatures to their owner's hand.
        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0, 2));

        // Search your library and/or graveyard for a card named Mu Yanling, reveal it, and put it into your hand. If you searched your library this way, shuffle it.
        this.getSpellAbility().addEffect(new SearchLibraryGraveyardPutInHandEffect(filter).concatBy("<br>"));
    }

    private RhythmicWaterVortex(final RhythmicWaterVortex card) {
        super(card);
    }

    @Override
    public RhythmicWaterVortex copy() {
        return new RhythmicWaterVortex(this);
    }
}
