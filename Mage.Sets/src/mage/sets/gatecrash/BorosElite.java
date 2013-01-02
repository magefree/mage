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
package mage.sets.gatecrash;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.continious.BoostSourceEffect;
import mage.abilities.keyword.BattalionAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.target.common.TargetCreatureOrPlayer;

/**
 *
 * @author LevelX2
 */
public class BorosElite extends CardImpl<BorosElite> {

    public BorosElite(UUID ownerId) {
        super(ownerId, 7, "Boros Elite", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{W}");
        this.expansionSetCode = "GTC";
        this.subtype.add("Human");
        this.subtype.add("Soldier");

        this.color.setWhite(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Battalion - Whenever Boros Elite and at least two other creatures attack, Boros Elite gets +2/+2 until end of turn.
        this.addAbility(new BattalionAbility(new BoostSourceEffect(2,2, Constants.Duration.EndOfTurn)));
    }

    public BorosElite(final BorosElite card) {
        super(card);
    }

    @Override
    public BorosElite copy() {
        return new BorosElite(this);
    }
}
