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
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.dynamicvalue.common.NumericSetToEffectValues;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 *
 * @author LevelX2
 */
public class Tamanoa extends CardImpl {

    public Tamanoa(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}{G}{W}");

        this.subtype.add("Spirit");
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Whenever a noncreature source you control deals damage, you gain that much life.
        Ability ability = new TamanoaDealsDamageTriggeredAbility(Zone.BATTLEFIELD, new GainLifeEffect(new NumericSetToEffectValues("that much", "damage")), false);

        this.addAbility(ability);
    }

    public Tamanoa(final Tamanoa card) {
        super(card);
    }

    @Override
    public Tamanoa copy() {
        return new Tamanoa(this);
    }
}

class TamanoaDealsDamageTriggeredAbility extends TriggeredAbilityImpl {

    public TamanoaDealsDamageTriggeredAbility(Zone zone, Effect effect, boolean optional) {
        super(zone, effect, optional);
    }

    public TamanoaDealsDamageTriggeredAbility(final TamanoaDealsDamageTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public TamanoaDealsDamageTriggeredAbility copy() {
        return new TamanoaDealsDamageTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_CREATURE
                || event.getType() == GameEvent.EventType.DAMAGED_PLAYER
                || event.getType() == GameEvent.EventType.DAMAGED_PLANESWALKER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        MageObject eventSourceObject = game.getObject(event.getSourceId());
        if (eventSourceObject != null && !eventSourceObject.isCreature()) {
            if (getControllerId().equals(game.getControllerId(event.getSourceId()))) {
                this.getEffects().forEach((effect) -> {
                    effect.setValue("damage", event.getAmount());
                });
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a noncreature source you control deals damage, " + super.getRule();
    }
}
