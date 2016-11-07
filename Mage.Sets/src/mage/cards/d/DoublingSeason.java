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
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.StackObject;

/**
 *
 * @author LevelX2
 */
public class DoublingSeason extends CardImpl {

    public DoublingSeason(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{G}");

        // If an effect would create one or more tokens under your control, it creates twice that many of those tokens instead.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new DoublingSeasonTokenEffect()));
        // If an effect would place one or more counters on a permanent you control, it places twice that many of those counters on that permanent instead.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new DoublingSeasonCounterEffect()));

    }

    public DoublingSeason(final DoublingSeason card) {
        super(card);
    }

    @Override
    public DoublingSeason copy() {
        return new DoublingSeason(this);
    }
}

class DoublingSeasonTokenEffect extends ReplacementEffectImpl {

    public DoublingSeasonTokenEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Copy);
        staticText = "If an effect would create one or more tokens under your control, it creates twice that many of those tokens instead";
    }

    public DoublingSeasonTokenEffect(final DoublingSeasonTokenEffect effect) {
        super(effect);
    }

    @Override
    public DoublingSeasonTokenEffect copy() {
        return new DoublingSeasonTokenEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType().equals(GameEvent.EventType.CREATE_TOKEN);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        StackObject stackObject = game.getStack().getStackObject(event.getSourceId());
        return stackObject != null && event.getPlayerId().equals(source.getControllerId());
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmount(event.getAmount() * 2);
        return false;
    }

}

class DoublingSeasonCounterEffect extends ReplacementEffectImpl {

    DoublingSeasonCounterEffect() {
        super(Duration.WhileOnBattlefield, Outcome.BoostCreature, false);
        staticText = "If an effect would place one or more counters on a permanent you control, it places twice that many of those counters on that permanent instead";
    }

    DoublingSeasonCounterEffect(final DoublingSeasonCounterEffect effect) {
        super(effect);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmount(event.getAmount() * 2);
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ADD_COUNTERS;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent permanent = game.getPermanent(event.getTargetId());
        if (permanent == null) {
            permanent = game.getPermanentEntering(event.getTargetId());
        }
        return permanent != null && permanent.getControllerId().equals(source.getControllerId());
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public DoublingSeasonCounterEffect copy() {
        return new DoublingSeasonCounterEffect(this);
    }
}
