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
package mage.sets.magic2012;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.common.OnEventTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author North
 */
public class ArachnusWeb extends CardImpl<ArachnusWeb> {

    public ArachnusWeb(UUID ownerId) {
        super(ownerId, 163, "Arachnus Web", Rarity.COMMON, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}");
        this.expansionSetCode = "M12";
        this.subtype.add("Aura");

        this.color.setGreen(true);

        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Detriment));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ArachnusWebEffect1()));
        // At the beginning of the end step, if enchanted creature's power is 4 or greater, destroy Arachnus Web.
        this.addAbility(new OnEventTriggeredAbility(EventType.END_TURN_STEP_PRE, "beginning of the end step", true, new ArachnusWebEffect2()));
    }

    public ArachnusWeb(final ArachnusWeb card) {
        super(card);
    }

    @Override
    public ArachnusWeb copy() {
        return new ArachnusWeb(this);
    }
}

class ArachnusWebEffect1 extends ReplacementEffectImpl<ArachnusWebEffect1> {

    public ArachnusWebEffect1() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        staticText = "Enchanted creature can't attack or block, and its activated abilities can't be activated";
    }

    public ArachnusWebEffect1(final ArachnusWebEffect1 effect) {
        super(effect);
    }

    @Override
    public ArachnusWebEffect1 copy() {
        return new ArachnusWebEffect1(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        return true;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType() == EventType.DECLARE_ATTACKER || event.getType() == EventType.DECLARE_BLOCKER || event.getType() == EventType.ACTIVATE_ABILITY) {
            Permanent enchantment = game.getPermanent(source.getSourceId());
            if (enchantment != null && enchantment.getAttachedTo() != null) {
                if (event.getSourceId().equals(enchantment.getAttachedTo())) {
                    return true;
                }
            }
        }
        return false;
    }
}

class ArachnusWebEffect2 extends OneShotEffect<ArachnusWebEffect2> {

    public ArachnusWebEffect2() {
        super(Outcome.Benefit);
        staticText = "if enchanted creature's power is 4 or greater, destroy {this}";
    }

    public ArachnusWebEffect2(final ArachnusWebEffect2 effect) {
        super(effect);
    }

    @Override
    public ArachnusWebEffect2 copy() {
        return new ArachnusWebEffect2(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent enchantment = game.getPermanent(source.getSourceId());
        if (enchantment != null && enchantment.getAttachedTo() != null) {
            Permanent permanent = game.getPermanent(enchantment.getAttachedTo());
            if (permanent != null && permanent.getPower().getValue() >= 4) {
                enchantment.destroy(source.getId(), game, false);
                return true;
            }
        }
        return false;
    }
}
