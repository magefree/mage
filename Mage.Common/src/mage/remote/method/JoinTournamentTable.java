/*
 *  Copyright 2011 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
package mage.remote.method;

import java.rmi.RemoteException;
import java.util.UUID;
import mage.cards.decks.DeckCardLists;
import mage.MageException;
import mage.constants.Constants.SessionState;
import mage.game.GameException;
import mage.interfaces.Server;
import mage.remote.Connection;
import mage.remote.RemoteMethodCall;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class JoinTournamentTable extends RemoteMethodCall<Boolean> {

	private UUID roomId;
	private UUID tableId;
	private UUID sessionId;
	private String playerName;
	private String playerType;
	private int skill;

	public JoinTournamentTable(Connection connection, UUID sessionId, UUID roomId, UUID tableId, String playerName, String playerType, int skill) {
		super(connection, "JoinTournamentTable", SessionState.CONNECTED);
		this.roomId = roomId;
		this.tableId = tableId;
		this.sessionId = sessionId;
		this.playerName = playerName;
		this.playerType = playerType;
		this.skill = skill;
	}

	@Override
	protected Boolean performRemoteCall(Server server) throws MageException, GameException, RemoteException {
		return server.joinTournamentTable(sessionId, roomId, tableId, playerName, playerType, skill);
	}

}
