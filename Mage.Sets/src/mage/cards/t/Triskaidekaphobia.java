package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.GainLifeAllEffect;
import mage.abilities.effects.common.LoseLifeAllPlayersEffect;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class Triskaidekaphobia extends CardImpl {

    public Triskaidekaphobia(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{B}");

        // At the beginning of your upkeep, choose one - Each player with exactly 13 life loses the game, then each player gains 1 life.
        // Each player with exactly 13 life loses the game, then each player loses 1 life.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(new TriskaidekaphobiaGainLifeEffect());
        ability.addEffect(new GainLifeAllEffect(1).concatBy(", then"));
        ability.addMode(new Mode(new TriskaidekaphobiaGainLifeEffect())
                .addEffect(new LoseLifeAllPlayersEffect(1).concatBy(", then")));
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

    TriskaidekaphobiaGainLifeEffect() {
        super(Outcome.Neutral);
        this.staticText = "each player with exactly 13 life loses the game";
    }

    private TriskaidekaphobiaGainLifeEffect(final TriskaidekaphobiaGainLifeEffect effect) {
        super(effect);
    }

    @Override
    public TriskaidekaphobiaGainLifeEffect copy() {
        return new TriskaidekaphobiaGainLifeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player != null && player.getLife() == 13) {
                player.lost(game);
            }
        }
        return true;
    }
}
