package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class AttachEffect extends OneShotEffect {

    public AttachEffect(Outcome outcome) {
        super(outcome);
    }

    public AttachEffect(Outcome outcome, String rule) {
        super(outcome);
        staticText = rule;
    }

    public AttachEffect(final AttachEffect effect) {
        super(effect);
    }

    @Override
    public AttachEffect copy() {
        return new AttachEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        if (sourcePermanent == null) {
            return false;
        }
        // if it activating on the stack then allow +1 zcc
        int zcc = game.getState().getZoneChangeCounter(sourcePermanent.getId());
        if (zcc != CardUtil.getActualSourceObjectZoneChangeCounter(game, source)
                && zcc != CardUtil.getActualSourceObjectZoneChangeCounter(game, source) + 1) {
            return false;
        }
        UUID targetId = getTargetPointer().getFirst(game, source);
        Permanent permanent = game.getPermanent(targetId);
        if (permanent != null) {
            return permanent.addAttachment(source.getSourceId(), source, game);
        }
        Player player = game.getPlayer(targetId);
        if (player != null) {
            return player.addAttachment(source.getSourceId(), source, game);
        }
        if (source.getTargets().isEmpty() || !(source.getTargets().get(0) instanceof TargetCard)) {
            return false;
        }
        Card card = game.getCard(targetId);
        return card != null && card.addAttachment(source.getSourceId(), source, game);
    }

}
