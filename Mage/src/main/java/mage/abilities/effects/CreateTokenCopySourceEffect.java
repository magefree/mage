package mage.abilities.effects;

import mage.abilities.Ability;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 * Created by glerman on 20/6/15.
 */
public class CreateTokenCopySourceEffect extends OneShotEffect {

    private final int number;

    public CreateTokenCopySourceEffect() {
        this(1);
    }

    public CreateTokenCopySourceEffect(int copies) {
        super(Outcome.PutCreatureInPlay);
        this.number = copies;
        staticText = "create a token that's a copy of {this}";
    }

    public CreateTokenCopySourceEffect(final CreateTokenCopySourceEffect effect) {
        super(effect);
        this.number = effect.number;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (permanent != null) {
            CreateTokenCopyTargetEffect effect = new CreateTokenCopyTargetEffect(source.getControllerId(), null, false, number);
            effect.setTargetPointer(new FixedTarget(source.getSourceId()));
            return effect.apply(game, source);
        }
        return false;
    }

    @Override
    public CreateTokenCopySourceEffect copy() {
        return new CreateTokenCopySourceEffect(this);
    }
}
