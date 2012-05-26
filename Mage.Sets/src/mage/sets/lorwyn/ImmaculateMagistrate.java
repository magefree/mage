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

import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author North
 */
public class ImmaculateMagistrate extends CardImpl<ImmaculateMagistrate> {

    public ImmaculateMagistrate(UUID ownerId) {
        super(ownerId, 219, "Immaculate Magistrate", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{3}{G}");
        this.expansionSetCode = "LRW";
        this.subtype.add("Elf");
        this.subtype.add("Shaman");

        this.color.setGreen(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {tap}: Put a +1/+1 counter on target creature for each Elf you control.
        SimpleActivatedAbility ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new ImmaculateMagistrateEffect(),
                new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    public ImmaculateMagistrate(final ImmaculateMagistrate card) {
        super(card);
    }

    @Override
    public ImmaculateMagistrate copy() {
        return new ImmaculateMagistrate(this);
    }
}

class ImmaculateMagistrateEffect extends OneShotEffect<ImmaculateMagistrateEffect> {
    static final FilterControlledPermanent filter = new FilterControlledPermanent("Elf");
    static {
        filter.getSubtype().add("Elf");
    }
    public ImmaculateMagistrateEffect() {
        super(Outcome.BoostCreature);
        this.staticText = "Put a +1/+1 counter on target creature for each Elf you control";
    }

    public ImmaculateMagistrateEffect(final ImmaculateMagistrateEffect effect) {
        super(effect);
    }

    @Override
    public ImmaculateMagistrateEffect copy() {
        return new ImmaculateMagistrateEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent != null) {
            int count = game.getBattlefield().count(filter, source.getControllerId(), game);
            if (count > 0) {
                permanent.addCounters(CounterType.P1P1.createInstance(count), game);
                return true;
            }
        }
        return false;
    }
}
