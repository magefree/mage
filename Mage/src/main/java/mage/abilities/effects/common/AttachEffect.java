package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;

/**
 *
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
            int zcc = game.getState().getZoneChangeCounter(sourcePermanent.getId());
            if (zcc == source.getSourceObjectZoneChangeCounter()
                    || zcc == source.getSourceObjectZoneChangeCounter() + 1
                    || zcc == source.getSourceObjectZoneChangeCounter() + 2) {
                Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
                if (permanent != null) {
                    return permanent.addAttachment(source.getSourceId(), game);
                } else {
                    Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
                    if (player != null) {
                        return player.addAttachment(source.getSourceId(), game);
                    }
                    if (!source.getTargets().isEmpty() && source.getTargets().get(0) instanceof TargetCard) { // e.g. Spellweaver Volute
                        Card card = game.getCard(getTargetPointer().getFirst(game, source));
                        if (card != null) {
                            return card.addAttachment(source.getSourceId(), game);
                        }
                    }
                }
            }
        }

        return false;
    }

}
