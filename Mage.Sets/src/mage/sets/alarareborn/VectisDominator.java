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
package mage.sets.alarareborn;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author jeffwadsworth
 */
public class VectisDominator extends CardImpl<VectisDominator> {

    public VectisDominator(UUID ownerId) {
        super(ownerId, 84, "Vectis Dominator", Rarity.COMMON, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}{W}{B}");
        this.expansionSetCode = "ARB";
        this.subtype.add("Human");
        this.subtype.add("Wizard");

        this.color.setBlack(true);
        this.color.setWhite(true);
        this.power = new MageInt(0);
        this.toughness = new MageInt(2);

        // {tap}: Tap target creature unless its controller pays 2 life.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new VectisDominatorEffect(new PayLifeCost(2)), new ManaCostsImpl("{1}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    public VectisDominator(final VectisDominator card) {
        super(card);
    }

    @Override
    public VectisDominator copy() {
        return new VectisDominator(this);
    }
}

class VectisDominatorEffect extends OneShotEffect<VectisDominatorEffect> {

    protected Cost cost;

    public VectisDominatorEffect(Cost cost) {
        super(Outcome.Detriment);
        this.staticText = "Tap target creature unless its controller pays 2 life";
        this.cost = cost;
    }

    public VectisDominatorEffect(final VectisDominatorEffect effect) {
        super(effect);
        this.cost = effect.cost.copy();
    }

    @Override
    public VectisDominatorEffect copy() {
        return new VectisDominatorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent targetCreature = game.getPermanent(source.getFirstTarget());
        if (targetCreature != null) {
            Player player = game.getPlayer(targetCreature.getControllerId());
            if (player != null) {
                cost.clearPaid();
                final StringBuilder sb = new StringBuilder("Pay 2 life otherwise ").append(targetCreature.getName()).append(" will be tapped)");
                if (player.chooseUse(Outcome.Benefit, sb.toString(), game)) {
                    cost.pay(source, game, targetCreature.getControllerId(), targetCreature.getControllerId(), true);
                }
                if (!cost.isPaid()) {
                    return targetCreature.tap(game);
                }
            }
        }
        return false;
    }
}
