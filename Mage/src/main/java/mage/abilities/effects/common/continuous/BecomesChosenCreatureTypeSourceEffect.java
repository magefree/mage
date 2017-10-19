package mage.abilities.effects.common.continuous;

import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

public class BecomesChosenCreatureTypeSourceEffect extends OneShotEffect {

    public BecomesChosenCreatureTypeSourceEffect() {
        this(false);
    }

    public BecomesChosenCreatureTypeSourceEffect(boolean nonWall) {
        super(Outcome.BoostCreature);
        staticText = "{this} becomes the creature type of your choice until end of turn.";
    }

    public BecomesChosenCreatureTypeSourceEffect(final BecomesChosenCreatureTypeSourceEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourcePerm = game.getPermanent(source.getSourceId());
        if (sourcePerm == null) {
            return false;
        }
        Effect effect = new BecomesChosenCreatureTypeTargetEffect();
        effect.setTargetPointer(new FixedTarget(sourcePerm, game));
        return effect.apply(game, source);
    }

    @Override
    public Effect copy() {
        return new BecomesChosenCreatureTypeSourceEffect(this);
    }

}
