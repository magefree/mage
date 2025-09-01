package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.EachTargetPointer;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public class AttachTargetToTargetEffect extends OneShotEffect {

    public AttachTargetToTargetEffect() {
        super(Outcome.BoostCreature);
        this.setTargetPointer(new EachTargetPointer());
    }

    private AttachTargetToTargetEffect(final AttachTargetToTargetEffect effect) {
        super(effect);
    }

    @Override
    public AttachTargetToTargetEffect copy() {
        return new AttachTargetToTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<Permanent> permanents = this.getTargetPointer()
                .getTargets(game, source)
                .stream()
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        return permanents.size() >= 2 && permanents.get(1).addAttachment(permanents.get(0).getId(), source, game);
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        if (mode.getTargets().size() != 2) {
            throw new IllegalStateException("It must have two targets, but found " + mode.getTargets().size());
        }
        return "attach " + mode.getTargets().get(0).getDescription() + " to " + mode.getTargets().get(1).getDescription();
    }
}
