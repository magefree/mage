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
package mage.sets.zendikar;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalContinousEffect;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.common.continious.BoostControlledEffect;
import mage.abilities.effects.common.continious.GainAbilityControlledEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;

import java.util.UUID;

/**
 * @author nantuko, Loki
 */
public class BoldDefense extends CardImpl<BoldDefense> {

    private final String staticText = "If Bold Defense was kicked, instead creatures you control get +2/+2 and gain first strike until end of turn";

    public BoldDefense(UUID ownerId) {
        super(ownerId, 3, "Bold Defense", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{2}{W}");
        this.expansionSetCode = "ZEN";
        this.color.setWhite(true);

        DynamicValue dn = new BoldDefensePTCount();
        this.getSpellAbility().addEffect(new BoostControlledEffect(dn, dn, Constants.Duration.EndOfTurn));

        this.getSpellAbility().addOptionalCost(new ManaCostsImpl("{3}{W}"));

        ContinuousEffect effect = new GainAbilityControlledEffect(FirstStrikeAbility.getInstance(), Constants.Duration.EndOfTurn, FilterCreaturePermanent.getDefault(), false);
        this.getSpellAbility().addEffect(new ConditionalContinousEffect(effect, KickedCondition.getInstance(), staticText));
    }

    public BoldDefense(final BoldDefense card) {
        super(card);
    }

    @Override
    public BoldDefense copy() {
        return new BoldDefense(this);
    }
}

class BoldDefensePTCount implements DynamicValue {

    public BoldDefensePTCount() {
    }

    @Override
    public int calculate(Game game, Ability sourceAbility) {
        if (KickedCondition.getInstance().apply(game, sourceAbility)) {
            return 2;
        } else {
            return 1;
        }
    }

    @Override
    public DynamicValue clone() {
        return new BoldDefensePTCount();
    }

    @Override
    public String getMessage() {
        return "";
    }
}