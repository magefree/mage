/*
* Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
*
* Redistribution and use in source and binary forms, with or without modification, are
* permitted provided that the following conditions are met:
*
*    1. Redistributions of source code must retain the above copyright notice, this list of
*       conditions and the following disclaimer.
*
*    2. Redistributions in binary form must reproduce the above copyright notice, this list
*       of conditions and the following disclaimer in the documentation and/or other materials
*       provided with the distribution.
*
* THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
* WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
* FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
* CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
* CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
* SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
* ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
* NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
* ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
* The views and conclusions contained in the software and documentation are those of the
* authors and should not be interpreted as representing official policies, either expressed
* or implied, of BetaSteward_at_googlemail.com.
*/

package mage.server;

import mage.MageException;
import mage.cards.decks.DeckCardLists;
import mage.game.GameException;
import mage.game.match.MatchOptions;
import mage.game.tournament.TournamentOptions;
import mage.interfaces.Action;
import mage.interfaces.ActionWithResult;
import mage.interfaces.MageServer;
import mage.interfaces.ServerState;
import mage.interfaces.callback.ClientCallback;
import mage.remote.MageVersionException;
import mage.server.draft.DraftManager;
import mage.server.game.*;
import mage.server.services.LogKeys;
import mage.server.services.impl.FeedbackServiceImpl;
import mage.server.services.impl.LogServiceImpl;
import mage.server.tournament.TournamentFactory;
import mage.server.tournament.TournamentManager;
import mage.server.util.ServerMessagesUtil;
import mage.server.util.ThreadExecutor;
import mage.utils.*;
import mage.view.*;
import mage.view.ChatMessage.MessageColor;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;

//import mage.interfaces.Server;

/**
 *
 * @author BetaSteward_at_googlemail.com, noxx
 */
public class MageServerImpl implements MageServer {

	private final static Logger logger = Logger.getLogger(MageServerImpl.class);
	private static ExecutorService callExecutor = ThreadExecutor.getInstance().getCallExecutor();

	private String password;
	private boolean testMode;

	public MageServerImpl(String password, boolean testMode) {
		this.password = password;
		this.testMode = testMode;
		ServerMessagesUtil.getInstance().getMessages();
	}

	@Override
	public boolean registerClient(String userName, String sessionId, MageVersion version) throws MageException {
		try {
			if (version.compareTo(Main.getVersion()) != 0) {
				logger.info("MageVersionException: userName=" + userName + ", version=" + version);
                LogServiceImpl.instance.log(LogKeys.KEY_WRONG_VERSION, userName, version.toString(), Main.getVersion().toString(), sessionId);
				throw new MageVersionException(version, Main.getVersion());
			}
			return SessionManager.getInstance().registerUser(sessionId, userName);
		} catch (Exception ex) {
            if (ex instanceof MageVersionException)
                throw (MageVersionException)ex;
			handleException(ex);
		}
		return false;
	}

	@Override
	public boolean setUserData(final String userName, final String sessionId, final UserDataView userDataView) throws MageException {
        return executeWithResult("setUserData", sessionId, new ActionWithBooleanResult() {
            public Boolean execute() throws MageException {
                return SessionManager.getInstance().setUserData(userName, sessionId, userDataView);
            }
        });
	}

	@Override
	public boolean registerAdmin(String password, String sessionId, MageVersion version) throws MageException {
		try {
			if (version.compareTo(Main.getVersion()) != 0)
				throw new MageException("Wrong client version " + version + ", expecting version " + Main.getVersion());
			if (!password.equals(this.password))
				throw new MageException("Wrong password");
			return SessionManager.getInstance().registerAdmin(sessionId);
		} catch (Exception ex) {
			handleException(ex);
		}
		return false;
	}

	@Override
	public TableView createTable(final String sessionId, final UUID roomId, final MatchOptions options) throws MageException {
		return executeWithResult("createTable", sessionId, new ActionWithTableViewResult() {
            public TableView execute() throws MageException {
                UUID userId = SessionManager.getInstance().getSession(sessionId).getUserId();
                TableView table = GamesRoomManager.getInstance().getRoom(roomId).createTable(userId, options);
                logger.info("Table " + table.getTableId() + " created");
                LogServiceImpl.instance.log(LogKeys.KEY_TABLE_CREATED, sessionId, userId.toString(), table.getTableId().toString());
                return table;
            }
        });
	}

	@Override
	public TableView createTournamentTable(final String sessionId, final UUID roomId, final TournamentOptions options) throws MageException {
        return executeWithResult("createTournamentTable", sessionId, new ActionWithTableViewResult() {
            public TableView execute() throws MageException {
                UUID userId = SessionManager.getInstance().getSession(sessionId).getUserId();
                TableView table = GamesRoomManager.getInstance().getRoom(roomId).createTournamentTable(userId, options);
                logger.info("Tournament table " + table.getTableId() + " created");
                LogServiceImpl.instance.log(LogKeys.KEY_TOURNAMENT_TABLE_CREATED, sessionId, userId.toString(), table.getTableId().toString());
                return table;
            }
        });
	}

	@Override
	public void removeTable(final String sessionId, final UUID roomId, final UUID tableId) throws MageException {
        execute("removeTable", sessionId, new Action() {
            public void execute() {
                UUID userId = SessionManager.getInstance().getSession(sessionId).getUserId();
                TableManager.getInstance().removeTable(userId, tableId);
            }
        });
	}

	@Override
	public boolean joinTable(final String sessionId, final UUID roomId, final UUID tableId, final String name, final String playerType, final int skill, final DeckCardLists deckList) throws MageException, GameException {
		return executeWithResult("joinTable", sessionId, new ActionWithBooleanResult() {
            public Boolean execute() throws MageException {
                UUID userId = SessionManager.getInstance().getSession(sessionId).getUserId();
                boolean ret = GamesRoomManager.getInstance().getRoom(roomId).joinTable(userId, tableId, name, playerType, skill, deckList);
                logger.info("Session " + sessionId + " joined table " + tableId);
                return ret;
            }
        });
	}

	@Override
	public boolean joinTournamentTable(final String sessionId, final UUID roomId, final UUID tableId, final String name, final String playerType, final int skill) throws MageException, GameException {
		return executeWithResult("joinTournamentTable", sessionId, new ActionWithBooleanResult() {
            public Boolean execute() throws MageException {
                UUID userId = SessionManager.getInstance().getSession(sessionId).getUserId();
                boolean ret = GamesRoomManager.getInstance().getRoom(roomId).joinTournamentTable(userId, tableId, name, playerType, skill);
                logger.info("Session " + sessionId + " joined table " + tableId);
                return ret;
            }
        });
	}

	@Override
	public boolean submitDeck(final String sessionId, final UUID tableId, final DeckCardLists deckList) throws MageException, GameException {
        return executeWithResult("submitDeck", sessionId, new ActionWithBooleanResult() {
            public Boolean execute() throws MageException {
                UUID userId = SessionManager.getInstance().getSession(sessionId).getUserId();
				boolean ret = TableManager.getInstance().submitDeck(userId, tableId, deckList);
				logger.info("Session " + sessionId + " submitted deck");
				return ret;
            }
        });
	}

	@Override
	public void updateDeck(final String sessionId, final UUID tableId, final DeckCardLists deckList) throws MageException, GameException {
		execute("updateDeck", sessionId, new Action() {
            public void execute() throws MageException {
                UUID userId = SessionManager.getInstance().getSession(sessionId).getUserId();
                TableManager.getInstance().updateDeck(userId, tableId, deckList);
                logger.debug("Session " + sessionId + " updated deck");
            }
        });
	}

    @Override
    //FIXME: why no sessionId here???
	public List<TableView> getTables(UUID roomId) throws MageException {
        try {
			return GamesRoomManager.getInstance().getRoom(roomId).getTables();
		}
		catch (Exception ex) {
			handleException(ex);
		}
		return null;
	}

	@Override
    //FIXME: why no sessionId here???
	public List<MatchView> getFinishedMatches(UUID roomId) throws MageException {
		try {
			return GamesRoomManager.getInstance().getRoom(roomId).getFinished();
		}
		catch (Exception ex) {
			handleException(ex);
		}
		return null;
	}

    @Override
    //FIXME: why no sessionId here???
	public List<String> getConnectedPlayers(UUID roomId) throws MageException {
		try {
			return GamesRoomManager.getInstance().getRoom(roomId).getPlayers();
		}
		catch (Exception ex) {
			handleException(ex);
		}
		return null;
	}

	@Override
    //FIXME: why no sessionId here???
	public TableView getTable(UUID roomId, UUID tableId) throws MageException {
		try {
			return GamesRoomManager.getInstance().getRoom(roomId).getTable(tableId);
		}
		catch (Exception ex) {
			handleException(ex);
		}
		return null;
	}

	@Override
	public void deregisterClient(final String sessionId) throws MageException {
		execute("deregisterClient", sessionId, new Action() {
            public void execute() {
                SessionManager.getInstance().disconnect(sessionId, true);
                logger.info("Client deregistered ...");
            }
        });
	}

	@Override
	public void startMatch(final String sessionId, final UUID roomId, final UUID tableId) throws MageException {
        execute("startMatch", sessionId, new Action() {
            public void execute() {
                UUID userId = SessionManager.getInstance().getSession(sessionId).getUserId();
				TableManager.getInstance().startMatch(userId, roomId, tableId);
            }
        });
	}

	@Override
	public void startChallenge(final String sessionId, final UUID roomId, final UUID tableId, final UUID challengeId) throws MageException {
        execute("startChallenge", sessionId, new Action() {
            public void execute() {
                UUID userId = SessionManager.getInstance().getSession(sessionId).getUserId();
                TableManager.getInstance().startChallenge(userId, roomId, tableId, challengeId);
            }
        });
    }

	@Override
	public void startTournament(final String sessionId, final UUID roomId, final UUID tableId) throws MageException {
        execute("startTournament", sessionId, new Action() {
            public void execute() {
                UUID userId = SessionManager.getInstance().getSession(sessionId).getUserId();
                TableManager.getInstance().startTournament(userId, roomId, tableId);
            }
        });
	}

	@Override
    //FIXME: why no sessionId here???
	public TournamentView getTournament(UUID tournamentId) throws MageException {
		try {
			return TournamentManager.getInstance().getTournamentView(tournamentId);
		}
		catch (Exception ex) {
			handleException(ex);
		}
		return null;
	}

	@Override
    //FIXME: why no sessionId here???
	public void sendChatMessage(final UUID chatId, final String userName, final String message) throws MageException {
		try {
			callExecutor.execute(
				new Runnable() {
					@Override
					public void run() {
						ChatManager.getInstance().broadcast(chatId, userName, message, MessageColor.BLUE);
					}
				}
			);
		}
		catch (Exception ex) {
			handleException(ex);
		}
	}

	@Override
	public void joinChat(final UUID chatId, final String sessionId, final String userName) throws MageException {
        execute("joinChat", sessionId, new Action() {
            public void execute() {
                UUID userId = SessionManager.getInstance().getSession(sessionId).getUserId();
                ChatManager.getInstance().joinChat(chatId, userId);
            }
        });
	}

	@Override
	public void leaveChat(final UUID chatId, final String sessionId) throws MageException {
        execute("leaveChat", sessionId, new Action() {
            public void execute() {
                UUID userId = SessionManager.getInstance().getSession(sessionId).getUserId();
                ChatManager.getInstance().leaveChat(chatId, userId);
            }
        });
	}

	@Override
    //FIXME: why no sessionId here???
	public UUID getMainRoomId() throws MageException {
		try {
			return GamesRoomManager.getInstance().getMainRoomId();
		}
		catch (Exception ex) {
			handleException(ex);
		}
		return null;
	}

	@Override
    //FIXME: why no sessionId here???
	public UUID getRoomChatId(UUID roomId) throws MageException {
		try {
			return GamesRoomManager.getInstance().getRoom(roomId).getChatId();
		}
		catch (Exception ex) {
			handleException(ex);
		}
		return null;
	}

	@Override
	public boolean isTableOwner(final String sessionId, UUID roomId, final UUID tableId) throws MageException {
		return executeWithResult("isTableOwner", sessionId, new ActionWithBooleanResult() {
            public Boolean execute() {
                UUID userId = SessionManager.getInstance().getSession(sessionId).getUserId();
                return TableManager.getInstance().isTableOwner(tableId, userId);
            }
        });
	}

	@Override
	public void swapSeats(final String sessionId, final UUID roomId, final UUID tableId, final int seatNum1, final int seatNum2) throws MageException {
        execute("swapSeats", sessionId, new Action() {
            public void execute() {
                UUID userId = SessionManager.getInstance().getSession(sessionId).getUserId();
                TableManager.getInstance().swapSeats(tableId, userId, seatNum1, seatNum2);
            }
        });
	}

	@Override
	public void leaveTable(final String sessionId, final UUID roomId, final UUID tableId) throws MageException {
        execute("leaveTable", sessionId, new Action() {
            public void execute() {
                UUID userId = SessionManager.getInstance().getSession(sessionId).getUserId();
                GamesRoomManager.getInstance().getRoom(roomId).leaveTable(userId, tableId);
            }
        });
	}

	@Override
    //FIXME: why no sessionId here???
	public UUID getTableChatId(UUID tableId) throws MageException {
		try {
			return TableManager.getInstance().getChatId(tableId);
		}
		catch (Exception ex) {
			handleException(ex);
		}
		return null;
	}

	@Override
	public void joinGame(final UUID gameId, final String sessionId) throws MageException {
        execute("joinGame", sessionId, new Action() {
            public void execute() {
                UUID userId = SessionManager.getInstance().getSession(sessionId).getUserId();
                GameManager.getInstance().joinGame(gameId, userId);
            }
        });
	}

	@Override
	public void joinDraft(final UUID draftId, final String sessionId) throws MageException {
        execute("joinDraft", sessionId, new Action() {
            public void execute() {
                UUID userId = SessionManager.getInstance().getSession(sessionId).getUserId();
                DraftManager.getInstance().joinDraft(draftId, userId);
            }
        });
	}

	@Override
	public void joinTournament(final UUID tournamentId, final String sessionId) throws MageException {
        execute("joinTournament", sessionId, new Action() {
            public void execute() {
                UUID userId = SessionManager.getInstance().getSession(sessionId).getUserId();
                TournamentManager.getInstance().joinTournament(tournamentId, userId);
            }
        });
	}

	@Override
    //FIXME: why no sessionId here???
	public UUID getGameChatId(UUID gameId) throws MageException {
		try {
			return GameManager.getInstance().getChatId(gameId);
		}
		catch (Exception ex) {
			handleException(ex);
		}
		return null;
	}

	@Override
    //FIXME: why no sessionId here???
	public UUID getTournamentChatId(UUID tournamentId) throws MageException {
		try {
			return TournamentManager.getInstance().getChatId(tournamentId);
		}
		catch (Exception ex) {
			handleException(ex);
		}
		return null;
	}

    @Override
    public void sendPlayerUUID(final UUID gameId, final String sessionId, final UUID data) throws MageException {
        execute("sendPlayerUUID", sessionId, new Action() {
            public void execute() {
                User user = SessionManager.getInstance().getUser(sessionId);
                user.sendPlayerUUID(gameId, data);
            }
        });
    }

	@Override
    public void sendPlayerString(final UUID gameId, final String sessionId, final String data) throws MageException {
        execute("sendPlayerString", sessionId, new Action() {
            public void execute() {
                User user = SessionManager.getInstance().getUser(sessionId);
                user.sendPlayerString(gameId, data);
            }
        });
	}

	@Override
	public void sendPlayerBoolean(final UUID gameId, final String sessionId, final Boolean data) throws MageException {
        execute("sendPlayerBoolean", sessionId, new Action() {
            public void execute() {
                User user = SessionManager.getInstance().getUser(sessionId);
                user.sendPlayerBoolean(gameId, data);
            }
        });
	}

	@Override
	public void sendPlayerInteger(final UUID gameId, final String sessionId, final Integer data) throws MageException {
        execute("sendPlayerInteger", sessionId, new Action() {
            public void execute() {
                User user = SessionManager.getInstance().getUser(sessionId);
                user.sendPlayerInteger(gameId, data);
            }
        });
    }

	@Override
	public DraftPickView sendCardPick(final UUID draftId, final String sessionId, final UUID cardPick) throws MageException {
		return executeWithResult("sendCardPick", sessionId, new ActionWithNullNegativeResult<DraftPickView>() {
            public DraftPickView execute() {
                UUID userId = SessionManager.getInstance().getSession(sessionId).getUserId();
				return DraftManager.getInstance().sendCardPick(draftId, userId, cardPick);
            }
        });
	}

    @Override
    public void concedeGame(final UUID gameId, final String sessionId) throws MageException {
        execute("concedeGame", sessionId, new Action() {
            public void execute() {
                UUID userId = SessionManager.getInstance().getSession(sessionId).getUserId();
                GameManager.getInstance().concedeGame(gameId, userId);
            }
        });
    }

	@Override
	public boolean watchTable(final String sessionId, final UUID roomId, final UUID tableId) throws MageException {
		return executeWithResult("setUserData", sessionId, new ActionWithBooleanResult() {
            public Boolean execute() throws MageException {
                UUID userId = SessionManager.getInstance().getSession(sessionId).getUserId();
				return GamesRoomManager.getInstance().getRoom(roomId).watchTable(userId, tableId);
            }
        });
	}

    @Override
    public void watchGame(final UUID gameId, final String sessionId) throws MageException {
        execute("watchGame", sessionId, new Action() {
            public void execute() {
                UUID userId = SessionManager.getInstance().getSession(sessionId).getUserId();
                GameManager.getInstance().watchGame(gameId, userId);
            }
        });
    }

    @Override
    public void stopWatching(final UUID gameId, final String sessionId) throws MageException {
        execute("stopWatching", sessionId, new Action() {
            public void execute() {
                UUID userId = SessionManager.getInstance().getSession(sessionId).getUserId();
                GameManager.getInstance().stopWatching(gameId, userId);
            }
        });
    }

	@Override
	public void replayGame(final UUID gameId, final String sessionId) throws MageException {
        execute("replayGame", sessionId, new Action() {
            public void execute() {
                UUID userId = SessionManager.getInstance().getSession(sessionId).getUserId();
                ReplayManager.getInstance().replayGame(gameId, userId);
            }
        });
	}

    @Override
    public void startReplay(final UUID gameId, final String sessionId) throws MageException {
        execute("startReplay", sessionId, new Action() {
            public void execute() {
                UUID userId = SessionManager.getInstance().getSession(sessionId).getUserId();
                ReplayManager.getInstance().startReplay(gameId, userId);
            }
        });
    }

	@Override
	public void stopReplay(final UUID gameId, final String sessionId) throws MageException {
        execute("stopReplay", sessionId, new Action() {
            public void execute() {
                UUID userId = SessionManager.getInstance().getSession(sessionId).getUserId();
                ReplayManager.getInstance().stopReplay(gameId, userId);
            }
        });
	}

	@Override
    public void nextPlay(final UUID gameId, final String sessionId) throws MageException {
        execute("nextPlay", sessionId, new Action() {
            public void execute() {
                UUID userId = SessionManager.getInstance().getSession(sessionId).getUserId();
                ReplayManager.getInstance().nextPlay(gameId, userId);
            }
        });
    }

	@Override
	public void previousPlay(final UUID gameId, final String sessionId) throws MageException {
        execute("previousPlay", sessionId, new Action() {
            public void execute() {
                UUID userId = SessionManager.getInstance().getSession(sessionId).getUserId();
                ReplayManager.getInstance().previousPlay(gameId, userId);
            }
        });
	}

    @Override
    public void skipForward(final UUID gameId, final String sessionId, final int moves) throws MageException {
        execute("skipForward", sessionId, new Action() {
            public void execute() {
                UUID userId = SessionManager.getInstance().getSession(sessionId).getUserId();
                ReplayManager.getInstance().skipForward(gameId, userId, moves);
            }
        });
    }

    @Override
    //TODO: check how often it is used
	public ServerState getServerState() throws MageException {
		try {
			return new ServerState(
					GameFactory.getInstance().getGameTypes(),
					TournamentFactory.getInstance().getTournamentTypes(),
					PlayerFactory.getInstance().getPlayerTypes().toArray(new String[0]),
					DeckValidatorFactory.getInstance().getDeckTypes().toArray(new String[0]),
					testMode,
					Main.getVersion());
		}
		catch (Exception ex) {
			handleException(ex);
		}
		return null;
	}

    @Override
    public void cheat(final UUID gameId, final String sessionId, final UUID playerId, final DeckCardLists deckList) throws MageException {
        execute("cheat", sessionId, new Action() {
            public void execute() {
                if (testMode) {
                    UUID userId = SessionManager.getInstance().getSession(sessionId).getUserId();
                    GameManager.getInstance().cheat(gameId, userId, playerId, deckList);
                }
            }
        });
    }

    @Override
	public boolean cheat(final UUID gameId, final String sessionId, final UUID playerId, final String cardName) throws MageException {
        return executeWithResult("cheatOne", sessionId, new ActionWithBooleanResult() {
            public Boolean execute() {
                if (testMode) {
                    UUID userId = SessionManager.getInstance().getSession(sessionId).getUserId();
                    return GameManager.getInstance().cheat(gameId, userId, playerId, cardName);
                }
                return false;
            }
        });
 	}

	public void handleException(Exception ex) throws MageException {
		logger.fatal("", ex);
		throw new MageException("Server error: " + ex.getMessage());
	}

	@Override
    public GameView getGameView(final UUID gameId, final String sessionId, final UUID playerId) throws MageException {
 		return executeWithResult("getGameView", sessionId, new ActionWithNullNegativeResult<GameView>() {
             public GameView execute() throws MageException {
                 UUID userId = SessionManager.getInstance().getSession(sessionId).getUserId();
                 return GameManager.getInstance().getGameView(gameId, userId, playerId);
             }
        });
    }

	@Override
	public List<UserView> getUsers(String sessionId) throws MageException {
        return executeWithResult("getUsers", sessionId, new ActionWithNullNegativeResult<List<UserView>>() {
            public List<UserView> execute() throws MageException {
                List<UserView> users = new ArrayList<UserView>();
                for (User user : UserManager.getInstance().getUsers()) {
                    users.add(new UserView(user.getName(), "", user.getSessionId(), user.getConnectionTime()));
                }
                return users;
            }
        }, true);
	}

	@Override
	public void disconnectUser(final String sessionId, final String userSessionId) throws MageException {
        execute("disconnectUser", sessionId, new Action() {
            public void execute() {
                SessionManager.getInstance().disconnectUser(sessionId, userSessionId);
            }
        });
	}

	@Override
	public void removeTable(final String sessionId, final UUID tableId) throws MageException {
        execute("removeTable", sessionId, new Action() {
            public void execute() {
                UUID userId = SessionManager.getInstance().getSession(sessionId).getUserId();
                TableManager.getInstance().removeTable(userId, tableId);
            }
        });
	}

	@Override
	public Object getServerMessagesCompressed(String sessionId) throws MageException {
		return executeWithResult("getGameView", sessionId, new ActionWithNullNegativeResult<Object>() {
             public Object execute() throws MageException {
                 return CompressUtil.compress(ServerMessagesUtil.getInstance().getMessages());
             }
        });
	}

    @Override
    public void sendFeedbackMessage(final String sessionId, final String username, final String title, final String type, final String message, final String email) throws MageException {
        if (title != null && message != null) {
            execute("sendFeedbackMessage", sessionId, new Action() {
                public void execute() {
                    String host = SessionManager.getInstance().getSession(sessionId).getHost();
                    FeedbackServiceImpl.instance.feedback(username, title, type, message, email, host);
                    LogServiceImpl.instance.log(LogKeys.KEY_FEEDBACK_ADDED, sessionId, username, host);
                }
            });
        }
    }

    public void sendBroadcastMessage(final String sessionId, final String message) throws MageException {
        if (message != null) {
            execute("sendBroadcastMessage", sessionId, new Action() {
                public void execute() {
                    for (User user : UserManager.getInstance().getUsers()) {
                        if (message.toLowerCase().startsWith("warn")) {
                            user.fireCallback(new ClientCallback("serverMessage", null, new ChatMessage("SERVER", message, null, MessageColor.RED)));
                        } else {
                            user.fireCallback(new ClientCallback("serverMessage", null, new ChatMessage("SERVER", message, null, MessageColor.BLUE)));
                        }
                    }
                }
            }, true);
        }
    }

    protected void execute(final String actionName, final String sessionId, final Action action, boolean checkAdminRights) throws MageException {
        if (checkAdminRights) {
            if (!SessionManager.getInstance().isAdmin(sessionId)) {
                LogServiceImpl.instance.log(LogKeys.KEY_NOT_ADMIN, actionName, sessionId);
                return;
            }
        }
        execute(actionName, sessionId, action);
    }

    protected void execute(final String actionName, final String sessionId, final Action action) throws MageException {
        if (SessionManager.getInstance().isValidSession(sessionId)) {
			try {
				callExecutor.execute(
					new Runnable() {
						@Override
						public void run() {
                            if (SessionManager.getInstance().isValidSession(sessionId)) {
                                try {
                                    action.execute();
                                } catch (MageException me) {
                                    throw new RuntimeException(me);
                                }
                            } else {
                                LogServiceImpl.instance.log(LogKeys.KEY_NOT_VALID_SESSION_INTERNAL, actionName, sessionId);
                            }
						}
					}
				);
			}
			catch (Exception ex) {
				handleException(ex);
			}
		} else {
            LogServiceImpl.instance.log(LogKeys.KEY_NOT_VALID_SESSION, actionName, sessionId);
        }
    }

    protected <T> T executeWithResult(String actionName, final String sessionId, final ActionWithResult<T> action, boolean checkAdminRights) throws MageException {
        if (checkAdminRights) {
            if (!SessionManager.getInstance().isAdmin(sessionId)) {
                LogServiceImpl.instance.log(LogKeys.KEY_NOT_ADMIN, actionName, sessionId);
                return action.negativeResult();
            }
        }
        return executeWithResult(actionName, sessionId, action);
    }

    //TODO: also run in threads with future task
    protected <T> T executeWithResult(String actionName, final String sessionId, final ActionWithResult<T> action) throws MageException {
        if (SessionManager.getInstance().isValidSession(sessionId)) {
            try {
                return action.execute();
            } catch (Exception ex) {
                handleException(ex);
            }
        } else {
            LogServiceImpl.instance.log(LogKeys.KEY_NOT_VALID_SESSION, actionName, sessionId);
        }
        return action.negativeResult();
    }
}
