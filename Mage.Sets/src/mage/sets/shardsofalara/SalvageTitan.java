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
package mage.sets.shardsofalara;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.AlternativeCostImpl;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.ExileFromGraveCost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToHandEffect;
import mage.cards.CardImpl;
import mage.filter.common.FilterArtifactCard;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author Plopman
 */
public class SalvageTitan extends CardImpl<SalvageTitan> {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("artifacts");
    static{
        filter.add(new CardTypePredicate(CardType.ARTIFACT));
    }
    
    public SalvageTitan(UUID ownerId) {
        super(ownerId, 84, "Salvage Titan", Rarity.RARE, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}{B}{B}");
        this.expansionSetCode = "ALA";
        this.subtype.add("Golem");

        this.color.setBlack(true);
        this.power = new MageInt(6);
        this.toughness = new MageInt(4);

        // You may sacrifice three artifacts rather than pay Salvage Titan's mana cost.
        Cost cost = new SacrificeTargetCost(new TargetControlledPermanent(3, 3, new FilterControlledArtifactPermanent(), true));
        this.getSpellAbility().addAlternativeCost(new AlternativeCostImpl("You may sacrifice three artifacts rather than pay {this}'s mana cost", cost));
        // Exile three artifact cards from your graveyard: Return Salvage Titan from your graveyard to your hand.
        this.addAbility(new SimpleActivatedAbility(Constants.Zone.GRAVEYARD, new ReturnSourceFromGraveyardToHandEffect(), new ExileFromGraveCost(new TargetCardInYourGraveyard(3, new FilterArtifactCard()))));
    }

    public SalvageTitan(final SalvageTitan card) {
        super(card);
    }

    @Override
    public SalvageTitan copy() {
        return new SalvageTitan(this);
    }
}
