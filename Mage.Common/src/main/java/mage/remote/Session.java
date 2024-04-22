package mage.remote;

import mage.remote.interfaces.ChatSession;
import mage.remote.interfaces.ClientData;
import mage.remote.interfaces.Connect;
import mage.remote.interfaces.Feedback;
import mage.remote.interfaces.GamePlay;
import mage.remote.interfaces.GameTypes;
import mage.remote.interfaces.PlayerActions;
import mage.remote.interfaces.Replays;
import mage.remote.interfaces.ServerState;
import mage.remote.interfaces.Testable;

/**
 * Network: client/server session
 *
 * @author noxx
 */
public interface Session extends ClientData, Connect, GamePlay, GameTypes, ServerState, ChatSession, Feedback, PlayerActions, Replays, Testable {

    void appendJsonLog(ActionData actionData);
}
