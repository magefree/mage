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
import mage.abilities.costs.common.SacrificeSourceCost;
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
        ability.addCost(new SacrificeSourceCost());
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

class EmrakulsEvangelEffect extends OneShotEffect {
        
    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("non-Eldrazi creatures you control");
    
    static {
        filter.add(new AnotherPredicate());
        filter.add(Predicates.not(new SubtypePredicate("Eldrazi")));
    }
    
    EmrakulsEvangelEffect() {
        super(Outcome.Sacrifice);
        this.staticText = "Sacrifice Emrakul's Evangel and any number of other non-Eldrazi creatures: Put a 3/2 colorless Eldrazi Horror creature token onto the battlefield for each creature sacrificed this way.";
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
            Target target = new TargetControlledCreaturePermanent(0, Integer.MAX_VALUE, filter, true);
            player.chooseTarget(Outcome.Sacrifice, target, source, game);
            int numSacrificed = 0;
            for (UUID permanentId : target.getTargets()) {
                Permanent permanent = game.getPermanent(permanentId);
                if (permanent != null) {
                    if (permanent.sacrifice(source.getSourceId(), game)) {
                        numSacrificed++;
                    }
                }
            }
            if (numSacrificed > 0) {
                EldraziHorrorToken token = new EldraziHorrorToken();
                token.putOntoBattlefield(numSacrificed, game, source.getSourceId(), source.getControllerId());
            }
            return true;
        }
        return false;
    }
}
