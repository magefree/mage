package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

/**
 * Created by Eric on 9/24/2016.
 */
public class MistmeadowWitchEffect extends OneShotEffect {

    public MistmeadowWitchEffect() {
        super(Outcome.Detriment);
        staticText = "Exile target creature. Return that card to the battlefield under its owner's control at the beginning of the next end step";
    }

    private MistmeadowWitchEffect(final MistmeadowWitchEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (player == null || permanent == null) {
            return false;
        }
        player.moveCardsToExile(permanent, source, game, true, CardUtil.getExileZoneId(game, source), CardUtil.getSourceName(game, source));
        Effect effect = new ReturnToBattlefieldUnderOwnerControlTargetEffect(false, false);
        effect.setText("Return the exiled card to the battlefield under its owner's control");
        effect.setTargetPointer(new FixedTarget(source.getFirstTarget(), game));
        game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(effect), source);
        return true;
    }

    @Override
    public MistmeadowWitchEffect copy() {
        return new MistmeadowWitchEffect(this);
    }
}
