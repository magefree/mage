
package mage.abilities.effects.common;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentCard;

/**
 *
 * @author nantuko
 */
public class TransformSourceEffect extends OneShotEffect {

    private boolean withoutTrigger;
    private boolean fromDayToNight;

    /**
     * @param fromDayToNight Defines whether we transform from "day" side to
     * "night" or vice versa.
     */
    public TransformSourceEffect(boolean fromDayToNight) {
        this(fromDayToNight, false);
    }

    public TransformSourceEffect(boolean fromDayToNight, boolean withoutTrigger) {
        super(Outcome.Transform);
        this.withoutTrigger = withoutTrigger;
        this.fromDayToNight = fromDayToNight;
        staticText = "transform {this}";
    }

    public TransformSourceEffect(final TransformSourceEffect effect) {
        super(effect);
        this.withoutTrigger = effect.withoutTrigger;
        this.fromDayToNight = effect.fromDayToNight;
    }

    @Override
    public TransformSourceEffect copy() {
        return new TransformSourceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject sourceObject = source.getSourceObjectIfItStillExists(game); // Transform only if it's the same object as the effect was put on the stack
        if (sourceObject instanceof Permanent) {
            Permanent sourcePermanent = (Permanent) sourceObject;
            if (sourcePermanent.canTransform(source, game)) {
                // check not to transform twice the same side
                if (sourcePermanent.isTransformed() != fromDayToNight) {
                    if (withoutTrigger) {
                        sourcePermanent.setTransformed(fromDayToNight);
                    } else {
                        sourcePermanent.transform(game);
                    }
                    if (!game.isSimulation()) {
                        if (fromDayToNight) {
                            if (sourcePermanent.getSecondCardFace() != null) {
                                if (sourcePermanent instanceof PermanentCard) {
                                    game.informPlayers(((PermanentCard) sourcePermanent).getCard().getLogName() + " transforms into " + sourcePermanent.getSecondCardFace().getLogName());
                                }
                            }
                        } else {
                            game.informPlayers(sourcePermanent.getSecondCardFace().getLogName() + " transforms into " + sourcePermanent.getLogName());
                        }
                    }
                }
            }
        }
        return true;
    }

}
