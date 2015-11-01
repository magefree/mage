/*
 * Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 *
 *    1. Redistributions of source code must retain the above copyright notice, this list of
 *       conditions and the following disclaimer.
 *
 *    2. Redistributions in binary form must reproduce the above copyright notice, this list
 *       of conditions and the following disclaimer in the documentation and/or other materials
 *       provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * The views and conclusions contained in the software and documentation are those of the
 * authors and should not be interpreted as representing official policies, either expressed
 * or implied, of BetaSteward_at_googlemail.com.
 */
package mage.abilities.keyword;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.SunburstCount;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.counters.Counter;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author Plopman
 */
public class SunburstAbility extends EntersBattlefieldAbility {

    private final static String ruleCreature = "Sunburst <i>(This enters the battlefield with a +1/+1 counter on it for each color of mana spent to cast it.)</i>";
    private final static String ruleNonCreature = "Sunburst <i>(This enters the battlefield with a charge counter on it for each color of mana spent to cast it.)</i>";
    private boolean isCreature;

    public SunburstAbility(Card card) {
        super(new SunburstEffect(), "");
        isCreature = card.getCardType().contains(CardType.CREATURE);
    }

    public SunburstAbility(final SunburstAbility ability) {
        super(ability);
        this.isCreature = ability.isCreature;
    }

    @Override
    public EntersBattlefieldAbility copy() {
        return new SunburstAbility(this);
    }

    @Override
    public String getRule() {
        return isCreature ? ruleCreature : ruleNonCreature;
    }

}

class SunburstEffect extends OneShotEffect {

    private static final DynamicValue amount = new SunburstCount();

    public SunburstEffect() {
        super(Outcome.Benefit);
        staticText = "Sunburst";
    }

    public SunburstEffect(final SunburstEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanentEntering(source.getSourceId());
        if (permanent != null) {
            Counter counter;
            if (permanent.getCardType().contains(CardType.CREATURE)) {
                counter = CounterType.P1P1.createInstance(amount.calculate(game, source, this));
            } else {
                counter = CounterType.CHARGE.createInstance(amount.calculate(game, source, this));
            }
            if (counter != null) {

                permanent.addCounters(counter, game);
                if (!game.isSimulation()) {
                    Player player = game.getPlayer(source.getControllerId());
                    if (player != null) {
                        game.informPlayers(player.getLogName() + " puts " + counter.getCount() + " " + counter.getName() + " counter on " + permanent.getName());
                    }
                }
            }
        }
        return true;
    }

    @Override
    public SunburstEffect copy() {
        return new SunburstEffect(this);
    }

}
