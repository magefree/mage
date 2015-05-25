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
package mage.sets.onslaught;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author anonymous
 */
public class StarlitSanctum extends CardImpl {
    
    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("Cleric", "a Cleric creature");

    public StarlitSanctum(UUID ownerId) {
        super(ownerId, 325, "Starlit Sanctum", Rarity.UNCOMMON, new CardType[]{CardType.LAND}, "");
        this.expansionSetCode = "ONS";

        // {tap}: Add {1} to your mana pool.
        this.addAbility(new ColorlessManaAbility());
        // {W}, {tap}, Sacrifice a Cleric creature: You gain life equal to the sacrificed creature's toughness.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new StarlitSanctumWhiteEffect(), new ManaCostsImpl<>("{W}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(new TargetControlledCreaturePermanent(filter)));
        this.addAbility(ability);
        // {B}, {tap}, Sacrifice a Cleric creature: Target player loses life equal to the sacrificed creature's power.
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new StarlitSanctumBlackEffect(), new ManaCostsImpl<>("{B}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(new TargetControlledCreaturePermanent(filter)));
        this.addAbility(ability);
    }

    public StarlitSanctum(final StarlitSanctum card) {
        super(card);
    }

    @Override
    public StarlitSanctum copy() {
        return new StarlitSanctum(this);
    }
}

class StarlitSanctumWhiteEffect extends OneShotEffect {

    public StarlitSanctumWhiteEffect() {
        super(Outcome.GainLife);
        staticText = "You gain life equal to the sacrificed creature's toughness";
    }

    public StarlitSanctumWhiteEffect(final StarlitSanctumWhiteEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int amount = 0;
        for (Cost cost : source.getCosts()) {
            if (cost instanceof SacrificeTargetCost && ((SacrificeTargetCost) cost).getPermanents().size() > 0) {
                amount = ((SacrificeTargetCost) cost).getPermanents().get(0).getToughness().getValue();
                break;
            }
        }
        if (amount > 0) {
            Player player = game.getPlayer(source.getControllerId());
            if (player != null) {
                player.gainLife(amount, game);
                return true;
            }
        }
        return false;
    }

    @Override
    public StarlitSanctumWhiteEffect copy() {
        return new StarlitSanctumWhiteEffect(this);
    }
}

class StarlitSanctumBlackEffect extends OneShotEffect {

    public StarlitSanctumBlackEffect() {
        super(Outcome.Damage);
        staticText = "Target player loses life equal to the sacrificed creature's power";
    }

    public StarlitSanctumBlackEffect(final StarlitSanctumBlackEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int amount = 0;
        for (Cost cost : source.getCosts()) {
            if (cost instanceof SacrificeTargetCost && ((SacrificeTargetCost) cost).getPermanents().size() > 0) {
                amount = ((SacrificeTargetCost) cost).getPermanents().get(0).getPower().getValue();
                break;
            }
        }
        if (amount > 0) {
            Player player = game.getPlayer(source.getFirstTarget());
            if (player != null) {
                player.damage(amount, source.getSourceId(), game, false, true);
                return true;
            }
        }
        return false;
    }

    @Override
    public StarlitSanctumBlackEffect copy() {
        return new StarlitSanctumBlackEffect(this);
    }
}