package mage.abilities.effects.common.combat;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttacksIfAbleTargetPlayerSourceEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.util.RandomUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AttackIfAbleTargetRandomOpponentSourceEffect extends OneShotEffect {

    public AttackIfAbleTargetRandomOpponentSourceEffect() {
        super(Outcome.Benefit);
        this.staticText = "choose an opponent at random. {this} attacks that player this combat if able";
    }

    public AttackIfAbleTargetRandomOpponentSourceEffect(final AttackIfAbleTargetRandomOpponentSourceEffect effect) {
        super(effect);
    }

    @Override
    public AttackIfAbleTargetRandomOpponentSourceEffect copy() {
        return new AttackIfAbleTargetRandomOpponentSourceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        List<UUID> opponents = new ArrayList<>(game.getOpponents(controller.getId()));
        Player opponent = game.getPlayer(opponents.get(RandomUtil.nextInt(opponents.size())));
        if (opponent != null) {
            game.informPlayers(opponent.getLogName() + " was chosen at random.");
            ContinuousEffect effect = new AttacksIfAbleTargetPlayerSourceEffect();
            effect.setTargetPointer(new FixedTarget(opponent.getId()));
            game.addEffect(effect, source);
            return true;
        }
        return false;
    }
}
