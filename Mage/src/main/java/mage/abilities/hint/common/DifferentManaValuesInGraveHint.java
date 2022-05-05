package mage.abilities.hint.common;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.hint.Hint;
import mage.game.Game;
import mage.players.Player;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public enum DifferentManaValuesInGraveHint implements Hint {
    instance;

    public String getText(Game game, Ability ability) {
        Player player = game.getPlayer(ability.getControllerId());
        if (player == null) {
            return null;
        }
        List<String> values = player
                .getGraveyard()
                .getCards(game)
                .stream()
                .mapToInt(MageObject::getManaValue)
                .distinct()
                .sorted()
                .mapToObj(String::valueOf)
                .collect(Collectors.toList());
        return "Different mana values among cards in your graveyard: " + values.size()
                + (values.size() > 0 ? " (" + String.join(", ", values) + ')' : "");
    }

    @Override
    public DifferentManaValuesInGraveHint copy() {
        return this;
    }
}
