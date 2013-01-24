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
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continious.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author LevelX2
 */
public class GatewayShade extends CardImpl<GatewayShade> {

    private final static FilterControlledLandPermanent filter = new FilterControlledLandPermanent("untapped Gate you control");
    static {
        filter.add(new SubtypePredicate("Gate"));
        filter.add(Predicates.not(new TappedPredicate()));
    }

    public GatewayShade(UUID ownerId) {
        super(ownerId, 65, "Gateway Shade", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{2}{B}");
        this.expansionSetCode = "GTC";
        this.subtype.add("Shade");
        this.color.setBlack(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {B}: Gateway Shade gets +1/+1 until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(1,1, Duration.EndOfTurn),new ManaCostsImpl("{B")));

        // Tap an untapped Gate you control: Gateway Shade gets +2/+2 until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(2,2, Duration.EndOfTurn),new TapTargetCost(new TargetControlledPermanent(1,1,filter, true))));

    }

    public GatewayShade(final GatewayShade card) {
        super(card);
    }

    @Override
    public GatewayShade copy() {
        return new GatewayShade(this);
    }
}
