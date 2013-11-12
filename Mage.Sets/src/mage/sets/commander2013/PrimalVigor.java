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
package mage.sets.commander2013;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author LevelX2
 */
public class PrimalVigor extends CardImpl<PrimalVigor> {

    public PrimalVigor(UUID ownerId) {
        super(ownerId, 162, "Primal Vigor", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{4}{G}");
        this.expansionSetCode = "C13";

        this.color.setGreen(true);

        // If one or more tokens would be put onto the battlefield, twice that many of those tokens are put onto the battlefield instead.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new PrimalVigorTokenEffect()));
        // If one or more +1/+1 counters would be placed on a creature, twice that many +1/+1 counters are placed on that creature instead.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new PrimalVigorCounterEffect()));

    }

    public PrimalVigor(final PrimalVigor card) {
        super(card);
    }

    @Override
    public PrimalVigor copy() {
        return new PrimalVigor(this);
    }
}

class PrimalVigorTokenEffect extends ReplacementEffectImpl<PrimalVigorTokenEffect> {

    public PrimalVigorTokenEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Copy);
        staticText = "If one or more tokens would be put onto the battlefield, twice that many of those tokens are put onto the battlefield instead";
    }

    public PrimalVigorTokenEffect(final PrimalVigorTokenEffect effect) {
        super(effect);
    }

    @Override
    public PrimalVigorTokenEffect copy() {
        return new PrimalVigorTokenEffect(this);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        switch (event.getType()) {
            case CREATE_TOKEN:
                return true;
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmount(event.getAmount() * 2);
        return false;
    }

}

class PrimalVigorCounterEffect extends ReplacementEffectImpl<PrimalVigorCounterEffect> {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    PrimalVigorCounterEffect() {
        super(Duration.WhileOnBattlefield, Outcome.BoostCreature, false);
        staticText = "If one or more +1/+1 counters would be placed on a creature, twice that many +1/+1 counters are placed on that creature instead";
    }

    PrimalVigorCounterEffect(final PrimalVigorCounterEffect effect) {
        super(effect);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent p = game.getPermanent(event.getTargetId());
        if (p != null) {
            p.addCounters(CounterType.P1P1.createInstance(event.getAmount() * 2), game, event.getAppliedEffects());
            return true;
        }
        return false;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType() == GameEvent.EventType.ADD_COUNTER) {
            Permanent target = game.getPermanent(event.getTargetId());
            if (target != null && filter.match(target, game)
                    && event.getData() != null && event.getData().equals("+1/+1")) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public PrimalVigorCounterEffect copy() {
        return new PrimalVigorCounterEffect(this);
    }
}
