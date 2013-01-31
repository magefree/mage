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
package mage.sets.gatecrash;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author jeffwadsworth
 */
public class CracklingPerimeter extends CardImpl<CracklingPerimeter> {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("untapped Gate you control");

    static {
        filter.add(new SubtypePredicate("Gate"));
        filter.add(Predicates.not(new TappedPredicate()));
    }

    public CracklingPerimeter(UUID ownerId) {
        super(ownerId, 88, "Crackling Perimeter", Rarity.UNCOMMON, new CardType[]{CardType.ENCHANTMENT}, "{1}{R}");
        this.expansionSetCode = "GTC";

        this.color.setRed(true);

        // Tap an untapped Gate you control: Crackling Perimeter deals 1 damage to each opponent.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new CracklingPerimeterEffect(), new TapTargetCost(new TargetControlledPermanent(filter))));
    }

    public CracklingPerimeter(final CracklingPerimeter card) {
        super(card);
    }

    @Override
    public CracklingPerimeter copy() {
        return new CracklingPerimeter(this);
    }
}

class CracklingPerimeterEffect extends OneShotEffect<CracklingPerimeterEffect> {

    public CracklingPerimeterEffect() {
        super(Constants.Outcome.Damage);
        staticText = "{this} deals 1 damage to each opponent";
    }

    public CracklingPerimeterEffect(final CracklingPerimeterEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        boolean result = true;
        for (UUID playerId : game.getOpponents(source.getControllerId())) {
            Player opponent = game.getPlayer(playerId);
            if (opponent != null) {
                opponent.damage(1, source.getSourceId(), game, false, true);
            } else {
                result = false;
            }
        }
        return result;
    }

    @Override
    public CracklingPerimeterEffect copy() {
        return new CracklingPerimeterEffect(this);
    }
}
