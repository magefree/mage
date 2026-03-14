package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.Optional;

/**
 * @author TheElk801
 */
public class FightEnchantedTargetEffect extends OneShotEffect {

    public FightEnchantedTargetEffect() {
        super(Outcome.Benefit);
    }

    private FightEnchantedTargetEffect(final FightEnchantedTargetEffect effect) {
        super(effect);
    }

    @Override
    public FightEnchantedTargetEffect copy() {
        return new FightEnchantedTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = Optional
                .ofNullable(source.getSourcePermanentOrLKI(game))
                .map(Permanent::getAttachedTo)
                .map(game::getPermanent)
                .orElse(null);
        Permanent creature = game.getPermanent(getTargetPointer().getFirst(game, source));
        return permanent != null && creature != null && permanent.fight(creature, source, game);
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        return "enchanted creature fights " + getTargetPointer().describeTargets(mode.getTargets(), "it");
    }
}
