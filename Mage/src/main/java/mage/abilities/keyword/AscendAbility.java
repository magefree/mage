/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
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
 */
package mage.abilities.keyword;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import static mage.abilities.keyword.AscendAbility.ASCEND_RULE;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.constants.Zone;
import mage.designations.CitysBlessing;
import mage.designations.DesignationType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class AscendAbility extends SimpleStaticAbility {

    public static String ASCEND_RULE = "Ascend <i>(If you control ten or more permanents, you get the city's blessing for the rest of the game.)</i>";

    public AscendAbility() {
        super(Zone.BATTLEFIELD, new AscendContinuousEffect());
    }

    public AscendAbility(final AscendAbility ability) {
        super(ability);
    }

    @Override
    public AscendAbility copy() {
        return new AscendAbility(this);
    }

    public static boolean checkAscend(Game game, Ability source, boolean verbose) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            if (!controller.hasDesignation(DesignationType.CITYS_BLESSING)) {
                if (game.getBattlefield().countAll(StaticFilters.FILTER_PERMANENT, controller.getId(), game) > 9) {
                    controller.addDesignation(new CitysBlessing());
                    game.informPlayers(controller.getLogName() + " gets the city's blessing for the rest of the game.");
                } else {
                    if (verbose) {
                        game.informPlayers(controller.getLogName() + " does not get the city's blessing.");
                    }
                }
            } else {
                if (verbose) {
                    game.informPlayers(controller.getLogName() + " already has the city's blessing.");
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return ASCEND_RULE;
    }

}

class AscendContinuousEffect extends ContinuousEffectImpl {

    public AscendContinuousEffect() {
        super(Duration.WhileOnBattlefield, Layer.PlayerEffects, SubLayer.NA, Outcome.Benefit);
        staticText = ASCEND_RULE;
    }

    public AscendContinuousEffect(final AscendContinuousEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return AscendAbility.checkAscend(game, source, false);
    }

    @Override
    public AscendContinuousEffect copy() {
        return new AscendContinuousEffect(this);
    }
}
