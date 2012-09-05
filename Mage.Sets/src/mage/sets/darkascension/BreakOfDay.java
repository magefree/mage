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
package mage.sets.darkascension;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Rarity;
import mage.abilities.condition.common.FatefulHourCondition;
import mage.abilities.decorator.ConditionalContinousEffect;
import mage.abilities.effects.common.IndestructibleAllEffect;
import mage.abilities.effects.common.continious.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.filter.common.FilterCreaturePermanent;

/**
 *
 * @author Loki

 */
public class BreakOfDay extends CardImpl<BreakOfDay> {

    public BreakOfDay(UUID ownerId) {
        super(ownerId, 3, "Break of Day", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{1}{W}");
        this.expansionSetCode = "DKA";

        this.color.setWhite(true);

        // Creatures you control get +1/+1 until end of turn.
        this.getSpellAbility().addEffect(new BoostControlledEffect(1, 1, Duration.EndOfTurn));
        // Fateful hour - If you have 5 or less life, those creatures also are indestructible this turn.
        this.getSpellAbility().addEffect(new ConditionalContinousEffect(
                new IndestructibleAllEffect(new FilterCreaturePermanent("creatures you control"), Duration.EndOfTurn),
                FatefulHourCondition.getInstance(),
                "If you have 5 or less life, those creatures also are indestructible this turn."));
    }

    public BreakOfDay(final BreakOfDay card) {
        super(card);
    }

    @Override
    public BreakOfDay copy() {
        return new BreakOfDay(this);
    }
}
