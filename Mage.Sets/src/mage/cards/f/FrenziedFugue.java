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
package mage.cards.f;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.targetpointer.FixedTarget;

/**
 * @author LevelX2
 */
public class FrenziedFugue extends CardImpl {

    public FrenziedFugue(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{R}");

        this.subtype.add("Aura");

        // Enchant permanent
        TargetPermanent auraTarget = new TargetPermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.GainControl));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);
        // When Frenzied Fugue enters the battlefield or at the beginning of your upkeep, gain control of enchanted permanent until end of turn. Untap that permanent. It gains haste until end of turn.
        this.addAbility(new FrenziedFugueTriggeredAbility());
    }

    public FrenziedFugue(final FrenziedFugue card) {
        super(card);
    }

    @Override
    public FrenziedFugue copy() {
        return new FrenziedFugue(this);
    }
}

class FrenziedFugueTriggeredAbility extends TriggeredAbilityImpl {

    public FrenziedFugueTriggeredAbility() {
        super(Zone.BATTLEFIELD, null, false);
    }

    public FrenziedFugueTriggeredAbility(final FrenziedFugueTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public FrenziedFugueTriggeredAbility copy() {
        return new FrenziedFugueTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.ENTERS_THE_BATTLEFIELD || event.getType() == EventType.UPKEEP_STEP_PRE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        boolean result;
        if (event.getType()==EventType.ENTERS_THE_BATTLEFIELD) {
            result = event.getTargetId().equals(this.getSourceId());
        } else {
            result = event.getPlayerId().equals(this.getControllerId());
        }
        if (result) {
            Permanent enchantment = game.getPermanentOrLKIBattlefield(getSourceId());
            if (enchantment != null && enchantment.getAttachedTo() != null) {
                Effect effect = new GainControlTargetEffect(Duration.EndOfTurn, true);
                effect.setTargetPointer(new FixedTarget(enchantment.getAttachedTo(), game));
                getEffects().add(effect);
                effect = new UntapTargetEffect();
                effect.setTargetPointer(new FixedTarget(enchantment.getAttachedTo(), game));
                getEffects().add(effect);
                effect = new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.EndOfTurn);
                effect.setTargetPointer(new FixedTarget(enchantment.getAttachedTo(), game));
                getEffects().add(effect);
            } else {
                result = false;
            }
        }
        return result;
    }

    @Override
    public String getRule() {
        return "When {this} enters the battlefield or at the beginning of your upkeep, gain control of enchanted permanent until end of turn. Untap that permanent. It gains haste until end of turn.";
    }
}
