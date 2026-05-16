package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.PrepareUtil;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;

public class UnprepareSourceEffect extends OneShotEffect {

    public UnprepareSourceEffect() {
        super(Outcome.Neutral);
        staticText = "{this} becomes unprepared";
    }

    private UnprepareSourceEffect(final UnprepareSourceEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        return PrepareUtil.unprepare(permanent, game);
    }

    @Override
    public UnprepareSourceEffect copy() {
        return new UnprepareSourceEffect(this);
    }
}
