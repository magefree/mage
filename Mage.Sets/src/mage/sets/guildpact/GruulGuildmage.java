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
package mage.sets.guildpact;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continious.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.target.TargetPlayer;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 * @author Loki
 */
public class GruulGuildmage extends CardImpl<GruulGuildmage> {

    private final static FilterControlledPermanent filter = new FilterControlledLandPermanent("a land");

    public GruulGuildmage(UUID ownerId) {
        super(ownerId, 144, "Gruul Guildmage", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{R/G}{R/G}");
        this.expansionSetCode = "GPT";
        this.subtype.add("Human");
        this.subtype.add("Shaman");
        this.color.setRed(true);
        this.color.setGreen(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {3}{R}, Sacrifice a land: Gruul Guildmage deals 2 damage to target player.
        Ability firstAbility = new SimpleActivatedAbility(Constants.Zone.BATTLEFIELD, new DamageTargetEffect(2), new ManaCostsImpl("{3}{R}"));
        firstAbility.addCost(new SacrificeTargetCost(new TargetControlledPermanent(filter)));
        firstAbility.addTarget(new TargetPlayer(1));
        this.addAbility(firstAbility);
        // {3}{G}: Target creature gets +2/+2 until end of turn.
        Ability secondAbility = new SimpleActivatedAbility(Constants.Zone.BATTLEFIELD, new BoostTargetEffect(2, 2, Constants.Duration.EndOfTurn), new ManaCostsImpl("{3}{G}"));
        secondAbility.addTarget(new TargetCreaturePermanent());
        this.addAbility(secondAbility);
    }

    public GruulGuildmage(final GruulGuildmage card) {
        super(card);
    }

    @Override
    public GruulGuildmage copy() {
        return new GruulGuildmage(this);
    }
}
