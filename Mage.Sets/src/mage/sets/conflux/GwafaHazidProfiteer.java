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
package mage.sets.conflux;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.RestrictionEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.counters.common.BriberyCounter;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author jeffwadsworth
 */
public class GwafaHazidProfiteer extends CardImpl<GwafaHazidProfiteer> {
    
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature you don't control");
    
    static {
        filter.add(new ControllerPredicate(TargetController.NOT_YOU));
    }

    public GwafaHazidProfiteer(UUID ownerId) {
        super(ownerId, 110, "Gwafa Hazid, Profiteer", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{1}{W}{U}");
        this.expansionSetCode = "CON";
        this.supertype.add("Legendary");
        this.subtype.add("Human");
        this.subtype.add("Rogue");

        this.color.setBlue(true);
        this.color.setWhite(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {W}{U}, {tap}: Put a bribery counter on target creature you don't control. Its controller draws a card.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GwafaHazidProfiteerEffect1(), new ManaCostsImpl("{W}{U}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);
        
        // Creatures with bribery counters on them can't attack or block.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GwafaHazidProfiteerEffect2()));
        
    }

    public GwafaHazidProfiteer(final GwafaHazidProfiteer card) {
        super(card);
    }

    @Override
    public GwafaHazidProfiteer copy() {
        return new GwafaHazidProfiteer(this);
    }
}

class GwafaHazidProfiteerEffect1 extends OneShotEffect<GwafaHazidProfiteerEffect1> {
    
    GwafaHazidProfiteerEffect1() {
        super(Outcome.Detriment);
        staticText = "Put a bribery counter on target creature you don't control. Its controller draws a card";
    }

    public GwafaHazidProfiteerEffect1(final GwafaHazidProfiteerEffect1 effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent targetCreature = game.getPermanent(source.getFirstTarget());
        if (targetCreature != null) {
            Player controller = game.getPlayer(targetCreature.getControllerId());
            targetCreature.addCounters(new BriberyCounter(), game);
            if (controller != null) {
                controller.drawCards(1, game);
            }
            return true;
        }
        return false;
    }

    @Override
    public GwafaHazidProfiteerEffect1 copy() {
        return new GwafaHazidProfiteerEffect1(this);
    }


}

class GwafaHazidProfiteerEffect2 extends RestrictionEffect<GwafaHazidProfiteerEffect2> {

    public GwafaHazidProfiteerEffect2() {
        super(Duration.WhileOnBattlefield);
        staticText = "Creatures with bribery counters on them can't attack or block";
    }

    public GwafaHazidProfiteerEffect2(final GwafaHazidProfiteerEffect2 effect) {
        super(effect);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return permanent.getCounters().containsKey(CounterType.BRIBERY);
    }

    @Override
    public boolean canAttack(Game game) {
        return false;
    }
    
    @Override
    public boolean canBlock(Permanent attacker, Permanent blocker, Ability source, Game game) {
        return false;
    }

    @Override
    public GwafaHazidProfiteerEffect2 copy() {
        return new GwafaHazidProfiteerEffect2(this);
    }
}
