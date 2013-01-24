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
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.target.common.TargetAttackingCreature;

/**
 *
 * @author LevelX2
 */
public class GhorClanRampager extends CardImpl<GhorClanRampager> {

    public GhorClanRampager(UUID ownerId) {
        super(ownerId, 167, "Ghor-Clan Rampager", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{2}{R}{G}");
        this.expansionSetCode = "GTC";
        this.subtype.add("Beast");

        this.color.setRed(true);
        this.color.setGreen(true);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Trample
        this.addAbility(TrampleAbility.getInstance());
        // Bloodrush - {R}{G}, Discard Ghor-Clan Rampager: Target attacking creature gets +4/+4 and gains trample until end of turn.
        Ability ability = new BloodrushAbility("{R}{G}",new BoostTargetEffect(4,4, Constants.Duration.EndOfTurn));
        ability.addEffect(new GainAbilityTargetEffect(TrampleAbility.getInstance(), Constants.Duration.EndOfTurn));
        ability.addTarget(new TargetAttackingCreature());
        this.addAbility(ability);
    }

    public GhorClanRampager(final GhorClanRampager card) {
        super(card);
    }

    @Override
    public GhorClanRampager copy() {
        return new GhorClanRampager(this);
    }
}
