/*
 *
 * Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com
 OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 *
 */
package mage.abilities.effects.common.continuous;

import mage.MageObject;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.choices.ChoiceColor;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.game.Game;
import mage.players.Player;

/**
 * @author LoneFox
 */
public class BecomesColorSourceEffect extends ContinuousEffectImpl {

    private ObjectColor setColor;

    public BecomesColorSourceEffect(Duration duration) {
        this(null, duration);
    }

    public BecomesColorSourceEffect(ObjectColor setColor, Duration duration) {
        super(duration, Layer.ColorChangingEffects_5, SubLayer.NA, Outcome.Benefit);
        this.setColor = setColor;
    }

    public BecomesColorSourceEffect(final BecomesColorSourceEffect effect) {
        super(effect);
        this.setColor = effect.setColor;
    }

    @Override
    public BecomesColorSourceEffect copy() {
        return new BecomesColorSourceEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return;
        }
        if (setColor == null) {
            ChoiceColor choice = new ChoiceColor();
            if (!controller.choose(Outcome.PutManaInPool, choice, game)) {
                discard();
                return;
            }
            setColor = choice.getColor();
            if (!game.isSimulation()) {
                game.informPlayers(controller.getLogName() + " has chosen the color: " + setColor.toString());
            }
        }
        super.init(source, game);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        if (setColor != null) {
            MageObject sourceObject = game.getObject(source);
            if (sourceObject != null) {
                sourceObject.getColor(game).setColor(setColor);
            } else {
                this.discard();
            }
            return true;
        }
        return false;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        return "{this} becomes " + (setColor == null ? "the color of your choice" : setColor.getDescription())
                + (duration.toString().isEmpty() ? "" : " " + duration.toString());
    }
}
