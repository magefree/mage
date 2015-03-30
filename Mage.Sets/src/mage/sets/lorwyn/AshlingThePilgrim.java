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
package mage.sets.lorwyn;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageEverythingEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class AshlingThePilgrim extends CardImpl {

    public AshlingThePilgrim(UUID ownerId) {
        super(ownerId, 149, "Ashling the Pilgrim", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{1}{R}");
        this.expansionSetCode = "LRW";
        this.supertype.add("Legendary");
        this.subtype.add("Elemental");
        this.subtype.add("Shaman");

        this.color.setRed(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {1}{R}: Put a +1/+1 counter on Ashling the Pilgrim. If this is the third time this ability has resolved this turn, remove all +1/+1 counters from Ashling the Pilgrim, and it deals that much damage to each creature and each player.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.P1P1.createInstance(), true), new ManaCostsImpl("{1}{R}"));
        ability.addEffect(new AshlingThePilgrimEffect());
        this.addAbility(ability);
    }

    public AshlingThePilgrim(final AshlingThePilgrim card) {
        super(card);
    }

    @Override
    public AshlingThePilgrim copy() {
        return new AshlingThePilgrim(this);
    }
}

class AshlingThePilgrimEffect extends OneShotEffect {

    class ActivationInfo {
        public int zoneChangeCounter;
        public int turn;
        public int activations;
    }

    public AshlingThePilgrimEffect() {
        super(Outcome.Damage);
        this.staticText = "If this is the third time this ability has resolved this turn, remove all +1/+1 counters from {this}, and it deals that much damage to each creature and each player";
    }

    public AshlingThePilgrimEffect(final AshlingThePilgrimEffect effect) {
        super(effect);
    }

    @Override
    public AshlingThePilgrimEffect copy() {
        return new AshlingThePilgrimEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        if (controller != null && sourcePermanent != null) {
            ActivationInfo info;
            Object object = game.getState().getValue(source.getSourceId() + "ActivationInfo");
            if (object instanceof ActivationInfo) {
                info = (ActivationInfo) object;
                if (info.turn != game.getTurnNum() || sourcePermanent.getZoneChangeCounter(game) != info.zoneChangeCounter) {
                    info.turn = game.getTurnNum();
                    info.zoneChangeCounter = sourcePermanent.getZoneChangeCounter(game);
                    info.activations = 0;
                }
            } else {
                info = new ActivationInfo();
                info.turn = game.getTurnNum();
                info.zoneChangeCounter = sourcePermanent.getZoneChangeCounter(game);
                game.getState().setValue(source.getSourceId() + "ActivationInfo", info);
            }
            info.activations++;
            if (info.activations == 3) {
                int damage = sourcePermanent.getCounters().getCount(CounterType.P1P1);
                if (damage > 0) {
                    sourcePermanent.removeCounters(CounterType.P1P1.getName(), damage, game);
                    return new DamageEverythingEffect(damage, new FilterCreaturePermanent()).apply(game, source);
                }
            }
            return true;
        }
        return false;
    }
}
