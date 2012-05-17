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
package mage.sets.avacynrestored;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continious.GainAbilityPairedEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.SoulbondAbility;
import mage.cards.CardImpl;

import java.util.UUID;

/**
 * @author noxx
 */
public class SilverbladePaladin extends CardImpl<SilverbladePaladin> {

    private static final String ruleText = "As long as {this} is paired with another creature, both creatures have double strike";

    public SilverbladePaladin(UUID ownerId) {
        super(ownerId, 36, "Silverblade Paladin", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{1}{W}{W}");
        this.expansionSetCode = "AVR";
        this.subtype.add("Human");
        this.subtype.add("Knight");

        this.color.setWhite(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Soulbond
        this.addAbility(SoulbondAbility.getInstance());

        // As long as Silverblade Paladin is paired with another creature, both creatures have double strike.
        this.addAbility(new SimpleStaticAbility(Constants.Zone.BATTLEFIELD, new GainAbilityPairedEffect(DoubleStrikeAbility.getInstance(), ruleText)));
    }

    public SilverbladePaladin(final SilverbladePaladin card) {
        super(card);
    }

    @Override
    public SilverbladePaladin copy() {
        return new SilverbladePaladin(this);
    }
}
