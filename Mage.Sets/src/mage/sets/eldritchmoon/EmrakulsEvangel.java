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
package mage.sets.eldritchmoon;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.EldraziHorrorToken;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public class EmrakulsEvangel extends CardImpl {

    public EmrakulsEvangel(UUID ownerId) {
        super(ownerId, 156, "Emrakul's Evangel", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{2}{G}");
        this.expansionSetCode = "EMN";
        this.subtype.add("Human");
        this.subtype.add("Horror");
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // {T}, Sacrifice Emrakul's Evangel and any number of other non-Eldrazi creatures:
        // Put a 3/2 colorless Eldrazi Horror creature token onto the battlefield for each creature sacrificed this way.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new EmrakulsEvangelEffect(), new TapSourceCost());
        ability.addCost(new EmrakulsEvangelCost());
        this.addAbility(ability);
    }

    public EmrakulsEvangel(final EmrakulsEvangel card) {
        super(card);
    }

    @Override
    public EmrakulsEvangel copy() {
        return new EmrakulsEvangel(this);
    }
}

class EmrakulsEvangelCost extends CostImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("non-Eldrazi creatures you control");

    static {
        filter.add(new AnotherPredicate());
        filter.add(Predicates.not(new SubtypePredicate("Eldrazi")));
    }

    private int numSacrificed = 1; // always sacrifices self at least

    public EmrakulsEvangelCost() {
        this.text = "Sacrifice {this} and any number of other non-Eldrazi creatures";
    }

    public EmrakulsEvangelCost(EmrakulsEvangelCost cost) {
        super(cost);
        this.numSacrificed = cost.getNumSacrificed();
    }

    @Override
    public boolean pay(Ability ability, Game game, UUID sourceId, UUID controllerId, boolean noMana, Cost costToPay) {
        Permanent selfPermanent = game.getPermanent(sourceId);
        Player player = game.getPlayer(controllerId);
        if (selfPermanent != null && player != null) {
            paid = selfPermanent.sacrifice(sourceId, game); // sacrifice self
            Target target = new TargetControlledCreaturePermanent(0, Integer.MAX_VALUE, filter, true);
            player.chooseTarget(Outcome.Sacrifice, target, ability, game);
            for (UUID permanentId : target.getTargets()) {
                Permanent otherPermanent = game.getPermanent(permanentId);
                if (otherPermanent != null) {
                    if (otherPermanent.sacrifice(sourceId, game)) {
                        numSacrificed++;
                    }
                }
            }
        }
        return paid;
    }

    public int getNumSacrificed() {
        return numSacrificed;
    }

    @Override
    public boolean canPay(Ability ability, UUID sourceId, UUID controllerId, Game game) {
        Permanent permanent = game.getPermanent(sourceId);

        return permanent != null && game.getPlayer(controllerId).canPaySacrificeCost(permanent, sourceId, controllerId, game);
    }

    @Override
    public EmrakulsEvangelCost copy() {
        return new EmrakulsEvangelCost(this);
    }
}

class EmrakulsEvangelEffect extends OneShotEffect {

    EmrakulsEvangelEffect() {
        super(Outcome.Sacrifice);
        this.staticText = "Put a 3/2 colorless Eldrazi Horror creature token onto the battlefield for each creature sacrificed this way.";
    }

    EmrakulsEvangelEffect(final EmrakulsEvangelEffect effect) {
        super(effect);
    }

    @Override
    public EmrakulsEvangelEffect copy() {
        return new EmrakulsEvangelEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            int tokensToCreate = 0;
            for (Cost cost : source.getCosts()) {
                if (cost instanceof EmrakulsEvangelCost) {
                    tokensToCreate = ((EmrakulsEvangelCost) cost).getNumSacrificed();
                }
            }
            if (tokensToCreate > 0) {
                EldraziHorrorToken token = new EldraziHorrorToken();
                token.putOntoBattlefield(tokensToCreate, game, source.getSourceId(), source.getControllerId());
            }
            return true;
        }
        return false;
    }
}
