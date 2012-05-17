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
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.discard.DiscardCardYouChooseTargetOpponentEffect;
import mage.cards.CardImpl;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author noxx
 */
public class CorpseTraders extends CardImpl<CorpseTraders> {

    public CorpseTraders(UUID ownerId) {
        super(ownerId, 90, "Corpse Traders", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{3}{B}");
        this.expansionSetCode = "AVR";
        this.subtype.add("Human");
        this.subtype.add("Rogue");

        this.color.setBlack(true);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // {2}{B}, Sacrifice a creature: Target opponent reveals his or her hand. You choose a card from it. That player discards that card. Activate this ability only any time you could cast a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(Constants.Zone.BATTLEFIELD, new DiscardCardYouChooseTargetOpponentEffect(), new ManaCostsImpl("{2}{B}"));
        ability.addTarget(new TargetOpponent(true));
        ability.addCost(new SacrificeTargetCost(new TargetControlledCreaturePermanent()));
        this.addAbility(ability);
    }

    public CorpseTraders(final CorpseTraders card) {
        super(card);
    }

    @Override
    public CorpseTraders copy() {
        return new CorpseTraders(this);
    }
}
