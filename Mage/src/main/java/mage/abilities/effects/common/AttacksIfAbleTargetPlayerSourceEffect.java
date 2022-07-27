package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.RequirementEffect;
import mage.constants.Duration;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

public class AttacksIfAbleTargetPlayerSourceEffect extends RequirementEffect {

    public AttacksIfAbleTargetPlayerSourceEffect() {
        super(Duration.EndOfCombat);
        staticText = "{this} attacks that player this combat if able";
    }

    public AttacksIfAbleTargetPlayerSourceEffect(final AttacksIfAbleTargetPlayerSourceEffect effect) {
        super(effect);
    }

    @Override
    public AttacksIfAbleTargetPlayerSourceEffect copy() {
        return new AttacksIfAbleTargetPlayerSourceEffect(this);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return permanent.getId().equals(source.getSourceId());
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
        return getTargetPointer().getFirst(game, source);
    }

}
