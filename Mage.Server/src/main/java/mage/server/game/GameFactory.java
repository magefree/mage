

package mage.server.game;

import mage.game.match.Match;
import mage.game.match.MatchOptions;
import mage.game.match.MatchType;
import mage.view.GameTypeView;
import org.apache.log4j.Logger;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public enum GameFactory {

    instance;
    private static final Logger logger = Logger.getLogger(GameFactory.class);

    private final Map<String, Class<Match>> games = new HashMap<>();
    private final Map<String, MatchType> gameTypes = new HashMap<>();
    private final List<GameTypeView> gameTypeViews = new ArrayList<>();



    private GameFactory() {}

    public Match createMatch(String gameType, MatchOptions options) {

        Match match;
        try {
            Constructor<Match> con = games.get(gameType).getConstructor(MatchOptions.class);
            match = con.newInstance(options);
        } catch (Exception ex) {
            logger.fatal("Error creating match - " + gameType, ex);
            return null;
        }
        logger.debug("Game created: " + gameType); // + game.getId().toString());

        return match;
    }

    public List<GameTypeView> getGameTypes() {
        return gameTypeViews;
    }

    public void addGameType(String name, MatchType matchType, Class game) {
        if (game != null) {
            this.games.put(name, game);
            this.gameTypes.put(name, matchType);
            this.gameTypeViews.add(new GameTypeView(matchType));
        }
    }

}
