package org.mage.test.serverside.base;

import mage.cards.Card;
import mage.cards.decks.Deck;
import mage.cards.decks.importer.DeckImporterUtil;
import mage.constants.MultiplayerAttackOption;
import mage.constants.RangeOfInfluence;
import mage.game.*;
import mage.game.permanent.Permanent;
import mage.players.Player;
import org.junit.Assert;
import org.mage.test.serverside.base.impl.CardTestPlayerAPIImpl;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Base class for testing single cards and effects in multiplayer game.
 * For PvP games {@see CardTestPlayerBase}
 *
 * @author magenoxx_at_gmail.com
 */
public abstract class CardTestMultiPlayerBase extends CardTestPlayerAPIImpl {

    @Override
    protected Game createNewGameAndPlayers() throws GameException, FileNotFoundException {
        Game game = new FreeForAll(MultiplayerAttackOption.LEFT, RangeOfInfluence.ONE, 0, 20);

        playerA = createPlayer(game, playerA, "PlayerA");
        playerB = createPlayer(game, playerB, "PlayerB");
        playerC = createPlayer(game, playerC, "PlayerC");
        playerD = createPlayer(game, playerD, "PlayerD");
        return game;
    }
    
}
