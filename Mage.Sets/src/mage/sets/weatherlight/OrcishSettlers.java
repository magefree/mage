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
package mage.sets.weatherlight;

import java.util.List;
import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author jeffwadsworth
 *
 */
public class OrcishSettlers extends CardImpl<OrcishSettlers> {

    public OrcishSettlers(UUID ownerId) {
        super(ownerId, 112, "Orcish Settlers", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{1}{R}");
        this.expansionSetCode = "WTH";
        this.subtype.add("Orc");

        this.color.setRed(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {X}{X}{R}, {tap}, Sacrifice Orcish Settlers: Destroy X target lands.
        Ability ability = new SimpleActivatedAbility(Constants.Zone.BATTLEFIELD, new OrcishSettlersEffect(), new ManaCostsImpl("{X}{X}{R}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    public OrcishSettlers(final OrcishSettlers card) {
        super(card);
    }

    @Override
    public OrcishSettlers copy() {
        return new OrcishSettlers(this);
    }
}

class OrcishSettlersEffect extends OneShotEffect<OrcishSettlersEffect> {

    public OrcishSettlersEffect() {
        super(Constants.Outcome.DestroyPermanent);
        this.staticText = "Destroy X target lands";
    }

    public OrcishSettlersEffect(final OrcishSettlersEffect effect) {
        super(effect);
    }

    @Override
    public OrcishSettlersEffect copy() {
        return new OrcishSettlersEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int amount = new ManacostVariableValue().calculate(game, source);
        TargetLandPermanent target = new TargetLandPermanent(amount);

        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }

        if (player.choose(Constants.Outcome.DestroyPermanent, target, id, game)) {
            List<UUID> targets = target.getTargets();
            for (UUID landId : targets) {
                Permanent land = game.getPermanent(landId);
                if (land != null) {
                    land.destroy(landId, game, false);
                }
            }

        }
        return true;
    }
}
