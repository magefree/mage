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
package mage.sets.magic2013;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardTargetCost;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.CardImpl;
import mage.filter.common.FilterArtifactCard;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.permanent.token.Token;
import mage.target.common.TargetCardInGraveyard;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author jeffwadsworth
 */
public class TradingPost extends CardImpl<TradingPost> {
    
    final static FilterControlledPermanent filter = new FilterControlledPermanent("creature");
    final static FilterControlledPermanent filter2 = new FilterControlledPermanent("artifact");
    
    static {
        filter.add(new CardTypePredicate(CardType.CREATURE));
        filter2.add(new CardTypePredicate(CardType.ARTIFACT));
    }

    public TradingPost(UUID ownerId) {
        super(ownerId, 220, "Trading Post", Rarity.RARE, new CardType[]{CardType.ARTIFACT}, "{4}");
        this.expansionSetCode = "M13";

        // {1}, {tap}, Discard a card: You gain 4 life.
        Ability ability1 = new SimpleActivatedAbility(Constants.Zone.BATTLEFIELD, new GainLifeEffect(4), new GenericManaCost(1));
        ability1.addCost(new TapSourceCost());
        ability1.addCost(new DiscardTargetCost(new TargetCardInHand()));
        this.addAbility(ability1);
        
        // {1}, {tap}, Pay 1 life: Put a 0/1 white Goat creature token onto the battlefield.
        Ability ability2 = new SimpleActivatedAbility(Constants.Zone.BATTLEFIELD, new CreateTokenEffect(new GoatToken()), new GenericManaCost(1));
        ability2.addCost(new TapSourceCost());
        ability2.addCost(new PayLifeCost(1));
        this.addAbility(ability2);
        
        // {1}, {tap}, Sacrifice a creature: Return target artifact card from your graveyard to your hand.
        Ability ability3 = new SimpleActivatedAbility(Constants.Zone.BATTLEFIELD, new ReturnFromGraveyardToHandTargetEffect(), new GenericManaCost(1));
        ability3.addTarget(new TargetCardInGraveyard(new FilterArtifactCard("artifact card in your graveyard")));
        ability3.addCost(new TapSourceCost());
        ability3.addCost(new SacrificeTargetCost(new TargetControlledPermanent(filter)));
        this.addAbility(ability3);
        
        // {1}, {tap}, Sacrifice an artifact: Draw a card.
        Ability ability4 = new SimpleActivatedAbility(Constants.Zone.BATTLEFIELD, new DrawCardControllerEffect(1), new GenericManaCost(1));
        ability4.addCost(new TapSourceCost());
        ability4.addCost(new SacrificeTargetCost(new TargetControlledPermanent(filter2)));
        this.addAbility(ability4);
        
    }

    public TradingPost(final TradingPost card) {
        super(card);
    }

    @Override
    public TradingPost copy() {
        return new TradingPost(this);
    }
}

class GoatToken extends Token {
    public GoatToken() {
        super("Goat", "a 0/1 white Goat creature token");
        cardType.add(Constants.CardType.CREATURE);
        color = ObjectColor.WHITE;
        subtype.add("Goat");
        power = new MageInt(0);
        toughness = new MageInt(1);
    }
}
