

package mage.abilities.effects.common;

import mage.constants.Outcome;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author noxx
 */
public class ImprintTargetEffect extends OneShotEffect {

    public ImprintTargetEffect() {
        super(Outcome.Neutral);
    }

    protected ImprintTargetEffect(final ImprintTargetEffect effect) {
        super(effect);
    }

    @Override
    public ImprintTargetEffect copy() {
        return new ImprintTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        if (sourcePermanent != null) {
            Permanent permanent = game.getPermanent(targetPointer.getFirst(game, source));
            if (permanent != null) {
                sourcePermanent.imprint(permanent.getId(), game);
            } else {
                Card card = game.getCard(targetPointer.getFirst(game, source));
                if (card != null) {
                    sourcePermanent.imprint(card.getId(), game);
                }
            }
        }

        return true;
    }

    @Override
    public String getText(Mode mode) {
        return null;
    }
}
