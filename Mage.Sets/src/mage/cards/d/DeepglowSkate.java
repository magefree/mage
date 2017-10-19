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
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.counters.Counter;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.Target;
import mage.target.TargetPermanent;

/**
 *
 * @author spjspj
 */
public class DeepglowSkate extends CardImpl {

    public DeepglowSkate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}");

        this.subtype.add(SubType.FISH);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Deepglow Skate enters the battlefield, double the number of each kind of counter on any number of target permanents.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DeepglowSkateEffect(), true);
        ability.addTarget(new TargetPermanent(0, Integer.MAX_VALUE, new FilterPermanent(), false));
        this.addAbility(ability);
    }

    public DeepglowSkate(final DeepglowSkate card) {
        super(card);
    }

    @Override
    public DeepglowSkate copy() {
        return new DeepglowSkate(this);
    }
}

class DeepglowSkateEffect extends OneShotEffect {

    public DeepglowSkateEffect() {
        super(Outcome.Benefit);
        this.staticText = "{this} double the number of each kind of counter on any number of target permanents";
    }

    public DeepglowSkateEffect(final DeepglowSkateEffect effect) {
        super(effect);
    }

    @Override
    public DeepglowSkateEffect copy() {
        return new DeepglowSkateEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        boolean didOne = false;

        for (Target target : source.getTargets()) {
            for (UUID targetID : target.getTargets()) {
                Permanent permanent = game.getPermanent(targetID);
                if (permanent != null) {
                    for (Counter counter : permanent.getCounters(game).values()) {
                        Counter newCounter = new Counter(counter.getName(), counter.getCount());
                        permanent.addCounters(newCounter, source, game);
                        didOne = true;
                    }
                }
            }
        }
        return didOne;
    }

}
