package mage.abilities.effects.common.continuous;

import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

public class BecomesChosenCreatureTypeSourceEffect extends OneShotEffect {

    private final boolean nonWall;
    private final Duration duration;

    public BecomesChosenCreatureTypeSourceEffect() {
        this(false, Duration.EndOfTurn);
    }

    public BecomesChosenCreatureTypeSourceEffect(boolean nonWall, Duration duration) {
        super(Outcome.BoostCreature);
        this.nonWall = nonWall;
        this.duration = duration;
        staticText = "{this} becomes the creature type of your choice" + (duration == Duration.EndOfTurn ? " until end of turn." : "");
    }

    protected BecomesChosenCreatureTypeSourceEffect(final BecomesChosenCreatureTypeSourceEffect effect) {
        super(effect);
        this.nonWall = effect.nonWall;
        this.duration = effect.duration;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourcePerm = game.getPermanent(source.getSourceId());
        if (sourcePerm == null) {
            return false;
        }
        Effect effect = new BecomesChosenCreatureTypeTargetEffect(nonWall, duration);
        effect.setTargetPointer(new FixedTarget(sourcePerm, game));
        return effect.apply(game, source);
    }

    @Override
    public BecomesChosenCreatureTypeSourceEffect copy() {
        return new BecomesChosenCreatureTypeSourceEffect(this);
    }

}
