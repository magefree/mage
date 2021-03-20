package mage.choices;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.abilities.Ability;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author TheElk801
 */
public class ChooseFriendsAndFoes {

    private List<Player> friends = new ArrayList<>();
    private List<Player> foes = new ArrayList<>();

    public boolean chooseFriendOrFoe(Player playerChoosing, Ability source, Game game) {
        if (playerChoosing == null) {
            return false;
        }
        friends.clear();
        foes.clear();
        for (UUID playerId : game.getState().getPlayersInRange(playerChoosing.getId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                if (playerChoosing.chooseUse(Outcome.Vote, "Is " + player.getName() + " friend or foe?", null, "Friend", "Foe", source, game)) {
                    friends.add(player);
                } else {
                    foes.add(player);
                }
            }
        }
        return true;
    }

    public List<Player> getFriends() {
        return friends;
    }

    public List<Player> getFoes() {
        return foes;
    }
}
