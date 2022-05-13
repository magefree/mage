
package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.game.Game;
import mage.players.Player;
import mage.players.PlayerList;

/**
 *
 * @author fireshoes
 */
public final class Triskaidekaphobia extends CardImpl {

    public Triskaidekaphobia(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{3}{B}");

        // At the beginning of your upkeep, choose one - Each player with exactly 13 life loses the game, then each player gains 1 life.
        // Each player with exactly 13 life loses the game, then each player loses 1 life.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(new TriskaidekaphobiaGainLifeEffect(), TargetController.YOU, false);
        Mode mode = new Mode(new TriskaidekaphobiaLoseLifeEffect());
        ability.addMode(mode);
        this.addAbility(ability);
    }

    private Triskaidekaphobia(final Triskaidekaphobia card) {
        super(card);
    }

    @Override
    public Triskaidekaphobia copy() {
        return new Triskaidekaphobia(this);
    }
}

class TriskaidekaphobiaGainLifeEffect extends OneShotEffect {

    public TriskaidekaphobiaGainLifeEffect() {
        super(Outcome.Neutral);
        this.staticText = "Each player with exactly 13 life loses the game, then each player gains 1 life";
    }

    public TriskaidekaphobiaGainLifeEffect(final TriskaidekaphobiaGainLifeEffect effect) {
        super(effect);
    }

    @Override
    public TriskaidekaphobiaGainLifeEffect copy() {
        return new TriskaidekaphobiaGainLifeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int life;
        PlayerList playerList = game.getState().getPlayersInRange(source.getControllerId(), game);
        for (UUID pid : playerList) {
            Player player = game.getPlayer(pid);
            if (player != null) {
                life = player.getLife();
                if (life == 13) {
                    player.lost(game);
                }
            }
        }
        for (UUID pid : playerList) {
            Player player = game.getPlayer(pid);
            if (player != null) {
                player.gainLife(1, game, source);
            }
        }
        return true;
    }
}

class TriskaidekaphobiaLoseLifeEffect extends OneShotEffect {

    public TriskaidekaphobiaLoseLifeEffect() {
        super(Outcome.Neutral);
        this.staticText = "Each player with exactly 13 life loses the game, then each player loses 1 life";
    }

    public TriskaidekaphobiaLoseLifeEffect(final TriskaidekaphobiaLoseLifeEffect effect) {
        super(effect);
    }

    @Override
    public TriskaidekaphobiaLoseLifeEffect copy() {
        return new TriskaidekaphobiaLoseLifeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int life;
        PlayerList playerList = game.getState().getPlayersInRange(source.getControllerId(), game);
        for (UUID pid : playerList) {
            Player player = game.getPlayer(pid);
            if (player != null) {
                life = player.getLife();
                if (life == 13) {
                    player.lost(game);
                }
            }
        }
        for (UUID pid : playerList) {
            Player player = game.getPlayer(pid);
            if (player != null) {
                player.loseLife(1, game, source, false);
            }
        }
        return true;
    }
}
