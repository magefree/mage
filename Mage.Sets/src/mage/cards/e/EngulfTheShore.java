
package mage.cards.e;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ToughnessPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author fireshoes
 */
public final class EngulfTheShore extends CardImpl {

    public EngulfTheShore(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{3}{U}");

        // Return to their owners' hands all creatures with toughness less than or equal to the number of Islands you control.
        getSpellAbility().addEffect(new EngulfTheShoreEffect());
    }

    private EngulfTheShore(final EngulfTheShore card) {
        super(card);
    }

    @Override
    public EngulfTheShore copy() {
        return new EngulfTheShore(this);
    }
}

class EngulfTheShoreEffect extends OneShotEffect {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("number of Islands you control");

    static {
        filter.add(SubType.ISLAND.getPredicate());
    }

    public EngulfTheShoreEffect() {
        super(Outcome.Benefit);
        this.staticText = "Return to their owners' hands all creatures with toughness less than or equal to the number of Islands you control";
    }

    public EngulfTheShoreEffect(final EngulfTheShoreEffect effect) {
        super(effect);
    }

    @Override
    public EngulfTheShoreEffect copy() {
        return new EngulfTheShoreEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int islands = game.getBattlefield().count(filter, source.getControllerId(), source, game);
            FilterPermanent creatureFilter = new FilterCreaturePermanent();
            creatureFilter.add(new ToughnessPredicate(ComparisonType.FEWER_THAN, islands + 1));
            Set<Card> cardsToHand = new HashSet<>();
            for (Permanent permanent : game.getBattlefield().getActivePermanents(creatureFilter, source.getControllerId(), source, game)) {
                cardsToHand.add(permanent);
            }
            controller.moveCards(cardsToHand, Zone.HAND, source, game);
            return true;
        }
        return false;
    }
}
