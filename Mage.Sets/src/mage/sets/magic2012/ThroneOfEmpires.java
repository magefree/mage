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
package mage.sets.magic2012;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.SoldierToken;
import mage.game.permanent.token.Token;

import java.util.UUID;

/**
 * @author nantuko
 */
public class ThroneOfEmpires extends CardImpl<ThroneOfEmpires> {

    public ThroneOfEmpires(UUID ownerId) {
        super(ownerId, 221, "Throne of Empires", Rarity.RARE, new CardType[]{CardType.ARTIFACT}, "{4}");
        this.expansionSetCode = "M12";

        // {1}, {tap}: Put a 1/1 white Soldier creature token onto the battlefield. Put five of those tokens onto the battlefield instead if you control artifacts named Crown of Empires and Scepter of Empires.
        Ability ability = new SimpleActivatedAbility(Constants.Zone.BATTLEFIELD, new ThroneOfEmpiresEffect(), new GenericManaCost(1));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    public ThroneOfEmpires(final ThroneOfEmpires card) {
        super(card);
    }

    @Override
    public ThroneOfEmpires copy() {
        return new ThroneOfEmpires(this);
    }
}

class ThroneOfEmpiresEffect extends OneShotEffect<ThroneOfEmpiresEffect> {

    public ThroneOfEmpiresEffect() {
        super(Constants.Outcome.PutCreatureInPlay);
        staticText = "Put a 1/1 white Soldier creature token onto the battlefield. Put five of those tokens onto the battlefield instead if you control artifacts named Crown of Empires and Scepter of Empires";
    }

    public ThroneOfEmpiresEffect(ThroneOfEmpiresEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        boolean scepter = false;
        boolean crown = false;
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(source.getControllerId())) {
            if (permanent.getName().equals("Scepter of Empires")) {
                scepter = true;
            } else if (permanent.getName().equals("Crown of Empires")) {
                crown = true;
            }
            if (scepter && crown) break;
        }
        Token soldier = new SoldierToken();
        int count = scepter && crown ? 5 : 1;
        soldier.putOntoBattlefield(count, game, source.getSourceId(), source.getControllerId());
        return false;
    }

    @Override
    public ThroneOfEmpiresEffect copy() {
        return new ThroneOfEmpiresEffect(this);
    }
}
