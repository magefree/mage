
package mage.cards.m;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.ReplicateAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author TheElk801
 */
public final class Mimeofacture extends CardImpl {

    public Mimeofacture(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{U}");

        // Replicate {3}{U}
        this.addAbility(new ReplicateAbility("{3}{U}"));

        // Choose target permanent an opponent controls. Search that player's library for a card with the same name and put it onto the battlefield under your control. Then that player shuffles their library.
        this.getSpellAbility().addEffect(new MimeofactureEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_OPPONENTS_PERMANENT));
    }

    private Mimeofacture(final Mimeofacture card) {
        super(card);
    }

    @Override
    public Mimeofacture copy() {
        return new Mimeofacture(this);
    }
}

class MimeofactureEffect extends OneShotEffect {

    MimeofactureEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "Choose target permanent an opponent controls. "
                + "Search that player's library for a card with the same name and put it onto the battlefield under your control. "
                + "Then that player shuffles.";
    }

    MimeofactureEffect(final MimeofactureEffect effect) {
        super(effect);
    }

    @Override
    public MimeofactureEffect copy() {
        return new MimeofactureEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (controller == null || permanent == null) {
            return false;
        }
        Player opponent = game.getPlayer(permanent.getControllerId());
        if (opponent == null) {
            return false;
        }
        FilterCard filter = new FilterCard("card named " + permanent.getName());
        filter.add(new NamePredicate(permanent.getName()));
        TargetCardInLibrary target = new TargetCardInLibrary(0, 1, filter);
        if (controller.searchLibrary(target, source, game, opponent.getId())) {
            Card card = opponent.getLibrary().getCard(target.getFirstTarget(), game);
            controller.moveCards(card, Zone.BATTLEFIELD, source, game);
        }
        opponent.shuffleLibrary(source, game);
        return true;
    }
}
