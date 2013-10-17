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
package mage.sets.eventide;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.CostImpl;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreatureOrPlayer;

/**
 *
 * @author jeffwadsworth
 */
public class HatchetBully extends CardImpl<HatchetBully> {

    public HatchetBully(UUID ownerId) {
        super(ownerId, 54, "Hatchet Bully", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{3}{R}");
        this.expansionSetCode = "EVE";
        this.subtype.add("Goblin");
        this.subtype.add("Warrior");

        this.color.setRed(true);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // {2}{R}, {tap}, Put a -1/-1 counter on a creature you control: Hatchet Bully deals 2 damage to target creature or player.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new HatchetBullyEffect(), new ManaCostsImpl("{2}{R}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new HatchetBullyCost());
        ability.addTarget(new TargetCreatureOrPlayer(true));
        Target target = new TargetControlledCreaturePermanent(true);
        target.setNotTarget(true);
        ability.addTarget(target);
        this.addAbility(ability);

    }

    public HatchetBully(final HatchetBully card) {
        super(card);
    }

    @Override
    public HatchetBully copy() {
        return new HatchetBully(this);
    }
}

class HatchetBullyCost extends CostImpl<HatchetBullyCost> {

    public HatchetBullyCost() {
        this.text = "Put a -1/-1 counter on a creature you control";
    }

    public HatchetBullyCost(HatchetBullyCost cost) {
        super(cost);
    }

    @Override
    public boolean canPay(UUID sourceId, UUID controllerId, Game game) {
        return true;
    }

    @Override
    public boolean pay(Ability ability, Game game, UUID sourceId, UUID controllerId, boolean noMana) {
        Permanent permanent = game.getPermanent(ability.getTargets().get(1).getFirstTarget());
        if (permanent != null) {
            permanent.addCounters(CounterType.M1M1.createInstance(), game);
            this.paid = true;
        }
        return paid;
    }

    @Override
    public HatchetBullyCost copy() {
        return new HatchetBullyCost(this);
    }
}

class HatchetBullyEffect extends OneShotEffect<HatchetBullyEffect> {
    
    public HatchetBullyEffect() {
        super(Outcome.Damage);
        staticText = "{this} deals 2 damage to target creature or player";
    }

    public HatchetBullyEffect(final HatchetBullyEffect effect) {
        super(effect);
    }

    @Override
    public HatchetBullyEffect copy() {
        return new HatchetBullyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent != null) {
            permanent.damage(2, source.getSourceId(), game, true, false);
        }
        Player player = game.getPlayer(source.getFirstTarget());
        if (player != null) {
            player.damage(2, source.getSourceId(), game, false, true);
        }
        return true;
    }
}
