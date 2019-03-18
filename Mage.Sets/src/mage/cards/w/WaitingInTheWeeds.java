
package mage.cards.w;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.permanent.token.TokenImpl;
import mage.game.permanent.token.Token;
import mage.game.permanent.token.WaitingInTheWeedsCatToken;
import mage.players.Player;

/**
 *
 * @author Quercitron
 */
public final class WaitingInTheWeeds extends CardImpl {

    public WaitingInTheWeeds(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{G}{G}");

        // Each player creates a 1/1 green Cat creature token for each untapped Forest he or she controls.
        this.getSpellAbility().addEffect(new WaitingInTheWeedsEffect());
    }

    public WaitingInTheWeeds(final WaitingInTheWeeds card) {
        super(card);
    }

    @Override
    public WaitingInTheWeeds copy() {
        return new WaitingInTheWeeds(this);
    }
}

class WaitingInTheWeedsEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterPermanent("untapped Forest he or she controls");

    static {
        filter.add(new SubtypePredicate(SubType.FOREST));
        filter.add(Predicates.not(TappedPredicate.instance));
    }

    public WaitingInTheWeedsEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "Each player creates a 1/1 green Cat creature token for each untapped Forest he or she controls";
    }

    public WaitingInTheWeedsEffect(final WaitingInTheWeedsEffect effect) {
        super(effect);
    }

    @Override
    public WaitingInTheWeedsEffect copy() {
        return new WaitingInTheWeedsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Token token = new WaitingInTheWeedsCatToken();
                int amount = game.getBattlefield().getAllActivePermanents(filter, playerId, game).size();
                token.putOntoBattlefield(amount, game, source.getSourceId(), playerId);
            }
            return true;
        }
        return false;
    }
}
