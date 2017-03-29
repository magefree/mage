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

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EndOfCombatTriggeredAbility;
import mage.abilities.condition.common.AttackedOrBlockedThisCombatSourceCondition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.CrewAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.watchers.common.AttackedOrBlockedThisCombatWatcher;

import java.util.UUID;

/**
 *
 * @author spjspj
 */
public class DaredevilDragster extends CardImpl {

    public DaredevilDragster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        this.subtype.add("Vehicle");
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // At end of combat, if Daredevil Dragster attacked or blocked this combat, put a velocity counter on it. Then if it has two or more velocity counters on it, sacrifice it and draw two cards.
        this.addAbility(new ConditionalTriggeredAbility(
                new EndOfCombatTriggeredAbility(new DaredevilDragsterEffect(), false),
                AttackedOrBlockedThisCombatSourceCondition.instance,
                "At end of combat, if {this} attacked or blocked this combat, put a velocity counter on it. Then if it has two or more velocity counters on it, sacrifice it and draw two cards."),
                new AttackedOrBlockedThisCombatWatcher());

        // Crew 2
        this.addAbility(new CrewAbility(2));
    }

    public DaredevilDragster(final DaredevilDragster card) {
        super(card);
    }

    @Override
    public DaredevilDragster copy() {
        return new DaredevilDragster(this);
    }
}

class DaredevilDragsterEffect extends OneShotEffect {

    public DaredevilDragsterEffect() {
        super(Outcome.Damage);
        this.staticText = "put a velocity counter on it. Then if it has two or more velocity counters on it, sacrifice it and draw two cards";
    }

    public DaredevilDragsterEffect(final DaredevilDragsterEffect effect) {
        super(effect);
    }

    @Override
    public DaredevilDragsterEffect copy() {
        return new DaredevilDragsterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null && permanent != null) {
            permanent.addCounters(CounterType.VELOCITY.createInstance(), source, game);
            if (permanent.getCounters(game).getCount(CounterType.VELOCITY) >= 2) {
                permanent.sacrifice(source.getSourceId(), game);
                controller.drawCards(2, game);
            }
            return true;
        }
        return false;
    }
}
