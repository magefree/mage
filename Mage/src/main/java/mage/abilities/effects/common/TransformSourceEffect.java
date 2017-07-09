/*
* Copyright 2011 BetaSteward_at_googlemail.com. All rights reserved.
*
* Redistribution and use in source and binary forms, with or without modification, are
* permitted provided that the following conditions are met:
*
*    1. Redistributions of source code must retain the above copyright notice, this list of
*       conditions and the following disclaimer.
*
*    2. Redistributions in binary form must reproduce the above copyright notice, this list
*       of conditions and the following disclaimer in the documentation and/or other materials
*       provided with the distribution.
*
* THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
* WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
* FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
* CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
* CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
* SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
* ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
* NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
* ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
* The views and conclusions contained in the software and documentation are those of the
* authors and should not be interpreted as representing official policies, either expressed
* or implied, of BetaSteward_at_googlemail.com.
 */
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
        if (sourceObject != null && sourceObject instanceof Permanent) {
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
