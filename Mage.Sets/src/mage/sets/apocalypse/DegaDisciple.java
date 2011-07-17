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
package mage.sets.apocalypse;

import java.util.UUID;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ColoredManaCost;
import mage.abilities.effects.common.continious.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Loki
 */
public class DegaDisciple extends CardImpl<DegaDisciple> {

    public DegaDisciple(UUID ownerId) {
        super(ownerId, 4, "Dega Disciple", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{W}");
        this.expansionSetCode = "APC";
        this.subtype.add("Human");
        this.subtype.add("Wizard");
        this.color.setWhite(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
        Ability firstAbility = new SimpleActivatedAbility(Constants.Zone.BATTLEFIELD, new BoostTargetEffect(-2, 0, Constants.Duration.EndOfTurn), new ColoredManaCost(Constants.ColoredManaSymbol.B));
        firstAbility.addCost(new TapSourceCost());
        firstAbility.addTarget(new TargetCreaturePermanent());
        this.addAbility(firstAbility);
        Ability secondAbility = new SimpleActivatedAbility(Constants.Zone.BATTLEFIELD, new BoostTargetEffect(2, 0, Constants.Duration.EndOfTurn), new ColoredManaCost(Constants.ColoredManaSymbol.R));
        secondAbility.addCost(new TapSourceCost());
        secondAbility.addTarget(new TargetCreaturePermanent());
        this.addAbility(secondAbility);
    }

    public DegaDisciple(final DegaDisciple card) {
        super(card);
    }

    @Override
    public DegaDisciple copy() {
        return new DegaDisciple(this);
    }
}
