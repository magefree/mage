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
import mage.abilities.effects.common.continious.BoostTargetEffect;
import mage.abilities.effects.common.continious.GainAbilityTargetEffect;
import mage.abilities.keyword.BloodrushAbility;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.cards.CardImpl;

/**
 *
 * @author Plopman
 */
public class WreckingOgre extends CardImpl<WreckingOgre> {

    public WreckingOgre(UUID ownerId) {
        super(ownerId, 112, "Wrecking Ogre", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{4}{R}");
        this.expansionSetCode = "GTC";
        this.subtype.add("Ogre");
        this.subtype.add("Warrior");

        this.color.setRed(true);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Double strike
        this.addAbility(DoubleStrikeAbility.getInstance());
        // Bloodrush - {3}{R}{R}, Discard Wrecking Ogre: Target attacking creature gets +3/+3 and gains double strike until end of turn.
        Ability ability = new BloodrushAbility("{3}{R}{R}", new BoostTargetEffect(3, 3, Constants.Duration.EndOfTurn));
        ability.addEffect(new GainAbilityTargetEffect(DoubleStrikeAbility.getInstance(), Constants.Duration.EndOfTurn));
    }

    public WreckingOgre(final WreckingOgre card) {
        super(card);
    }

    @Override
    public WreckingOgre copy() {
        return new WreckingOgre(this);
    }
}
