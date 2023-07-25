
package mage.cards.k;

import java.util.UUID;
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
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author caldover
 */
public final class KindredDominance extends CardImpl {

    public KindredDominance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{5}{B}{B}");

        // Choose a creature type. Destroy all creatures that are not the chosen type.
        this.getSpellAbility().addEffect(new KindredDominanceEffect());
    }

    private KindredDominance(final KindredDominance card) {
        super(card);
    }

    @Override
    public KindredDominance copy() {
        return new KindredDominance(this);
    }
}

class KindredDominanceEffect extends OneShotEffect {

    public KindredDominanceEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "Choose a creature type. Destroy all creatures that aren't of the chosen type.";
    }

    public KindredDominanceEffect(final KindredDominanceEffect effect) {
        super(effect);
    }

    @Override
    public KindredDominanceEffect copy() {
        return new KindredDominanceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Choice typeChoice = new ChoiceCreatureType(game.getObject(source));
        if (controller != null && controller.choose(outcome, typeChoice, game)) {
            game.informPlayers(controller.getLogName() + " has chosen " + typeChoice.getChoice());
            FilterCreaturePermanent filter = new FilterCreaturePermanent("All creatures not of the chosen type");
            filter.add(Predicates.not(SubType.byDescription(typeChoice.getChoice()).getPredicate()));
            return new DestroyAllEffect(filter).apply(game, source);
        }
        return false;
    }
}
