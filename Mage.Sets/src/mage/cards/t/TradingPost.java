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
package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardTargetCost;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.common.FilterArtifactCard;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.permanent.token.GoatToken;
import mage.target.common.TargetCardInGraveyard;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author jeffwadsworth
 */
public class TradingPost extends CardImpl {

    public TradingPost(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        // {1}, {T}, Discard a card: You gain 4 life.
        Ability ability1 = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainLifeEffect(4), new GenericManaCost(1));
        ability1.addCost(new TapSourceCost());
        ability1.addCost(new DiscardTargetCost(new TargetCardInHand()));
        this.addAbility(ability1);

        // {1}, {T}, Pay 1 life: Create a 0/1 white Goat creature token.
        Ability ability2 = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new GoatToken()), new GenericManaCost(1));
        ability2.addCost(new TapSourceCost());
        ability2.addCost(new PayLifeCost(1));
        this.addAbility(ability2);

        // {1}, {T}, Sacrifice a creature: Return target artifact card from your graveyard to your hand.
        Ability ability3 = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ReturnFromGraveyardToHandTargetEffect(), new GenericManaCost(1));
        ability3.addTarget(new TargetCardInGraveyard(new FilterArtifactCard("an artifact card in your graveyard")));
        ability3.addCost(new TapSourceCost());
        ability3.addCost(new SacrificeTargetCost(new TargetControlledPermanent(new FilterControlledCreaturePermanent("a creature"))));
        this.addAbility(ability3);

        // {1}, {T}, Sacrifice an artifact: Draw a card.
        Ability ability4 = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1), new GenericManaCost(1));
        ability4.addCost(new TapSourceCost());
        ability4.addCost(new SacrificeTargetCost(new TargetControlledPermanent(new FilterControlledArtifactPermanent("an artifact"))));
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
