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
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
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

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.choices.ChoiceColor;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;


/**
 * @author LevelX
 */
public class BecomesColorAllEffect extends ContinuousEffectImpl {

    private ObjectColor setColor;
    protected boolean loseOther;
    protected FilterPermanent filter;

    /**
     * Set the color of a spell or permanent
     *
     * @param duration
     */
    public BecomesColorAllEffect(Duration duration) {
        this(null, duration);
    }

    public BecomesColorAllEffect(ObjectColor setColor, Duration duration) {
        this(setColor, duration, new FilterPermanent("All permanents"), true, null);
    }

    public BecomesColorAllEffect(ObjectColor setColor, Duration duration, FilterPermanent filter, boolean loseOther, String text) {
        super(duration, Layer.ColorChangingEffects_5, SubLayer.NA, Outcome.Neutral);
        this.setColor = setColor;
        this.filter = filter;
        this.loseOther = loseOther;
        staticText = text;
    }

    public BecomesColorAllEffect(final BecomesColorAllEffect effect) {
        super(effect);
        this.setColor = effect.setColor;
        this.filter = effect.filter;
        this.loseOther = effect.loseOther;
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

        super.init(source, game); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        if (setColor != null) {
            for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source, game)) {
                if (permanent != null) {
                    switch (layer) {
                        case ColorChangingEffects_5:
                            if (loseOther) {
                                permanent.getColor(game).setColor(new ObjectColor());
                            }
                            permanent.getColor(game).addColor(setColor);
                            break;
                    }
                } else if (duration == Duration.Custom) {
                    discard();
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public BecomesColorAllEffect copy() {
        return new BecomesColorAllEffect(this);
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(filter.getMessage());
        sb.append(" become ");
        if (setColor == null) {
            sb.append("the color of your choice");
        } else {
            sb.append(setColor.getDescription());
        }
        if (!duration.toString().equals("")) {
            sb.append(' ').append(duration.toString());
        }
        return sb.toString();
    }
}
