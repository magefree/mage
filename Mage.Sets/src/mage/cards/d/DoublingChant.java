
package mage.cards.d;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardIdPredicate;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author North
 */
public final class DoublingChant extends CardImpl {

    public DoublingChant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{5}{G}");

        // For each creature you control, you may search your library for a creature card with the same name as that creature.
        // Put those cards onto the battlefield, then shuffle your library.
        this.getSpellAbility().addEffect(new DoublingChantEffect());
    }

    public DoublingChant(final DoublingChant card) {
        super(card);
    }

    @Override
    public DoublingChant copy() {
        return new DoublingChant(this);
    }
}

class DoublingChantEffect extends OneShotEffect {

    public DoublingChantEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "For each creature you control, you may search your library for a creature card with the same name as that creature. Put those cards onto the battlefield, then shuffle your library";
    }

    public DoublingChantEffect(final DoublingChantEffect effect) {
        super(effect);
    }

    @Override
    public DoublingChantEffect copy() {
        return new DoublingChantEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Set<Card> chosenCards = new HashSet<>();
        List<Permanent> creatures = game.getBattlefield().getAllActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, source.getControllerId(), game);
        if (creatures.isEmpty()) {
            //9/22/2011: If you control no creatures when Doubling Chant resolves, you may still search your library and you must shuffle your library.
            if (controller.chooseUse(Outcome.PutCreatureInPlay, "Search in your library?", source, game)) {
                FilterCreatureCard filter = new FilterCreatureCard("nothing (no valid card available)");
                filter.add(new NamePredicate("creatureName"));
                TargetCardInLibrary target = new TargetCardInLibrary(0, 1, filter);
                controller.searchLibrary(target, game);
            }
        }
        for (Permanent creature : creatures) {
            final String creatureName = creature.getName();
            List<CardIdPredicate> uuidPredicates = new ArrayList<>();
            if (controller.chooseUse(Outcome.PutCreatureInPlay, "Search for " + creatureName + " in your library?", source, game)) {
                FilterCreatureCard filter = new FilterCreatureCard("creature card named " + creatureName);
                filter.add(new NamePredicate(creatureName));
                if (!uuidPredicates.isEmpty()) { // Prevent to select a card twice
                    filter.add(Predicates.not(Predicates.or(uuidPredicates)));
                }
                TargetCardInLibrary target = new TargetCardInLibrary(filter);
                if (controller.searchLibrary(target, game)) {
                    Card card = controller.getLibrary().getCard(target.getFirstTarget(), game);
                    if (card != null) {
                        chosenCards.add(card);
                        uuidPredicates.add(new CardIdPredicate(card.getId()));
                    }
                }
            }

        }
        controller.moveCards(chosenCards, Zone.BATTLEFIELD, source, game);
        controller.shuffleLibrary(source, game);
        return true;
    }
}
