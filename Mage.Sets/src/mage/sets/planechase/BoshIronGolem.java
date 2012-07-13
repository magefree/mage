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
package mage.sets.planechase;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetCreatureOrPlayer;

/**
 *
 * @author jeffwadsworth
 */
public class BoshIronGolem extends CardImpl<BoshIronGolem> {
    
    private static final FilterControlledPermanent filter = new FilterControlledPermanent("artifact");
    
    static {
        filter.add(new CardTypePredicate(CardType.ARTIFACT));
    }
    
    public BoshIronGolem(UUID ownerId) {
        super(ownerId, 108, "Bosh, Iron Golem", Rarity.RARE, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{8}");
        this.expansionSetCode = "HOP";
        this.supertype.add("Legendary");
        this.subtype.add("Golem");

        this.power = new MageInt(6);
        this.toughness = new MageInt(7);

        // Trample
        this.addAbility(TrampleAbility.getInstance());
        
        // {3}{R}, Sacrifice an artifact: Bosh, Iron Golem deals damage equal to the sacrificed artifact's converted mana cost to target creature or player.
        Ability ability = new SimpleActivatedAbility(Constants.Zone.BATTLEFIELD, new BoshIronGolemEffect(), new ManaCostsImpl("{3}{R}"));
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(filter)));
        ability.addTarget(new TargetCreatureOrPlayer());
        this.addAbility(ability);
    }

    public BoshIronGolem(final BoshIronGolem card) {
        super(card);
    }

    @Override
    public BoshIronGolem copy() {
        return new BoshIronGolem(this);
    }
}

class BoshIronGolemEffect extends OneShotEffect<BoshIronGolemEffect> {

    public BoshIronGolemEffect() {
        super(Constants.Outcome.Damage);
        staticText = "{this} deals damage equal to the sacrificed artifact's converted mana cost to target creature or player";
    }

    public BoshIronGolemEffect(final BoshIronGolemEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int amount = 0;
        for (Cost cost: source.getCosts()) {
            if (cost instanceof SacrificeTargetCost && ((SacrificeTargetCost)cost).getPermanents().size() > 0) {
                amount = ((SacrificeTargetCost)cost).getPermanents().get(0).getManaCost().convertedManaCost();
                break;
            }
        }
        if (amount > 0) {
            Permanent permanent = game.getPermanent(source.getFirstTarget());
            if (permanent != null) {
                permanent.damage(amount, source.getSourceId(), game, true, false);
                return true;
            }
            Player player = game.getPlayer(source.getFirstTarget());
            if (player != null) {
                player.damage(amount, source.getSourceId(), game, false, true);
                return true;
            }
        }
        return false;
    }

    @Override
    public BoshIronGolemEffect copy() {
        return new BoshIronGolemEffect(this);
    }

}
