package mage.abilities.hint.common;

import mage.abilities.Ability;
import mage.abilities.condition.common.EnduringStoryCondition;
import mage.abilities.hint.Hint;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.players.Player;

import java.util.Arrays;

/**
 * @author muz
 */
public enum EnduringStoryHint implements Hint {

    instance;
    private static final FilterControlledPermanent filter = new FilterControlledPermanent("artifacts, legendaries, and/or Sagas");

    static {
        filter.add(Predicates.or(Arrays.asList(
            CardType.ARTIFACT.getPredicate(),
            SuperType.LEGENDARY.getPredicate(),
            SubType.SAGA.getPredicate()
        )));
    }

    @Override
    public String getText(Game game, Ability ability) {
        Player controller = game.getPlayer(ability.getControllerId());
        if (EnduringStoryCondition.instance.apply(game, ability)) {
            return "You have an enduring story";
        }

        int count = game.getBattlefield().countAll(filter, controller.getId(), game);
        return "You don't have an enduring story (controlled artifacts/sagas/legendaries: " + count + " of 3)";
    }

    @Override
    public Hint copy() {
        return instance;
    }
}
