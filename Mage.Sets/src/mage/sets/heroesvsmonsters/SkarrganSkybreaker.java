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
package mage.sets.heroesvsmonsters;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerCount;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.BloodthirstAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.target.common.TargetCreatureOrPlayer;

/**
 *
 * @author LevelX2
 */
public class SkarrganSkybreaker extends CardImpl<SkarrganSkybreaker> {

    public SkarrganSkybreaker(UUID ownerId) {
        super(ownerId, 60, "Skarrgan Skybreaker", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{4}{R}{R}{G}");
        this.expansionSetCode = "DDL";
        this.subtype.add("Giant");
        this.subtype.add("Shaman");

        this.color.setRed(true);
        this.color.setGreen(true);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Bloodthirst 3
        this.addAbility(new BloodthirstAbility(3));
        // {1}, Sacrifice Skarrgan Skybreaker: Skarrgan Skybreaker deals damage equal to its power to target creature or player.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(new SourcePermanentPowerCount()), new GenericManaCost(1));
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetCreatureOrPlayer(true));
        this.addAbility(ability);
    }

    public SkarrganSkybreaker(final SkarrganSkybreaker card) {
        super(card);
    }

    @Override
    public SkarrganSkybreaker copy() {
        return new SkarrganSkybreaker(this);
    }
}
