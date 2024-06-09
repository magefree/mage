package mage.choices;

import mage.abilities.Ability;
import mage.game.Game;
import mage.players.Player;

import java.util.Objects;
import java.util.Optional;

/**
 * @author TheElk801
 */
public abstract class VillainousChoice {

    private final String rule;
    private final String message;

    protected VillainousChoice(String rule, String message) {
        this.rule = rule;
        this.message = message;
    }

    public abstract boolean doChoice(Player player, Game game, Ability source);

    public String getRule() {
        return rule;
    }

    public String getMessage(Game game, Ability source) {
        if (!message.contains("{controller}")) {
            return message;
        }
        String controllerName = Optional
                .ofNullable(game.getPlayer(source.getControllerId()))
                .map(Player::getName)
                .orElse("Opponent");
        return message.replace("{controller}", controllerName);
    }
}
