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
package mage.sets.dissension;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Quercitron
 */
public class MightOfTheNephilim extends CardImpl {

    public MightOfTheNephilim(UUID ownerId) {
        super(ownerId, 88, "Might of the Nephilim", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{1}{G}");
        this.expansionSetCode = "DIS";

        // Target creature gets +2/+2 until end of turn for each of its colors.
        DynamicValue boostValue = MightOfTheNephilimValue.getInstance();
        Effect effect = new BoostTargetEffect(boostValue, boostValue, Duration.EndOfTurn, true);
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    public MightOfTheNephilim(final MightOfTheNephilim card) {
        super(card);
    }

    @Override
    public MightOfTheNephilim copy() {
        return new MightOfTheNephilim(this);
    }
}

class MightOfTheNephilimValue implements DynamicValue {

    private static final MightOfTheNephilimValue fINSTANCE =  new MightOfTheNephilimValue();

    public static MightOfTheNephilimValue getInstance() {
        return fINSTANCE;
    }

    private MightOfTheNephilimValue() {}

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Permanent target = game.getPermanentOrLKIBattlefield(sourceAbility.getFirstTarget());
        if (target != null) {
            return 2 * target.getColor(game).getColorCount();
        }
        return 0;
    }

    @Override
    public MightOfTheNephilimValue copy() {
        return fINSTANCE;
    }

    @Override
    public String toString() {
        return "2";
    }

    @Override
    public String getMessage() {
        return "of its colors";
    }
}
