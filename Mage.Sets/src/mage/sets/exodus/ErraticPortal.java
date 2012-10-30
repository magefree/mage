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
package mage.sets.exodus;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author jeffwadsworth
 */
public class ErraticPortal extends CardImpl<ErraticPortal> {

    public ErraticPortal(UUID ownerId) {
        super(ownerId, 132, "Erratic Portal", Rarity.RARE, new CardType[]{CardType.ARTIFACT}, "{4}");
        this.expansionSetCode = "EXO";

        // {1}, {tap}: Return target creature to its owner's hand unless its controller pays {1}.
        Ability ability = new SimpleActivatedAbility(Constants.Zone.BATTLEFIELD, new ErraticPortalEffect(new GenericManaCost(1)), new ManaCostsImpl("{1}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    public ErraticPortal(final ErraticPortal card) {
        super(card);
    }

    @Override
    public ErraticPortal copy() {
        return new ErraticPortal(this);
    }
}

class ErraticPortalEffect extends OneShotEffect<ErraticPortalEffect> {

    protected Cost cost;

    public ErraticPortalEffect(Cost cost) {
        super(Constants.Outcome.Detriment);
        this.staticText = "Return target creature to its owner's hand unless its controller pays {1}";
        this.cost = cost;
    }

    public ErraticPortalEffect(final ErraticPortalEffect effect) {
        super(effect);
        this.cost = effect.cost.copy();
    }

    @Override
    public ErraticPortalEffect copy() {
        return new ErraticPortalEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent targetCreature = game.getPermanent(source.getFirstTarget());
        if (targetCreature != null) {
            Player player = game.getPlayer(targetCreature.getControllerId());
            if (player != null) {
                cost.clearPaid();
                if (!cost.pay(source, game, targetCreature.getControllerId(), targetCreature.getControllerId(), false)) {
                    return targetCreature.moveToZone(Constants.Zone.HAND, source.getSourceId(), game, true);
                }
            }
        }
        return false;
    }
}
