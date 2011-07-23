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
package mage.sets.newphyrexia;

import java.util.UUID;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.continious.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Loki
 */
public class TormentorExarch extends CardImpl<TormentorExarch> {

    public TormentorExarch(UUID ownerId) {
        super(ownerId, 97, "Tormentor Exarch", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{3}{R}");
        this.expansionSetCode = "NPH";
        this.subtype.add("Cleric");
        this.color.setRed(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        Ability ability = new EntersBattlefieldTriggeredAbility(new BoostTargetEffect(2, 0, Constants.Duration.EndOfTurn), false);
        ability.addTarget(new TargetCreaturePermanent());
        Mode mode = new Mode();
        mode.getEffects().add(new BoostTargetEffect(0, -2, Constants.Duration.EndOfTurn));
        mode.getTargets().add(new TargetCreaturePermanent());
        ability.addMode(mode);
        ability.addMode(mode);
        this.addAbility(ability);
    }

    public TormentorExarch(final TormentorExarch card) {
        super(card);
    }

    @Override
    public TormentorExarch copy() {
        return new TormentorExarch(this);
    }
}
