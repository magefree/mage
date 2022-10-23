package mage.abilities.effects.common.combat;

import mage.abilities.Ability;
import mage.abilities.effects.RequirementEffect;
import mage.constants.Duration;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.Target;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public class MustAttackOpponentWithCreatureTargetEffect extends RequirementEffect {

    public MustAttackOpponentWithCreatureTargetEffect() {
        super(Duration.EndOfTurn);
        staticText = "target creature attacks target opponent this turn if able";
    }

    private MustAttackOpponentWithCreatureTargetEffect(final MustAttackOpponentWithCreatureTargetEffect effect) {
        super(effect);
    }

    @Override
    public MustAttackOpponentWithCreatureTargetEffect copy() {
        return new MustAttackOpponentWithCreatureTargetEffect(this);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return this.getTargetPointer().getTargets(game, source).contains(permanent.getId());
    }

    @Override
    public boolean mustAttack(Game game) {
        return true;
    }

    @Override
    public boolean mustBlock(Game game) {
        return false;
    }

    @Override
    public UUID mustAttackDefender(Ability source, Game game) {
        return Optional.ofNullable(source.getTargets().get(1)).map(Target::getFirstTarget).orElse(null);
    }
}
