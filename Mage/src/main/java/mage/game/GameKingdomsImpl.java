package mage.game;

import mage.constants.MultiplayerAttackOption;
import mage.constants.RangeOfInfluence;
import mage.players.Player;
import mage.players.PlayerList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * Created by Andrew Bueide on 7/23/17.
 */
public abstract class GameKingdomsImpl extends GameCommanderImpl {

    protected static final KingdomRole[] roles = {
            new KingdomRole("King", "You are the king, Mighty ruler of the land! Your objective is to reclaim " +
                    "control over your empire at any cost and eliminate any pesky barbarians or spies. Many will " +
                    "claim to be your knight in shining armor, but alas, there is no way of telling who is the true knight." +
                    "The king wins when either the king or the king and the knight are the only players left standing."),
            new KingdomRole("Knight", "You are the knight. Your mission in life is to protect your Majesty! " +
                    "Unfortunately, it appears that they are filled with imperialist rage and can’t recognize who you are! " +
                    "The knight wins when the king and the knight are the last players that remain."),
            new KingdomRole("Barbarian", "You are 1 of 2 barbarian who lives on the outermost fringes of the kingdom. " +
                    "Mustering up a crew of irritated guardsmen in the countryside you set out to challenge the king and his throne. " +
                    "The barbarians wins when the king dies."),
            new KingdomRole("Assassin", "Your goal is to kill the king, but you know that if you kill the king, " +
                    "the gruesome barbarians will push you aside and take the throne for themselves. The assassin wins " +
                    "when the everyone but the king dies, and then the king dies. The king has to die last in order for the assassin to win.")
    };

    protected PlayerList randomizedPlayerList = getPlayerList().copy();

    public GameKingdomsImpl(MultiplayerAttackOption attackOption, RangeOfInfluence range, int freeMulligans, int startLife) {
        super(attackOption, range, freeMulligans, startLife);
        //Randomize the randomizedPlayerList, used for role selection.
        Collections.shuffle(randomizedPlayerList);
    }

    public GameKingdomsImpl(final GameCommanderImpl game) {
        super(game);
        this.alsoHand = game.alsoHand;
        this.alsoLibrary = game.alsoLibrary;
        this.startingPlayerSkipsDraw = game.startingPlayerSkipsDraw;
        this.checkCommanderDamage = game.checkCommanderDamage;

        Collections.shuffle(randomizedPlayerList);
    }

    protected void initializeRoles() {

    }

    protected KingdomRole getRole(Player player){

    }

//    @Override
//    protected UUID pickChoosingPlayer() {
//        UUID[] players = getPlayers().keySet().toArray(new UUID[0]);
//        UUID playerId;
//        while (!hasEnded()) {
//            playerId = players[RandomUtil.nextInt(players.length)];
//            Player player = getPlayer(playerId);
//            if (player != null && player.isInGame()) {
//                fireInformEvent(state.getPlayer(playerId).getLogName() + " won the toss");
//                return player.getId();
//            }
//        }
//        logger.debug("Game was not possible to pick a choosing player.  GameId:" + getId());
//        return null;
//    }
}
