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
    private final boolean tapped;

    public CreateTokenCopySourceEffect() {
        this(1);
    }

    public CreateTokenCopySourceEffect(int copies) {
        this(copies, false);
    }

    public CreateTokenCopySourceEffect(int copies, boolean tapped) {
        super(Outcome.PutCreatureInPlay);
        this.number = copies;
        this.tapped = tapped;
        staticText = "create a " + (tapped ? "tapped " : "") + "token that's a copy of {this}";
    }

    public CreateTokenCopySourceEffect(final CreateTokenCopySourceEffect effect) {
        super(effect);
        this.number = effect.number;
        this.tapped = effect.tapped;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (permanent == null) {
            return false;
        }
        CreateTokenCopyTargetEffect effect = new CreateTokenCopyTargetEffect(
                source.getControllerId(), null, false, number, tapped, false
        );
        effect.setTargetPointer(new FixedTarget(source.getSourceId(), game));
        return effect.apply(game, source);
    }

    @Override
    public CreateTokenCopySourceEffect copy() {
        return new CreateTokenCopySourceEffect(this);
    }
}
