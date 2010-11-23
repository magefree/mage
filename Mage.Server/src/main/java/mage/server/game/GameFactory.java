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

package mage.server.game;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import mage.Constants.MultiplayerAttackOption;
import mage.Constants.RangeOfInfluence;
import mage.game.Game;
import mage.util.Logging;
import mage.game.GameType;
import mage.view.GameTypeView;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class GameFactory {

	private final static GameFactory INSTANCE = new GameFactory();
	private final static Logger logger = Logging.getLogger(GameFactory.class.getName());

	private Map<String, Class<Game>> games = new HashMap<String, Class<Game>>();
	private Map<String, GameType> gameTypes = new HashMap<String, GameType>();
	private List<GameTypeView> gameTypeViews = new ArrayList<GameTypeView>();


	public static GameFactory getInstance() {
		return INSTANCE;
	}

	private GameFactory() {}

	public Game createGame(String gameType, MultiplayerAttackOption attackOption, RangeOfInfluence range) {

		Game game;
		Constructor<Game> con;
		try {
			con = games.get(gameType).getConstructor(new Class[]{MultiplayerAttackOption.class, RangeOfInfluence.class});
			game = con.newInstance(new Object[] {attackOption, range});
		} catch (Exception ex) {
			logger.log(Level.SEVERE, null, ex);
			return null;
		}
		logger.info("Game created: " + game.getId().toString());

		return game;
	}

	public List<GameTypeView> getGameTypes() {
		return gameTypeViews;
	}

	public void addGameType(String name, GameType gameType, Class game) {
		if (game != null) {
			this.games.put(name, game);
			this.gameTypes.put(name, gameType);
			this.gameTypeViews.add(new GameTypeView(gameType));
		}
	}

}
