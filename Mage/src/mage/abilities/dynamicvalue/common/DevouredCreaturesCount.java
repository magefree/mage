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

package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DevourEffect;
import mage.abilities.keyword.DevourAbility;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author LevelX2
 */

public class DevouredCreaturesCount implements DynamicValue {

    int multiplier;

    public DevouredCreaturesCount() {
        this(1);
    }

    public DevouredCreaturesCount(int multiplier) {
        super();
        this.multiplier = multiplier;
    }

    public DevouredCreaturesCount(final DevouredCreaturesCount dynamicValue) {
        super();
        this.multiplier = dynamicValue.multiplier;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility) {
        Permanent sourcePermanent = game.getPermanent(sourceAbility.getSourceId());
        if (sourcePermanent != null) {
            for (Ability ability : sourcePermanent.getAbilities()) {
                if (ability instanceof DevourAbility) {
                    for (Effect effect: ability.getEffects()) {
                        if (effect instanceof DevourEffect) {
                            DevourEffect devourEffect = (DevourEffect) effect;
                            return devourEffect.getDevouredCreaturesAmount(game, sourcePermanent.getId()) * multiplier;
                        }
                    }

                }
            }
        }
        return 0;
    }

    @Override
    public DevouredCreaturesCount copy() {
        return new DevouredCreaturesCount(this);
    }

    @Override
    public String toString() {
        return Integer.toString(multiplier);
    }

    @Override
    public String getMessage() {
        return "creature it devoured";
    }
}
