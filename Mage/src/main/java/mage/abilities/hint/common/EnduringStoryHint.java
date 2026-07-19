package mage.abilities.hint.common;

import mage.abilities.Ability;
import mage.abilities.condition.common.EnduringStoryCondition;
import mage.abilities.hint.Hint;
import mage.abilities.hint.HintUtils;
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
        boolean hasEnduringStory = EnduringStoryCondition.instance.apply(game, ability);
        if (hasEnduringStory) {
            return HintUtils.prepareText("You have an enduring story", null, HintUtils.HINT_ICON_GOOD);
        }

        int count = controller == null ? 0 : game.getBattlefield().countAll(filter, controller.getId(), game);
        return HintUtils.prepareText(
                "You don't have an enduring story (controlled artifacts/legendaries/sagas: " + count + " of 3)",
                null, HintUtils.HINT_ICON_BAD
        );
    }

    @Override
    public Hint copy() {
        return instance;
    }
}
