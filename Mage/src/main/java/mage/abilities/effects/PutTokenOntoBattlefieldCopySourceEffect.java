package mage.abilities.effects;

import mage.abilities.Ability;
import mage.abilities.effects.common.PutTokenOntoBattlefieldCopyTargetEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 * Created by glerman on 20/6/15.
 */
public class PutTokenOntoBattlefieldCopySourceEffect extends OneShotEffect {

    private final int number;

    public PutTokenOntoBattlefieldCopySourceEffect() {
        this(1);
    }

    public PutTokenOntoBattlefieldCopySourceEffect(int copies) {
        super(Outcome.PutCreatureInPlay);
        this.number = copies;
        staticText = "put a token onto the battlefield that's a copy of {this}";
    }

    public PutTokenOntoBattlefieldCopySourceEffect(final PutTokenOntoBattlefieldCopySourceEffect effect) {
        super(effect);
        this.number = effect.number;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (permanent != null) {
            PutTokenOntoBattlefieldCopyTargetEffect effect = new PutTokenOntoBattlefieldCopyTargetEffect(source.getControllerId(), null, false, number);
            effect.setTargetPointer(new FixedTarget(source.getSourceId()));
            return effect.apply(game, source);
        }
        return false;
    }

    @Override
    public PutTokenOntoBattlefieldCopySourceEffect copy() {
        return new PutTokenOntoBattlefieldCopySourceEffect(this);
    }
}
