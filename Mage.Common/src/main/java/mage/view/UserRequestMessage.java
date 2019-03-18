package mage.view;

import mage.constants.PlayerAction;

import java.io.Serializable;
import java.util.UUID;

/**
 * @author LevelX2
 */
public class UserRequestMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String title;
    private final String message;
    private UUID relatedUserId;
    private String relatedUserName;
    private UUID matchId;
    private UUID tournamentId;
    private UUID gameId;
    private UUID roomId;
    private UUID tableId;

    private String button1Text;
    private PlayerAction button1Action;

    private String button2Text;
    private PlayerAction button2Action;

    private String button3Text;
    private PlayerAction button3Action;

    public UserRequestMessage(String title, String message) {
        this.title = title;
        this.message = message;
        this.button1Action = null;
        this.button2Action = null;
        this.button3Action = null;
    }

    public void setMatchId(UUID matchId) {
        this.matchId = matchId;
    }

    public void setGameId(UUID gameId) {
        this.gameId = gameId;
    }

    public void setRelatedUser(UUID userId, String name) {
        this.relatedUserId = userId;
        this.relatedUserName = name;
    }

    public void setButton1(String text, PlayerAction buttonAction) {
        this.button1Text = text;
        this.button1Action = buttonAction;
    }

    public void setButton2(String text, PlayerAction buttonAction) {
        this.button2Text = text;
        this.button2Action = buttonAction;
    }

    public void setButton3(String text, PlayerAction buttonAction) {
        this.button3Text = text;
        this.button3Action = buttonAction;
    }

    public String getTitle() {
        return title;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getMessage() {
        return message;
    }

    public UUID getRelatedUserId() {
        return relatedUserId;
    }

    public String getRelatedUserName() {
        return relatedUserName;
    }

    public UUID getMatchId() {
        return matchId;
    }

    public UUID getGameId() {
        return gameId;
    }

    public UUID getTournamentId() {
        return tournamentId;
    }

    public void setTournamentId(UUID tournamentId) {
        this.tournamentId = tournamentId;
    }

    public UUID getRoomId() {
        return roomId;
    }

    public void setRoomId(UUID roomId) {
        this.roomId = roomId;
    }

    public UUID getTableId() {
        return tableId;
    }

    public void setTableId(UUID tableId) {
        this.tableId = tableId;
    }

    public String getButton1Text() {
        return button1Text;
    }

    public PlayerAction getButton1Action() {
        return button1Action;
    }

    public String getButton2Text() {
        return button2Text;
    }

    public PlayerAction getButton2Action() {
        return button2Action;
    }

    public String getButton3Text() {
        return button3Text;
    }

    public PlayerAction getButton3Action() {
        return button3Action;
    }

}
