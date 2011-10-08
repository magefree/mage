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
package mage.sets.ravnika;

import java.util.UUID;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DiscardTargetEffect;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.cards.CardImpl;
import mage.target.TargetPlayer;

/**
 *
 * @author Loki
 */
public class DimirGuildmage extends CardImpl<DimirGuildmage> {

    public DimirGuildmage(UUID ownerId) {
        super(ownerId, 245, "Dimir Guildmage", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{U/B}{U/B}");
        this.expansionSetCode = "RAV";
        this.subtype.add("Human");
        this.subtype.add("Wizard");

        this.color.setBlue(true);
        this.color.setBlack(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {3}{U}: Target player draws a card. Activate this ability only any time you could cast a sorcery.
        Ability firstAbility = new ActivateAsSorceryActivatedAbility(Constants.Zone.BATTLEFIELD, new DrawCardTargetEffect(1), new ManaCostsImpl("{3}{U}"));
        firstAbility.addTarget(new TargetPlayer());
        this.addAbility(firstAbility);
        // {3}{B}: Target player discards a card. Activate this ability only any time you could cast a sorcery.
        Ability secondAbility = new ActivateAsSorceryActivatedAbility(Constants.Zone.BATTLEFIELD, new DiscardTargetEffect(1), new ManaCostsImpl("{3}{B}"));
        secondAbility.addTarget(new TargetPlayer());
        this.addAbility(secondAbility);
    }

    public DimirGuildmage(final DimirGuildmage card) {
        super(card);
    }

    @Override
    public DimirGuildmage copy() {
        return new DimirGuildmage(this);
    }
}
