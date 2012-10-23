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
package mage.sets.stronghold;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Outcome;
import mage.Constants.PhaseStep;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.ZoneChangeTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.CardImpl;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author Plopman
 */
public class IntruderAlarm extends CardImpl<IntruderAlarm> {

    public IntruderAlarm(UUID ownerId) {
        super(ownerId, 34, "Intruder Alarm", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}");
        this.expansionSetCode = "STH";

        this.color.setBlue(true);

        // Creatures don't untap during their controllers' untap steps.
        this.addAbility(new SimpleStaticAbility(Constants.Zone.BATTLEFIELD, new IntruderAlarmEffect()));
        // Whenever a creature enters the battlefield, untap all creatures.
        this.addAbility(new IntruderAlarmTriggeredAbility(new UntapAllCreatureEffect()));
    }

    public IntruderAlarm(final IntruderAlarm card) {
        super(card);
    }

    @Override
    public IntruderAlarm copy() {
        return new IntruderAlarm(this);
    }
}


class IntruderAlarmEffect extends ReplacementEffectImpl<IntruderAlarmEffect> {


    public IntruderAlarmEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
    }

    public IntruderAlarmEffect(final IntruderAlarmEffect effect) {
        super(effect);
    }

    @Override
    public IntruderAlarmEffect copy() {
        return new IntruderAlarmEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        used = false;
        return true;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent creature = game.getPermanent(event.getTargetId());
        if (game.getTurn().getStepType() == PhaseStep.UNTAP &&  event.getType() == EventType.UNTAP
                && creature != null && creature.getCardType().contains(CardType.CREATURE) && creature.getControllerId() == event.getPlayerId()) {
            return true;
        }
        return false;
    }

    @Override
    public String getText(Mode mode) {
        return "Creatures don't untap during their controllers' untap steps";
    }
    
    

}

class IntruderAlarmTriggeredAbility extends ZoneChangeTriggeredAbility<IntruderAlarmTriggeredAbility> {

    public IntruderAlarmTriggeredAbility(Effect effect) {
        super(Constants.Zone.BATTLEFIELD, effect, "Whenever a creature enters the battlefield, ", false);
    }



    public IntruderAlarmTriggeredAbility(IntruderAlarmTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE && !event.getTargetId().equals(this.getSourceId())) {
            ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
            if (zEvent.getToZone() == Constants.Zone.BATTLEFIELD) {
                Permanent permanent = game.getPermanent(event.getTargetId());
                if (permanent != null && permanent.getCardType().contains(Constants.CardType.CREATURE)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public IntruderAlarmTriggeredAbility copy() {
        return new IntruderAlarmTriggeredAbility(this);
    }

}

class UntapAllCreatureEffect extends OneShotEffect<UntapAllCreatureEffect> {


    public UntapAllCreatureEffect() {
        super(Outcome.Untap);
        staticText = "untap all creatures";
    }

    public UntapAllCreatureEffect(final UntapAllCreatureEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            for (Permanent land: game.getBattlefield().getAllActivePermanents(new FilterCreaturePermanent(), game)) {
                land.untap(game);
            }
            return true;
        }
        return false;
    }

    @Override
    public UntapAllCreatureEffect copy() {
        return new UntapAllCreatureEffect(this);
    }

}