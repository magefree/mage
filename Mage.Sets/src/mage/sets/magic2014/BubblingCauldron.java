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
package mage.sets.magic2014;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author Plopman
 */
public class BubblingCauldron extends CardImpl<BubblingCauldron> {

    private static final FilterControlledCreaturePermanent  filter = new FilterControlledCreaturePermanent("a creature named Festering Newt");
    static {
        filter.add(new NamePredicate("Festering Newt"));
    }
    public BubblingCauldron(UUID ownerId) {
        super(ownerId, 205, "Bubbling Cauldron", Rarity.UNCOMMON, new CardType[]{CardType.ARTIFACT}, "{2}");
        this.expansionSetCode = "M14";

        // {1}, {T}, Sacrifice a creature: You gain 4 life.
        Ability ability1 = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainLifeEffect(4), new ManaCostsImpl("{1}"));
        ability1.addCost(new TapSourceCost());
        ability1.addCost(new SacrificeTargetCost(new TargetControlledCreaturePermanent(1, 1, new FilterControlledCreaturePermanent("a creature"), true)));
        this.addAbility(ability1);
        // {1}, {T}, Sacrifice a creature named Festering Newt: Each opponent loses 4 life. You gain life equal to the life lost this way. 
        Ability ability2 = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BubblingCauldronEffect(), new ManaCostsImpl("{1}"));
        ability2.addCost(new TapSourceCost());
        ability2.addCost(new SacrificeTargetCost(new TargetControlledCreaturePermanent(1, 1, filter, true)));
        this.addAbility(ability2);
    }

    public BubblingCauldron(final BubblingCauldron card) {
        super(card);
    }

    @Override
    public BubblingCauldron copy() {
        return new BubblingCauldron(this);
    }
}

class BubblingCauldronEffect extends OneShotEffect<BubblingCauldronEffect> {

    public BubblingCauldronEffect() {
        super(Outcome.Damage);
        staticText = "Each opponent loses 4 life. You gain life equal to the life lost this way";
    }

    public BubblingCauldronEffect(final BubblingCauldronEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int damage = 0;
        for (UUID opponentId: game.getOpponents(source.getControllerId())) {
            damage += game.getPlayer(opponentId).damage(4, source.getSourceId(), game, false, true);
        }
        game.getPlayer(source.getControllerId()).gainLife(damage, game);
        return true;
    }

    @Override
    public BubblingCauldronEffect copy() {
        return new BubblingCauldronEffect(this);
    }

}