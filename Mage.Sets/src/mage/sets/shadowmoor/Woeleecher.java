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
package mage.sets.shadowmoor;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
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
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author jeffwadsworth
 *
 */
public class Woeleecher extends CardImpl<Woeleecher> {

    public Woeleecher(UUID ownerId) {
        super(ownerId, 27, "Woeleecher", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{5}{W}");
        this.expansionSetCode = "SHM";
        this.subtype.add("Elemental");

        this.color.setWhite(true);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // {W}, {tap}: Remove a -1/-1 counter from target creature. If you do, you gain 2 life.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new WoeleecherEffect(), new ManaCostsImpl("{W}"));
        ability.addTarget(new TargetCreaturePermanent(true));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

    }

    public Woeleecher(final Woeleecher card) {
        super(card);
    }

    @Override
    public Woeleecher copy() {
        return new Woeleecher(this);
    }
}

class WoeleecherEffect extends OneShotEffect<WoeleecherEffect> {

    private int numberCountersOriginal = 0;
    private int numberCountersAfter = 0;

    public WoeleecherEffect() {
        super(Outcome.ReturnToHand);
        this.staticText = "Remove a -1/-1 counter from target creature. If you do, you gain 2 life";
    }

    public WoeleecherEffect(final WoeleecherEffect effect) {
        super(effect);
    }

    @Override
    public WoeleecherEffect copy() {
        return new WoeleecherEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent target = game.getPermanent(source.getFirstTarget());
        Player you = game.getPlayer(source.getControllerId());
        if (target != null) {
            numberCountersOriginal = target.getCounters().getCount(CounterType.M1M1);
            target.removeCounters(CounterType.M1M1.createInstance(), game);
            numberCountersAfter = target.getCounters().getCount(CounterType.M1M1);
            if (numberCountersAfter < numberCountersOriginal && you != null) {
                you.gainLife(2, game);
                return true;
            }
        }
        return false;
    }
}