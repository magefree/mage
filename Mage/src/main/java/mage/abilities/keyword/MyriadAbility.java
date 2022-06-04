
package mage.abilities.keyword;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.delayed.AtTheEndOfCombatDelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.constants.Outcome;
import mage.constants.SetTargetPointer;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.target.targetpointer.FixedTargets;
import org.apache.log4j.Logger;

public class MyriadAbility extends AttacksTriggeredAbility {

    public MyriadAbility() {
        super(new MyriadEffect(), false,
                "myriad <i>(Whenever this creature attacks, for each opponent other than the defending player, "
                + "put a token that's a copy of this creature onto the battlefield tapped and attacking "
                + "that player or a planeswalker they control. Exile those tokens at the end of combat.)</i>",
                SetTargetPointer.PLAYER
        );
    }

    public MyriadAbility(final MyriadAbility ability) {
        super(ability);
    }

    @Override
    public MyriadAbility copy() {
        return new MyriadAbility(this);
    }

}

class MyriadEffect extends OneShotEffect {

    public MyriadEffect() {
        super(Outcome.Benefit);
        this.staticText = "for each opponent other than the defending player, you may put a token "
                + "that's a copy of this creature onto the battlefield tapped and attacking that "
                + "player or a planeswalker they control. "
                + "Exile the tokens at the end of combat";
    }

    public MyriadEffect(final MyriadEffect effect) {
        super(effect);
    }

    @Override
    public MyriadEffect copy() {
        return new MyriadEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourceObject = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (controller != null && sourceObject != null) {
            UUID defendingPlayerId = game.getCombat().getDefendingPlayerId(source.getSourceId(), game);
            if (defendingPlayerId == null) {
                Logger.getLogger(MyriadEffect.class).error("defending player == null source: " + sourceObject.getName() + " attacking: " + (sourceObject.isAttacking() ? "Y" : "N"));
                return false;
            }
            List<Permanent> tokens = new ArrayList<>();
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                if (!playerId.equals(defendingPlayerId) && controller.hasOpponent(playerId, game)) {
                    Player opponent = game.getPlayer(playerId);
                    if (opponent != null && controller.chooseUse(Outcome.PutCreatureInPlay,
                            "Put a copy of " + sourceObject.getIdName() + " onto battlefield attacking " + opponent.getName() + '?', source, game)) {
                        CreateTokenCopyTargetEffect effect = new CreateTokenCopyTargetEffect(controller.getId(), null, false, 1, true, true, playerId);
                        effect.setTargetPointer(new FixedTarget(sourceObject, game));
                        effect.apply(game, source);
                        tokens.addAll(effect.getAddedPermanents());
                    }
                }
            }
            if (!tokens.isEmpty()) {
                ExileTargetEffect exileEffect = new ExileTargetEffect();
                exileEffect.setTargetPointer(new FixedTargets(tokens, game));
                game.addDelayedTriggeredAbility(new AtTheEndOfCombatDelayedTriggeredAbility(exileEffect), source);
            }
            return true;
        }
        return false;
    }
}
