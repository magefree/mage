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
package mage.sets.battleforzendikar;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.keyword.DevoidAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class TurnAgainst extends CardImpl {

    public TurnAgainst(UUID ownerId) {
        super(ownerId, 135, "Turn Against", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{4}{R}");
        this.expansionSetCode = "BFZ";

        // Devoid
        Ability ability = new DevoidAbility(this.color);
        ability.setRuleAtTheTop(true);
        this.addAbility(ability);
        // Gain control of target creature until end of turn. Untap that creature. It gains haste until end of turn.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new GainControlTargetEffect(Duration.EndOfTurn));
        this.getSpellAbility().addEffect(new UntapTargetEffect());
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.EndOfTurn));
    }

    public TurnAgainst(final TurnAgainst card) {
        super(card);
    }

    @Override
    public TurnAgainst copy() {
        return new TurnAgainst(this);
    }
}
