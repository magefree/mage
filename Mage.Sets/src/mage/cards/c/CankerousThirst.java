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
package mage.cards.c;

import java.util.UUID;
import mage.abilities.condition.LockedInCondition;
import mage.abilities.condition.common.ManaWasSpentCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ColoredManaSymbol;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.SecondTargetPointer;
import mage.watchers.common.ManaSpentToCastWatcher;

/**
 *
 * @author jeffwadsworth
 *
 */
public class CankerousThirst extends CardImpl {

    public CankerousThirst(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{B/G}");

        // If {B} was spent to cast Cankerous Thirst, you may have target creature get -3/-3 until end of turn. If {G} was spent to cast Cankerous Thirst, you may have target creature get +3/+3 until end of turn.
        this.getSpellAbility().addEffect(new ConditionalContinuousEffect(
                new BoostTargetEffect(-3, -3, Duration.EndOfTurn),
                new LockedInCondition(new ManaWasSpentCondition(ColoredManaSymbol.B)),
                "If {B} was spent to cast {this}, you may have target creature get -3/-3 until end of turn"));

        ContinuousEffect effect = new BoostTargetEffect(3, 3, Duration.EndOfTurn);
        effect.setTargetPointer(new SecondTargetPointer());
        this.getSpellAbility().addEffect(new ConditionalContinuousEffect(
                effect,
                new LockedInCondition(new ManaWasSpentCondition(ColoredManaSymbol.G)),
                "If {G} was spent to cast {this}, you may have target creature get +3/+3 until end of turn"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new InfoEffect("<i>(Do both if {B}{G} was spent.)</i>"));
        this.getSpellAbility().addWatcher(new ManaSpentToCastWatcher());
    }

    public CankerousThirst(final CankerousThirst card) {
        super(card);
    }

    @Override
    public CankerousThirst copy() {
        return new CankerousThirst(this);
    }

}
