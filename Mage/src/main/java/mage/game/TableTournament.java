package mage.game;

import mage.cards.decks.DeckValidator;
import mage.game.tournament.Tournament;
import mage.players.PlayerType;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public class TableTournament {
    final UUID roomId;
    final String gameType;
    final String name;
    final String controllerName;
    final DeckValidator validator;
    final List<PlayerType> playerTypes;
    final Table.TableRecorder recorder;
    final Tournament tournament;
    final Set<String> bannedUsernames;
    final boolean isPlaneChase;

    public TableTournament(UUID roomId, String gameType, String name, String controllerName, DeckValidator validator,
                           List<PlayerType> playerTypes, Table.TableRecorder recorder, Tournament tournament,
                           Set<String> bannedUsernames, boolean isPlaneChase) {
        this.roomId = roomId;
        this.gameType = gameType;
        this.name = name;
        this.controllerName = controllerName;
        this.validator = validator;
        this.playerTypes = playerTypes;
        this.recorder = recorder;
        this.tournament = tournament;
        this.bannedUsernames = bannedUsernames;
        this.isPlaneChase = isPlaneChase;
    }
}
