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
package mage.sets.apocalypse;

import java.util.UUID;
import mage.abilities.condition.common.KickedCostCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.DamageTargetControllerEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LoneFox
 */
public class Illuminate extends CardImpl {

    public Illuminate(UUID ownerId) {
        super(ownerId, 63, "Illuminate", Rarity.UNCOMMON, new CardType[]{CardType.SORCERY}, "{X}{R}");
        this.expansionSetCode = "APC";

        // Kicker {2}{R} and/or {3}{U}
        KickerAbility kickerAbility = new KickerAbility("{2}{R}");
        kickerAbility.addKickerCost("{3}{U}");
        this.addAbility(kickerAbility);
        // Illuminate deals X damage to target creature. If Illuminate was kicked with its {2}{R} kicker, it deals X damage to that creature's controller. If Illuminate was kicked with its {3}{U} kicker, you draw X cards.
        this.getSpellAbility().addEffect(new DamageTargetEffect(new ManacostVariableValue()));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
            new DamageTargetControllerEffect(new ManacostVariableValue()),
            new KickedCostCondition("{2}{R}"),
            "If {this} was kicked with its {2}{R} kicker, it deals X damage to that creature's controller."));
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
            new DrawCardSourceControllerEffect(new ManacostVariableValue()),
            new KickedCostCondition("{3}{U}"),
            "If {this} was kicked with its {3}{U} kicker, you draw X cards."));

    }

    public Illuminate(final Illuminate card) {
        super(card);
    }

    @Override
    public Illuminate copy() {
        return new Illuminate(this);
    }
}
