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
package mage.sets.betrayersofkamigawa;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.common.continious.GainProtectionFromColorTargetEffect;
import mage.abilities.keyword.SoulshiftAbility;
import mage.cards.CardImpl;
import mage.choices.ChoiceColor;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class MoonlitStrider extends CardImpl<MoonlitStrider> {

    public MoonlitStrider(UUID ownerId) {
        super(ownerId, 16, "Moonlit Strider", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{3}{W}");
        this.expansionSetCode = "BOK";
        this.subtype.add("Spirit");
        this.color.setWhite(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Sacrifice Moonlit Strider: Target creature you control gains protection from the color of your choice until end of turn.
        Ability ability = new SimpleActivatedAbility(Constants.Zone.BATTLEFIELD, new GainProtectionFromColorTargetEffect(Constants.Duration.EndOfTurn), new SacrificeSourceCost());
        ability.addChoice(new ChoiceColor());
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);        
        
        // Soulshift 3 (When this creature dies, you may return target Spirit card with converted mana cost 3 or less from your graveyard to your hand.)
        this.addAbility(new SoulshiftAbility(3));
    }

    public MoonlitStrider(final MoonlitStrider card) {
        super(card);
    }

    @Override
    public MoonlitStrider copy() {
        return new MoonlitStrider(this);
    }
}

