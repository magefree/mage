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
package mage.sets.magicorigins;

import java.util.UUID;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public class TouchOfMoonglove extends CardImpl {

    public TouchOfMoonglove(UUID ownerId) {
        super(ownerId, 123, "Touch of Moonglove", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{B}");
        this.expansionSetCode = "ORI";

        // Target creature you control gets +1/+0 and gains deathtouch until end of turn.
        Effect effect = new BoostTargetEffect(1, 0, Duration.EndOfTurn);
        effect.setText("Target creature you control gets +1/+0");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(
                DeathtouchAbility.getInstance(), Duration.EndOfTurn, "and gains deathtouch until end of turn"));
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        // Whenever a creature dealt damage by that creature this turn dies, its controller loses 2 life.
        this.getSpellAbility().addEffect(new TouchOfMoongloveAddTriggerEffect());

    }

    public TouchOfMoonglove(final TouchOfMoonglove card) {
        super(card);
    }

    @Override
    public TouchOfMoonglove copy() {
        return new TouchOfMoonglove(this);
    }
}

class TouchOfMoongloveAddTriggerEffect extends OneShotEffect {

    public TouchOfMoongloveAddTriggerEffect() {
        super(Outcome.LoseLife);
        this.staticText = "Whenever a creature dealt damage by that creature this turn dies, its controller loses 2 life";
    }

    public TouchOfMoongloveAddTriggerEffect(final TouchOfMoongloveAddTriggerEffect effect) {
        super(effect);
    }

    @Override
    public TouchOfMoongloveAddTriggerEffect copy() {
        return new TouchOfMoongloveAddTriggerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanentOrLKIBattlefield(getTargetPointer().getFirst(game, source));
        if (permanent != null) {
            DelayedTriggeredAbility delayedAbility = new TouchOfMoongloveDelayedTriggeredAbility(new MageObjectReference(permanent, game));
            delayedAbility.setSourceId(source.getSourceId());
            delayedAbility.setControllerId(source.getControllerId());
            delayedAbility.setSourceObject(source.getSourceObject(game), game);
            game.addDelayedTriggeredAbility(delayedAbility);
        }
        return true;
    }
}

class TouchOfMoongloveDelayedTriggeredAbility extends DelayedTriggeredAbility {

    private final MageObjectReference creatureToCheck;

    public TouchOfMoongloveDelayedTriggeredAbility(MageObjectReference creatureToCheck) {
        super(new LoseLifeTargetEffect(2), Duration.EndOfTurn, false);
        this.creatureToCheck = creatureToCheck;
    }

    public TouchOfMoongloveDelayedTriggeredAbility(TouchOfMoongloveDelayedTriggeredAbility ability) {
        super(ability);
        this.creatureToCheck = ability.creatureToCheck;
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        if (zEvent.getFromZone() == Zone.BATTLEFIELD && zEvent.getToZone() == Zone.GRAVEYARD
                && zEvent.getTarget().getCardType().contains(CardType.CREATURE)) {
            boolean damageDealt = false;
            for (MageObjectReference mor : zEvent.getTarget().getDealtDamageByThisTurn()) {
                if (mor.equals(creatureToCheck)) {
                    damageDealt = true;
                    break;
                }
            }
            if (damageDealt) {
                for (Effect effect : getEffects()) {
                    effect.setTargetPointer(new FixedTarget(zEvent.getTarget().getControllerId()));
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public TouchOfMoongloveDelayedTriggeredAbility copy() {
        return new TouchOfMoongloveDelayedTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever a creature dealt damage by that creature this turn dies, its controller loses 2 life.";
    }
}
