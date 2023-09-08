
package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.filter.common.FilterPermanentCard;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author ciaccona007
 */
public final class PackHunt extends CardImpl {

    public PackHunt(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{G}");

        // Search your library for up to three cards with the same name as target creature, reveal them, and put them into your hand. Then shuffle your library.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new PackHuntEffect());
    }

    private PackHunt(final PackHunt card) {
        super(card);
    }

    @Override
    public PackHunt copy() {
        return new PackHunt(this);
    }
}
class PackHuntEffect extends OneShotEffect {

    public PackHuntEffect() {
        super(Outcome.Benefit);
        this.staticText = "Search your library for up to three cards with the same name as target creature, reveal them, and put them into your hand. Then shuffle";
    }

    private PackHuntEffect(final PackHuntEffect effect) {
        super(effect);
    }

    @Override
    public PackHuntEffect copy() {
        return new PackHuntEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        FilterCard filter = new FilterPermanentCard();
        filter.add(new NamePredicate(permanent.getName()));
        return new SearchLibraryPutInHandEffect(new TargetCardInLibrary(0,3, filter), true).apply(game, source);
    }
}
