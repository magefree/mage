
package mage.cards.p;

import java.util.*;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoiceCreatureType;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.players.Player;

/**
 * @author duncant
 */
public final class PatriarchsBidding extends CardImpl {

    public PatriarchsBidding(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{B}{B}");

        // Each player chooses a creature type. Each player returns all creature cards of a type chosen this way from their graveyard to the battlefield.
        this.getSpellAbility().addEffect(new PatriarchsBiddingEffect());
    }

    private PatriarchsBidding(final PatriarchsBidding card) {
        super(card);
    }

    @Override
    public PatriarchsBidding copy() {
        return new PatriarchsBidding(this);
    }
}

class PatriarchsBiddingEffect extends OneShotEffect {

    public PatriarchsBiddingEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "each player chooses a creature type. Each player returns all creature cards of a type chosen this way from their graveyard to the battlefield";
    }

    public PatriarchsBiddingEffect(final PatriarchsBiddingEffect effect) {
        super(effect);
    }

    @Override
    public PatriarchsBiddingEffect copy() {
        return new PatriarchsBiddingEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source);
        if (controller != null && sourceObject != null) {
            Set<String> chosenTypes = new HashSet<>();
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                Choice typeChoice = new ChoiceCreatureType(sourceObject);
                if (!player.choose(Outcome.PutCreatureInPlay, typeChoice, game)) {
                    continue;
                }
                String chosenType = typeChoice.getChoice();
                game.informPlayers(sourceObject.getLogName() + ": " + player.getLogName() + " has chosen " + chosenType);
                chosenTypes.add(chosenType);
            }

            List<SubType.SubTypePredicate> predicates = new ArrayList<>();
            for (String type : chosenTypes) {
                predicates.add(SubType.byDescription(type).getPredicate());
            }
            FilterCard filter = new FilterCreatureCard();
            filter.add(Predicates.or(predicates));
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    player.moveCards(player.getGraveyard().getCards(filter, game), Zone.BATTLEFIELD, source, game);
                }
            }
            return true;
        }
        return false;
    }
}
