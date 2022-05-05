
package mage.cards.h;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoiceCreatureType;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author pcasaretto
 */
public final class HarshMercy extends CardImpl {

    public HarshMercy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{W}");

        // Each player chooses a creature type. Destroy all creatures that aren't of a type chosen this way. They can't be regenerated.
        this.getSpellAbility().addEffect(new HarshMercyEffect());
    }

    private HarshMercy(final HarshMercy card) {
        super(card);
    }

    @Override
    public HarshMercy copy() {
        return new HarshMercy(this);
    }
}

class HarshMercyEffect extends OneShotEffect {

    public HarshMercyEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "Each player chooses a creature type. Destroy all creatures that aren't of a type chosen this way. They can't be regenerated.";
    }

    public HarshMercyEffect(final HarshMercyEffect effect) {
        super(effect);
    }

    @Override
    public HarshMercyEffect copy() {
        return new HarshMercyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source);
        if (controller != null && sourceObject != null) {
            Set<String> chosenTypes = new HashSet<>();
            PlayerIteration:
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                Choice typeChoice = new ChoiceCreatureType(sourceObject);
                if (player != null && !player.choose(Outcome.DestroyPermanent, typeChoice, game)) {
                    continue PlayerIteration;
                }
                String chosenType = typeChoice.getChoice();
                if (chosenType != null) {
                    game.informPlayers(sourceObject.getIdName() + ": " + player.getLogName() + " has chosen " + chosenType);
                    chosenTypes.add(chosenType);
                }
            }

            FilterPermanent filter = new FilterCreaturePermanent("creatures");
            for (String type : chosenTypes) {
                filter.add(Predicates.not(SubType.byDescription(type).getPredicate()));
            }

            return new DestroyAllEffect(filter, true).apply(game, source);
        }
        return false;
    }

}
