package mage.abilities.hint.common;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.hint.Hint;
import mage.constants.CardType;
import mage.game.Game;
import mage.players.Player;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author JayDi85
 */
public enum DeliriumHint implements Hint {

    instance;

    @Override
    public String getText(Game game, Ability ability) {
        Player player = game.getPlayer(ability.getControllerId());
        if (player == null) {
            return null;
        }
        List<String> types = player
                .getGraveyard()
                .getCards(game)
                .stream()
                .map(MageObject::getCardType)
                .flatMap(Collection::stream)
                .distinct()
                .map(CardType::toString)
                .sorted()
                .collect(Collectors.toList());
        String message = "" + types.size();
        if (types.size() > 0) {
            message += " (";
            message += types.stream().reduce((a, b) -> a + ", " + b).orElse("");
            message += ')';
        }
        return "Card types in your graveyard: " + message;
    }

    @Override
    public Hint copy() {
        return instance;
    }
}
