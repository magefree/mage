package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 *
 * @author jmharmon
 */

public final class SkeletonToken2 extends TokenImpl {

    public SkeletonToken2() {
        super("Skeleton", "1/1 black Skeleton creature token with \"When this creature dies, each opponent gains 2 life.\"");
        cardType.add(CardType.CREATURE);
        this.subtype.add(SubType.SKELETON);
        color.setBlack(true);
        power = new MageInt(1);
        toughness = new MageInt(1);
        Ability ability = new DiesTriggeredAbility(new OpponentsGainLifeEffect());
        this.addAbility(ability);
    }

    public SkeletonToken2(final SkeletonToken2 token) {
        super(token);
    }

    public SkeletonToken2 copy() {
        return new SkeletonToken2(this);
    }
}

class OpponentsGainLifeEffect extends OneShotEffect {

    public OpponentsGainLifeEffect() {
        super(Outcome.GainLife);
        staticText = "each opponent gains 2 life";
    }

    public OpponentsGainLifeEffect(final OpponentsGainLifeEffect effect) {
        super(effect);
    }

    @Override
    public OpponentsGainLifeEffect copy() {
        return new OpponentsGainLifeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player != null && game.isOpponent(player, source.getControllerId())) {
                player.gainLife(2, game, source);
            }
        }
        return true;
    }
}
