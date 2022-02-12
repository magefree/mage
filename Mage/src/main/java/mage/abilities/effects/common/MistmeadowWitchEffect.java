package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
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
        game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(new ReturnFromExileEffect(Zone.BATTLEFIELD, "return the exiled card to the battlefield under its owner's control")), source);
        return true;
    }

    @Override
    public MistmeadowWitchEffect copy() {
        return new MistmeadowWitchEffect(this);
    }
}
