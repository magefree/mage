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
package mage.sets.fatereforged;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.VigilanceAbility;
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
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class DaghatarTheAdamant extends CardImpl {

    public DaghatarTheAdamant(UUID ownerId) {
        super(ownerId, 9, "Daghatar the Adamant", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{3}{W}");
        this.expansionSetCode = "FRF";
        this.supertype.add("Legendary");
        this.subtype.add("Human");
        this.subtype.add("Warrior");
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());
        // Daghatar the Adamant enters the battlefield with four +1/+1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance(4)),
                "with four +1/+1 counters on it"));

        // {1}{B/G}{B/G}: Move a +1/+1 counter from target creature onto a second target creature.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new MoveCounterFromTargetToTargetEffect(),new ManaCostsImpl("{1}{B/G}{B/G}"));
        ability.addTarget(new TargetCreaturePermanent(new FilterCreaturePermanent("creature the +1/+1 counter is moved from")));
        ability.addTarget(new TargetCreaturePermanent(new FilterCreaturePermanent("creature the +1/+1 counter is moved to")));
        this.addAbility(ability);


    }

    public DaghatarTheAdamant(final DaghatarTheAdamant card) {
        super(card);
    }

    @Override
    public DaghatarTheAdamant copy() {
        return new DaghatarTheAdamant(this);
    }
}

class MoveCounterFromTargetToTargetEffect extends OneShotEffect {

    public MoveCounterFromTargetToTargetEffect() {
        super(Outcome.Detriment);
        this.staticText = "Move a +1/+1 counter from target creature onto a second target creature";
    }

    public MoveCounterFromTargetToTargetEffect(final MoveCounterFromTargetToTargetEffect effect) {
        super(effect);
    }

    @Override
    public MoveCounterFromTargetToTargetEffect copy() {
        return new MoveCounterFromTargetToTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (sourceObject != null && controller != null) {
            Permanent fromPermanent = game.getPermanent(getTargetPointer().getFirst(game, source));
            if (fromPermanent != null && fromPermanent.getCounters(game).getCount(CounterType.P1P1) > 0) {
                Permanent toPermanent = game.getPermanent(source.getTargets().get(1).getFirstTarget());
                if (toPermanent != null) {
                    fromPermanent.removeCounters(CounterType.P1P1.createInstance(), game);
                    toPermanent.addCounters(CounterType.P1P1.createInstance(), game);
                    game.informPlayers(sourceObject.getLogName() + ": Moved a +1/+1 counter from " + fromPermanent.getLogName() +" to " + toPermanent.getLogName());
                }
            }
            return true;
        }
        return false;
    }
}
