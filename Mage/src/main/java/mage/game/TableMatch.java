package mage.game;

import mage.cards.decks.DeckValidator;
import mage.game.match.Match;
import mage.players.PlayerType;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public class TableMatch {

    final UUID roomId;
    final String gameType;
    final String name;
    final String controllerName;
    final DeckValidator validator;
    final List<PlayerType> playerTypes;
    final Table.TableRecorder recorder;
    final Match match;
    final Set<String> bannedUsernames;
    final boolean isPlaneChase;

    public TableMatch(UUID roomId, String gameType, String name, String controllerName, DeckValidator validator,
                      List<PlayerType> playerTypes, Table.TableRecorder recorder, Match match, Set<String> bannedUsernames,
                      boolean isPlaneChase) {
        this.roomId = roomId;
        this.gameType = gameType;
        this.name = name;
        this.controllerName = controllerName;
        this.validator = validator;
        this.playerTypes = playerTypes;
        this.recorder = recorder;
        this.match = match;
        this.bannedUsernames = bannedUsernames;
        this.isPlaneChase = isPlaneChase;
    }
}
