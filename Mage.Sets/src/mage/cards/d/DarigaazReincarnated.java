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
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.counter.RemoveCounterSourceEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author TheElk801
 */
public class DarigaazReincarnated extends CardImpl {

    public DarigaazReincarnated(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}{R}{G}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // If Darigaaz Reincarnated would die, instead exile it with three egg counters on it.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new DarigaazReincarnatedDiesEffect()));

        // At the beginning of your upkeep, if Darigaaz is exiled with an egg counter on it, remove an egg counter from it. Then if Darigaaz has no egg counters on it, return it to the battlefield.
        this.addAbility(new DarigaazReincarnatedTriggeredAbility());
    }

    public DarigaazReincarnated(final DarigaazReincarnated card) {
        super(card);
    }

    @Override
    public DarigaazReincarnated copy() {
        return new DarigaazReincarnated(this);
    }
}

class DarigaazReincarnatedDiesEffect extends ReplacementEffectImpl {

    public DarigaazReincarnatedDiesEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Exile);
        staticText = "If {this} would die, instead exile it with three egg counters on it";
    }

    public DarigaazReincarnatedDiesEffect(final DarigaazReincarnatedDiesEffect effect) {
        super(effect);
    }

    @Override
    public DarigaazReincarnatedDiesEffect copy() {
        return new DarigaazReincarnatedDiesEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent permanent = ((ZoneChangeEvent) event).getTarget();
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null && permanent != null) {
            Card permCard = game.getCard(permanent.getId());
            if (permCard == null) {
                return false;
            }
            return controller.moveCardToExileWithInfo(permanent, null, null, source.getSourceId(), game, Zone.BATTLEFIELD, true)
                    && permCard.addCounters(CounterType.EGG.createInstance(3), source, game);
        }
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getTargetId().equals(source.getSourceId())) {
            ZoneChangeEvent zce = (ZoneChangeEvent) event;
            return zce.isDiesEvent();
        }
        return false;
    }

}

class DarigaazReincarnatedTriggeredAbility extends ConditionalTriggeredAbility {

    public DarigaazReincarnatedTriggeredAbility() {
        super(new BeginningOfUpkeepTriggeredAbility(Zone.EXILED, new DarigaazReincarnatedReturnEffect(), TargetController.YOU, false),
                DarigaazReincarnatedCondition.instance,
                "At the beginning of your upkeep, if {this} is exiled with an egg counter on it, "
                + "remove an egg counter from it. Then if {this} has no egg counters on it, return it to the battlefield");
        this.setRuleVisible(false);

    }

    public DarigaazReincarnatedTriggeredAbility(final DarigaazReincarnatedTriggeredAbility effect) {
        super(effect);
    }

    @Override
    public DarigaazReincarnatedTriggeredAbility copy() {
        return new DarigaazReincarnatedTriggeredAbility(this);
    }
}

class DarigaazReincarnatedReturnEffect extends OneShotEffect {

    DarigaazReincarnatedReturnEffect() {
        super(Outcome.Benefit);
        this.staticText = "";
    }

    DarigaazReincarnatedReturnEffect(final DarigaazReincarnatedReturnEffect effect) {
        super(effect);
    }

    @Override
    public DarigaazReincarnatedReturnEffect copy() {
        return new DarigaazReincarnatedReturnEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Card card = game.getCard(source.getSourceId());
        if (player == null || card == null) {
            return false;
        }
        new RemoveCounterSourceEffect(CounterType.EGG.createInstance()).apply(game, source);
        if (card.getCounters(game).getCount(CounterType.EGG) == 0) {
            return card.putOntoBattlefield(game, Zone.EXILED, source.getSourceId(), player.getId());
        }
        return false;
    }
}

enum DarigaazReincarnatedCondition implements Condition {

    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(source.getSourceId());
        if (card != null) {
            if (game.getState().getZone(card.getId()) == Zone.EXILED
                    && card.getCounters(game).getCount(CounterType.EGG) > 0) {
                return true;
            }
        }
        return false;
    }
}
