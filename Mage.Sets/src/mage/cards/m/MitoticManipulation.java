
package mage.cards.m;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;

/**
 *
 * @author North
 */
public final class MitoticManipulation extends CardImpl {

    public MitoticManipulation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{U}{U}");

        this.getSpellAbility().addEffect(new MitoticManipulationEffect());
    }

    private MitoticManipulation(final MitoticManipulation card) {
        super(card);
    }

    @Override
    public MitoticManipulation copy() {
        return new MitoticManipulation(this);
    }
}

class MitoticManipulationEffect extends OneShotEffect {

    public MitoticManipulationEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "Look at the top seven cards of your library. You may put one of those cards onto the battlefield if it has the same name as a permanent. Put the rest on the bottom of your library in any order";
    }

    public MitoticManipulationEffect(final MitoticManipulationEffect effect) {
        super(effect);
    }

    @Override
    public MitoticManipulationEffect copy() {
        return new MitoticManipulationEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller != null && sourceObject != null) {
            Set<String> permanentNames = new HashSet<>();
            for (Permanent permanent : game.getBattlefield().getActivePermanents(source.getControllerId(), game)) {
                permanentNames.add(permanent.getName());
            }

            Cards cardsFromTop = new CardsImpl();
            cardsFromTop.addAll(controller.getLibrary().getTopCards(game, 7));
            controller.lookAtCards(sourceObject.getIdName(), cardsFromTop, game);
            FilterCard filter = new FilterCard("card to put onto the battlefield");
            List<NamePredicate> namePredicates = new ArrayList<>();
            for (String name : permanentNames) {
                namePredicates.add(new NamePredicate(name));
            }
            if (!namePredicates.isEmpty() && !cardsFromTop.isEmpty()) {
                filter.add(Predicates.or(namePredicates));
                TargetCard target = new TargetCard(Zone.LIBRARY, filter);
                if (cardsFromTop.count(filter, source.getControllerId(), source, game) > 0
                        && controller.chooseUse(Outcome.PutCardInPlay, "Put a card on the battlefield?", source, game)) {
                    if (controller.choose(Outcome.PutCardInPlay, cardsFromTop, target, game)) {
                        Card card = cardsFromTop.get(target.getFirstTarget(), game);
                        if (card != null) {
                            controller.moveCards(card, Zone.BATTLEFIELD, source, game);
                            cardsFromTop.remove(card);
                        }
                    }
                }
            }
            controller.putCardsOnBottomOfLibrary(cardsFromTop, game, source, true);
            return true;
        }
        return false;
    }
}
