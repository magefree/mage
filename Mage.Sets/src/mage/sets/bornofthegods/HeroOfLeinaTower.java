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
package mage.sets.bornofthegods;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.HeroicAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class HeroOfLeinaTower extends CardImpl<HeroOfLeinaTower> {

    public HeroOfLeinaTower(UUID ownerId) {
        super(ownerId, 123, "Hero of Leina Tower", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{G}");
        this.expansionSetCode = "BNG";
        this.subtype.add("Human");
        this.subtype.add("Warrior");

        this.color.setGreen(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // <i>Heroic</i> - Whenever you cast a spell that targets Hero of Leina Tower, you may pay {X}. If you do, put X +1/+1 counters on Hero of Leina Tower.
        this.addAbility(new HeroicAbility(new HeroOfLeinaTowerEffect()));

    }

    public HeroOfLeinaTower(final HeroOfLeinaTower card) {
        super(card);
    }

    @Override
    public HeroOfLeinaTower copy() {
        return new HeroOfLeinaTower(this);
    }
}

class HeroOfLeinaTowerEffect extends OneShotEffect<HeroOfLeinaTowerEffect> {

    public HeroOfLeinaTowerEffect() {
        super(Outcome.BoostCreature);
        staticText = "you may pay {X}. If you do, put X +1/+1 counters on {this}";
    }

    public HeroOfLeinaTowerEffect(final HeroOfLeinaTowerEffect effect) {
        super(effect);
    }

    @Override
    public HeroOfLeinaTowerEffect copy() {
        return new HeroOfLeinaTowerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player you = game.getPlayer(source.getControllerId());
        ManaCosts cost = new ManaCostsImpl("{X}");
        if (you != null && you.chooseUse(Outcome.BoostCreature, "Do you want to to pay {X}?", game)) {
            int costX = you.announceXMana(0, Integer.MAX_VALUE, "Announce the value for {X}", game, source);
            cost.add(new GenericManaCost(costX));
            if (cost.pay(source, game, source.getId(), source.getControllerId(), false)) {
                Permanent sourcePermanent = game.getPermanent(source.getSourceId());
                if (sourcePermanent != null) {
                    return new AddCountersSourceEffect(CounterType.P1P1.createInstance(costX), true).apply(game, source);
                }
            }
        }
        return false;
    }
}
