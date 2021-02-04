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
        if (sourcePermanent != null) {
            // if it activating on the stack then allow +1 zcc
            int zcc = game.getState().getZoneChangeCounter(sourcePermanent.getId());
            if (zcc == CardUtil.getActualSourceObjectZoneChangeCounter(game, source)
                    || zcc == CardUtil.getActualSourceObjectZoneChangeCounter(game, source) + 1) {
                Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
                if (permanent != null) {
                    return permanent.addAttachment(source.getSourceId(), source, game);
                } else {
                    Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
                    if (player != null) {
                        return player.addAttachment(source.getSourceId(), source, game);
                    }
                    if (!source.getTargets().isEmpty() && source.getTargets().get(0) instanceof TargetCard) { // e.g. Spellweaver Volute
                        Card card = game.getCard(getTargetPointer().getFirst(game, source));
                        if (card != null) {
                            return card.addAttachment(source.getSourceId(), source, game);
                        }
                    }
                }
            }
        }

        return false;
    }

}
