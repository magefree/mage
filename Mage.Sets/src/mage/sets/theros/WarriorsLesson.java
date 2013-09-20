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
package mage.sets.theros;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.DealsDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.common.DrawCardControllerEffect;
import mage.abilities.effects.common.continious.GainAbilityTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class WarriorsLesson extends CardImpl<WarriorsLesson> {

    public WarriorsLesson(UUID ownerId) {
        super(ownerId, 184, "Warriors' Lesson", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{G}");
        this.expansionSetCode = "THS";

        this.color.setGreen(true);

        // Until end of turn, up to two target creatures you control each gain "Whenever this creature deals combat damage to a player, draw a card."
        Ability ability = new DealsDamageToAPlayerTriggeredAbility(new DrawCardControllerEffect(1),false);
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(ability, Duration.EndOfGame));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0,2));
    }

    public WarriorsLesson(final WarriorsLesson card) {
        super(card);
    }

    @Override
    public WarriorsLesson copy() {
        return new WarriorsLesson(this);
    }
}
