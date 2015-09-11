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
package mage.sets.mirage;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.game.Game;

/**
 *
 * @author LoneFox
 */
public class VitalizingCascade extends CardImpl {

    public VitalizingCascade(UUID ownerId) {
        super(ownerId, 346, "Vitalizing Cascade", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{X}{G}{W}");
        this.expansionSetCode = "MIR";

        // You gain X plus 3 life.
        this.getSpellAbility().addEffect(new GainLifeEffect(new VitalizingCascadeValue()));
    }

    public VitalizingCascade(final VitalizingCascade card) {
        super(card);
    }

    @Override
    public VitalizingCascade copy() {
        return new VitalizingCascade(this);
    }
}

class VitalizingCascadeValue extends ManacostVariableValue {

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return super.calculate(game, sourceAbility, effect) + 3;
    }

    @Override
    public VitalizingCascadeValue copy() {
        return new VitalizingCascadeValue();
    }

    @Override
    public String toString() {
        return "X plus 3";
    }
}

